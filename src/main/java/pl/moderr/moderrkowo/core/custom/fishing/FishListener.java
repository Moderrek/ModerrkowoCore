package pl.moderr.moderrkowo.core.custom.fishing;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.RandomUtils;
import pl.moderr.moderrkowo.core.utils.WeightedList;

import java.util.Objects;
import java.util.Random;

public class FishListener implements Listener {

    private final WeightedList<FishDrop> randomDrop = new WeightedList<>();

    public FishListener(){
        randomDrop.put((FishDropItemStack) () -> Material.CHARCOAL,24);
        randomDrop.put((FishDropItemStack) () -> Material.GOLD_INGOT,4);
        randomDrop.put((FishDropItemStack) () -> Material.BOWL,8);
        randomDrop.put((FishDropItemStack) () -> Material.STRING,8);
        randomDrop.put((FishDropItemStack) () -> Material.GUNPOWDER,10);
        randomDrop.put((FishDropItemStack) () -> Material.EMERALD,4);
        randomDrop.put((FishDropItemStack) () -> Material.SADDLE,2);
        randomDrop.put((FishDropItemStack) () -> Material.LEATHER,12);
        randomDrop.put((FishDropItemStack) () -> Material.CLAY_BALL,12);
        randomDrop.put((FishDropItemStack) () -> Material.BOOK,4);
        randomDrop.put((FishDropItemStack) () -> Material.COD,60);
        randomDrop.put((FishDropItemStack) () -> Material.SALMON,48);
        randomDrop.put((FishDropItemStack) () -> Material.TROPICAL_FISH,20);
        randomDrop.put((FishDropItemStack) () -> Material.PUFFERFISH,14);
        randomDrop.put((FishDropItemStack) () -> Material.BONE,6);
        randomDrop.put((FishDropItemStack) () -> Material.EXPERIENCE_BOTTLE,3);
        randomDrop.put((FishDropItemStack) () -> Material.NAME_TAG,2);
        randomDrop.put((FishDropItemStack) () -> Material.ENDER_PEARL,2);
        randomDrop.put((FishDropItemStack) () -> Material.COD,8);
        randomDrop.put(new FishDropShulkerBox() {},1);
        randomDrop.put((FishDropBanknot) () -> new RandomMinMax(1,100),10);
    }

    @EventHandler
    public void onFish(PlayerFishEvent e){
        Player p = e.getPlayer();
        PlayerFishEvent.State state = e.getState();
        if(state.equals(PlayerFishEvent.State.CAUGHT_FISH)){
            e.setExpToDrop(e.getExpToDrop() * 4);
            Objects.requireNonNull(e.getCaught()).remove();
            e.getHook().setHookedEntity(null);
            e.getHook().remove();
            ItemStack item = null;
            if(p.getInventory().getItemInOffHand().getType().equals(Material.FISHING_ROD)){
                item = p.getInventory().getItemInOffHand();
            }
            if(p.getInventory().getItemInMainHand().getType().equals(Material.FISHING_ROD)){
                item = p.getInventory().getItemInMainHand();
            }
            int countOfDrop = 1;
            assert item != null;
            if(item.containsEnchantment(Main.getInstance().multihookEnchantment)){
                countOfDrop += item.getEnchantmentLevel(Main.getInstance().multihookEnchantment);
            }
            for(int i = 0; i != countOfDrop; i++){
                ItemStack drop = randomDrop.get(new Random()).getItemStack();
                p.getLocation().getWorld().dropItem(p.getLocation(), drop);
            }
        }
    }
}

class RandomMinMax{
    private int min;
    private int max;

    @Contract(pure = true)
    public RandomMinMax(int min, int max){
        this.min = min;
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }
    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }

    public int getRandom(){
        return RandomUtils.getRandomInt(min, max);
    }
}
interface FishDrop{
    ItemStack getItemStack();
}
interface FishDropItemStack extends FishDrop{
    Material getMaterial();
    @Override
    default ItemStack getItemStack(){
        return new ItemStack(getMaterial(), 1);
    }
}
interface FishDropShulkerBox extends FishDrop{
    @Override
    default ItemStack getItemStack(){
        Bukkit.broadcastMessage(ColorUtils.color("&8[!] &eKtoś wyłowił skrzynkę"));
        return Main.getInstance().shulkerDropBox.getRandomShulker();
    }
}
interface FishDropBanknot extends FishDrop{
    RandomMinMax getRange();
    @Override
    default ItemStack getItemStack(){
        return Main.getInstance().banknot.generateItem(1, getRange().getRandom());
    }
}
interface FishDropRandomItemStack extends FishDrop{
    RandomMinMax getRange();
    Material getMaterial();
    @Override
    default ItemStack getItemStack(){
        return new ItemStack(getMaterial(), getRange().getMax());
    }
}
