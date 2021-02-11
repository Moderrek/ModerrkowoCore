package pl.moderr.moderrkowo.core.listeners;

import net.agentlv.namemanager.api.NameManagerAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.moderr.moderrkowo.core.utils.ChatUtil;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.ModerrkowoLog;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.events.DatabaseLog;
import pl.moderr.moderrkowo.database.events.ModerrDatabaseListener;

import java.util.ArrayList;
import java.util.UUID;

public class DatabaseListener implements ModerrDatabaseListener {

    public final ArrayList<UUID> player_Listeners = new ArrayList<>();

    @Override
    public void onLoadUser(User user) {
        if (user.getPlayer() != null) {
            user.getPlayer().setPlayerListName(ChatUtil.getChatName(user.getPlayer()));
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(user.getUUID());
            String prefix = ChatUtil.getOnlyPrefix(user.getPlayer()) + ColorUtils.color("&e");
            if(offlinePlayer == null){
                System.out.println(user.getName() + " >> OfflinePlayer is null");
            }else{
                NameManagerAPI.setNametag(offlinePlayer, prefix, "");
            }
        }
        ModerrkowoLog.LogAdmin("Załadowano gracza pomyślnie " + user.getName());
    }

    @Override
    public void onLog(DatabaseLog log) {

    }

    @Override
    public void onSaveUser(User user) {
        Player p = user.getPlayer();
        if (p != null) {
            p.sendMessage(ColorUtils.color("&aZapisano dane"));
        }
        ModerrkowoLog.LogAdmin("Zapisano gracza pomyślnie " + user.getName());
    }

    @Override
    public void onRegisterUser(User user) {
        if (user.getPlayer() != null) {
            user.getPlayer().getInventory().addItem(new ItemStack(Material.WOODEN_AXE));
            user.getPlayer().getInventory().addItem(new ItemStack(Material.BREAD, 16));
            Bukkit.broadcastMessage(ColorUtils.color("&9> &7Gracz &6" + user.getName() + " &7dołączył po raz pierwszy!"));
        }
    }
}
