package pl.moderr.moderrkowo.core.commands.user.teleportation;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ColorUtils;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            p.sendMessage(ColorUtils.color("&eTeleport na spawna znajduje się na"));
            Location teleport = Main.getInstance().spawn.getSpawnTeleportLocation();
            p.sendMessage(ColorUtils.color("&ex: " + teleport.getBlockX() + " y: " + teleport.getBlockY() + " z: " + teleport.getBlockZ()));
            p.sendMessage(ColorUtils.color("&eGdy już tam będziesz zniszcz blok"));
        }
        return false;
    }
}
