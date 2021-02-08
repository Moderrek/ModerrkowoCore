package pl.moderr.moderrkowo.core.antylogout;

import org.bukkit.boss.BossBar;
import org.jetbrains.annotations.Contract;

import java.util.UUID;

public class AntyLogoutItem {

    public final UUID uuid;
    public int seconds;
    public final BossBar bossBar;

    @Contract(pure = true)
    public AntyLogoutItem(UUID uuid, int seconds, BossBar bossBar) {
        this.uuid = uuid;
        this.seconds = seconds;
        this.bossBar = bossBar;
    }

}
