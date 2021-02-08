package pl.moderr.moderrkowo.core.commands.player.teleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ColorUtils;

import java.util.Objects;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (Main.getInstance().instanceAntyLogout.inFight(p.getUniqueId())) {
                p.sendMessage(ColorUtils.color("&cNie możesz uciec podczas walki!"));
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                return false;
            }
            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
            p.teleport(new Location(Bukkit.getWorld(Objects.requireNonNull(Main.getInstance().getConfig().getString("SpawnWorld"))), Main.getInstance().getConfig().getDouble("SpawnX"), Main.getInstance().getConfig().getDouble("SpawnY"), Main.getInstance().getConfig().getDouble("SpawnZ"), (float) Main.getInstance().getConfig().getDouble("SpawnYaw"), (float) Main.getInstance().getConfig().getDouble("SpawnPitch")));
            p.sendMessage(ColorUtils.color("&aWitaj na spawnie!"));
            p.sendMessage(ColorUtils.color("&aPrzejdź się po wiosce główniej w celu poszukiwania nowych zadań/celów jak i ciekawych przedmiotów"));
        }
        return false;
    }
}
