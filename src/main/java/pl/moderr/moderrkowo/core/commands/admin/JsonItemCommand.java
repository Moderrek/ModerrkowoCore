package pl.moderr.moderrkowo.core.commands.admin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.ItemStackUtils;
import pl.moderr.moderrkowo.database.JsonItemStack;

public class JsonItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            String value = JsonItemStack.toJson(player.getInventory().getItemInMainHand());
            TextComponent textComponent = new TextComponent(value);
            textComponent.setColor(ChatColor.GRAY);
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, value));
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(ColorUtils.color("&aSkopiuj do schowka"))));
            ItemStack sample = ItemStackUtils.createGuiItem(Material.EMERALD,1, ColorUtils.color("&aNajbogatszy gracz I"), ColorUtils.color("&8Ten gracz miał najwięcej zł w portfelu na całej edycji I"), ColorUtils.color("1"), ColorUtils.color("2"));
            player.getInventory().addItem(sample);
            player.spigot().sendMessage(textComponent);
        }
        return false;
    }
}
