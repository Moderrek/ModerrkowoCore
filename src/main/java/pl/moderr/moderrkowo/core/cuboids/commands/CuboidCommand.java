package pl.moderr.moderrkowo.core.cuboids.commands;

import com.destroystokyo.paper.Title;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.cuboids.CuboidsManager;
import pl.moderr.moderrkowo.core.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CuboidCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length > 0) {
                // Administrators
                if (p.hasPermission("moderr.cuboids.admin")) {
                    if (args[0].equalsIgnoreCase("admin-give")) {
                        p.getInventory().addItem(Objects.requireNonNull(CuboidsManager.getCuboidItem(1)));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
                        p.sendTitle(new Title(Main.getServerName(), ColorUtils.color(" &aOtrzymałeś działke!")));
                        return true;
                    }
                }
                // Players
                if (args[0].equalsIgnoreCase("dodaj")) {
                    if (args.length > 1) {
                        Player addPlayer = Bukkit.getPlayer(args[1]);
                        if (addPlayer != null) {
                            if (p.getUniqueId().equals(addPlayer.getUniqueId())) {
                                p.sendMessage(Main.getServerName() + ColorUtils.color(" &cNie możesz dodać siebie!"));
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                                return false;
                            }
                            RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                            RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(p.getWorld()));
                            assert regionManager != null;
                            ApplicableRegionSet set = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(p.getLocation()));
                            ProtectedRegion cuboid = null;
                            for (ProtectedRegion cub : set) {
                                if (cub.getId().startsWith(CuboidsManager.getCuboidNamePrefix().toLowerCase())) {
                                    cuboid = cub;
                                }
                            }
                            if (cuboid != null) {
                                cuboid.getMembers().addPlayer(addPlayer.getUniqueId());
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
                                p.sendTitle(new Title(Main.getServerName(), ColorUtils.color("&aPomyślnie dodano!")));
                                p.sendMessage(Main.getServerName() + ColorUtils.color(String.format(" &aDodałeś&2 %s &ado swojej działki!", addPlayer.getName())));
                                addPlayer.sendMessage(ColorUtils.color("&aZostałeś dodany do działki gracza &2" + p.getName()));
                                return true;
                            } else {
                                p.sendMessage(Main.getServerName() + ColorUtils.color(" &cMusisz stać na swojej działce!"));
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                                return false;
                            }
                        } else {
                            p.sendMessage(Main.getServerName() + ColorUtils.color(" &cGracz jest offline!"));
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            return false;
                        }
                    } else {
                        p.sendMessage(Main.getServerName() + ColorUtils.color(" &cPodaj nazwę gracza, którego chcesz dodać!"));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        return false;
                    }
                }
                if (args[0].equalsIgnoreCase("usun")) {
                    if (args.length > 1) {
                        Player addPlayer = Bukkit.getPlayer(args[1]);
                        if (addPlayer != null) {
                            if (p.getUniqueId().equals(addPlayer.getUniqueId())) {
                                p.sendMessage(Main.getServerName() + ColorUtils.color(" &cNie możesz usunąć siebie!"));
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                                return false;
                            }
                            RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                            RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(p.getWorld()));
                            assert regionManager != null;
                            ApplicableRegionSet set = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(p.getLocation()));
                            ProtectedRegion cuboid = null;
                            for (ProtectedRegion cub : set) {
                                if (cub.getId().startsWith(CuboidsManager.getCuboidNamePrefix().toLowerCase())) {
                                    cuboid = cub;
                                }
                            }
                            if (cuboid != null) {
                                if (cuboid.getMembers().getUniqueIds().contains(addPlayer.getUniqueId())) {
                                    cuboid.getMembers().removePlayer(addPlayer.getUniqueId());
                                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
                                    p.sendTitle(new Title(Main.getServerName(), ColorUtils.color("&aPomyślnie usunięto!")));
                                    p.sendMessage(Main.getServerName() + ColorUtils.color(String.format(" &aUsunąłeś&2 %s &az swojej działki!", addPlayer.getName())));
                                    addPlayer.sendMessage(Main.getServerName() + ColorUtils.color(" &cZostałeś usunięty z działki gracza &4" + p.getName()));
                                    return true;
                                } else {
                                    p.sendMessage(Main.getServerName() + ColorUtils.color(" &cGracz nie jest dodany!"));
                                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                                    return false;
                                }
                            } else {
                                p.sendMessage(Main.getServerName() + ColorUtils.color(" &cMusisz stać na swojej działce!"));
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                                return false;
                            }
                        } else {
                            p.sendMessage(Main.getServerName() + ColorUtils.color(" &cGracz jest offline!"));
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                            return false;
                        }
                    } else {
                        p.sendMessage(ColorUtils.color(Main.getServerName() + " &cPodaj nazwę gracza, którego chcesz usunąć!"));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        return false;
                    }
                }
                if (args[0].equalsIgnoreCase("info")) {
                    RegionContainer regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
                    RegionManager regionManager = regionContainer.get(BukkitAdapter.adapt(p.getWorld()));
                    assert regionManager != null;
                    ApplicableRegionSet set = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(p.getLocation()));
                    ProtectedRegion cub = null;
                    for (ProtectedRegion cuboid : set) {
                        if (cuboid.getId().startsWith(CuboidsManager.getCuboidNamePrefix().toLowerCase())) {
                            cub = cuboid;
                        }
                    }
                    if (cub != null) {
                        p.sendMessage(ColorUtils.color("&3Informacje o działce &b" + cub.getId().replaceFirst(CuboidsManager.getCuboidNamePrefix().toLowerCase(), "").toUpperCase()));
                        p.sendMessage(ColorUtils.color("&bWłaściciel &f" + cub.getId().replaceFirst(CuboidsManager.getCuboidNamePrefix().toLowerCase(), "").toUpperCase()));
                        StringBuilder members = new StringBuilder();
                        for (String uuid : cub.getMembers().getPlayers()) {
                            members.append(" ").append(uuid);
                        }
                        p.sendMessage(ColorUtils.color("&bDodani:&f" + members));
                        return true;
                    } else {
                        p.sendMessage(Main.getServerName() + ColorUtils.color(" &cAby pobrać informacje o działce najpierw musisz na niej stać!"));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        return false;
                    }
                }
                // If null
                p.sendMessage(Main.getServerName() + ColorUtils.color(" &cBłąd! Błędny argument"));
            } else {
                p.sendMessage(Main.getServerName() + ColorUtils.color(" &cBłąd! Brak argumentów"));
            }
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
            return false;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            ArrayList<String> hints = new ArrayList<>();
            //hints.add("panel");
            hints.add("info");
            hints.add("dodaj");
            hints.add("usun");
            if (sender.hasPermission("moderr.cuboids.admin")) {
                hints.add("admin-give");
            }
            return hints;
        }
        return null;
    }
}
