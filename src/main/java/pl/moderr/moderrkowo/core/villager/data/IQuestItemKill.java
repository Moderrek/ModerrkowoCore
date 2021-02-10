package pl.moderr.moderrkowo.core.villager.data;

import org.bukkit.entity.EntityType;

public interface IQuestItemKill extends IQuestItem {

    EntityType getEntityType();
    int getCount();

    @Override
    default String getQuestItemPrefix(){
        return "Zabij";
    }

}
