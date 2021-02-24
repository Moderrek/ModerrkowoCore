package pl.moderr.moderrkowo.core.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.ItemStackUtils;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.callback.Callback;
import pl.moderr.moderrkowo.database.data.Badge;
import pl.moderr.moderrkowo.database.data.BadgeData;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.UserNotLoaded;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class BadgeCommand implements CommandExecutor, Listener {

    private final String inventoryName = ColorUtils.color("&aOdznaki gracza &2");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length >= 1){
                try {
                    Player otherPlayer = Bukkit.getPlayer(args[0]);
                    if(otherPlayer != null){
                        User u = ModerrkowoDatabase.getInstance().getUserManager().getUser(otherPlayer.getUniqueId());
                        player.openInventory(getInventory(u));
                        player.sendMessage(ColorUtils.color("&aOtworzono znaki gracza " + otherPlayer.getName()));
                        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);
                        return true;
                    }else{
                        player.sendMessage(ColorUtils.color("&cPodany gracz jest offline"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                        return false;
                    }
                } catch (UserNotLoaded userNotLoaded) {
                    userNotLoaded.printStackTrace();
                    player.sendMessage(ColorUtils.color("&cWystąpił błąd podczas wczytywania odznak"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                    return false;
                }
            }else{
                try {
                    User u = ModerrkowoDatabase.getInstance().getUserManager().getUser(player.getUniqueId());
                    player.openInventory(getInventory(u));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);
                    player.sendMessage(ColorUtils.color("&aOtworzono twoje odznaki"));
                    return true;
                } catch (UserNotLoaded userNotLoaded) {
                    userNotLoaded.printStackTrace();
                    player.sendMessage(ColorUtils.color("&cWystąpił błąd podczas wczytywania odznak"));
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                    return false;
                }
            }
        }else{
            sender.sendMessage(ColorUtils.color("&cNie jesteś graczem!"));
        }
        return false;
    }


    public Inventory getInventory(User user){
        Inventory inventory = Bukkit.createInventory(null, 54, inventoryName + user.getName());
        for(Badge badges : user.getBadges().badgeList){
            ModerrkowoDatabase.getInstance().getBadge(badges.badgeId, new Callback<BadgeData>() {
                @Override
                public void onDone(BadgeData badgeData) {
                    ItemStack itemStack = ItemStackUtils.createGuiItem(badgeData.material, 1, ColorUtils.color("&a" + badgeData.name));
                    ItemMeta meta = itemStack.getItemMeta();
                    ArrayList<String> lore = new ArrayList<>();
                    for(String line : badgeData.lore.lore){
                        lore.add(ColorUtils.color("&8" + line));
                    }
                    lore.add(" ");
                    LocalDateTime datetime = LocalDateTime.ofInstant(badges.badgeGetTime, ZoneOffset.UTC);
                    lore.add(ColorUtils.color("&aOtrzymano &2" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(datetime)));
                    meta.setLore(lore);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    itemStack.setItemMeta(meta);
                    itemStack.addUnsafeEnchantment(Enchantment.DURABILITY,1);
                    inventory.addItem(itemStack);
                }

                @Override
                public void onFail(Exception e) {
                    inventory.addItem(ItemStackUtils.createGuiItem(Material.IRON_BARS, 1, ColorUtils.color("&cWystąpił błąd")));
                }
            });
        }
        return inventory;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        if (e.getClickedInventory() == null) {
            return;
        }
        if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
            if (e.getView().getTitle().contains(inventoryName)) {
                e.setCancelled(true);
                return;
            }
            return;
        }
        if(e.getView().getTitle().contains(inventoryName)){
            e.setCancelled(true);
        }
    }
}
