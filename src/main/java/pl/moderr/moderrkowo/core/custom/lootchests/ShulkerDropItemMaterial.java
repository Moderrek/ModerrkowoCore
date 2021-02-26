package pl.moderr.moderrkowo.core.custom.lootchests;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.moderr.moderrkowo.core.utils.RandomMinMax;

public interface ShulkerDropItemMaterial extends ShulkerDropItem{
    RandomMinMax getRandomCount();
    Material getMaterial();
    @Override
    default ItemStack getDrop(){
        return new ItemStack(getMaterial(), getRandomCount().getRandom());
    }
}
