package pl.moderr.moderrkowo.core.custom.enchantments;


import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.ItemStackUtils;
import pl.moderr.moderrkowo.core.utils.PowerUtils;

import java.util.*;

import static org.bukkit.Material.*;

public class HammerEnchantment extends Enchantment implements Listener {


    public HammerEnchantment() {
        super(new NamespacedKey(Main.getInstance(), "HammerEnchantment"));
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR)){
            return;
        }
        if(e.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Main.getInstance().hammerEnchantment)){
            if(e.getPlayer().isSneaking()){
                return;
            }
            for(Block b : PowerUtils.getSurroundingBlocks(Objects.requireNonNull(getBlockFace(e.getPlayer())), e.getBlock())){
                if(b == null){
                    continue;
                }
                ArrayList<Material> stone = new ArrayList<Material>(){
                    {
                        add(STONE);
                        add(DIORITE);
                        add(ANDESITE);
                        add(GRANITE);
                    }
                };
                if(b.getType() == e.getBlock().getType() || (stone.contains(b.getType()) && stone.contains(e.getBlock().getType()))){
                    if(!inRegion(b.getLocation(), e.getPlayer().getUniqueId())){
                        b.breakNaturally(e.getPlayer().getInventory().getItemInMainHand(), true);
                        e.getPlayer().updateInventory();
                    }
                }
            }
        }
    }


    public boolean inRegion(Location loc, UUID uuid) {
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(loc.getWorld()));
        if (regions == null) {
            return false;
        }
        ApplicableRegionSet set = regions.getApplicableRegions(BukkitAdapter.asBlockVector(loc));
        for (ProtectedRegion region : set.getRegions()) {
            if (region.getOwners().contains(uuid) || region.getMembers().contains(uuid)) {
                return false;
            }
        }
        return set.size() > 0;
    }

    @EventHandler
    public void anvil(PrepareAnvilEvent e){
        ItemStack first = e.getInventory().getFirstItem();
        ItemStack second = e.getInventory().getSecondItem();
        if(first == null || second == null){
            return;
        }
        if(first.getType().equals(Material.WOODEN_PICKAXE) || first.getType().equals(Material.STONE_PICKAXE) || first.getType().equals(Material.IRON_PICKAXE) || first.getType().equals(Material.DIAMOND_PICKAXE) || first.getType().equals(Material.NETHERITE_PICKAXE)){
            if(e.getInventory().getResult() == null) {
                if(second.getType().equals(Material.ENCHANTED_BOOK)){
                    EnchantmentStorageMeta book = (EnchantmentStorageMeta) second.getItemMeta();
                    if(book.hasStoredEnchant(Main.getInstance().hammerEnchantment)){
                        e.getInventory().setRepairCost(first.getRepairCost());
                        ItemStack result = new ItemStack(first.getType(), first.getAmount());
                        HashMap<Enchantment, Integer> enchantments = new HashMap<>();
                        for(Enchantment ench : first.getEnchantments().keySet()){
                            enchantments.put(ench, first.getEnchantments().get(ench));
                        }
                        for(Enchantment ench : book.getStoredEnchants().keySet()){
                            int level = book.getStoredEnchants().get(ench);
                            if(enchantments.containsKey(ench)) {
                                if(level == enchantments.get(ench)){
                                    if(enchantments.get(ench)+1 <= ench.getMaxLevel()){
                                        enchantments.replace(ench, enchantments.get(ench)+1);
                                    }
                                }
                            }else {
                                enchantments.put(ench, level);
                            }
                        }
                        for(Enchantment ench : enchantments.keySet()){
                            int level = enchantments.get(ench);
                            if(!ench.canEnchantItem(result)){
                                continue;
                            }
                            result = ItemStackUtils.addEnchantment(result, ench, level);
                            e.getInventory().setRepairCost(e.getInventory().getRepairCost()+(2*level));
                        }
                        e.setResult(result);
                    }
                }
            }
        }
    }

    private static String toRoman(int n) {
        String[] romanNumerals = { "M",  "CM", "D", "CD", "C", "XC", "L",  "X", "IX", "V", "I" };
        int[] romanNumeralNums = {  1000, 900, 500,  400 , 100,  90,  50,   10,    9,   5,   1 };
        String finalRomanNum = "";

        for (int i = 0; i < romanNumeralNums.length; i ++) {
            int currentNum = n /romanNumeralNums[i];
            if (currentNum==0) {
                continue;
            }

            for (int j = 0; j < currentNum; j++) {
                finalRomanNum +=romanNumerals[i];
            }

            n = n%romanNumeralNums[i];
        }
        return finalRomanNum;
    }

    public static List<Block> getSquare(Location location, int radius) {
        List<Block> blocks = new ArrayList<>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                blocks.add(location.getWorld().getBlockAt(x,location.getBlockY(),  z));
            }
        }
        return blocks;
    }

    public static List<Block> getSquareRotation(Location loc, BlockFace face) {
        List<Block> blocks = getSquare(loc,1);
        if(face == BlockFace.UP || face == BlockFace.DOWN) {
            return blocks;
        } else {
            List<Block> rotated = new ArrayList<>();
            blocks.forEach(b -> {
                Location center = loc.clone();
                Vector v = face.getDirection();
                if(face == BlockFace.NORTH || face == BlockFace.SOUTH){
                    v.rotateAroundAxis(v, Math.toRadians(90));
                }else{
                    v.rotateAroundAxis(v, Math.toRadians(180));
                }
                Block newBlock = center.add(v).getBlock();
                rotated.add(newBlock);
            });
            return rotated;
        }

    }


    public static BlockFace getBlockFace(Player player) {
        List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks(null, 16);
        if (lastTwoTargetBlocks.size() != 2 || !lastTwoTargetBlocks.get(1).getType().isOccluding()) return null;
        Block targetBlock = lastTwoTargetBlocks.get(1);
        Block adjacentBlock = lastTwoTargetBlocks.get(0);
        return targetBlock.getFace(adjacentBlock);
    }

    @Override
    public @NotNull NamespacedKey getKey(){
        return super.getKey();
    }

    @Override
    public @NotNull String getName() {
        return "Szeroki Trzon";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return other == Enchantment.SILK_TOUCH;
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return item.getType() == Material.DIAMOND_PICKAXE;
    }

    @NotNull
    @Override
    public Component displayName(int level) {
        return Component.text(getName());
    }
}
