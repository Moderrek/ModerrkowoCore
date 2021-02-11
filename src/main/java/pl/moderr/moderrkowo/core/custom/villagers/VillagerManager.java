package pl.moderr.moderrkowo.core.custom.villagers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import pl.moderr.moderrkowo.core.Main;
import pl.moderr.moderrkowo.core.custom.villagers.data.*;
import pl.moderr.moderrkowo.core.utils.ChatUtil;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.ItemStackUtils;
import pl.moderr.moderrkowo.core.utils.ModerrkowoLog;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.data.PlayerVillagerData;
import pl.moderr.moderrkowo.database.data.PlayerVillagersData;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.UserNotLoaded;

import java.util.ArrayList;
import java.util.HashMap;

public class VillagerManager implements Listener {

    public VillagerManager(){
        AddShop(new VillagerData("Rybak", new ArrayList<Quest>(){
            {
                add(new Quest(
                        "Początkowy połów",
                        "Hej! Zauważyłem, że widzisz żyłkę złota w łowieniu.\nChętnie Ci wytłumaczę, o co w tym chodzi,\nprzy okazji rozwiniemy swoje biznesy.\nTo jak, wchodzisz w to?",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.FISHING_ROD;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 1;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.COD;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 64;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.WATER_BUCKET;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 8;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }
                                    });
                                }
                            }
                        },
                        2500,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    ItemStack item = new ItemStack(Material.FISHING_ROD);
                                    item.addEnchantment(Enchantment.LURE,3);
                                    add(item);
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Przygotowanie do ogromnych połowów",
                        "Aby osiągnąć większy sukces, musimy się przygotować!",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.BUCKET;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 10;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.OAK_BOAT;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 4;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.STRING;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 64;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }
                                    });
                                }
                            }
                        },
                        1000,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE, 16));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Czas na żniwa!",
                        "Dobra jesteśmy już gotowi, zostawię Cię jak wrócisz ocenimy twoją prace!",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.KELP;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 128;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.COD;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 128;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.STRING;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 256;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.SEAGRASS;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 16;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "4";
                                        }
                                    });
                                }
                            }
                        },
                        5000,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.WATER_BUCKET));
                                    add(new ItemStack(Material.WATER_BUCKET));
                                    add(new ItemStack(Material.WATER_BUCKET));
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE, 32));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Świątynia",
                        "Mam dla Ciebie zadanie,\nostatnio znalazłem jakiś ogromy budynek\nChodzi o \"wodną swiątynie\",\nmusimy sprawdzić ten temat",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.PRISMARINE_SHARD;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 128;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemKill() {
                                        @Override
                                        public EntityType getEntityType() {
                                            return EntityType.SALMON;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 128;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.GLASS_BOTTLE;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 300;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }
                                    });
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.BARREL;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 512;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "4";
                                        }
                                    });
                                }
                            }
                        },
                        10500,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.ENDER_PEARL, 64));
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE, 128));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Żółwik",
                        "Widzę że Ci się już powodzi.\nA co powiesz na żółwie?",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.SEAGRASS;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 128;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.SAND;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 128;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.DIRT;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 1024;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.KELP;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 32;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "4";
                                        }
                                    });
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.BONE_MEAL;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 64;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "5";
                                        }
                                    });
                                }
                            }
                        },
                        15000,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.SEAGRASS, 64));
                                    add(new ItemStack(Material.TURTLE_HELMET, 1));
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE, 128));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Szef",
                        "Ja już jestem stary..\nZauważyłem jaki masz talent, pokażę Ci coś",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.TURTLE_HELMET;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 30;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.COD;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 768;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.SALMON;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 768;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.TROPICAL_FISH;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 512;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "4";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.NAME_TAG;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 64;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "5";
                                        }
                                    });
                                }
                            }
                        },
                        22250,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE, 512));
                                    add(new ItemStack(Material.DIAMOND_BLOCK,4));
                                    add(new ItemStack(Material.NETHERITE_SCRAP, 16));
                                }
                            }
                        }
                ));
            }
        }, new ArrayList<VillagerShopItem>(){
            {
                add(new VillagerShopItem(new ItemStack(Material.COOKED_COD, 1), 1, "Świeżo złapany pieczony dorsz!", 16, 4));
                add(new VillagerShopItem(new ItemStack(Material.COOKED_SALMON, 1), 1, "Łosoś jak ta lala", 22, 5));
                add(new VillagerShopItem(new ItemStack(Material.PUFFERFISH, 1), 1, "Puf puf zzz", 100, 7));
                add(new VillagerShopItem(new ItemStack(Material.TROPICAL_FISH, 1), 7, "Lubisz rybki Nemo?", 100, 13));

                add(new VillagerShopItem(new ItemStack(Material.WATER_BUCKET, 1), 2, "Wiaderko z brudną wodą", 56));
                add(new VillagerShopItem(new ItemStack(Material.FISHING_ROD,1), 2, "Prosta, standardowa wędka", 30));
                add(new VillagerShopItem(new ItemStack(Material.OAK_BOAT, 1), 2, "Stabilna?", 45, 10));
                add(new VillagerShopItem(new ItemStack(Material.NAUTILUS_SHELL, 1), 2, "Los Muszelkos", 70, 30));

                add(new VillagerShopItem(new ItemStack(Material.BIRCH_BOAT, 1), 3, "Stabilniejsza wersja przodków", 40));
                add(new VillagerShopItem(new ItemStack(Material.SPRUCE_BOAT, 1), 3, "Stabilniejsza wersja przodków", 40));
                add(new VillagerShopItem(new ItemStack(Material.DARK_OAK_BOAT, 1), 3, "Stabilniejsza wersja przodków", 40));
                add(new VillagerShopItem(new ItemStack(Material.JUNGLE_BOAT, 1), 3, "Stabilniejsza wersja przodków", 40));
                add(new VillagerShopItem(new ItemStack(Material.ACACIA_BOAT, 1), 3, "Stabilniejsza wersja przodków", 40));
                add(new VillagerShopItem(new ItemStack(Material.BIRCH_BOAT, 1), 3, "Stabilniejsza wersja przodków", 40));

                add(new VillagerShopItem(new ItemStack(Material.SEAGRASS, 1), 4, "Słyszałem że interesujesz się żółwiami", 23, 10));
                add(new VillagerShopItem(new ItemStack(Material.KELP, 1), 4, "Czaisz, że inni robią tym windy??", 2,1));
                add(new VillagerShopItem(new ItemStack(Material.SEA_PICKLE, 1), 4, "Piklesy mmm", 100, 50));
                add(new VillagerShopItem(new ItemStack(Material.DRIED_KELP, 1), 4, "Słyszałem że interesujesz się żółwiami", 36, 20));

                add(new VillagerShopItem(new ItemStack(Material.PRISMARINE_SHARD, 1), 5, "Uważaj! Ostre!", 40, 30));
                add(new VillagerShopItem(new ItemStack(Material.PRISMARINE_CRYSTALS, 1), 5, "Ale fajne kryształki. Zobacz", 20, 10));
                add(new VillagerShopItem(new ItemStack(Material.SEA_LANTERN, 1), 5, "Mocne światło ua", 300, 130));

                add(new VillagerShopItem(new ItemStack(Material.TURTLE_EGG, 1), 6, "Hmm jajo smo.. żółwia!", 1000, 200));
                add(new VillagerShopItem(new ItemStack(Material.CLAY_BALL, 1), 6, "Glina, kiedyś jakieś drogie to było", 3, 2));
                add(new VillagerShopItem(new ItemStack(Material.STRING, 1), 6, "Nitki ahh potrzebuje je, masz?", 5, 3));

                ItemStack item = new ItemStack(Material.FISHING_ROD);
                item.addUnsafeEnchantment(Enchantment.LURE, 5);
                item.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
                add(new VillagerShopItem(item, 7, "Jesteś szefem w tym co robisz! Sprzedam ci moją wędkę", 75000));

                add(new VillagerShopItem(new ItemStack(Material.COOKED_COD, 1), 7, "Świeżo złapany pieczony dorsz!", 16, 16));
                add(new VillagerShopItem(new ItemStack(Material.COOKED_SALMON, 1), 7, "Łosoś jak ta lala", 22, 22));
                add(new VillagerShopItem(new ItemStack(Material.PUFFERFISH, 1), 7, "Puf puf zzz", 100, 34));
                add(new VillagerShopItem(new ItemStack(Material.TROPICAL_FISH, 1), 7, "Lubisz rybki Nemo?", 100, 40));
            }
        }));
        AddShop(new VillagerData("Elektryk", new ArrayList<Quest>(){
            {
                add(new Quest(
                        "Kabelki",
                        "Dobra wytworzysz mi te kabelki a ja Ci wytłumaczę",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.REDSTONE;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 64;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.REPEATER;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 16;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.REDSTONE_TORCH;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 2;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }
                                    });
                                }
                            }
                        },
                        1500,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE,16));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Obserwują nas",
                        "Potrzebuje teraz itemki na farme. Załatwisz mi?",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.OBSERVER;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 64;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                }
                            }
                        },
                        5000,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.SLIME_BALL, 16));
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE,32));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Automatyczna farma 2",
                        "Potrzebuje teraz itemki na farme. Załatwisz mi?",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.REDSTONE;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 256;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.REPEATER;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 32;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.PISTON;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 64;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }
                                    });
                                }
                            }
                        },
                        15000,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.SLIME_BALL, 8));
                                    add(new ItemStack(Material.HOPPER, 16));
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE,64));
                                }
                            }
                        }
                ));
                add(new Quest(
                    "Muzyka część 1",
                    "Muszę przygotować scenę pod wydarzenie\nPomożesz?",
                    new ArrayList<IQuestItem>(){
                        {
                            add(new IQuestItemGive() {
                                @Override
                                public Material getMaterial() {
                                    return Material.REDSTONE;
                                }

                                @Override
                                public int getCount() {
                                    return 128;
                                }

                                @Override
                                public String getQuestItemDataId() {
                                    return "1";
                                }
                            });
                            add(new IQuestItemCraft() {
                                @Override
                                public Material getMaterial() {
                                    return Material.NOTE_BLOCK;
                                }

                                @Override
                                public int getCount() {
                                    return 512;
                                }

                                @Override
                                public String getQuestItemDataId() {
                                    return "2";
                                }
                            });
                        }
                    },
                    10000,
                    new ArrayList<ItemStack>(){

                    }
                ));
                add(new Quest(
                        "Muzyka część 2",
                        "Dobra teraz dekoracje",
                        new ArrayList<IQuestItem>(){
                            {
                                add(new IQuestItemGive() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.GLOWSTONE;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 256;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "1";
                                    }
                                });
                                add(new IQuestItemGive() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.GLASS;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 384;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "2";
                                    }
                                });
                                add(new IQuestItemGive() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.OAK_LEAVES;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 64;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "3";
                                    }
                                });
                                add(new IQuestItemGive() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.GRAY_CONCRETE;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 512;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "4";
                                    }
                                });
                            }
                        },
                        40000,
                        new ArrayList<ItemStack>(){

                        }
                ));
                add(new Quest(
                        "Piroman",
                        "Interesujesz się TNT?\nJest mega mocne..",
                        new ArrayList<IQuestItem>(){
                            {
                                add(new IQuestItemKill() {
                                    @Override
                                    public EntityType getEntityType() {
                                        return EntityType.CREEPER;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 500;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "1";
                                    }
                                });
                            }
                        },
                        0,
                        new ArrayList<ItemStack>(){
                            {
                                add(new ItemStack(Material.CREEPER_HEAD, 1));
                            }
                        }
                ));
            }
        }, new ArrayList<VillagerShopItem>(){
            {
                add(new VillagerShopItem(new ItemStack(Material.CHEST,1), 1, "Prosta, schludna skrzyneczka", 200));
                add(new VillagerShopItem(new ItemStack(Material.TRAPPED_CHEST,1), 1, "Przydatne pod pułapki", 250, 20));

                // 4 max
                add(new VillagerShopItem(new ItemStack(Material.REDSTONE,1), 2, "Kabelek", 7, 2));
                add(new VillagerShopItem(new ItemStack(Material.REDSTONE_TORCH,1), 2, "Aktywowany kabel na patyku :0", 7, 3));
                add(new VillagerShopItem(new ItemStack(Material.REPEATER,1), 2, "No co, przedłużacz", 21, 10));
                add(new VillagerShopItem(new ItemStack(Material.COMPARATOR,1), 2, "Potrzebujesz tego? Znalazłem w garażu", 43, 20));

                add(new VillagerShopItem(new ItemStack(Material.OBSERVER,1), 3, "Sam wytworzyłem. Obserwuje zmiany w otoczeniu", 200, 15));
                add(new VillagerShopItem(new ItemStack(Material.HOPPER,1), 3, "Metalowy lej, transportuje przedmioty", 200, 30));
                add(new VillagerShopItem(new ItemStack(Material.PISTON,1), 3, "Wypycha moby, przedmioty", 125, 10));

                add(new VillagerShopItem(new ItemStack(Material.REDSTONE_LAMP,1), 4, "Świeci jak ma prąd!", 100, 30));
                add(new VillagerShopItem(new ItemStack(Material.DISPENSER,1), 4, "Słyszałem o automatycznej farmie wełny", 100, 10));
                add(new VillagerShopItem(new ItemStack(Material.STICKY_PISTON,1), 4, "Zwykły tłok ale ze szlamem!", 450, 30));
                add(new VillagerShopItem(new ItemStack(Material.SLIME_BALL,1), 4, "Kula szlamu", 300, 15));

                add(new VillagerShopItem(new ItemStack(Material.TNT, 1), 7, "\"&cMega mocne\"", 75000));
            }
        }));
        AddShop(new VillagerData("Górnik", new ArrayList<Quest>(){
            {
                add(new Quest(
                        "Metale",
                        "Jak chcesz rozwinąć swój biznes?\nMusisz się na tym znać!",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.COBBLESTONE;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 128;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.IRON_INGOT;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 128;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.GOLD_INGOT;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 32;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }
                                    });
                                }
                            }
                        },
                        3250,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.OBSIDIAN,16));
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE,16));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Hobby",
                        "Interesujesz się tym.\nZobaczymy na co Cię stać.",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.COBBLESTONE;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 256;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.STONE;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 256;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                }
                            }
                        },
                        2500,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE,8));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Powrót",
                        "Przychodzę do Ciebie ze zleceniem",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.OAK_FENCE;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 64;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.OAK_PLANKS;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 128;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.LAVA_BUCKET;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 4;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.COAL;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 128;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "4";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.GRAVEL;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 32;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "4";
                                        }
                                    });
                                }
                            }
                        },
                        12000,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.EMERALD, 64));
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE,16));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Messa",
                        "Potrzebujemy złota, mam dobrą cenę",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.STRIPPED_OAK_LOG;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 512;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.GOLD_BLOCK;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 56;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                }
                            }
                        },
                        15800,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.EMERALD, 128));
                                    ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
                                    item.addEnchantment(Enchantment.DIG_SPEED, 5);
                                    item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,3);
                                    item.addEnchantment(Enchantment.DURABILITY, 3);
                                    add(item);
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE,64));
                                    add(new ItemStack(Material.NETHERITE_SCRAP, 12));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Szyb kopalniany",
                        "Zlokalizuj szyb kopalniany.\nMożesz mi przynieść materiały?",
                        new ArrayList<IQuestItem>(){
                            {
                                add(new IQuestItemKill() {
                                    @Override
                                    public EntityType getEntityType() {
                                        return EntityType.CAVE_SPIDER;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 500;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "1";
                                    }
                                });
                                add(new IQuestItemGive() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.RAIL;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 256;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "2";
                                    }
                                });
                                add(new IQuestItemGive() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.LAPIS_LAZULI;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 512;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "3";
                                    }
                                });
                                add(new IQuestItemGive() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.IRON_BLOCK;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 48;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "4";
                                    }
                                });
                            }
                        },
                        15000,
                        new ArrayList<ItemStack>(){
                            {
                                add(new ItemStack(Material.EMERALD, 128));
                                add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE,1));
                                add(new ItemStack(Material.COAL_BLOCK, 32));
                                ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
                                item.addEnchantment(Enchantment.DIG_SPEED, 5);
                                item.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS,3);
                                item.addEnchantment(Enchantment.DURABILITY, 3);
                                add(item);
                            }
                        }
                ));
                add(new Quest(
                        "Biblioteka",
                        "Przynieś mi przedmioty,\nktóre są potrzebne do biblioteki\nNiedługo mam grube zlecenie.",
                        new ArrayList<IQuestItem>(){
                            {
                                add(new IQuestItemGive() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.BOOKSHELF;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 256;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "1";
                                    }
                                });
                                add(new IQuestItemCraft() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.WHITE_CONCRETE_POWDER;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 512;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "2";
                                    }
                                });
                            }
                        },
                        20000,
                        new ArrayList<ItemStack>(){

                        }
                ));
                add(new Quest(
                        "Lotniskowiec",
                        "Wojsko skupuje materiały na lotniskowiec.\nWiesz ile płacą?!",
                        new ArrayList<IQuestItem>(){
                            {
                                add(new IQuestItemGive() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.BLACK_CONCRETE;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 2048;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "1";
                                    }
                                });
                                add(new IQuestItemGive() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.WHITE_CONCRETE_POWDER;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 256;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "2";
                                    }
                                });
                                add(new IQuestItemKill() {
                                    @Override
                                    public EntityType getEntityType() {
                                        return EntityType.SKELETON;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 100;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "3";
                                    }
                                });
                            }
                        },
                        200000,
                        new ArrayList<ItemStack>(){

                        }
                ));
                add(new Quest(
                        "Baza wojskowa",
                        "Wojsko skupuje kolejne materiały na bazę.",
                        new ArrayList<IQuestItem>(){
                            {
                                add(new IQuestItemGive() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.CARROT;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 1024;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "1";
                                    }
                                });
                                add(new IQuestItemCraft() {
                                    @Override
                                    public Material getMaterial() {
                                        return Material.STONE_BRICKS;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 2048;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "2";
                                    }
                                });
                            }
                        },
                        100000,
                        new ArrayList<ItemStack>(){

                        }
                ));
            }
        }, new ArrayList<VillagerShopItem>(){
            {
                ItemStack item = new ItemStack(Material.ENCHANTED_BOOK, 1);
                item.addUnsafeEnchantment(Main.getInstance().hammerEnch, 1);
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
                meta.addStoredEnchant(Main.getInstance().hammerEnch, 1, true);
                meta.setLore(new ArrayList<String>(){
                    {
                        add(ColorUtils.color("&7" + Main.getInstance().hammerEnch.getName()));
                    }
                });
                item.setItemMeta(meta);
                add(new VillagerShopItem(item, 8, "Szeroki Trzon z ogromną mocą! Kopie 3x3\nPołącz w kowadle",1000000));
            }
        }));
        AddShop(new VillagerData("Pszczelarz", new ArrayList<Quest>(){
            {
                add(new Quest(
                        "Przygotowanie",
                        "Przygotuj teren pod pszczoły!",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.CAMPFIRE;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 4;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.GLASS_BOTTLE;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 32;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.GLASS;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 128;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }
                                    });
                                }
                            }
                        },
                        2000,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE,16));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Początkowe pszczoły",
                        "Interesujesz się tym. Zobaczymy na co Cię stać.",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.HONEY_BOTTLE;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 16;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemGive() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.HONEY_BLOCK;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 64;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                }
                            }
                        },
                        20500,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.BEEHIVE, 1));
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE,128));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Urlop",
                        "Idę na urlop.. Wrócę za jakieś dwa dni z nowymi zadaniami!",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemCraft() {
                                        @Override
                                        public Material getMaterial() {
                                            return Material.COARSE_DIRT;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 16;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                }
                            }
                        },
                        256,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE,16));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Brutal",
                        "Właśnie otrzymaliśmy brutalne zlecenie...\nMusimy zabijać zarażone pszczoły",
                        new ArrayList<IQuestItem>(){
                            {
                                add(new IQuestItemKill() {
                                    @Override
                                    public EntityType getEntityType() {
                                        return EntityType.BEE;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 50;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "1";
                                    }
                                });
                            }
                        },
                        50000,
                        new ArrayList<ItemStack>(){
                            {
                                add(ItemStackUtils.createGuiItem(Material.TRIDENT, 1,ColorUtils.color("&cBrutalny trójząb")));
                            }
                        }
                ));
            }
        }, new ArrayList<VillagerShopItem>(){
            {
                add(new VillagerShopItem(new ItemStack(Material.DANDELION, 1), 0, "Mleczyk... mleczyk", 10, 3));
                add(new VillagerShopItem(new ItemStack(Material.POPPY, 1), 0, "Mmm kwiatek :)", 12, 4));

                add(new VillagerShopItem(new ItemStack(Material.CAMPFIRE, 1), 1, "Ognisko, dobrze wpływa na samopoczucie", 100, 10));
                add(new VillagerShopItem(new ItemStack(Material.BEEHIVE, 1), 1, "Domek!", 200, 20));

                add(new VillagerShopItem(new ItemStack(Material.HONEYCOMB, 1), 3, "Plasterek miodu", 100, 10));
                add(new VillagerShopItem(new ItemStack(Material.HONEYCOMB_BLOCK, 1), 2, "Blok piastru miodu", 10, 3));
                add(new VillagerShopItem(new ItemStack(Material.HONEY_BOTTLE, 1), 3, "Ale dobry miodek", 200, 20));

                add(new VillagerShopItem(new ItemStack(Material.HONEY_BLOCK, 1), 3, "Ale się lepi jak SLIME!", 1000, 100));
            }
        }));
        AddShop(new VillagerData("Lowca", new ArrayList<Quest>(){
            {
                add(new Quest(
                        "Łowca",
                        "Czas na wytworzenie zbroi dla Ciebie",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemCraft(){

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }

                                        @Override
                                        public Material getMaterial() {
                                            return Material.IRON_HELMET;
                                        }
                                        @Override
                                        public int getCount() {
                                            return 1;
                                        }
                                    });
                                    add(new IQuestItemCraft(){
                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }

                                        @Override
                                        public Material getMaterial() {
                                            return Material.IRON_CHESTPLATE;
                                        }
                                        @Override
                                        public int getCount() {
                                            return 1;
                                        }
                                    });
                                    add(new IQuestItemCraft(){
                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }

                                        @Override
                                        public Material getMaterial() {
                                            return Material.IRON_LEGGINGS;
                                        }
                                        @Override
                                        public int getCount() {
                                            return 1;
                                        }
                                    });
                                    add(new IQuestItemCraft(){
                                        @Override
                                        public String getQuestItemDataId() {
                                            return "4";
                                        }

                                        @Override
                                        public Material getMaterial() {
                                            return Material.IRON_BOOTS;
                                        }
                                        @Override
                                        public int getCount() {
                                            return 1;
                                        }
                                    });
                                }
                            }
                        },
                        2250,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE,16));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Tester smaku",
                        "Sprawdź jak smakują moby",
                        new ArrayList<IQuestItem>(){
                            {
                                {
                                    add(new IQuestItemKill() {
                                        @Override
                                        public EntityType getEntityType() {
                                            return EntityType.ZOMBIE;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 10;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "1";
                                        }
                                    });
                                    add(new IQuestItemKill() {
                                        @Override
                                        public EntityType getEntityType() {
                                            return EntityType.SKELETON;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 10;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "2";
                                        }
                                    });
                                    add(new IQuestItemKill() {
                                        @Override
                                        public EntityType getEntityType() {
                                            return EntityType.SPIDER;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 20;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return "3";
                                        }
                                    });
                                    add(new IQuestItemKill() {
                                        @Override
                                        public EntityType getEntityType() {
                                            return EntityType.HUSK;
                                        }

                                        @Override
                                        public int getCount() {
                                            return 10;
                                        }

                                        @Override
                                        public String getQuestItemDataId() {
                                            return null;
                                        }
                                    });
                                }
                            }
                        },
                        5250,
                        new ArrayList<ItemStack>(){
                            {
                                {
                                    add(new ItemStack(Material.EXPERIENCE_BOTTLE,16));
                                    add(new ItemStack(Material.DIAMOND_SWORD,1));
                                    add(new ItemStack(Material.SHIELD,1));
                                }
                            }
                        }
                ));
                add(new Quest(
                        "Co?",
                        "&kdfjuisfhydsfhsdasdkajdi sdjaisdhaisd",
                        new ArrayList<IQuestItem>(){
                            {
                                add(new IQuestItemKill() {
                                    @Override
                                    public EntityType getEntityType() {
                                        return EntityType.WITHER_SKELETON;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 50;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "1";
                                    }
                                });
                            }
                        },
                        5000,
                        new ArrayList<ItemStack>(){

                        }
                ));
                add(new Quest(
                        "Ostrzał",
                        "Polecam używać tarczy :wink:",
                        new ArrayList<IQuestItem>(){
                            {
                                add(new IQuestItemKill() {
                                    @Override
                                    public EntityType getEntityType() {
                                        return EntityType.SKELETON;
                                    }

                                    @Override
                                    public int getCount() {
                                        return 220;
                                    }

                                    @Override
                                    public String getQuestItemDataId() {
                                        return "2";
                                    }
                                });
                            }
                        },
                        6000,
                        new ArrayList<ItemStack>(){

                        }
                ));
            }
        }));

        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }


    public final HashMap<String, VillagerData> villagers = new HashMap<>();

    public void AddShop(VillagerData shop){
        villagers.put(shop.getId().toLowerCase(), shop);
        ModerrkowoLog.LogAdmin("Zarejestrowano nowy sklep " + shop.getName());
    }

    public ItemStack getItemOfShop(User u, VillagerData villagerData, VillagerShopItem item){
        PlayerVillagersData data = u.getVillagersData();
        PlayerVillagerData playerVillagerData = data.getVillagersData().get(villagerData.getId());
        Material mat;
        String name;
        boolean unlocked = playerVillagerData.getQuestIndex()+1 >= item.getRequiredLevel();
        if(unlocked){
            mat = item.getItem().getType();
            name = ColorUtils.color("&6" + ChatUtil.materialName(item.getItem().getType()));
        }else{
            mat = Material.IRON_BARS;
            name = ColorUtils.color("&c&k" + ChatUtil.materialName(item.getItem().getType()));
        }
        ItemStack i = item.getItem().clone();
        i.setType(mat);
        if(!unlocked){
            for(Enchantment enchs : i.getEnchantments().keySet()){
                i.removeEnchantment(enchs);
            }
        }
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        if(unlocked){
            //lore.add(ColorUtils.color("&eOpis"));
            lore.add(ColorUtils.color(ColorUtils.color("&8" + item.getDescription())));
            lore.add(" ");
            if(item.canSell()){
                if(item.getCost() <= u.getBank().money){
                    lore.add(ColorUtils.color("&7Cena: &a" + ChatUtil.getMoney(item.getCost())));
                }else{
                    lore.add(ColorUtils.color("&7Cena: &c" + ChatUtil.getMoney(item.getCost())));
                }
                lore.add(ColorUtils.color("&7Cena sprzedaży: &a" + ChatUtil.getMoney(item.getSellCost())));
            }else{
                if(item.getCost() <= u.getBank().money){
                    lore.add(ColorUtils.color("&aCena: " + ChatUtil.getMoney(item.getCost())));
                }else{
                    lore.add(ColorUtils.color("&cCena: " + ChatUtil.getMoney(item.getCost())));
                }
            }
            if(item.canSell()){
                lore.add(ColorUtils.color("&8LPM aby zakupić | PPM aby sprzedać"));
            }else{
                lore.add(ColorUtils.color("&8Kliknij aby zakupić"));
            }
        }else{
            lore.add(ColorUtils.color("&eZostanie odblokowane na " + item.getRequiredLevel() + " poz."));
        }
        meta.setLore(lore);
        i.setItemMeta(meta);
        return i;
    }
    public ItemStack getItemOfQuest(VillagerData villagerData, PlayerVillagerData playerVillagerData, Player player){
        Material mat;
        String name;
        String details;
        if(playerVillagerData.getQuestIndex() >= villagerData.getQuests().size()){
            mat = Material.DIAMOND;
            name = ColorUtils.color("&aZakończono wszystkie zadania");
            details = ColorUtils.color("&8Gratulacje! Zakończyłeś już wszystkie zadania");
            ItemStack item = new ItemStack(mat, playerVillagerData.getQuestIndex()+1);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            ArrayList<String> lore = new ArrayList<String>(){
                {
                    add(details);
                }
            };
            meta.setLore(lore);
            item.setItemMeta(meta);
            return item;
        }
        Quest q = villagerData.getQuests().get(playerVillagerData.getQuestIndex());
        if(playerVillagerData.isActiveQuest()){
            mat = Material.GOLD_INGOT;
            name = ColorUtils.color("&a" + q.getName());
            details = ColorUtils.color("&8LPM aby oddać &8| &cPPM aby anulować");
            //
        }else{
            mat = Material.BOOK;
            name = ColorUtils.color("&a" + q.getName());
            details = ColorUtils.color("&8Kliknij aby zaakceptować");
        }
        int amount = playerVillagerData.getQuestIndex();
        if(amount == 0){
            amount = 1;
        }
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        ArrayList<String> lore = new ArrayList<>();
        String[] lines = q.getDescription().split("\\n");
        for(String line : lines){
            lore.add(ColorUtils.color("&8" + line));
        }
        lore.add(" ");
        lore.add(ColorUtils.color("&eZadanie:"));
        for(IQuestItem qItem : q.getQuestItems()){

            if(qItem instanceof IQuestItemGive){
                IQuestItemGive questItem = (IQuestItemGive) qItem;
                int value = questItem.getCount();
                if(playerVillagerData.isActiveQuest()){
                    value = questItem.getCount()-playerVillagerData.getQuestItemData().get(questItem.getQuestItemDataId());
                }
                boolean hasItem = value <= 0;
                if(hasItem){
                    String count = "";
                    if(questItem.getCount() != 1){
                        count = questItem.getCount() + "";
                    }
                    lore.add(ColorUtils.color("&a✔ " + questItem.getQuestItemPrefix() + " " + count + " " + ChatUtil.materialName(questItem.getMaterial())));
                }else{
                    lore.add(ColorUtils.color("&c✘ " + questItem.getQuestItemPrefix() + " " + value + " " + ChatUtil.materialName(questItem.getMaterial())));
                }
            }
            if(qItem instanceof IQuestItemCraft){
                IQuestItemCraft questItem = (IQuestItemCraft) qItem;
                int value = questItem.getCount();
                if(playerVillagerData.isActiveQuest()){
                    value = questItem.getCount()-playerVillagerData.getQuestItemData().get(questItem.getQuestItemDataId());
                }
                boolean hasItem = value <= 0;
                if(hasItem){
                    String count = "";
                    if(questItem.getCount() != 1){
                        count = questItem.getCount() + "";
                    }
                    lore.add(ColorUtils.color("&a✔ " + questItem.getQuestItemPrefix() + " " + count + " " + ChatUtil.materialName(questItem.getMaterial())));
                }else{
                    lore.add(ColorUtils.color("&c✘ " + questItem.getQuestItemPrefix() + " " + value + " " + ChatUtil.materialName(questItem.getMaterial())));
                }
            }
            if(qItem instanceof IQuestItemKill){
                IQuestItemKill questItem = (IQuestItemKill) qItem;
                int value = questItem.getCount();
                if(playerVillagerData.isActiveQuest()){
                    value = questItem.getCount()-playerVillagerData.getQuestItemData().get(questItem.getQuestItemDataId());
                }
                boolean hasItem = value <= 0;
                if(hasItem){
                    lore.add(ColorUtils.color("&a✔ " + questItem.getQuestItemPrefix() + " " + questItem.getCount() + " " + ChatUtil.materialName(questItem.getEntityType())));
                }else{
                    lore.add(ColorUtils.color("&c✘ " + questItem.getQuestItemPrefix() + " " + value + " " + ChatUtil.materialName(questItem.getEntityType())));
                }
            }

        }
        lore.add(" ");
        lore.add(ColorUtils.color("&eNagroda:"));
        for(ItemStack przedmiot : q.getRewardItems()){
            int przedmiotAmount = przedmiot.getAmount();
            String prefix = "";
            if(przedmiot.getEnchantments().size() > 0){
                prefix = "Enchanted ";
            }
            if(przedmiotAmount > 1){
                lore.add(ColorUtils.color("&a" + prefix + ChatUtil.materialName(przedmiot.getType()) + " x" + przedmiotAmount));
            }else{
                lore.add(ColorUtils.color("&a" + prefix + ChatUtil.materialName(przedmiot.getType())));
            }
        }
        lore.add(ColorUtils.color("&a" + ChatUtil.getMoney(q.getMoney())));
        if(!details.equals("")){
            lore.add(" ");
            lore.add(details);
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public Inventory getInventoryOfVillager(VillagerData villager, Player player){
        User u;
        try {
            u = ModerrkowoDatabase.getInstance().getUserManager().getUser(player.getUniqueId());
        } catch (UserNotLoaded userNotLoaded) {
            System.out.println("User Not Loaded");
            userNotLoaded.printStackTrace();
            return null;
        }
        try{
            if(!u.getVillagersData().getVillagersData().containsKey(villager.getId())){
                u.getVillagersData().getVillagersData().put(villager.getId(), new PlayerVillagerData(villager.getId(), 0, false, new HashMap<>()));
            }
        }catch(Exception e){
            System.out.println("Exception on fixing");
            e.printStackTrace();
        }
        int size = 54 - 1;

        Inventory inv;
        if(villager.isShop()){
            inv = Bukkit.createInventory(null, 54, villager.getName());
            for (int i = size - 8; i != size + 1; i++) {
                inv.setItem(i, ItemStackUtils.createGuiItem(Material.BLACK_STAINED_GLASS_PANE, 1, " "));
            }
            inv.setItem(size - 4, getItemOfQuest(villager, u.getVillagersData().getVillagersData().get(villager.getId()), player));
            if (villager.getShopItems().size() > 0) {
                for (int i = 0; i != villager.getShopItems().size(); i++) {
                    inv.setItem(i, getItemOfShop(u, villager, villager.getShopItems().get(i)));
                }
            }
        }else{
            inv = Bukkit.createInventory(null, 9, villager.getName());
            size = 9-1;
            for (int i = 0; i != size + 1; i++) {
                inv.setItem(i, ItemStackUtils.createGuiItem(Material.BLACK_STAINED_GLASS_PANE, 1, " "));
            }
            inv.setItem(size - 4, getItemOfQuest(villager, u.getVillagersData().getVillagersData().get(villager.getId()), player));
        }
        return inv;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        if (e.getClickedInventory() == null) {
            return;
        }
        if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
            if (e.getView().getTitle().contains(ColorUtils.color("&c&lQ"))) {
                e.setCancelled(true);
                return;
            }
            return;
        }
        if (e.getView().getTitle().contains(ColorUtils.color("&c&lQ"))) {
            e.setCancelled(true);
            String villagerName = e.getView().getTitle().replace(ColorUtils.color("&c&lQ"),"").replace(ColorUtils.color("&9&lS"), "").replace(ColorUtils.color("&7"), "").replace(" ", "");
            if(!villagers.containsKey(villagerName.toLowerCase())){
                System.out.println("Sklep nie istnieje!");
            }
            // Varibales
            VillagerData villagerData = villagers.get(villagerName.toLowerCase());
            Player p = (Player) e.getWhoClicked();
            User u;
            // Get user
            try {
                u = ModerrkowoDatabase.getInstance().getUserManager().getUser(p.getUniqueId());
            } catch (UserNotLoaded userNotLoaded) {
                userNotLoaded.printStackTrace();
                p.sendMessage(ColorUtils.color("&cWystąpił błąd. Jeżeli się powtarza, wyjdź i wejdź na serwer."));
                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                return;
            }
            // Shop
            PlayerVillagerData data = u.getVillagersData().getVillagersData().get(villagerData.getId());
            int questSlot;
            if(villagerData.isShop()){
                // SHOP
                if(e.getSlot() > -1 && e.getSlot() < 45){
                    // SHOP
                    if(e.getSlot() > villagerData.getShopItems().size()){
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1, 1);
                        return;
                    }
                    VillagerShopItem shopItem = villagerData.getShopItems().get(e.getSlot());
                    if(e.getAction() == InventoryAction.PICKUP_ALL){
                        boolean unlocked = data.getQuestIndex()+1 >= shopItem.getRequiredLevel();
                        if(unlocked){
                            if(u.getBank().money >= shopItem.getCost()){
                                u.getBank().money -= shopItem.getCost();
                                //p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &c- " + ChatUtil.getMoney(shopItem.getCost())));
                                //p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &a+ " + ChatUtil.materialName(shopItem.getItem().getType()) + " x" + shopItem.getItem().getAmount()));
                                if(p.getInventory().firstEmpty() != -1){
                                    p.getInventory().addItem(shopItem.getItem().clone());
                                }else{
                                    p.getWorld().dropItem(p.getLocation(), shopItem.getItem().clone());
                                }
                                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);
                                ModerrkowoLog.LogAdmin(ColorUtils.color("&6" + p.getName() + " &7zakupił &6" + ChatUtil.materialName(shopItem.getItem().getType()) + " &7od " + villagerData.getName()));
                            }else{
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                                p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &cNiestety nie dogadamy się. Nie posiadasz tyle pieniędzy."));
                            }
                        }else{
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                            p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &cNie, tego nie sprzedaję."));
                        }
                        return;
                    }
                    if(e.getAction() == InventoryAction.PICKUP_HALF){
                        boolean unlocked = data.getQuestIndex()+1 >= shopItem.getRequiredLevel();
                        if(unlocked){
                            if(ItemStackUtils.getCountOfMaterial(p, shopItem.getItem().getType()) > 0){
                                ItemStackUtils.consumeItem(p, 1, shopItem.getItem().getType());
                                u.getBank().money += shopItem.getSellCost();
                                p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &c- " + ChatUtil.materialName(shopItem.getItem().getType()) + " x1"));
                                p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &a+ " + ChatUtil.getMoney(shopItem.getSellCost())));
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_TRADE,1,1);
                                ModerrkowoLog.LogAdmin(ColorUtils.color("&6" + p.getName() + " &7sprzedał &6" + ChatUtil.materialName(shopItem.getItem().getType()) + " &7do " + villagerData.getName()));
                            }else{
                                p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                                p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &cChcesz sprzedać coś czego nie masz?"));
                                return;
                            }
                        }else{
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                            p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &cNiestety, tego nie kupuję."));
                            return;
                        }
                    }
                }
                // QuestSlot
                questSlot = 49;
            }else{
                // QuestSlot
                questSlot = 4;
            }
            if(e.getSlot() == questSlot){
                // IF MAX QUEST
                if(data.getQuestIndex() >= villagerData.getQuests().size()){
                    p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &7Niestety nie mam już nic dla Ciebie..."));
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_AMBIENT,1,1);
                    p.openInventory(getInventoryOfVillager(villagerData, p));
                    return;
                }
                // IF HAS ACTIVE QUEST
                boolean hasQuest = false;
                String otherVillagerName = null;
                for(PlayerVillagerData villagers : u.getVillagersData().getVillagersData().values()){
                    if(villagers.getVillagerId().equals(villagerData.getId())){
                        continue;
                    }
                    if(villagers.isActiveQuest()){
                        otherVillagerName = this.villagers.get(villagers.getVillagerId().toLowerCase()).getName();
                        hasQuest = true;
                    }
                }
                if(hasQuest){
                    p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &7Najpierw zakończ zadanie u " + otherVillagerName));
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                    return;
                }
                Quest activeQuest = villagerData.getQuests().get(data.getQuestIndex());
                if(data.isActiveQuest()){
                    // Jeżeli chce anulować quest
                    if(e.getAction() == InventoryAction.PICKUP_HALF){
                        for(IQuestItem item : activeQuest.getQuestItems()){
                            if(item instanceof IQuestItemGive){
                                if(data.getQuestItemData().get(item.getQuestItemDataId()) > 0){
                                    p.getLocation().getWorld().dropItem(p.getLocation(), new ItemStack(((IQuestItemGive) item).getMaterial(), ((IQuestItemGive) item).getCount()));
                                }
                            }
                        }
                        data.setActiveQuest(false);
                        if(data.getQuestItemData() == null){
                            data.setQuestItemData(new HashMap<>());
                        }else{
                            data.getQuestItemData().clear();
                        }
                        p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &7Szkoda, że się rozmyśliłeś"));
                        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                        p.openInventory(getInventoryOfVillager(villagerData, p));
                        return;
                    }
                    // Jeżeli chce oddać itemki
                    if(e.getAction() == InventoryAction.PICKUP_ALL){
                        int haveItem = 0;
                        for(IQuestItem item : activeQuest.getQuestItems())
                        {
                            if(item instanceof IQuestItemGive){
                                IQuestItemGive itemGive = (IQuestItemGive) item;
                                int items = data.getQuestItemData().get(item.getQuestItemDataId());
                                int temp = items;
                                if(temp >= itemGive.getCount()){
                                    haveItem++;
                                }else{
                                    int required = itemGive.getCount();
                                    required = required-temp;
                                    int have = ItemStackUtils.getCountOfMaterial(p, itemGive.getMaterial());
                                    if(have > required){
                                        have = required;
                                    }
                                    ItemStackUtils.consumeItem(p, have, itemGive.getMaterial());
                                    temp += have;
                                    data.getQuestItemData().replace(item.getQuestItemDataId(), items, temp);
                                    int count = itemGive.getCount()-temp;
                                    if(temp >= itemGive.getCount()){
                                        p.sendMessage(ColorUtils.color("&cMusisz jeszcze przynieść " + count + " " + ChatUtil.materialName(itemGive.getMaterial())));
                                    }else{
                                        haveItem++;
                                    }
                                }
                            }
                            if(item instanceof IQuestItemCraft){
                                IQuestItemCraft itemCraft = (IQuestItemCraft) item;
                                int temp = data.getQuestItemData().get(item.getQuestItemDataId());
                                if(temp >= itemCraft.getCount()){
                                    haveItem++;
                                }else{
                                    int count = itemCraft.getCount()-temp;
                                    p.sendMessage(ColorUtils.color("&cMusisz jeszcze wytworzyć " + count + " " + ChatUtil.materialName(itemCraft.getMaterial())));
                                }
                            }
                            if(item instanceof IQuestItemKill){
                                IQuestItemKill itemKill = (IQuestItemKill) item;
                                int temp = data.getQuestItemData().get(item.getQuestItemDataId());
                                if(temp >= itemKill.getCount()){
                                    haveItem++;
                                }else{
                                    int count = itemKill.getCount()-temp;
                                    p.sendMessage(ColorUtils.color("&cMusisz jeszcze zabić " + count + " " + ChatUtil.materialName(itemKill.getEntityType())));
                                }
                            }
                        }
                        boolean haveItems = haveItem == activeQuest.getQuestItems().size();
                        if(haveItems){
                            u.getBank().money += activeQuest.getMoney();
                            p.sendMessage(" ");
                            p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &a+ " + ChatUtil.getMoney(activeQuest.getMoney())));
                            p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &aInteresy z tobą to przyjemność!"));
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_CELEBRATE,1,1);
                            data.setActiveQuest(false);
                            if(data.getQuestItemData() == null){
                                data.setQuestItemData(new HashMap<>());
                            }else{
                                data.getQuestItemData().clear();
                            }
                            data.setQuestIndex(data.getQuestIndex()+1);
                            p.openInventory(getInventoryOfVillager(villagerData, p));
                            for(ItemStack item : activeQuest.getRewardItems()){
                                boolean isEmpty = true;
                                for (ItemStack items : p.getInventory().getContents()) {
                                    if(items != null) {
                                        isEmpty = false;
                                        break;
                                    }
                                }
                                if(p.getInventory().firstEmpty() != -1) {
                                    p.getInventory().addItem(item);
                                } else {
                                    p.getWorld().dropItem(p.getLocation(), item);
                                    p.sendMessage(ColorUtils.color("&cMasz pełny ekwipunek. Więc przedmiot wyleciał z Ciebie"));
                                }
                            }
                            ModerrkowoLog.LogAdmin(ColorUtils.color("&6" + p.getName() + " &7zakończył Questa &6" + activeQuest.getName() + " &7od " + villagerData.getName()));
                        }else{
                            p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &7Nie wykonałeś wszystkich zadań.."));
                            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO,1,1);
                            p.openInventory(getInventoryOfVillager(villagerData, p));
                        }
                        return;
                    }
                }else{
                    // Przyjmuje zadanie
                    if(data.getQuestItemData() == null){
                        data.setQuestItemData(new HashMap<>());
                    }else{
                        data.getQuestItemData().clear();
                    }
                    data.setActiveQuest(true);
                    for(IQuestItem item : activeQuest.getQuestItems()){
                        data.getQuestItemData().put(item.getQuestItemDataId(), 0);
                    }
                    p.sendMessage(ColorUtils.color(villagerData.getName() + " &6> &aDzięki, że przyjąłeś zadanie."));
                    p.openInventory(getInventoryOfVillager(villagerData, p));
                    p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_YES,1,1);
                }
            }
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void click(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Entity entity = e.getRightClicked();

        if (entity.getType() == EntityType.VILLAGER) {
            if (entity.isCustomNameVisible()) {
                if (entity.getCustomName() == null) {
                    return;
                }
                if (entity.getCustomName().contains(ColorUtils.color("&c&lQ"))) {
                    String villagerName =
                            entity.getCustomName()
                            .replace(ColorUtils.color("&c&lQ"),"")
                            .replace(ColorUtils.color("&9&lS"), "")
                            .replace(ColorUtils.color("&7"), "")
                            .replace(" ", "");

                    if(villagers.containsKey(villagerName.toLowerCase())){
                        VillagerData villagerData = villagers.get(villagerName.toLowerCase());
                        e.setCancelled(true);
                        p.openInventory(
                                getInventoryOfVillager(villagerData, p)
                        );
                        p.playSound(
                                p.getLocation(),
                                Sound.ENTITY_VILLAGER_TRADE,
                                1,
                                1
                        );
                        double data = 0.1;
                    }
                }
            }
        }
    }

    //<editor-fold> IQuestItemKill
    @EventHandler
    public void kill(EntityDeathEvent e){
        if(e.getEntity() instanceof Player){
            return;
        }
        if(e.getEntity().getKiller() == null){
            return;
        }
        try{
            Player p = e.getEntity().getKiller();
            User u = ModerrkowoDatabase.getInstance().getUserManager().getUser(p.getUniqueId());
            PlayerVillagerData data = null;
            for(PlayerVillagerData villagers : u.getVillagersData().getVillagersData().values()){
                if(villagers.isActiveQuest()){
                    data = villagers;
                    break;
                }
            }
            if(data == null){
                return;
            }
            VillagerData villager = villagers.get(data.getVillagerId().toLowerCase());
            Quest quest = villager.getQuests().get(data.getQuestIndex());
            for(IQuestItem item : quest.getQuestItems()){
                if(item instanceof IQuestItemKill){
                    IQuestItemKill craftItem = (IQuestItemKill) item;
                    if(craftItem.getEntityType().equals(e.getEntityType())){
                        int recipeAmount = 1;
                        int items = data.getQuestItemData().get(craftItem.getQuestItemDataId());
                        int temp = items;
                        temp += recipeAmount;
                        data.getQuestItemData().replace(craftItem.getQuestItemDataId(), items, temp);
                        p.sendMessage(ColorUtils.color(villager.getName() + " &6> &aZabito " + ChatUtil.materialName(e.getEntityType())));
                    }
                }
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
    //</editor-fold> IQuestItemKill
    //<editor-fold> IQuestItemCraft
    public static int fits(ItemStack stack, Inventory inv) {
        ItemStack[] contents = inv.getContents();
        int result = 0;

        for (ItemStack is : contents)
            if (is == null)
                result += stack.getMaxStackSize();
            else if (is.isSimilar(stack))
                result += Math.max(stack.getMaxStackSize() - is.getAmount(), 0);

        return result;
    }
    public static int getMaxCraftAmount(CraftingInventory inv) {
        if (inv.getResult() == null)
            return 0;

        int resultCount = inv.getResult().getAmount();
        int materialCount = Integer.MAX_VALUE;

        for (ItemStack is : inv.getMatrix())
            if (is != null && is.getAmount() < materialCount)
                materialCount = is.getAmount();

        return resultCount * materialCount;
    }

    @EventHandler
    public void craft(CraftItemEvent e){
        if(e.getInventory().getResult() == null || e.getInventory().getResult().getType().equals(Material.AIR)){
            return;
        }
        try{
            Player p = (Player) e.getWhoClicked();
            User u = ModerrkowoDatabase.getInstance().getUserManager().getUser(p.getUniqueId());
            PlayerVillagerData data = null;
            for(PlayerVillagerData villagers : u.getVillagersData().getVillagersData().values()){
                if(villagers.isActiveQuest()){
                    data = villagers;
                    break;
                }
            }
            if(data == null){
                return;
            }
            VillagerData villager = villagers.get(data.getVillagerId().toLowerCase());
            Quest quest = villager.getQuests().get(data.getQuestIndex());
            for(IQuestItem item : quest.getQuestItems()){
                if(item instanceof IQuestItemCraft){
                    IQuestItemCraft craftItem = (IQuestItemCraft) item;
                    if(craftItem.getMaterial().equals(e.getInventory().getResult().getType())){
                        int recipeAmount = e.getInventory().getResult().getAmount();
                        ClickType click = e.getClick();
                        switch (click) {
                            case NUMBER_KEY:
                                // If hotbar slot selected is full, crafting fails (vanilla behavior, even when
                                // items match)
                                if (e.getWhoClicked().getInventory().getItem(e.getHotbarButton()) != null)
                                    recipeAmount = 0;
                                break;

                            case DROP:
                            case CONTROL_DROP:
                                // If we are holding items, craft-via-drop fails (vanilla behavior)
                                ItemStack cursor = e.getCursor();
                                // Apparently, rather than null, an empty cursor is AIR. I don't think that's
                                // intended.
                                if (cursor != null && cursor.getType().equals(Material.AIR)) recipeAmount = 0;
                                break;

                            case SHIFT_RIGHT:
                            case SHIFT_LEFT:
                                // Fixes ezeiger92/QuestWorld2#40
                                if (recipeAmount == 0)
                                    break;

                                int maxCraftable = getMaxCraftAmount(e.getInventory());
                                int capacity = fits(e.getInventory().getResult(), e.getView().getBottomInventory());

                                // If we can't fit everything, increase "space" to include the items dropped by
                                // crafting
                                // (Think: Un crafting 8 iron blocks into 1 slot)
                                if (capacity < maxCraftable)
                                    maxCraftable = ((capacity + recipeAmount - 1) / recipeAmount) * recipeAmount;

                                recipeAmount = maxCraftable;
                                break;
                            default:
                        }
                        int items = data.getQuestItemData().get(craftItem.getQuestItemDataId());
                        int temp = items;
                        temp += recipeAmount;
                        data.getQuestItemData().replace(craftItem.getQuestItemDataId(), items, temp);
                        p.sendMessage(ColorUtils.color(villager.getName() + " &6> &aWytworzono " + recipeAmount + " " + ChatUtil.materialName(e.getInventory().getResult().getType())));
                    }
                }
            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
    //</editor-fold> IQuestItemCraft

}
