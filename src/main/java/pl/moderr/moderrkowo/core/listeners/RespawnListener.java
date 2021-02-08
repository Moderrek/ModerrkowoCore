package pl.moderr.moderrkowo.core.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.callback.CallbackExists;
import pl.moderr.moderrkowo.database.callback.CallbackHomeLoad;
import pl.moderr.moderrkowo.database.data.Home;

public class RespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        ModerrkowoDatabase.getInstance().existsHome(p.getUniqueId(), new CallbackExists() {
            @Override
            public void onDone(Boolean aBoolean) {
                if (aBoolean) {
                    ModerrkowoDatabase.getInstance().getHome(p.getUniqueId(), new CallbackHomeLoad() {
                        @Override
                        public void onDone(Home home) {
                            e.setRespawnLocation(home.ToLocation());
                            p.sendMessage(ColorUtils.color("&aOdrodziłeś się w miejscu domu"));
                        }

                        @Override
                        public void onFail(Exception e) {

                        }
                    });
                }
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }

}
