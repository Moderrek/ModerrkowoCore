package pl.moderr.moderrkowo.core.custom.enchantments;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.ItemStackUtils;

import java.util.*;

public class MultihookEnchantment extends Enchantment implements Listener {

    public MultihookEnchantment() {
        super(new NamespacedKey(Main.getInstance(), "MultihookEnchantment"));
    }

    @EventHandler
    public void anvil(PrepareAnvilEvent e){
        ItemStack first = e.getInventory().getFirstItem();
        ItemStack second = e.getInventory().getSecondItem();
        if(first == null || second == null){
            return;
        }
        if(first.getType().equals(Material.ENCHANTED_BOOK) && second.getType().equals(Material.ENCHANTED_BOOK)){
            EnchantmentStorageMeta book1 = (EnchantmentStorageMeta) first.getItemMeta();
            EnchantmentStorageMeta book2 = (EnchantmentStorageMeta) second.getItemMeta();
            ItemStack itemStack;
            HashMap<Enchantment, Integer> enchantments = new HashMap<>();
            for(Enchantment ench : book1.getStoredEnchants().keySet()){
                enchantments.put(ench, book1.getStoredEnchantLevel(ench));
            }
            for(Enchantment ench : book2.getStoredEnchants().keySet()){
                int level = book2.getStoredEnchants().get(ench);
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
            e.getInventory().setRepairCost(10);
            e.getInventory().setRepairCost(e.getInventory().getRepairCost()+(2*enchantments.size()));
            itemStack = ItemStackUtils.generateEnchantmentBook(enchantments);
            e.setResult(itemStack);
            return;
        }
        if(first.getType().equals(Material.FISHING_ROD) && second.getType().equals(Material.FISHING_ROD)){
            ItemMeta meta1 = first.getItemMeta();
            ItemMeta meta2 = second.getItemMeta();
            ItemStack firstStack = new ItemStack(Material.FISHING_ROD);
            HashMap<Enchantment, Integer> enchantments = new HashMap<>();
            for(Enchantment ench : meta1.getEnchants().keySet()){
                enchantments.put(ench, meta1.getEnchants().get(ench));
            }
            for(Enchantment ench : meta2.getEnchants().keySet()){
                int level = meta2.getEnchants().get(ench);
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
            e.getInventory().setRepairCost(10);
            for(Enchantment ench : enchantments.keySet()){
                int level = enchantments.get(ench);
                firstStack = ItemStackUtils.addEnchantment(firstStack, ench, level);
                e.getInventory().setRepairCost(e.getInventory().getRepairCost()+2);
            }
            e.setResult(firstStack);
            return;
        }
        if(Objects.requireNonNull(e.getInventory().getFirstItem()).getType() == Material.FISHING_ROD){
            if(e.getInventory().getResult() == null) {
                if(second.getType().equals(Material.ENCHANTED_BOOK)){
                    EnchantmentStorageMeta book = (EnchantmentStorageMeta) second.getItemMeta();
                    if(book.hasStoredEnchant(Main.getInstance().multihookEnchantment)){
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
        return;
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

    @NotNull
    @Override
    public String getName() {
        return "Wielokrotny haczyk";
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @NotNull
    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.FISHING_ROD;
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
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return item.getType().equals(Material.FISHING_ROD) && !item.containsEnchantment(this);
    }

    @NotNull
    @Override
    public Component displayName(int level) {
        return Component.text(getName() + " " + level);
    }
}
