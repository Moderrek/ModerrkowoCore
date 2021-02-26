package pl.moderr.moderrkowo.core.custom.economy;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.utils.ChatUtil;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.Logger;
import pl.moderr.moderrkowo.core.utils.ModerrkowoLog;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.callback.CallbackEmpty;
import pl.moderr.moderrkowo.database.callback.CallbackExists;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.UserNotLoaded;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class PrzelejCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length > 1) {

                User u;
                try {
                    u = ModerrkowoDatabase.getInstance().getUserManager().getUser(p.getUniqueId());
                } catch (UserNotLoaded userNotLoaded) {
                    userNotLoaded.printStackTrace();
                    p.sendMessage(ColorUtils.color("&cNie udało się wczytać danych o twoim portfelu!"));
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    return false;
                }
                int kwota;
                try {
                    kwota = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    p.sendMessage(ColorUtils.color("&cPodano nieprawidłową kwotę!"));
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    return false;
                }
                if (kwota < 0) {
                    p.sendMessage(ColorUtils.color("&cPodano nieprawidłową kwotę!"));
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    return false;
                }
                if (kwota > u.getBank().money) {
                    p.sendMessage(ColorUtils.color("&cNie posiadasz tyle pieniędzy!"));
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    return false;
                }
                String message = null;
                if (args.length > 2) {
                    message = Logger.getMessage(args, 2, true);
                }
                if (Bukkit.getPlayer(args[0]) != null) {
                    if (Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId().equals(p.getUniqueId())) {
                        p.sendMessage(ColorUtils.color("&cNie możesz robić przelewu do siebie!"));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        return false;
                    }
                }
                String finalMessage = message;
                String finalMessage1 = message;
                ModerrkowoDatabase.getInstance().existsUser(args[0], new CallbackExists() {
                    @Override
                    public void onDone(Boolean aBoolean) {
                        if (aBoolean) {
                            if (Bukkit.getPlayer(args[0]) == null) {
                                ModerrkowoDatabase.getInstance().addBankByName(args[0], kwota, new CallbackEmpty() {
                                    @Override
                                    public void onDone() {
                                        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pl-PL"));
                                        nf.setMaximumFractionDigits(2);
                                        DecimalFormat df = (DecimalFormat) nf;
                                        u.getBank().money -= kwota;
                                        if (finalMessage == null) {
                                            p.sendMessage(ColorUtils.color("&9Przelew &6> &aWykonano przelew &6" + df.format(kwota) + " zł &ado " + args[0]));
                                        } else {
                                            p.sendMessage(ColorUtils.color("&9Przelew &6> &aWykonano przelew z wiadomością &6" + df.format(kwota) + " zł &ado " + args[0]));
                                        }
                                        Player receive = Bukkit.getPlayer(args[0]);
                                        if (receive != null) {
                                            receive.sendMessage(ColorUtils.color("&9Przelew &6> &aOtrzymano &6" + nf.format(kwota) + " zł &aod " + p.getName()));
                                            receive.sendMessage(ColorUtils.color("&9Przelew &6> &f" + p.getName() + "&8: &a" + finalMessage1));
                                        }
                                        ModerrkowoLog.LogAdmin(ColorUtils.color("&6" + p.getName() + " &7zrobił przelew o kwocie &6" + ChatUtil.getMoney(kwota) + " &7do &6" + args[0]));
                                    }

                                    @Override
                                    public void onFail(Exception e) {
                                        p.sendMessage(ColorUtils.color("&cWystąpił problem, spróbuj ponownie."));
                                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                                    }
                                });
                            } else {
                                try {
                                    User receive = ModerrkowoDatabase.getInstance().getUserManager().getUser(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getUniqueId());
                                    NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pl-PL"));
                                    nf.setMaximumFractionDigits(2);
                                    DecimalFormat df = (DecimalFormat) nf;
                                    u.getBank().money -= kwota;
                                    if (finalMessage == null) {
                                        p.sendMessage(ColorUtils.color("&9Przelew &6> &aWykonano przelew &6" + df.format(kwota) + " zł &ado " + args[0]));
                                    } else {
                                        p.sendMessage(ColorUtils.color("&9Przelew &6> &aWykonano przelew z wiadomością &6" + df.format(kwota) + " zł &ado " + args[0]));
                                    }
                                    receive.getBank().money += kwota;
                                    Player receiveP = Bukkit.getPlayer(args[0]);
                                    if (receiveP != null) {
                                        receiveP.sendMessage(ColorUtils.color("&9Przelew &6> &aOtrzymano &6" + nf.format(kwota) + " zł &aod " + p.getName()));
                                        receiveP.sendMessage(ColorUtils.color("&9Przelew &6> &f" + p.getName() + "&8: &a" + finalMessage1));
                                    }
                                    ModerrkowoLog.LogAdmin(ColorUtils.color("&6" + p.getName() + " &7zrobił przelew o kwocie &6" + ChatUtil.getMoney(kwota) + " &7do &6" + args[0]));
                                } catch (UserNotLoaded userNotLoaded) {
                                    userNotLoaded.printStackTrace();
                                    p.sendMessage(ColorUtils.color("&cWystąpił problem, spróbuj ponownie."));
                                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                                }
                            }
                        } else {
                            p.sendMessage(ColorUtils.color("&cPodany użytkownik nie istnieje!"));
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        }
                    }

                    @Override
                    public void onFail(Exception e) {
                        p.sendMessage(ColorUtils.color("&cWystąpił problem, spróbuj ponownie."));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                    }
                });
            } else {
                p.sendMessage(ColorUtils.color("&cżzyj: /przelej <nick> <kwota>"));
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                return false;
            }
        }
        return false;
    }
}
