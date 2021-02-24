package pl.moderr.moderrkowo.core.custom.items;

import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class FireballManager implements Listener {

    @EventHandler
    public void click(PlayerInteractEvent e){
        if(e.getItem() != null){
            if(e.getItem().getType().equals(Material.FIRE_CHARGE)){
                e.getItem().setAmount(e.getItem().getAmount()-1);
                e.getPlayer().launchProjectile(Fireball.class);
            }
        }
    }

}
