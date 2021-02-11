package pl.moderr.moderrkowo.core.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.utils.ColorUtils;

public class TopkaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            p.openInventory(getInventory());
            p.sendMessage(ColorUtils.color("&cWystąpił problem podczas wczytania topki"));
        }
        return false;
    }

    final String invName = ColorUtils.color("&6Ranking graczy");
    public Inventory getInventory(){
        Inventory inv = Bukkit.createInventory(null, 9, invName);
        return inv;
    }

}