package pl.moderr.moderrkowo.core.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ColorUtils;

public class ALOGI_Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender.isOp()) {
            if (sender instanceof Player) {
                if (Main.getInstance().instanceDatabaseListener.player_Listeners.contains(((Player) sender).getUniqueId())) {
                    Main.getInstance().instanceDatabaseListener.player_Listeners.remove(((Player) sender).getUniqueId());
                    sender.sendMessage(ColorUtils.color("&cPrzestano nasłuchiwać logi!"));
                } else {
                    Main.getInstance().instanceDatabaseListener.player_Listeners.add(((Player) sender).getUniqueId());
                    sender.sendMessage(ColorUtils.color("&aZaczęto nasłuchiwać logi!"));
                }
            } else {
                sender.sendMessage(ColorUtils.color("&cNie możesz tej komendy wywołać w konsoli!"));
            }
        } else {
            sender.sendMessage(ColorUtils.color("&cNie masz permisji!"));
        }
        return false;
    }
}
