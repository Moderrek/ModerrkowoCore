package pl.moderr.moderrkowo.core.utils;

import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.UserNotLoaded;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class ChatUtil {

    public static void clearChat(Player player) {
        for (int i = 0; i < 100; i++) {
            player.sendMessage(" ");
        }
    }

    public static String materialName(Material material) {
        String materialName = material.toString();
        materialName = materialName.replaceAll("_", " ");
        materialName = materialName.toLowerCase();
        return WordUtils.capitalizeFully(materialName);
    }
    public static String materialName(EntityType material) {
        String materialName = material.toString();
        materialName = materialName.replaceAll("_", " ");
        materialName = materialName.toLowerCase();
        return WordUtils.capitalizeFully(materialName);
    }

    /**
     * @param money kwota pieniędzy
     * @return 1 = 1 zł ; money = money zł; 1235 = 1,235 zł
     */
    public static String getMoney(int money) {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pl-PL"));
        nf.setMaximumFractionDigits(2);
        return nf.format(money) + " zł";
    }

    public static String getChatName(Player player) {
        StringBuilder prefix = new StringBuilder();
        try {
            User u = ModerrkowoDatabase.getInstance().getUserManager().getUser(player.getUniqueId());
            if (u.getStuffRank().equals("MODERATOR")) {
                prefix.append(ColorUtils.color("&aMOD "));
            }
            if (u.getStuffRank().equals("ADMIN")) {
                prefix.append(ColorUtils.color("&cADM "));
            }
            if (u.getRank().equals("DIAMENT")) {
                prefix.append(ColorUtils.color("&b&lDIAMENT"));
            }
        } catch (UserNotLoaded userNotLoaded) {
            userNotLoaded.printStackTrace();
        }
        if (player.isOp()) {
            return prefix.toString() + ChatColor.of(new Color(128, 95, 217)) + player.getName();
        } else {
            return prefix.toString() + (ChatColor.of(new Color(105, 95, 217)) + player.getName());
        }
    }

    public static String getPrefix(Player player) {
        StringBuilder prefix = new StringBuilder();
        try {
            User u = ModerrkowoDatabase.getInstance().getUserManager().getUser(player.getUniqueId());
            if (u.getStuffRank().equals("MODERATOR")) {
                prefix.append(ColorUtils.color("&aMOD "));
            }
            if (u.getStuffRank().equals("ADMIN")) {
                prefix.append(ColorUtils.color("&cADM "));
            }
            if (u.getRank().equals("DIAMENT")) {
                prefix.append(ColorUtils.color("&b&lDIAMENT"));
            }
        } catch (UserNotLoaded userNotLoaded) {
            userNotLoaded.printStackTrace();
        }
        if (player.isOp()) {
            return prefix.toString() + ChatColor.of(new Color(128, 95, 217)) + player.getName();
        } else {
            return prefix.toString() + (ChatColor.of(new Color(105, 95, 217))) + player.getName();
        }
    }

}
