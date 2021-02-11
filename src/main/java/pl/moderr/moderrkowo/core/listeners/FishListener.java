package pl.moderr.moderrkowo.core.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import pl.moderr.moderrkowo.core.utils.ChatUtil;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.RandomUtils;
import pl.moderr.moderrkowo.core.utils.WeightedList;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.UserNotLoaded;

import java.util.Objects;
import java.util.Random;

public class FishListener implements Listener {

    private final WeightedList<ItemStack> randomDrop = new WeightedList<>();

    public FishListener(){
        randomDrop.put(new ItemStack(Material.CHARCOAL), 12);
        randomDrop.put(new ItemStack(Material.GOLD_INGOT), 2);
        randomDrop.put(new ItemStack(Material.BOWL), 4);
        randomDrop.put(new ItemStack(Material.STRING), 4);
        randomDrop.put(new ItemStack(Material.GUNPOWDER), 10);
        randomDrop.put(new ItemStack(Material.SADDLE), 2);
        randomDrop.put(new ItemStack(Material.LEATHER), 8);
        randomDrop.put(new ItemStack(Material.CLAY), 8);
        randomDrop.put(new ItemStack(Material.PAPER), 8);
        randomDrop.put(new ItemStack(Material.BOOK), 2);
        randomDrop.put(new ItemStack(Material.COD), 30);
        randomDrop.put(new ItemStack(Material.SALMON), 24);
        randomDrop.put(new ItemStack(Material.TROPICAL_FISH), 10);
        randomDrop.put(new ItemStack(Material.PUFFERFISH), 12);
        randomDrop.put(new ItemStack(Material.INK_SAC), 10);
        randomDrop.put(new ItemStack(Material.COCOA_BEANS), 1);
        randomDrop.put(new ItemStack(Material.BONE), 3);
        randomDrop.put(new ItemStack(Material.ENDER_PEARL), 2);
        randomDrop.put(new ItemStack(Material.GLASS_BOTTLE), 4);
        randomDrop.put(new ItemStack(Material.EXPERIENCE_BOTTLE), 1);
        randomDrop.put(new ItemStack(Material.NAME_TAG), 4);
        randomDrop.put(new ItemStack(Material.NAUTILUS_SHELL), 5);
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
            ItemStack item = randomDrop.get(new Random());
            p.getLocation().getWorld().dropItem(p.getLocation(), item);
            try {
                User u = ModerrkowoDatabase.getInstance().getUserManager().getUser(p.getUniqueId());
                int kwota = RandomUtils.getRandomInt(1,3);
                u.getBank().money += kwota;
                p.sendMessage(ColorUtils.color("&9Łowienie &6> &a+ " + ChatUtil.getMoney(kwota)));
            } catch (UserNotLoaded userNotLoaded) {
                userNotLoaded.printStackTrace();
            }
        }
    }

}
