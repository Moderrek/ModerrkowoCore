package pl.moderr.moderrkowo.core.custom.lootchests;

import org.bukkit.inventory.ItemStack;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.RandomMinMax;

public interface ShulkerDropItemBanknot extends ShulkerDropItem{
    RandomMinMax getRandomCount();
    @Override
    default ItemStack getDrop(){
        return Main.getInstance().banknot.generateItem(1, getRandomCount().getRandom());
    }
}