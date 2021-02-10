package pl.moderr.moderrkowo.core.villager.data;

import org.bukkit.Material;

public interface IQuestItemCraft extends IQuestItem {

    Material getMaterial();
    int getCount();

    @Override
    default String getQuestItemPrefix(){
        return "Wytwórz";
    }
}
