package pl.moderr.moderrkowo.core.custom.events.drop;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ChatUtil;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.RandomUtils;
import pl.moderr.moderrkowo.core.utils.WeightedList;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.UserNotLoaded;

import java.util.Objects;
import java.util.Random;

public class DropEvent implements Listener {

    // TODO DROP
    private final WeightedList<DropItem> dropItemWeightedListOVERWORLD;

    public DropEvent() {
        this.dropItemWeightedListOVERWORLD = new WeightedList<>();
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.NETHERITE_SCRAP, 1), 1);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.COD, 2,64), 8);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.ENDER_PEARL, 1,5), 1);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.EMERALD, 1, 30), 15);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.COAL, 1, 64), 10);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.IRON_INGOT, 1, 40), 15);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.BAMBOO, 1, 3), 10);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.FIREWORK_ROCKET, 1, 7), 10);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.QUARTZ, 1, 20), 14);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.REDSTONE, 1, 20), 10);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.BLAZE_POWDER, 1, 30), 10);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.COCOA_BEANS, 1, 32), 2);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.LEATHER, 1, 20), 2);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.SLIME_BALL, 1, 16), 2);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.HONEY_BOTTLE, 1, 16), 2);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.EXPERIENCE_BOTTLE, 1, 20), 2);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.MUSIC_DISC_CAT), 1);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.MUSIC_DISC_CHIRP), 1);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.EXPERIENCE_BOTTLE, 20,64), 5);
        this.dropItemWeightedListOVERWORLD.put(new DropItem(Material.EXPERIENCE_BOTTLE, 1,20), 10);
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
        Random rnd = new Random();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
            int i;
            i = rnd.nextInt(100);
            if (i <= 60) {
                this.doDrop();
            }
        }, 0L, 72000L);
    }

    @EventHandler
    public void click(InventoryOpenEvent e){
        if(e.getInventory().getHolder() instanceof Chest){
            Chest chest = (Chest) e.getInventory().getHolder();
            if(chest.getCustomName() == null){
                return;
            }
            if(Objects.requireNonNull(chest.getCustomName()).equalsIgnoreCase(ColorUtils.color("&c&lZrzut zasobów"))){
                try {
                    User u = ModerrkowoDatabase.getInstance().getUserManager().getUser(e.getPlayer().getUniqueId());
                    int kwota = RandomUtils.getRandomInt(100,10000);
                    u.getBank().money += kwota;
                    e.getPlayer().sendMessage(ColorUtils.color("&9Drop &6> &a+ " + ChatUtil.getMoney(kwota)));
                    Bukkit.broadcastMessage(ColorUtils.color("&8[&e*&8] &7Zrzut zasobów został otworzony!"));
                    Objects.requireNonNull(Bukkit.getWorld("world")).spawnParticle(Particle.TOTEM,chest.getLocation().getBlockX(),chest.getLocation().getBlockY(),chest.getLocation().getBlockZ(),20,1,1,1,0.1);
                    chest.setCustomName(ColorUtils.color("&eOtworzony zrzut przez &2" + e.getPlayer().getName()));
                    Chest chestBlock = (Chest) chest.getBlock().getState();
                    chestBlock.setCustomName(ColorUtils.color("&eOtworzony zrzut przez &2" + e.getPlayer().getName()));
                    chestBlock.update();
                } catch (UserNotLoaded userNotLoaded) {
                    userNotLoaded.printStackTrace();
                }
            }
        }
    }

    public void doDrop() {
        final Location loc = RandomUtils.getRandom(Bukkit.getWorld("world"));
        try {
            new BukkitRunnable() {
                public void run() {
                    loc.getBlock().setType(Material.CHEST);
                    final Chest chest = (Chest)loc.getWorld().getBlockAt(loc).getState();
                    chest.setCustomName(ColorUtils.color("&c&lZrzut zasobów"));
                    chest.update();
                    final Random rnd = new Random();
                    for (int i = 0; i != chest.getInventory().getSize(); ++i) {
                        final int chance = rnd.nextInt(100);
                        if (chance <= 50) {
                            final DropItem item = DropEvent.this.dropItemWeightedListOVERWORLD.get(rnd);
                            ItemStack is;
                            if (item.isRandom) {
                                is = new ItemStack(item.mat, DropEvent.this.getRandomNumberInRange(item.min, item.max));
                            }
                            else {
                                is = new ItemStack(item.mat, item.count);
                            }
                            chest.getInventory().setItem(i, is);
                        }
                    }
                    Bukkit.broadcastMessage(ColorUtils.color("&8[&e*&8] &7Zrzut zasobów spadł na mapę! &8(&7x " + Math.floor(loc.getX()) + " z " + Math.floor(loc.getZ()) + "&8)"));
                    Objects.requireNonNull(Bukkit.getWorld("world")).strikeLightningEffect(Objects.requireNonNull(chest.getLocation()));
                }
            }.runTaskLater(Main.getInstance(), 1L);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getRandomNumberInRange(final int min, final int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        final Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}
