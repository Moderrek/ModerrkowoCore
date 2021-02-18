package pl.moderr.moderrkowo.core.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.UserNotLoaded;

import java.util.HashMap;
import java.util.Objects;

public class QuestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args[0].equalsIgnoreCase("dodajlevel")){
                try {
                    Player p2 = Bukkit.getPlayer(args[1]);
                    User u = ModerrkowoDatabase.getInstance().getUserManager().getUser(Objects.requireNonNull(p2).getUniqueId());
                    u.getVillagersData().getVillagersData().get(args[2]).setQuestIndex(u.getVillagersData().getVillagersData().get(args[2]).getQuestIndex()+1);
                } catch (UserNotLoaded userNotLoaded) {
                    userNotLoaded.printStackTrace();
                }
            }
            if(args[0].equalsIgnoreCase("odejmijlevel")){
                try {
                    Player p2 = Bukkit.getPlayer(args[1]);
                    User u = ModerrkowoDatabase.getInstance().getUserManager().getUser(Objects.requireNonNull(p2).getUniqueId());
                    u.getVillagersData().getVillagersData().get(args[2]).setQuestIndex(u.getVillagersData().getVillagersData().get(args[2]).getQuestIndex()-1);
                } catch (UserNotLoaded userNotLoaded) {
                    userNotLoaded.printStackTrace();
                }
            }
            if(args[0].equalsIgnoreCase("reset")){
                try {
                    Player p2 = Bukkit.getPlayer(args[1]);
                    User u = ModerrkowoDatabase.getInstance().getUserManager().getUser(Objects.requireNonNull(p2).getUniqueId());
                    u.getVillagersData().setVillagersData(new HashMap<>());
                } catch (UserNotLoaded userNotLoaded) {
                    userNotLoaded.printStackTrace();
                }
            }
        }
        return false;
    }
}
