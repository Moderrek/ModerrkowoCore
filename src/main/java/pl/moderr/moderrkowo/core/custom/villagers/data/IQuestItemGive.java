package pl.moderr.moderrkowo.core.custom.villagers.data;

import org.bukkit.Material;

public interface IQuestItemGive extends IQuestItem {

    Material getMaterial();
    int getCount();

    @Override
    default String getQuestItemPrefix(){
        return "Przynieś";
    }

}
