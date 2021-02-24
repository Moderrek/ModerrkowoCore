package pl.moderr.moderrkowo.core.custom.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class SpawnTeleport {

    private final int x;
    private final int y;
    private final int z;
    private final String world;
    private final Material material;

    public SpawnTeleport(Material material, String world, int x, int y, int z){
        this.material = material;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getZ() {
        return z;
    }
    public World getWorld(){
        return Bukkit.getWorld(world);
    }
    public Material getMaterial() {
        return material;
    }
    public Location toLocation(){
        return new Location(getWorld(), getX(), getY(), getZ());
    }
}
