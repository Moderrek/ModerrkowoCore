package pl.moderr.moderrkowo.core.commands.admin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.ModerrkowoLog;

public class ASetSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Location loc = ((Player) sender).getLocation();
            Main.getInstance().getConfig().set("SpawnWorld", loc.getWorld().getName());
            Main.getInstance().getConfig().set("SpawnX", loc.getX());
            Main.getInstance().getConfig().set("SpawnY", loc.getY());
            Main.getInstance().getConfig().set("SpawnZ", loc.getZ());
            Main.getInstance().getConfig().set("SpawnYaw", (double) loc.getYaw());
            Main.getInstance().getConfig().set("SpawnPitch", (double) loc.getPitch());
            Main.getInstance().saveConfig();
            sender.sendMessage(ColorUtils.color("&aPomyślnie ustawiono nowy spawn!"));
            ModerrkowoLog.LogAdmin(ColorUtils.color("&6" + sender.getName() + " &7ustawił nowe miejsce spawna"));
        }
        return false;
    }
}
