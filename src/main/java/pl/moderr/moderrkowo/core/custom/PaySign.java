package pl.moderr.moderrkowo.core.custom;


import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ChatUtil;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.callback.CallbackEmpty;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.UserNotLoaded;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Supplier;

public class PaySign implements Listener {


    public String paySignPrefix = "[Sprzedaz]";
    public String paySignPrefixColor = ColorUtils.color("&8[&aSprzedaz&8]");
    // [Sprzedaz]
    // MODERR
    // 10

    ArrayList<Block> blocks = new ArrayList<>();

    @EventHandler
    public void buy(PlayerInteractEvent e){
        Player p = e.getPlayer();


        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(Objects.requireNonNull(e.getClickedBlock()).getState() instanceof Sign){
                Player player = e.getPlayer();
                if (player.isSneaking()) {
                    return;
                }
                Block clickedBlock = e.getClickedBlock();
                if (clickedBlock == null) {
                    return;
                }
                if (!Tag.SIGNS.isTagged(clickedBlock.getType())) {
                    return;
                }
                User u;
                try {
                    u = ModerrkowoDatabase.getInstance().getUserManager().getUser(p.getUniqueId());
                } catch (UserNotLoaded userNotLoaded) {
                    userNotLoaded.printStackTrace();
                    p.sendMessage(ColorUtils.color("&cWystąpił błąd"));
                    return;
                }

                Sign sign = (Sign) e.getClickedBlock().getState();
                String line0 = sign.getLine(0);
                String line1 = sign.getLine(1);
                String line2 = sign.getLine(2);
                String line3 = sign.getLine(3);
                if(sign.getLine(0).equalsIgnoreCase(paySignPrefixColor)){

                    String nickname = sign.getLine(1);
                    nickname = nickname.replace("§e", "");
                    String linijkaZcena = sign.getLine(2);
                    linijkaZcena = linijkaZcena.replace("§6", "");
                    linijkaZcena = linijkaZcena.replace("§7", "");
                    linijkaZcena = linijkaZcena.replace("-", "");
                    linijkaZcena = linijkaZcena.replace("§e", "");
                    linijkaZcena = linijkaZcena.replace("zł", "");
                    linijkaZcena = linijkaZcena.replace(" ", "");
                    linijkaZcena = linijkaZcena.replace(" ", "");
                    int kwota = Integer.parseInt(linijkaZcena);
                    if(u.getBank().money >= kwota){
                        // dodanie graczu
                        if(Bukkit.getPlayer(nickname) != null){
                            User u2;
                            try {
                                u2 = ModerrkowoDatabase.getInstance().getUserManager().getUser(Objects.requireNonNull(Bukkit.getPlayer(nickname)).getUniqueId());
                                u2.getBank().money += kwota;
                                u.getBank().money -= kwota;
                                u.getPlayer().sendMessage(ColorUtils.color("&9PaySign &6> &c- " + ChatUtil.getMoney(kwota)));
                            } catch (UserNotLoaded userNotLoaded) {
                                userNotLoaded.printStackTrace();
                            }
                        }else{
                            ModerrkowoDatabase.getInstance().addBankByName(nickname, kwota, new CallbackEmpty() {
                                @Override
                                public void onDone() {
                                    u.getBank().money -= kwota;
                                    u.getPlayer().sendMessage(ColorUtils.color("&9PaySign &6> &c- " + ChatUtil.getMoney(kwota)));
                                }

                                @Override
                                public void onFail(Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES,1,1);

                        Player whoGet = Bukkit.getPlayer(nickname);
                        if(whoGet != null){
                            whoGet.playSound(whoGet.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1,1);
                            TextComponent txt = new TextComponent();
                            txt.setText("§9PaySign §6> §6" + p.getName() + " §7zakupił tabliczkę od Ciebie §8[§a+" + ChatUtil.getMoney(kwota) + "§8]");
                            txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                    new ComponentBuilder("§fX: " + sign.getBlock().getLocation().getX() + " Y: " + sign.getBlock().getLocation().getY() + " Z: " + sign.getBlock().getLocation().getZ()).create()));
                            whoGet.spigot().sendMessage(txt);
                        }

                    }else{
                        p.sendMessage(ColorUtils.color("&cNie posiadasz tyle pieniędzy w porfelu!"));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                        return;
                    }

                    Block base = getBaseBlock(sign);
                    BlockFace FLOOR_FACING = BlockFace.NORTH;

                    Supplier<Switch> BUTTON_FACTORY = () -> (Switch) Material.OAK_BUTTON.createBlockData();
                    BlockFace facing = getFacing(sign);
                    Switch button = BUTTON_FACTORY.get();

                    button.setFace(facing.equals(BlockFace.UP) ? Switch.Face.FLOOR : Switch.Face.WALL);
                    button.setFacing(facing.equals(BlockFace.UP) ? FLOOR_FACING : facing);
                    button.setPowered(true);

                    e.getClickedBlock().setBlockData(button);
                    e.getClickedBlock().getState().update(true, false);
                    updateBaseBlockNeighbors(e.getClickedBlock());
                    updateBaseBlockNeighbors(base);
                    base.getState().update();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
                        if(e.getClickedBlock().getType() == Material.AIR){
                            return;
                        }
                        e.getClickedBlock().setType(Material.AIR);
                        e.getClickedBlock().setType(sign.getType());
                        e.getClickedBlock().setBlockData(sign.getBlockData());
                        Sign newSign = (Sign) e.getClickedBlock().getState();
                        newSign.setLine(0, line0);
                        newSign.setLine(1, line1);
                        newSign.setLine(2, line2);
                        newSign.setLine(3, line3);
                        newSign.update(true);
                    }, 30);
                    p.sendMessage(ColorUtils.color("&aPomyślnie zapłacono!"));
                }
            }
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event){
        Player p = event.getPlayer();
        if(Objects.requireNonNull(event.getLine(0)).equalsIgnoreCase(paySignPrefix)){
            event.setLine(0, paySignPrefixColor);
            if(Objects.requireNonNull(event.getLine(1)).equalsIgnoreCase(p.getName()) || p.isOp()){
                if(!p.isOp()){
                    event.setLine(1, ColorUtils.color("&e" + p.getName()));
                }else{
                    event.setLine(1, ColorUtils.color("&e" + event.getLine(1)));
                }
                if(event.getLine(2) == null || Objects.requireNonNull(event.getLine(2)).equalsIgnoreCase("")){
                    p.sendMessage(ColorUtils.color("&cPodaj kwotę!"));
                    event.setCancelled(true);
                }else{
                    int kwota = 0;
                    try {
                        kwota = Integer.parseInt(Objects.requireNonNull(event.getLine(2)));
                    } catch (NumberFormatException e) {
                        p.sendMessage(ColorUtils.color("&cTabliczka nie zawiera kwoty!"));
                        event.setCancelled(true);
                        return;
                    }
                    kwota = (int) clamp(kwota, 0,10000);
                    event.setLine(2, ColorUtils.color("&6" + kwota + " zł"));
                    p.sendMessage(ColorUtils.color("&ePomyślnie ustawiono paysign"));
                }
            }else{
                p.sendMessage(ColorUtils.color("&cTabliczka nie posiada twojego nicku!"));
                event.setCancelled(true);
            }
        }
    }


    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public BlockFace getFacing(Sign sign) {
        BlockData blockData = sign.getBlock().getBlockData();
        Material material = blockData.getMaterial();

        if (Tag.STANDING_SIGNS.isTagged(material)) {
            return BlockFace.DOWN;
        } else if (Tag.WALL_SIGNS.isTagged(material) && blockData instanceof Directional) {
            return ((Directional) blockData).getFacing();
        } else {
            throw new IllegalStateException("Invalid block material: " + material);
        }
    }

    private void updateBaseBlockNeighbors(Block block) {
        BlockData realBlockData = block.getBlockData();
        Material material = realBlockData.getMaterial().equals(Material.BARRIER)
                ? Material.STONE : Material.BARRIER;

        // Simulate block change to call World.applyPhysics on the base block.
        block.setBlockData(material.createBlockData(), false);
        block.setBlockData(realBlockData, true);
    }

    public Block getBaseBlock(Sign sign) {
        return sign.getBlock().getRelative(this.getFacing(sign).getOppositeFace());
    }



    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void cancelPhysics(BlockPhysicsEvent event) {
        if (blocks.contains(event.getBlock())) {
            event.setCancelled(true);
        }
    }


}
