package pl.moderr.moderrkowo.core.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.moderr.moderrkowo.core.utils.ChatUtil;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.ModerrkowoLog;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.callback.CallbackEmpty;
import pl.moderr.moderrkowo.database.callback.CallbackExists;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.UserNotLoaded;
import scala.Int;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ABankCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length > 2){
                if(args[0].equalsIgnoreCase("dodaj")){
                    int kwota;
                    try{
                        kwota = Integer.parseInt(args[2]);
                    }catch (Exception e){
                        p.sendMessage(ColorUtils.color("&cPodano niepoprawną kwote!"));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                        return false;
                    }
                    if(kwota < 1){
                        p.sendMessage(ColorUtils.color("&cPodano niepoprawną kwote!"));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                        return false;
                    }
                    if(Bukkit.getPlayer(args[1]) != null){
                        User u;
                        try {
                            u = ModerrkowoDatabase.getInstance().getUserManager().getUser(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId());
                            u.getBank().money += kwota;
                            ModerrkowoLog.LogAdmin("&6" + p.getName() + " &7dodał &6" + ChatUtil.getMoney(kwota) + " &7dla gracza &6" + args[1]);
                        } catch (UserNotLoaded userNotLoaded) {
                            p.sendMessage(ColorUtils.color("&cWystąpił błąd"));
                            userNotLoaded.printStackTrace();
                        }
                        return false;
                    }
                    ModerrkowoDatabase.getInstance().existsUser(args[1], new CallbackExists() {
                        @Override
                        public void onDone(Boolean aBoolean) {
                            if(aBoolean){
                                ModerrkowoDatabase.getInstance().addBankByName(args[1], kwota, new CallbackEmpty() {
                                    @Override
                                    public void onDone() {
                                         p.sendMessage(ColorUtils.color("&aPomyślnie dodano " + ChatUtil.getMoney(kwota)));
                                        ModerrkowoLog.LogAdmin("&6" + p.getName() + " &7dodał &6" + ChatUtil.getMoney(kwota) + " &7dla gracza &6" + args[1]);
                                    }

                                    @Override
                                    public void onFail(Exception e) {
                                        p.sendMessage(ColorUtils.color("&cWystąpił błąd"));
                                        e.printStackTrace();
                                    }
                                });
                            }else{
                                p.sendMessage(ColorUtils.color("&cPodany gracz nie istnieje"));
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                            }
                        }

                        @Override
                        public void onFail(Exception e) {
                            p.sendMessage(ColorUtils.color("&cWystąpił błąd"));
                            e.printStackTrace();
                        }
                    });
                    return false;
                }
                if(args[0].equalsIgnoreCase("odejmij")){
                    int kwota;
                    try{
                        kwota = Integer.parseInt(args[2]);
                    }catch (Exception e){
                        p.sendMessage(ColorUtils.color("&cPodano niepoprawną kwote!"));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                        return false;
                    }
                    if(kwota < 1){
                        p.sendMessage(ColorUtils.color("&cPodano niepoprawną kwote!"));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                        return false;
                    }
                    if(Bukkit.getPlayer(args[1]) != null){
                        User u;
                        try {
                            u = ModerrkowoDatabase.getInstance().getUserManager().getUser(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId());
                            u.getBank().money -= kwota;
                            ModerrkowoLog.LogAdmin("&6" + p.getName() + " &7odejmij &6" + ChatUtil.getMoney(kwota) + " &7dla gracza &6" + args[1]);
                        } catch (UserNotLoaded userNotLoaded) {
                            p.sendMessage(ColorUtils.color("&cWystąpił błąd"));
                            userNotLoaded.printStackTrace();
                        }
                        return false;
                    }
                    ModerrkowoDatabase.getInstance().existsUser(args[1], new CallbackExists() {
                        @Override
                        public void onDone(Boolean aBoolean) {
                            if(aBoolean){
                                ModerrkowoDatabase.getInstance().substractBankByName(args[1], kwota, new CallbackEmpty() {
                                    @Override
                                    public void onDone() {
                                        p.sendMessage(ColorUtils.color("&aPomyślnie odjęto " + ChatUtil.getMoney(kwota)));
                                        ModerrkowoLog.LogAdmin("&6" + p.getName() + " &7odjął &6" + ChatUtil.getMoney(kwota) + " &7dla gracza &6" + args[1]);
                                    }

                                    @Override
                                    public void onFail(Exception e) {
                                        p.sendMessage(ColorUtils.color("&cWystąpił błąd"));
                                        e.printStackTrace();
                                    }
                                });
                            }else{
                                p.sendMessage(ColorUtils.color("&cPodany gracz nie istnieje"));
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                            }
                        }

                        @Override
                        public void onFail(Exception e) {
                            p.sendMessage(ColorUtils.color("&cWystąpił błąd"));
                            e.printStackTrace();
                        }
                    });
                    return false;
                }
                if(args[0].equalsIgnoreCase("ustaw")){
                    int kwota;
                    try{
                        kwota = Integer.parseInt(args[2]);
                    }catch (Exception e){
                        p.sendMessage(ColorUtils.color("&cPodano niepoprawną kwote!"));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                        return false;
                    }
                    if(kwota < 1){
                        p.sendMessage(ColorUtils.color("&cPodano niepoprawną kwote!"));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                        return false;
                    }
                    if(Bukkit.getPlayer(args[1]) != null){
                        User u;
                        try {
                            u = ModerrkowoDatabase.getInstance().getUserManager().getUser(Objects.requireNonNull(Bukkit.getPlayer(args[1])).getUniqueId());
                            u.getBank().money = kwota;
                            ModerrkowoLog.LogAdmin("&6" + p.getName() + " &7ustawił &6" + ChatUtil.getMoney(kwota) + " &7dla gracza &6" + args[1]);
                        } catch (UserNotLoaded userNotLoaded) {
                            p.sendMessage(ColorUtils.color("&cWystąpił błąd"));
                            userNotLoaded.printStackTrace();
                        }
                        return false;
                    }
                    ModerrkowoDatabase.getInstance().existsUser(args[1], new CallbackExists() {
                        @Override
                        public void onDone(Boolean aBoolean) {
                            if(aBoolean){
                                ModerrkowoDatabase.getInstance().setBankByName(args[1], kwota, new CallbackEmpty() {
                                    @Override
                                    public void onDone() {
                                        p.sendMessage(ColorUtils.color("&aPomyślnie ustawiono " + ChatUtil.getMoney(kwota)));
                                        ModerrkowoLog.LogAdmin("&6" + p.getName() + " &7ustawił &6" + ChatUtil.getMoney(kwota) + " &7dla gracza &6" + args[1]);
                                    }

                                    @Override
                                    public void onFail(Exception e) {
                                        p.sendMessage(ColorUtils.color("&cWystąpił błąd"));
                                        e.printStackTrace();
                                    }
                                });
                            }else{
                                p.sendMessage(ColorUtils.color("&cPodany gracz nie istnieje"));
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                            }
                        }

                        @Override
                        public void onFail(Exception e) {
                            p.sendMessage(ColorUtils.color("&cWystąpił błąd"));
                            e.printStackTrace();
                        }
                    });
                    return false;
                }
            }
            p.sendMessage(ColorUtils.color("&cUżyj: /abank <dodaj/odejmij/ustaw> <nick> <kwota>"));
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 1){
            ArrayList<String> list = new ArrayList<>();
            list.add("dodaj");
            list.add("odejmij");
            list.add("ustaw");
            return list;
        }
        return null;
    }
}
