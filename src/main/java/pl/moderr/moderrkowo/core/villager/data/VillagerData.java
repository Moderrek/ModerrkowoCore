package pl.moderr.moderrkowo.core.villager.data;

import org.jetbrains.annotations.Contract;
import pl.moderr.moderrkowo.core.utils.ColorUtils;

import java.util.ArrayList;

public class VillagerData {

    private boolean shop = false;
    private final String villagerName;
    private ArrayList<VillagerShopItem> shopItems;
    private final ArrayList<Quest> quests;

    @Contract(pure = true)
    public VillagerData(String villagerName, ArrayList<Quest> quests){
        this.villagerName = villagerName;
        this.quests = quests;
    }
    @Contract(pure = true)
    public VillagerData(String villagerName, ArrayList<Quest> quests, ArrayList<VillagerShopItem> items){
        this.villagerName = villagerName;
        this.quests = quests;
        this.shopItems = items;
        shop = true;
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }
    public String getId() {
        return villagerName.toLowerCase();
    }
    public void setShop(boolean shop) {
        this.shop = shop;
    }
    public void setShopItems(ArrayList<VillagerShopItem> shopItems) {
        this.shopItems = shopItems;
    }
    public ArrayList<VillagerShopItem> getShopItems(){
        return shopItems;
    }
    public boolean isShop() {
        return shop;
    }
    public String getName(){
        StringBuilder prefix = new StringBuilder("&c&lQ ");
        if(shop){
            prefix.append("&9&lS ");
        }
        return ColorUtils.color(prefix.toString() + "&7" + villagerName);
    }
    public String getCommandSpawnName(){
        StringBuilder prefix = new StringBuilder("&c&lQ ");
        if(shop){
            prefix.append("&9&lS ");
        }
        return prefix.toString() + "&7" + villagerName;
    }

}
