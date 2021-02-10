package pl.moderr.moderrkowo.core.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.data.PlayerVillagerData;
import pl.moderr.moderrkowo.database.data.User;

public class UnactiveAllQuest implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        for(User u : ModerrkowoDatabase.getInstance().getUserManager().getAllUsers()){
            for(PlayerVillagerData villagerData : u.getVillagersData().getVillagersData().values()){
                if(villagerData.isActiveQuest()){
                    villagerData.setActiveQuest(false);
                    sender.sendMessage("UNACTIVE QUEST >> " + u.getName() + ": " + villagerData.getVillagerId());
                }
            }
        }
        return false;
    }
}
