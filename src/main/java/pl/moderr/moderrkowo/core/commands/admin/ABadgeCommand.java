package pl.moderr.moderrkowo.core.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.data.Badge;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.UserNotLoaded;

import java.time.Instant;

public class ABadgeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 2){
                Player otherPlayer = Bukkit.getPlayer(args[0]);
                try {
                    User otherUser = ModerrkowoDatabase.getInstance().getUserManager().getUser(otherPlayer.getUniqueId());
                    otherUser.getBadges().badgeList.add(new Badge(Integer.parseInt(args[1]), Instant.now()));
                } catch (UserNotLoaded userNotLoaded) {
                    userNotLoaded.printStackTrace();
                }

            }
        }
        return false;
    }
}
