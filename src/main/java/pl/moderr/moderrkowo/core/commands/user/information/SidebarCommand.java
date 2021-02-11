package pl.moderr.moderrkowo.core.commands.user.information;

import com.destroystokyo.paper.Title;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.UserNotLoaded;

public class SidebarCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            try {
                User u = ModerrkowoDatabase.getInstance().getUserManager().getUser(p.getUniqueId());
                u.setSidebar(!u.getSidebar());
                if(u.getSidebar()){
                    p.sendTitle(new Title("", ColorUtils.color("&aWłączono sidebar")));
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);
                }else{
                    p.sendTitle(new Title("", ColorUtils.color("&cWyłączono sidebar")));
                    p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES,1,1);
                }
                return false;
            } catch (UserNotLoaded userNotLoaded) {
                userNotLoaded.printStackTrace();
                p.sendMessage(ColorUtils.color("&cNie udało się zmienić widoczności sidebara"));
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
            }
        }
        return false;
    }
}
