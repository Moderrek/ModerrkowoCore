package pl.moderr.moderrkowo.core.villager.data;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Quest {

    private final String name;
    private final String description;
    private final ArrayList<IQuestItem> questItems;
    private final ArrayList<ItemStack> rewardItems;
    private final int money;

    public Quest(String name, String description, ArrayList<IQuestItem> questItems, int rewardInMoney, ArrayList<ItemStack> rewardItems){
        this.name = name;
        this.description = description;
        this.questItems = questItems;
        this.money = rewardInMoney;
        this.rewardItems = rewardItems;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getMoney() {
        return money;
    }
    public ArrayList<IQuestItem> getQuestItems() {
        return questItems;
    }
    public ArrayList<ItemStack> getRewardItems() {
        return rewardItems;
    }

}
