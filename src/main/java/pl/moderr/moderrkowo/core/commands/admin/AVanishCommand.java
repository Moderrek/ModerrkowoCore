package pl.moderr.moderrkowo.core.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.listeners.JoinQuitListener;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.ModerrkowoLog;

import java.util.ArrayList;
import java.util.UUID;

public class AVanishCommand implements CommandExecutor {

    public static ArrayList<UUID> hidden = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (hidden.contains(p.getUniqueId())) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (players.isOp()) {
                        continue;
                    }
                    players.showPlayer(Main.getInstance(), p);
                }
                hidden.remove(p.getUniqueId());
                p.sendMessage(ColorUtils.color("&cZostałeś odkryty"));
                Bukkit.broadcastMessage(JoinQuitListener.getJoinMessage(p));
                ModerrkowoLog.LogAdmin(p.getName() + " został odkryty");
            } else {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (players.isOp()) {
                        continue;
                    }
                    players.hidePlayer(Main.getInstance(), p);
                }
                hidden.add(p.getUniqueId());
                p.sendMessage(ColorUtils.color("&aZostałeś ukryty"));
                Bukkit.broadcastMessage(JoinQuitListener.getQuitMessage(p));
                ModerrkowoLog.LogAdmin(p.getName() + " został ukryty");
            }
        }
        return false;
    }
}