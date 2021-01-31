package pl.moderr.moderrkowo.core.antylogout;

import org.bukkit.boss.BossBar;

import java.util.UUID;

public class AntyLogoutItem {

    public UUID uuid;
    public int seconds;
    public BossBar bossBar;

    public AntyLogoutItem(UUID uuid, int seconds, BossBar bossBar) {
        this.uuid = uuid;
        this.seconds = seconds;
        this.bossBar = bossBar;
    }

}
