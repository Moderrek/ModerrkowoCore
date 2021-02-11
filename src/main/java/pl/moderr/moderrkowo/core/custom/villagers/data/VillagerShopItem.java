package pl.moderr.moderrkowo.core.custom.villagers.data;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;

public class VillagerShopItem {

    private final int requiredLevel;
    private final ItemStack item;
    private final String description;
    private final int cost;
    private boolean canSell;
    private int sellCost;

    @Contract(pure = true)
    public VillagerShopItem(ItemStack item, int requiredLevel, String description, int cost){
        this.item = item;
        this.requiredLevel = requiredLevel;
        this.description = description;
        this.cost = cost;
    }

    @Contract(pure = true)
    public VillagerShopItem(ItemStack item, int requiredLevel, String description, int cost, int sellCost){
        this.item = item;
        this.requiredLevel = requiredLevel;
        this.description = description;
        this.cost = cost;
        this.sellCost = sellCost;
        this.canSell = true;
    }

    public ItemStack getItem(){
        return item.clone();
    }
    public String getDescription(){
        return description;
    }
    public int getRequiredLevel(){
        return requiredLevel;
    }
    public int getCost(){
        return cost;
    }
    public boolean canSell() {
        return canSell;
    }
    public int getSellCost() {
        return sellCost;
    }
}
