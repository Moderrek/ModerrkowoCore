package pl.moderr.moderrkowo.core.custom.antylogout;

import org.bukkit.boss.BossBar;
import org.jetbrains.annotations.Contract;

public class AntyLogoutItem {

    public int seconds;
    public final BossBar bossBar;

    @Contract(pure = true)
    public AntyLogoutItem(int seconds, BossBar bossBar) {
        this.seconds = seconds;
        this.bossBar = bossBar;
    }

}
