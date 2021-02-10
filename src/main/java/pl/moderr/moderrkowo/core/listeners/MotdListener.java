package pl.moderr.moderrkowo.core.listeners;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.moderr.moderrkowo.core.utils.ColorUtils;

public class MotdListener implements Listener {

    final int dlugoscMotd = 59;

    @EventHandler
    public void ping(PaperServerListPingEvent e) {
        e.setMaxPlayers(e.getNumPlayers() + 1);
        e.setVersion("1.16.x");
        String line1 = ColorUtils.color("&e⚔ &6Moderrkowo &e⚔ &eNajlepszy survival 1.16.x");
        String line2 = ColorUtils.color("&9> &6Questy wkroczyły na serwer! &9<");
        e.setMotd(centerText(line1) + "\n" + centerText(line2));
    }

    String centerText(String text) {
        StringBuilder builder = new StringBuilder(text);
        char space = ' ';
        int distance = (dlugoscMotd - text.length()) / 2;
        for (int i = 0; i < distance; ++i) {
            builder.insert(0, space);
            builder.append(space);
        }
        return builder.toString();
    }

}
