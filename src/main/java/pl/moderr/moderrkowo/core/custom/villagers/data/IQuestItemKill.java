package pl.moderr.moderrkowo.core.custom.villagers.data;

import org.bukkit.entity.EntityType;

public interface IQuestItemKill extends IQuestItem {

    EntityType getEntityType();
    int getCount();

    @Override
    default String getQuestItemPrefix(){
        return "Zabij";
    }

}
