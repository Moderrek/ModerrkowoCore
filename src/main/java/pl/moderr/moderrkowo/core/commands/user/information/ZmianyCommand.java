package pl.moderr.moderrkowo.core.commands.user.information;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.utils.ColorUtils;

public class ZmianyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(ColorUtils.color("&cOstatnie zmiany"));
        sender.sendMessage(ColorUtils.color("&eNowe questy, do odkrycia"));
        sender.sendMessage(ColorUtils.color("&eNaprawione działki"));
        sender.sendMessage(ColorUtils.color("&eNaprawione questy"));
        sender.sendMessage(ColorUtils.color("&eNowe typy questów"));
        sender.sendMessage(ColorUtils.color("&eDostarczanie itemków do questa"));
        sender.sendMessage(ColorUtils.color("&eMożliwość anulowania questa"));
        sender.sendMessage(ColorUtils.color("&eJak roślina wyrośnie dostaje się punkty exp"));
        sender.sendMessage(ColorUtils.color("&ePoprawiony rynek"));
        sender.sendMessage(ColorUtils.color("&eNowa komenda /topka"));
        sender.sendMessage(ColorUtils.color("&eZmieniony drop z łowienia"));
        sender.sendMessage(ColorUtils.color("&eZmieniono TNT"));
        sender.sendMessage(ColorUtils.color("&eZmieniono dropy"));
        sender.sendMessage(ColorUtils.color("&eZmieniono MOTD"));
        sender.sendMessage(ColorUtils.color("&eDodano kolory na nametagu gracza"));
        sender.sendMessage(ColorUtils.color("&eZmieniono regulamin"));
        return false;
    }
}
