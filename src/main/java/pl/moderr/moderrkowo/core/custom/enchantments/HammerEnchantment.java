package pl.moderr.moderrkowo.core.custom.enchantments;


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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.PowerUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            for(Block b : PowerUtils.getSurroundingBlocks(Objects.requireNonNull(getBlockFace(e.getPlayer())), e.getBlock())){
                if(b.getType() == e.getBlock().getType()){
                    b.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
                    e.getPlayer().updateInventory();
                }
            }
        }
    }

    @EventHandler
    public void interact(PlayerInteractEvent e){
        if(e.getItem() != null){
            if(e.getItem().getItemMeta().hasLore()){
                if(Objects.requireNonNull(e.getItem().getLore()).contains(ColorUtils.color("&7" + Main.getInstance().hammerEnchantment.getName()))){
                    if(!e.getItem().containsEnchantment(Main.getInstance().hammerEnchantment)){
                        e.getItem().addUnsafeEnchantment(Main.getInstance().hammerEnchantment, 1);
                    }
                }
            }
        }
    }

    @EventHandler
    public void anvil(PrepareAnvilEvent e){
        ItemStack first = e.getInventory().getFirstItem();
        ItemStack secound = e.getInventory().getSecondItem();
        if(first == null || secound == null){
            return;
        }
        if(Objects.requireNonNull(e.getInventory().getFirstItem()).getType() == Material.DIAMOND_PICKAXE || e.getInventory().getFirstItem().getType() == Material.NETHERITE_PICKAXE){
            if(e.getInventory().getResult() == null) {
                if (secound.getItemMeta().hasEnchant(Main.getInstance().hammerEnchantment)) {
                    if (secound.getType() == Material.ENCHANTED_BOOK) {
                        ItemStack result = first.clone();
                        result.addUnsafeEnchantment(Main.getInstance().hammerEnchantment, 1);
                        ItemMeta meta = result.getItemMeta();
                        ArrayList<String> lore = new ArrayList<>();
                        if(result.getItemMeta().getLore() != null){
                            lore = (ArrayList<String>) meta.getLore();
                        }
                        assert lore != null;
                        lore.add(ColorUtils.color("&7" + Main.getInstance().hammerEnchantment.getName()));
                        meta.setLore(lore);
                        result.setItemMeta(meta);
                        e.getInventory().setRepairCost(30);
                        e.setResult(result);
                    }
                }
            }
        }
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
}
