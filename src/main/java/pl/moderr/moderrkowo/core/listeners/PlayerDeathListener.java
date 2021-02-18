package pl.moderr.moderrkowo.core.listeners;

import com.destroystokyo.paper.Title;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.HexResolver;
import pl.moderr.moderrkowo.core.utils.ModerrkowoLog;

import java.util.Objects;


public class PlayerDeathListener implements Listener {

    @EventHandler
    public void death(PlayerDeathEvent e) {
        e.getEntity().spawnParticle(Particle.TOTEM, e.getEntity().getLocation().getX(), e.getEntity().getLocation().getY(), e.getEntity().getLocation().getZ(), 20, 1, 1, 1, 0.1f);
        e.getEntity().sendMessage(Main.getServerName() + " " + ColorUtils.color(String.format("&fUmarłeś na koordynatach X: %s Y: %s Z: %s", e.getEntity().getLocation().getBlockX(), e.getEntity().getLocation().getBlockY(), e.getEntity().getLocation().getBlockZ())));
        ModerrkowoLog.LogAdmin(e.getDeathMessage());
        if (e.getEntity().getKiller() != null) {
            Player p = e.getEntity();
            Player killer = e.getEntity().getKiller();

            assert killer != null;
            killer.sendTitle(new Title("☠", ColorUtils.color("&7Zabiłeś " + p.getName())));
            p.sendTitle(new Title("☠"));

            killer.playSound(killer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            p.playSound(p.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
            p.sendMessage(ColorUtils.color("&8[&c!&8] &7Zostałeś zabity przez &c" + killer.getName()));
            killer.sendMessage(ColorUtils.color("&8[&a!&8] &7Zabiłeś &6" + p.getName()));
        }
        if (Objects.requireNonNull(e.getDeathMessage()).contains("drowned")) {
            e.setDeathMessage(ColorUtils.color("&6" + e.getEntity().getName() + " &7utonął"));
            return;
        } else if (e.getDeathMessage().contains("blew up")) {
            e.setDeathMessage(ColorUtils.color("&6" + e.getEntity().getName() + " &7został wysadzony"));
            return;
        } else if (e.getDeathMessage().contains("was killed by")) {
            e.setDeathMessage(ColorUtils.color("&6" + e.getEntity().getName() + " &7został zabity"));
            return;
        } else if (e.getDeathMessage().contains("hit the ground too hard") || e.getDeathMessage().contains("fell from a high place")) {
            e.setDeathMessage(ColorUtils.color("&6" + e.getEntity().getName() + " &7umarł z wysokości"));
            return;
        } else if (e.getDeathMessage().contains("burned to death") || e.getDeathMessage().contains("went up in flames")) {
            e.setDeathMessage(ColorUtils.color("&6" + e.getEntity().getName() + " &7spalił sie"));
            return;
        } else if (e.getDeathMessage().contains("tried to swim in lava") || e.getDeathMessage().contains("tried to swim in lava to escape")) {
            e.setDeathMessage(ColorUtils.color("&6" + e.getEntity().getName() + " &7poszedł się kąpać w lawie peperonczki ^^"));
            return;
        } else if (e.getDeathMessage().contains("was burnt")) {
            e.setDeathMessage(ColorUtils.color("&6" + e.getEntity().getName() + " &7spalił się"));
        } else if (e.getDeathMessage().contains("was fireballed")) {
            e.setDeathMessage(ColorUtils.color("&6" + e.getEntity().getName() + " &7został rzucony kulą ognia"));
        } else if (e.getDeathMessage().contains("was slain by")) {
            e.setDeathMessage(ColorUtils.color("&6" + e.getEntity().getName() + " &7został zabity"));
            return;
        } else if (e.getDeathMessage().contains("was shot by")) {
            e.setDeathMessage(ColorUtils.color("&6" + e.getEntity().getName() + " &7został rozstrzelany"));
            return;
        }
        e.setDeathMessage(ColorUtils.color(HexResolver.parseHexString("&6{playername} &7umarł").replace("{playername}", e.getEntity().getName())));
    }

}
