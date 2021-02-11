package pl.moderr.moderrkowo.core.listeners;

import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class CropBreakListener implements Listener {

    @EventHandler
    public void onBreakCrop(BlockBreakEvent e) {
        BlockData blockData = e.getBlock().getBlockData();
        if(blockData instanceof Ageable){
            Ageable age = (Ageable) blockData;
            if(age.getAge() == age.getMaximumAge()){
                e.setExpToDrop(1);
            }
        }
    }

}
