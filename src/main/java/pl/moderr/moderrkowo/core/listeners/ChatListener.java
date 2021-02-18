package pl.moderr.moderrkowo.core.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.commands.admin.ChatCommand;
import pl.moderr.moderrkowo.core.utils.ColorUtils;

public class ChatListener implements Listener {

    @EventHandler
    public void chat(@NotNull AsyncPlayerChatEvent e) {
        if (!e.getPlayer().isOp() && !ChatCommand.canChat) {
            e.getPlayer().sendMessage(ColorUtils.color("&cChat jest wyłączony!"));
            e.setCancelled(true);
            return;
        }
        e.setMessage(e.getMessage().replace("%", "%%"));
        if (e.getPlayer().hasPermission("moderr.admin")) {
            e.setMessage(ColorUtils.color(e.getMessage()));
            e.setFormat(ColorUtils.color("&6" + e.getPlayer().getName() + ": &e") + e.getMessage());
        }else{
            e.setFormat(ColorUtils.color("&7" + e.getPlayer().getName() + ": &f") + e.getMessage());
        }
    }

}
