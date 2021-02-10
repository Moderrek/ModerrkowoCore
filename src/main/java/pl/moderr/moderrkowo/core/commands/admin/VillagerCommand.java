package pl.moderr.moderrkowo.core.commands.admin;

import com.destroystokyo.paper.Title;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.Logger;
import pl.moderr.moderrkowo.core.utils.ModerrkowoLog;
import pl.moderr.moderrkowo.core.utils.RandomUtils;
import pl.moderr.moderrkowo.core.villager.data.VillagerData;

import java.util.ArrayList;
import java.util.List;

public class VillagerCommand implements CommandExecutor, TabCompleter, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            Villager e = p.getWorld().spawn(p.getLocation(), Villager.class);
            e.setInvulnerable(true);
            e.setAI(false);
            e.setCustomNameVisible(true);
            e.setCustomName(ColorUtils.color(Logger.getMessage(args, 0, true)));
            e.setSilent(true);
            e.setRemoveWhenFarAway(false);
            ModerrkowoLog.LogAdmin(ColorUtils.color("&6" + p.getName() + " &7postawił nowego Villager'a &8(&f" + Logger.getMessage(args, 0, true) + "&8)"));
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("&aLosowy teleport");
        list.add("&c&lQ &7???");
        for(VillagerData data : Main.getInstance().villagerManager.shops.values()){
            list.add(data.getCommandSpawnName());
        }
        return list;
    }

    @EventHandler
    public void click(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Entity entity = e.getRightClicked();

        if (entity.getType() == EntityType.VILLAGER) {
            if (entity.isCustomNameVisible()) {
                if (entity.getCustomName() == null) {
                    return;
                }


                if (entity.getCustomName().equals(ColorUtils.color("&aLosowy teleport"))) {
                    p.teleport(RandomUtils.getRandom(p.getWorld()));
                    p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
                    p.sendMessage(ColorUtils.color("&aUżyłeś losowego teleportu u podróżnika"));
                    p.sendTitle(new Title("", "" + ChatColor.GREEN + "x: " + p.getLocation().getBlockX() + " z:" + p.getLocation().getBlockZ()));
                }
            }
        }
    }

}
