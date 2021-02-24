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

        AddVillager(new VillagerData("Drwal", new ArrayList<Quest>(){
            {
                add(new Quest(
                        "Początek",
                        "Potrzebuję wędki.\nOgarniesz mi?",new ArrayList<IQuestItem>(){
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
                    }
                },0,new ArrayList<ItemStack>(){
                    {
                        add(Main.getInstance().banknot.generateItem(1, 1000));
                        add(ItemStackUtils.createGuiItem(Material.EXPERIENCE_BOTTLE, 16, ColorUtils.color("&aButelki z doświadczeniem")));
                    }
                }));
            }
        }, new ArrayList<VillagerShopItem>(){
            {

            }
        }));
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }


    public final HashMap<String, VillagerData> villagers = new HashMap<>();

    public void AddVillager(VillagerData shop){
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
            String przedmiotName = ChatUtil.materialName(przedmiot.getType());
            String prefix = "";
            if(przedmiot.getEnchantments().size() > 0){
                prefix = "Enchanted ";
            }
            if(przedmiot.hasItemMeta() && przedmiot.getItemMeta().hasDisplayName()){
                przedmiotName = przedmiot.getItemMeta().getDisplayName();
            }

            if(przedmiotAmount > 1){
                lore.add(ColorUtils.color("&a" + prefix + przedmiotName + " x" + przedmiotAmount));
            }else{
                lore.add(ColorUtils.color("&a" + prefix + przedmiotName));
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
                                    ItemStackUtils.addItemStackToPlayer(p, new ItemStack(((IQuestItemGive) item).getMaterial(), data.getQuestItemData().get(item.getQuestItemDataId())));
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
