package pl.moderr.moderrkowo.core.custom.spawn;

import com.destroystokyo.paper.Title;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.Logger;
import pl.moderr.moderrkowo.core.utils.RandomUtils;

public class SpawnManager implements Listener {

    //TODO SPAWN
    private final Main main;

    private String spawnName;
    private Location spawnLocation = null;
    private SpawnTeleport spawnTeleport = null;

    private boolean isLoaded = false;

    public SpawnManager(Main main){
        this.main = main;
        LoadConfig();
        LoadSpawnWorld();
        LoadSpawnLocation();
        LoadSpawnTeleport();
        isLoaded = true;
    }
    private void LoadConfig(){
        spawnName = main.getConfig().getString("SpawnWorld");
    }
    private void LoadSpawnWorld(){
        Logger.logWorldManager(spawnName,"Próba wczytania");
        World spawn = new WorldCreator(spawnName).createWorld();
        Logger.logWorldManager(spawnName, "Pomyślnie wczytano");
    }
    private void LoadSpawnLocation(){
        FileConfiguration config = main.getConfig();
        spawnLocation = new Location(Bukkit.getWorld(spawnName), config.getDouble("SpawnX"), config.getDouble("SpawnY"), config.getDouble("SpawnZ"), (float) config.getDouble("SpawnYaw"), (float) config.getDouble("SpawnPitch"));
    }
    private void LoadSpawnTeleport(){
        FileConfiguration config = main.getConfig();
        spawnTeleport = new SpawnTeleport(Material.valueOf(config.getString("SpawnTeleportMaterial")), config.getString("SpawnTeleportWorld"),config.getInt("SpawnTeleportX"),config.getInt("SpawnTeleportY"),config.getInt("SpawnTeleportZ"));
        Bukkit.getPluginManager().registerEvents(this, main);
    }
    public Location getSpawnLocation(){
        return spawnLocation;
    }
    public Location getSpawnTeleportLocation(){
        return spawnTeleport.toLocation();
    }

    @EventHandler
    public void move(PlayerMoveEvent e){
        if(isLoaded){
            if(e.getTo().getWorld().getName().equals(spawnLocation.getWorld().getName())){
                if(isInOffsetedBorder(e.getTo(), e.getTo().getWorld(), 10)){
                    e.getPlayer().teleport(RandomUtils.getRandom(spawnTeleport.getWorld()));
                    e.getPlayer().sendTitle(new Title("", ColorUtils.color("&aWyszedłeś ze spawna")));
                }
            }
        }
    }
    private boolean isInOffsetedBorder(Location loc, World world, int blockOffset){
        WorldBorder border = world.getWorldBorder();
        double size = border.getSize()-blockOffset;
        size = size/2;
        Location center = border.getCenter().clone();
        center.setY(0);
        Location playerLocation = loc.clone();
        playerLocation.setY(0);
        return center.distance(playerLocation) > size;
    }

    @EventHandler
    public void breakBlock(BlockBreakEvent e){
        if(e.getBlock().getWorld().getName().equals(spawnTeleport.getWorld().getName())){
            if(e.getBlock().getType().equals(spawnTeleport.getMaterial())){
                if(e.getBlock().getLocation().getBlockX() == spawnTeleport.getX() && e.getBlock().getLocation().getBlockY() == spawnTeleport.getY() && e.getBlock().getLocation().getBlockZ() == spawnTeleport.getZ()){
                    e.setCancelled(true);
                    Player player = e.getPlayer();
                    if(isLoaded){
                        player.sendMessage(ColorUtils.color("&aTeleportowanie na spawna"));
                        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT,1,1);
                        player.teleportAsync(getSpawnLocation());
                        player.sendTitle(new Title("", ColorUtils.color("&aWitaj na spawnie")));
                        player.sendMessage(" ");
                        player.sendMessage(ColorUtils.color("&aWitaj na spawnie!"));
                        player.sendMessage(ColorUtils.color("&aZnajdziesz tutaj sporo ciekawych rzeczy m.in zadania skrzynki i ukryte nagrody"));
                        player.sendMessage(ColorUtils.color("&aAby wrócić na normalny świat, wejdź w border"));
                    }else{
                        player.sendMessage(ColorUtils.color("&cSpawn jest jeszcze niewczytany!"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                    }
                }
            }
        }
    }

}
