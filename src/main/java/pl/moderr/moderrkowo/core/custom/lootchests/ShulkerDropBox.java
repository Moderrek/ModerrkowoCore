package pl.moderr.moderrkowo.core.custom.lootchests;

import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.Contract;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.ItemStackUtils;
import pl.moderr.moderrkowo.core.utils.RandomUtils;
import pl.moderr.moderrkowo.core.utils.WeightedList;

import java.util.*;

public class ShulkerDropBox {

    /*
    Brązowa skrzynia | Srebrzysta skrzynia | Pozłocona skrzynia | Legendarna skrzynia
    */

    // leather

    // Boxes
    private final ShulkerDrop brownBox = new ShulkerDrop(new WeightedList<ShulkerDropItem>(){
        {
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(3,5);
                }

                @Override
                public Material getMaterial() {
                    return Material.SLIME_BALL;
                }
            },60);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,64);
                }

                @Override
                public Material getMaterial() {
                    return Material.INK_SAC;
                }
            },60);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,32);
                }

                @Override
                public Material getMaterial() {
                    return Material.GLOWSTONE_DUST;
                }
            },60);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,64);
                }

                @Override
                public Material getMaterial() {
                    return Material.COD;
                }
            }, 100);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,64);
                }

                @Override
                public Material getMaterial() {
                    return Material.SEAGRASS;
                }
            },100);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,5);
                }
                @Override
                public Material getMaterial() {
                    return Material.IRON_INGOT;
                }
            },130);
            put((ShulkerDropItemBanknot) () -> new RandomMinMax(100,500), 130);
            put(new ShulkerDropItemRandomEnchantmentBook() {}, 20);
        }
    }, 60, 0, ColorUtils.color("&eBrązowa skrzynia"), Material.BROWN_SHULKER_BOX);
    private final ShulkerDrop silverBox = new ShulkerDrop(new WeightedList<ShulkerDropItem>(){
        {
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(3,15);
                }

                @Override
                public Material getMaterial() {
                    return Material.SLIME_BALL;
                }
            },60);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,30);
                }

                @Override
                public Material getMaterial() {
                    return Material.INK_SAC;
                }
            },60);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,64);
                }

                @Override
                public Material getMaterial() {
                    return Material.GLOWSTONE_DUST;
                }
            },60);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,64);
                }

                @Override
                public Material getMaterial() {
                    return Material.COD;
                }
            }, 100);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,64);
                }

                @Override
                public Material getMaterial() {
                    return Material.SEAGRASS;
                }
            },100);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,10);
                }
                @Override
                public Material getMaterial() {
                    return Material.IRON_INGOT;
                }
            },130);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,30);
                }

                @Override
                public Material getMaterial() {
                    return Material.LEATHER;
                }
            }, 100);
            put((ShulkerDropItemBanknot) () -> new RandomMinMax(100,500), 130);
            put(new ShulkerDropItemRandomEnchantmentBook() {}, 50);
        }
    }, 45, 0, ColorUtils.color("&8Srebrzysta skrzynia"), Material.GRAY_SHULKER_BOX);
    private final ShulkerDrop goldBox = new ShulkerDrop(new WeightedList<ShulkerDropItem>(){
        {
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(3,15);
                }

                @Override
                public Material getMaterial() {
                    return Material.SLIME_BALL;
                }
            },50);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,30);
                }

                @Override
                public Material getMaterial() {
                    return Material.INK_SAC;
                }
            },50);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,64);
                }

                @Override
                public Material getMaterial() {
                    return Material.GLOWSTONE_DUST;
                }
            },50);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(5,15);
                }

                @Override
                public Material getMaterial() {
                    return Material.SCUTE;
                }
            },50);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,2);
                }

                @Override
                public Material getMaterial() {
                    return Material.TURTLE_EGG;
                }
            }, 20);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,3);
                }

                @Override
                public Material getMaterial() {
                    return Material.NAUTILUS_SHELL;
                }
            }, 10);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,64);
                }

                @Override
                public Material getMaterial() {
                    return Material.COD;
                }
            }, 100);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,64);
                }

                @Override
                public Material getMaterial() {
                    return Material.SEAGRASS;
                }
            },100);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,28);
                }
                @Override
                public Material getMaterial() {
                    return Material.IRON_INGOT;
                }
            },130);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,50);
                }

                @Override
                public Material getMaterial() {
                    return Material.EMERALD;
                }
            },100);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,30);
                }

                @Override
                public Material getMaterial() {
                    return Material.GOLD_INGOT;
                }
            }, 100);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,16);
                }

                @Override
                public Material getMaterial() {
                    return Material.ENDER_PEARL;
                }
            }, 40);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,30);
                }

                @Override
                public Material getMaterial() {
                    return Material.LEATHER;
                }
            }, 100);
            put((ShulkerDropItemBanknot) () -> new RandomMinMax(200,3000), 130);
            put(new ShulkerDropItemEnchantmentTool() {},90);
            put(new ShulkerDropItemRandomEnchantmentBook() {}, 110);
        }
    }, 50, 0, ColorUtils.color("&ePozłocona skrzynia"), Material.YELLOW_SHULKER_BOX);
    private final ShulkerDrop legendBox = new ShulkerDrop(new WeightedList<ShulkerDropItem>(){
        {
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(3,15);
                }

                @Override
                public Material getMaterial() {
                    return Material.SLIME_BALL;
                }
            },30);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,30);
                }

                @Override
                public Material getMaterial() {
                    return Material.INK_SAC;
                }
            },30);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,64);
                }

                @Override
                public Material getMaterial() {
                    return Material.GLOWSTONE_DUST;
                }
            },30);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(5,15);
                }

                @Override
                public Material getMaterial() {
                    return Material.SCUTE;
                }
            },30);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,2);
                }

                @Override
                public Material getMaterial() {
                    return Material.TURTLE_EGG;
                }
            }, 50);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,3);
                }

                @Override
                public Material getMaterial() {
                    return Material.NAUTILUS_SHELL;
                }
            }, 50);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,64);
                }

                @Override
                public Material getMaterial() {
                    return Material.COD;
                }
            }, 100);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,64);
                }

                @Override
                public Material getMaterial() {
                    return Material.SEAGRASS;
                }
            },100);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,28);
                }
                @Override
                public Material getMaterial() {
                    return Material.IRON_INGOT;
                }
            },150);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,50);
                }

                @Override
                public Material getMaterial() {
                    return Material.EMERALD;
                }
            },150);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,30);
                }

                @Override
                public Material getMaterial() {
                    return Material.GOLD_INGOT;
                }
            }, 150);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,16);
                }

                @Override
                public Material getMaterial() {
                    return Material.ENDER_PEARL;
                }
            }, 70);
            put(new ShulkerDropItemMaterial() {
                @Override
                public RandomMinMax getRandomCount() {
                    return new RandomMinMax(1,30);
                }

                @Override
                public Material getMaterial() {
                    return Material.LEATHER;
                }
            }, 100);
            put((ShulkerDropItemBanknot) () -> new RandomMinMax(1000,4500), 130);
            put(new ShulkerDropItemEnchantmentTool() {},120);
            put(new ShulkerDropItemRandomEnchantmentBook() {}, 120);
        }
    }, 80, 0, ColorUtils.color("&6Legendarna skrzynia"), Material.ORANGE_SHULKER_BOX);

    // Box list
    private final WeightedList<ShulkerDrop> shulkers = new WeightedList<ShulkerDrop>(){
        {
            put(silverBox, 30);
            put(brownBox, 50);
            put(legendBox, 5);
            put(goldBox, 15);
        }
    };

    // Get Box List
    public WeightedList<ShulkerDrop> getShulkers() {
        return shulkers;
    }
    public ItemStack getRandomShulker(){
        return shulkers.get(new Random()).generateShulker();
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
class ShulkerDrop{

    private WeightedList<ShulkerDropItem> drops;
    private final int percentageForSlot;
    private final int maxSlots;
    private final String name;
    private final Material color;

    @Contract(pure = true)
    public ShulkerDrop(WeightedList<ShulkerDropItem> drops, int percentageForSlot, int maxSlots, String name, Material color){
        this.drops = drops;
        this.percentageForSlot = percentageForSlot;
        this.maxSlots = maxSlots;
        this.name = name;
        this.color = color;
    }
    public WeightedList<ShulkerDropItem> getDrops() {
        return drops;
    }
    public void setDrops(WeightedList<ShulkerDropItem> drops) {
        this.drops = drops;
    }
    public ItemStack generateShulker(){
        ItemStack item = new ItemStack(color,1);
        BlockStateMeta im = (BlockStateMeta) item.getItemMeta();
        ShulkerBox shulker = (ShulkerBox) im.getBlockState();
            for(int i = 0; i != shulker.getInventory().getSize(); ++i){
                final int chance = new Random().nextInt(100);
                if(chance <= percentageForSlot){
                    shulker.getInventory().setItem(i, drops.get(new Random()).getDrop());
                }
            }
            im.setBlockState(shulker);
            item.setItemMeta(im);
        item.setDisplayName(name);
        return item;
    }

    public Material getColor() {
        return color;
    }
    public int getPercentageForSlot() {
        return percentageForSlot;
    }
    public int getMaxSlots() {
        return maxSlots;
    }
}
interface ShulkerDropItem{
    ItemStack getDrop();
}
interface ShulkerDropItemBanknot extends ShulkerDropItem{
    RandomMinMax getRandomCount();
    @Override
    default ItemStack getDrop(){
        return Main.getInstance().banknot.generateItem(1, getRandomCount().getRandom());
    }
}
interface ShulkerDropItemMaterial extends ShulkerDropItem{
    RandomMinMax getRandomCount();
    Material getMaterial();
    @Override
    default ItemStack getDrop(){
        return new ItemStack(getMaterial(), getRandomCount().getRandom());
    }
}
interface ShulkerDropItemEnchantmentTool extends ShulkerDropItem{
    default Enchantment getRandomEnchantment(){
        return Enchantment.values()[(int) (Math.random()*Enchantment.values().length)];
    }
    default ItemStack randomTool(){
        WeightedList<Material> materialWeightedList = new WeightedList<Material>(){
            {
                put(Material.DIAMOND_SWORD,10);
                put(Material.DIAMOND_PICKAXE,10);
                put(Material.DIAMOND_CHESTPLATE,10);
                put(Material.DIAMOND_SHOVEL,10);
                put(Material.DIAMOND_LEGGINGS,10);
                put(Material.DIAMOND_HOE,10);
                put(Material.DIAMOND_AXE,10);
                put(Material.NETHERITE_AXE,1);
                put(Material.NETHERITE_PICKAXE,1);
                put(Material.NETHERITE_HOE,1);
                put(Material.NETHERITE_SWORD,1);
                put(Material.FISHING_ROD,1);
            }
        };
        return new ItemStack(materialWeightedList.get(new Random()),1);
    }


    default ItemStack randomEnchantment(ItemStack item) {
        // Store all possible enchantments for the item
        List<Enchantment> possible = new ArrayList<Enchantment>();

        // Loop through all enchantemnts
        for (Enchantment ench : Enchantment.values()) {
            // Check if the enchantment can be applied to the item, save it if it can
            if (ench.canEnchantItem(item)) {
                possible.add(ench);
            }
        }

        // If we have at least one possible enchantment
        if (possible.size() >= 1) {
            // Randomize the enchantments
            Collections.shuffle(possible);
            // Get the first enchantment in the shuffled list
            Enchantment chosen = possible.get(0);
            // Apply the enchantment with a random level between 1 and the max level
            item = ItemStackUtils.addEnchantment(item, chosen, 1 + (int) (Math.random() * ((chosen.getMaxLevel() - 1) + 1)));
        }

        // Return the item even if it doesn't have any enchantments
        return item;
    }

    @Override
    default ItemStack getDrop(){
        ItemStack tool = randomTool();
        tool = randomEnchantment(tool);
        tool = randomEnchantment(tool);
        tool = randomEnchantment(tool);
        return tool;
    }
}
interface ShulkerDropItemRandomEnchantmentBook extends ShulkerDropItem{
    default Enchantment getRandomEnchantment(){
        return Enchantment.values()[(int) (Math.random()*Enchantment.values().length)];
    }
    default int getRandomLevel(Enchantment enchantment){
        if(enchantment.getMaxLevel() == 1){
            return 1;
        }else{
            return RandomUtils.getRandomInt(1, enchantment.getMaxLevel());
        }
    }
    default int getRandomEnchantsCount(){
        WeightedList<Integer> weightedList = new WeightedList<Integer>();
        weightedList.put(1, 75);
        weightedList.put(2, 10);
        weightedList.put(3, 15);
        return weightedList.get(new Random());
    }
    @Override
    default ItemStack getDrop() {
        Enchantment enchantment = getRandomEnchantment();
        HashMap<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>(){
            {
                put(enchantment, getRandomLevel(enchantment));
            }
        };
        return ItemStackUtils.generateEnchantmentBook(enchantments);
    }
}
