package pl.moderr.moderrkowo.core.custom.lootchests;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.RandomMinMax;
import pl.moderr.moderrkowo.core.utils.WeightedList;

import java.util.Random;

public class ShulkerDropBox {
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
