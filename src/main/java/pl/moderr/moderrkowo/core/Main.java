package pl.moderr.moderrkowo.core;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import pl.moderr.moderrkowo.core.commands.admin.*;
import pl.moderr.moderrkowo.core.commands.user.BadgeCommand;
import pl.moderr.moderrkowo.core.commands.user.information.*;
import pl.moderr.moderrkowo.core.commands.user.messages.HelpopCommand;
import pl.moderr.moderrkowo.core.commands.user.messages.MessageCommand;
import pl.moderr.moderrkowo.core.commands.user.messages.ReplyCommand;
import pl.moderr.moderrkowo.core.commands.user.teleportation.*;
import pl.moderr.moderrkowo.core.commands.user.weather.PogodaCommand;
import pl.moderr.moderrkowo.core.custom.cuboids.CuboidsManager;
import pl.moderr.moderrkowo.core.custom.PaySign;
import pl.moderr.moderrkowo.core.custom.antylogout.AntyLogoutManager;
import pl.moderr.moderrkowo.core.custom.discord.DiscordManager;
import pl.moderr.moderrkowo.core.custom.enchantments.HammerEnchantment;
import pl.moderr.moderrkowo.core.custom.enchantments.MultihookEnchantment;
import pl.moderr.moderrkowo.core.custom.events.drop.DropEvent;
import pl.moderr.moderrkowo.core.custom.fishing.FishListener;
import pl.moderr.moderrkowo.core.custom.items.FireballManager;
import pl.moderr.moderrkowo.core.custom.lootchests.ShulkerDropBox;
import pl.moderr.moderrkowo.core.custom.npc.NPCManager;
import pl.moderr.moderrkowo.core.custom.spawn.SpawnManager;
import pl.moderr.moderrkowo.core.custom.timevoter.TimeVoter;
import pl.moderr.moderrkowo.core.custom.villagers.VillagerManager;
import pl.moderr.moderrkowo.core.custom.villagers.data.*;
import pl.moderr.moderrkowo.core.custom.economy.*;
import pl.moderr.moderrkowo.core.custom.listeners.*;
import pl.moderr.moderrkowo.core.utils.ChatUtil;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.HexResolver;
import pl.moderr.moderrkowo.core.utils.Logger;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.data.PlayerVillagerData;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.ConnectionIsNotOpenedException;
import pl.moderr.moderrkowo.database.exceptions.ConnectionReconnectException;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public final class Main extends JavaPlugin {

    // Instance
    private static Main instance;
    // Managers
    public CuboidsManager instanceCuboids;
    public AntyLogoutManager instanceAntyLogout;
    public RynekManager instanceRynekManager;
    public VillagerManager villagerManager;
    public TimeVoter timeVoter;
    public DiscordManager discordManager;
    public SpawnManager spawn;
    public WithdrawCommand banknot;
    public ShulkerDropBox shulkerDropBox;
    // Listeners
    public DatabaseListener instanceDatabaseListener;
    // Events
    public DropEvent dropEvent;
    // Enchantments
    public HashMap<String, Enchantment> customEnchants = new HashMap<>();
    public HammerEnchantment hammerEnchantment = null;
    public MultihookEnchantment multihookEnchantment = null;
    // Files
    public File dataFile = new File(getDataFolder(), "data.yml");
    public FileConfiguration dataConfig;
    // Commands
    public BadgeCommand badgeCommand;

    @Override
    public void onEnable() {
        // Start
        long start = System.currentTimeMillis();
        Logger.logPluginMessage("Wczytywanie wtyczki Main");
        // Constructor
        instance = this;
        // Load configuration file
        LoadPluginConfig();
        // Load custom enchantments
        LoadEnchantments();
        // Load custom items and drop
        banknot = new WithdrawCommand();
        shulkerDropBox = new ShulkerDropBox();
        // Load asynchronous listeners and commands
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            LoadListeners();
            LoadCommands();
        });
        // Start random auto message
        StartAutoMessage();
        // Load world
        LoadWorlds();
        // Load data.yml
        LoadDataYML();
        // Start cuboids system
        StartCuboids();
        // Start custom events
        StartEvents();
        // Start villager quest and shop system
        StartVillagers();
        // Start discord gate
        StartDiscordBot();
        // Enables scoreboard for all players
        StartScoreboard();
        // Start rynek
        StartRynek();
        // Start time voter
        StartTimeVoter();
        // Finish
        Logger.logPluginMessage("Wczytano plugin [CORE] w &8(&a" + (System.currentTimeMillis() - start) + "ms&8)");
        Logger.logPluginMessage("Wczytano wersję pluginu 2.0");
    }

    private void LoadPluginConfig() {
        //<editor-fold> Config
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveConfig();
        Logger.logPluginMessage("Wczytano config");
        //</editor-fold> Config
    }
    private void LoadEnchantments() {
        //<editor-fold> Enchantments
        hammerEnchantment = new HammerEnchantment();
        Bukkit.getPluginManager().registerEvents(hammerEnchantment, this);
        loadEnchantments(hammerEnchantment);
        Logger.logPluginMessage("Załadowano Szeroki Trzon");
        multihookEnchantment = new MultihookEnchantment();
        Bukkit.getPluginManager().registerEvents(multihookEnchantment, this);
        loadEnchantments(multihookEnchantment);
        Logger.logPluginMessage("Załadowano " + multihookEnchantment.getName());
        //</editor-fold> Enchantments
    }
    private void LoadCommands() {
        //<editor-fold> Commands
        Objects.requireNonNull(getCommand("alogi")).setExecutor(new ALogiCommand());
        Objects.requireNonNull(getCommand("ahelpop")).setExecutor(new AHelpopCommand());
        Objects.requireNonNull(getCommand("adminchat")).setExecutor(new AdminChatCommand());
        Objects.requireNonNull(getCommand("invsee")).setExecutor(new InvseeCommand());
        Objects.requireNonNull(getCommand("sendalert")).setExecutor(new SendAlertCommand());
        Objects.requireNonNull(getCommand("chat")).setExecutor(new ChatCommand());
        Objects.requireNonNull(getCommand("endersee")).setExecutor(new EnderseeCommand());
        Objects.requireNonNull(getCommand("vanish")).setExecutor(new VanishCommand());
        Objects.requireNonNull(getCommand("villager")).setExecutor(new VillagerCommand());
        Objects.requireNonNull(getCommand("nazwa")).setExecutor(new NazwaCommand());
        Objects.requireNonNull(getCommand("tpa")).setExecutor(new TPACommand());
        Objects.requireNonNull(getCommand("tpdeny")).setExecutor(new TPDeny());
        Objects.requireNonNull(getCommand("tpaccept")).setExecutor(new TPAccept());
        Objects.requireNonNull(getCommand("sethome")).setExecutor(new SetHomeCommand());
        Objects.requireNonNull(getCommand("home")).setExecutor(new HomeCommand());
        Objects.requireNonNull(getCommand("pogoda")).setExecutor(new PogodaCommand());
        Objects.requireNonNull(getCommand("helpop")).setExecutor(new HelpopCommand());
        Objects.requireNonNull(getCommand("msg")).setExecutor(new MessageCommand());
        Objects.requireNonNull(getCommand("reply")).setExecutor(new ReplyCommand());
        Objects.requireNonNull(getCommand("say")).setExecutor(new SayCommand());
        Objects.requireNonNull(getCommand("regulamin")).setExecutor(new RegulaminCommand());
        Objects.requireNonNull(getCommand("discord")).setExecutor(new DiscordCommand());
        Objects.requireNonNull(getCommand("mkick")).setExecutor(new MKickCommand());
        Objects.requireNonNull(getCommand("craftingdzialki")).setExecutor(new CraftingDzialkaCommand());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new SetSpawnCommand());
        Objects.requireNonNull(getCommand("abank")).setExecutor(new ABankCommand());
        Objects.requireNonNull(getCommand("przelej")).setExecutor(new PrzelejCommand());
        Objects.requireNonNull(getCommand("topka")).setExecutor(new TopkaCommand());
        Objects.requireNonNull(getCommand("mban")).setExecutor(new MBanCommand());
        Objects.requireNonNull(getCommand("gamemode")).setExecutor(new GameModeCommand());
        Objects.requireNonNull(getCommand("fly")).setExecutor(new FlyCommand());
        Objects.requireNonNull(getCommand("sidebar")).setExecutor(new SidebarCommand());
        Objects.requireNonNull(getCommand("drop")).setExecutor(new DropCommand());
        Objects.requireNonNull(getCommand("unactiveallquest")).setExecutor(new UnactiveAllQuest());
        Objects.requireNonNull(getCommand("zmiany")).setExecutor(new ZmianyCommand());
        Objects.requireNonNull(getCommand("aquest")).setExecutor(new QuestCommand());
        Objects.requireNonNull(getCommand("npc")).setExecutor(new NPCManager());
        Objects.requireNonNull(getCommand("jsonitem")).setExecutor(new JsonItemCommand());
        Objects.requireNonNull(getCommand("abadge")).setExecutor(new ABadgeCommand());
        Objects.requireNonNull(getCommand("badge")).setExecutor(badgeCommand);
        Objects.requireNonNull(getCommand("wyplac")).setExecutor(banknot);
        Objects.requireNonNull(getCommand("aenchant")).setExecutor(new AEnchantment());
        Logger.logPluginMessage("Wczytano komendy");
        //</editor-fold> Commands
    }
    private void StartAutoMessage() {
        //<editor-fold> AutoMessage
        ArrayList<String> message = new ArrayList<>();
        /*message.add("&cPamiętajcie że to 2 dniowy okres testowy)");
        message.add("&cOkres testowy kończy się 26 lutego");
        message.add("&7Nowa edycja zaczyna się 26 lutego");
        message.add("&7Gracz może otrzymać przedmioty w celu testu!");
        message.add("&7Wszystko znajduję się w trybie testu");
        message.add("&7Zgłoszenie błędu = nagroda na edycji");
        message.add("&7Wszystko może ulec zmianie, serwer może być niestabilny");
        message.add("&7Wszystko może być zbugowane/posiadać błędy");
        message.add("&7Celem testu jest wypuszczenie jak najbardziej dopracowanego pluginu");
        message.add("&7Wszystko co zdobędziecie w okresie testowym zostanie zrestartowane!");
        message.add("&7Gdy zgłosicie chociaż jeden błąd otrzymacie na stałe odznakę \"Tester Edycji II\"");*/
        message.add("&7Więcej informacji niedługo");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            String messageS = message.get(new Random().nextInt(message.size()));
            Bukkit.broadcastMessage(ColorUtils.color("&8[&6❄&8] " + messageS));
        }, 0, 20 * 60 * 2);
        Logger.logPluginMessage("Wczytano AutoMessage");
        //</editor-fold> AutoMessage
    }
    private void LoadWorlds() {
        LoadSpawn();
    }
    private void LoadSpawn() {
        spawn = new SpawnManager(this);
    }
    private void LoadDataYML() {
        //<editor-fold> Data.yml
        if (!dataFile.exists()) {
            saveResource("data.yml", false);
        }
        dataFile = new File(getDataFolder(), "data.yml");
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        Logger.logPluginMessage("Wczytano rekord graczy");
        //</editor-fold> Data.yml
    }
    private void StartCuboids() {
        //<editor-fold> Cuboids
        instanceCuboids = new CuboidsManager();
        instanceCuboids.Start();
        Logger.logPluginMessage("Wczytano działki");
        //</editor-fold> Cuboids
    }
    private void StartEvents() {
        StartDrop();
    }
    private void StartDrop() {
        //<editor-fold> EventDrop
        dropEvent = new DropEvent();
        Logger.logPluginMessage("Wczytano event DROP");
        //</editor-fold> EventDrop
    }
    private void StartVillagers() {
        villagerManager = new VillagerManager();
        Logger.logPluginMessage("Wczytano villagerów");
    }
    private void StartDiscordBot() {
        discordManager = new DiscordManager();
        // Start Async Bot
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try {
                discordManager.StartBot();
            } catch (LoginException e) {
                e.printStackTrace();
            }
        });
    }
    private void StartRynek() {
        //<editor-fold> Rynek
        instanceRynekManager = new RynekManager();
        Bukkit.getPluginManager().registerEvents(instanceRynekManager, this);
        Objects.requireNonNull(getCommand("rynek")).setExecutor(new RynekCommand());
        Logger.logPluginMessage("Wczytano rynek");
        //</editor-fold> Rynek
    }
    private void StartTimeVoter() {
        timeVoter = new TimeVoter();
    }
    private void StartScoreboard() {
        //<editor-fold> Scoreboard
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (User user : ModerrkowoDatabase.getInstance().getUserManager().getAllUsers()) {
                if (user.getPlayer() == null) {
                    continue;
                }
                if(!user.getSidebar()){
                    continue;
                }
                ScoreboardManager sm = Bukkit.getScoreboardManager();
                Scoreboard scoreboard = sm.getNewScoreboard();
                Objective objective;
                objective = scoreboard.registerNewObjective(user.getName(), "dummy", ColorUtils.color("&e&lModerrkowo"));
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                PlayerVillagerData data = null;
                try{
                    for(PlayerVillagerData villagers : user.getVillagersData().getVillagersData().values()){
                        if(villagers.isActiveQuest()){
                            data = villagers;
                            break;
                        }
                    }
                }catch(Exception ignored){

                }
                Score score1 = objective.getScore(" ");
                Score score2 = objective.getScore(ColorUtils.color("&9" + user.getName()));
                Score score3 = objective.getScore(ColorUtils.color("&fPortfel: &6" + ChatUtil.getMoney(user.getBank().money)));
                Score score4 = objective.getScore(ColorUtils.color("&fCzas gry: &a" + getTicksToTime(user.getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE))));
                Score score7 = objective.getScore(ColorUtils.color("&6moderrkowo.pl"));
                if(data == null) {
                    Score score5 = objective.getScore(ColorUtils.color("&fAktywny quest: &abrak"));
                    Score score6 = objective.getScore("  ");
                    score1.setScore(-1);
                    score2.setScore(-2);
                    score3.setScore(-3);
                    score4.setScore(-4);
                    score5.setScore(-5);
                    score6.setScore(-6);
                    score7.setScore(-7);
                }
                else{
                    if(!villagerManager.villagers.containsKey(data.getVillagerId())){
                        Score score5 = objective.getScore(ColorUtils.color("&fAktywny quest: &abrak"));
                        Score score6 = objective.getScore("  ");
                        score1.setScore(-1);
                        score2.setScore(-2);
                        score3.setScore(-3);
                        score4.setScore(-4);
                        score5.setScore(-5);
                        score6.setScore(-6);
                        score7.setScore(-7);
                        user.getPlayer().setScoreboard(scoreboard);
                        continue;
                    }
                    Quest q = villagerManager.villagers.get(data.getVillagerId()).getQuests().get(data.getQuestIndex());
                    Score score5 = objective.getScore(ColorUtils.color("&fAktywny quest: &a" + q.getName()));
                    int itemI = 0;
                    int last = 0;
                    for(int i = -6; i != -6-villagerManager.villagers.get(data.getVillagerId()).getQuests().get(data.getQuestIndex()).getQuestItems().size(); i--){
                        try{
                            IQuestItem iQuestItem = q.getQuestItems().get(itemI);
                            if(iQuestItem instanceof IQuestItemGive){
                                IQuestItemGive item = (IQuestItemGive) iQuestItem;
                                int temp = data.getQuestItemData().get(item.getQuestItemDataId());
                                Score tempScore;
                                if(temp >= item.getCount()){
                                    tempScore = objective.getScore(ColorUtils.color("&a✔ " + item.getQuestItemPrefix() + " " + item.getCount() + " " + ChatUtil.materialName(item.getMaterial())));
                                }else{
                                    int count = item.getCount()-temp;
                                    if(count > item.getCount()){
                                        count = item.getCount();
                                    }
                                    tempScore = objective.getScore(ColorUtils.color("&c✘ " + item.getQuestItemPrefix() + " " + count + " " + ChatUtil.materialName(item.getMaterial())));
                                }
                                tempScore.setScore(i);
                            }
                            if(iQuestItem instanceof IQuestItemCraft){
                                IQuestItemCraft item = (IQuestItemCraft) iQuestItem;
                                int temp = data.getQuestItemData().get(item.getQuestItemDataId());
                                Score tempScore;
                                if(temp >= item.getCount()){
                                    tempScore = objective.getScore(ColorUtils.color("&a✔ " + item.getQuestItemPrefix() + " " + item.getCount() + " " + ChatUtil.materialName(item.getMaterial())));
                                }else{
                                    int count = item.getCount()-temp;
                                    if(count > item.getCount()){
                                        count = item.getCount();
                                    }
                                    tempScore = objective.getScore(ColorUtils.color("&c✘ " + item.getQuestItemPrefix() + " " + count + " " + ChatUtil.materialName(item.getMaterial())));
                                }
                                tempScore.setScore(i);
                            }
                            if(iQuestItem instanceof IQuestItemKill){
                                IQuestItemKill item = (IQuestItemKill) iQuestItem;
                                int temp = data.getQuestItemData().get(item.getQuestItemDataId());
                                Score tempScore;
                                if(temp >= item.getCount()){
                                    tempScore = objective.getScore(ColorUtils.color("&a✔ " + item.getQuestItemPrefix() + " " + item.getCount() + " " + ChatUtil.materialName(item.getEntityType())));
                                }else{
                                    int count = item.getCount()-temp;
                                    if(count > item.getCount()){
                                        count = item.getCount();
                                    }
                                    tempScore = objective.getScore(ColorUtils.color("&c✘ " + item.getQuestItemPrefix() + " " + count + " " + ChatUtil.materialName(item.getEntityType())));
                                }
                                tempScore.setScore(i);
                            }
                            itemI++;
                            last = i;
                        }catch(Exception exception){
                            System.out.println("Exception  >> " + user.getName());
                            exception.printStackTrace();
                        }
                    }
                    Score score6 = objective.getScore("  ");
                    score1.setScore(-1);
                    score2.setScore(-2);
                    score3.setScore(-3);
                    score4.setScore(-4);
                    score5.setScore(-5);
                    score6.setScore(last-1);
                    score7.setScore(last-2);
                }
                user.getPlayer().setScoreboard(scoreboard);
            }
        }, 0, 20 * 15);
        Logger.logPluginMessage("Wczytano scoreboard");
        //</editor-fold> Scoreboard
    }
    private void LoadListeners() {
        //<editor-fold> Listeners
        try {
            instanceDatabaseListener = new DatabaseListener();
            ModerrkowoDatabase.getInstance().registerDatabaseListener(instanceDatabaseListener);
        } catch (Exception ignored) { }
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new MotdListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new CropBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new VillagerCommand(), this);
        Bukkit.getPluginManager().registerEvents(new PogodaCommand(), this);
        Bukkit.getPluginManager().registerEvents(new RespawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new TNTListener(), this);
        instanceAntyLogout = new AntyLogoutManager();
        Bukkit.getPluginManager().registerEvents(instanceAntyLogout, this);
        Bukkit.getPluginManager().registerEvents(new FishListener(), this);
        Bukkit.getPluginManager().registerEvents(new PaySign(), this);
        badgeCommand = new BadgeCommand();
        Bukkit.getPluginManager().registerEvents(badgeCommand, this);
        Bukkit.getPluginManager().registerEvents(banknot, this);
        Bukkit.getPluginManager().registerEvents(new FireballManager(), this);
        Logger.logPluginMessage("Wczytano listeners");
        //</editor-fold> Listeners
    }

    public static void loadEnchantments(Enchantment enchantment) {
        boolean registered = true;
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }
        if (registered) {
            Main.getInstance().customEnchants.put(enchantment.getName().replace(" ", "_"), enchantment);
            Logger.logPluginMessage("Zarejestrowano " + enchantment.getKey() + " [" + enchantment.getName() + "]");
        }else{
            Main.getInstance().customEnchants.put(enchantment.getName().replace(" ", "_"), enchantment);
            Logger.logPluginMessage("Wystąpił błąd przy rejestrowaniu " + enchantment.getKey());
        }
    }

    public String getTicksToTime(int minutes){
        if(minutes > 20*60){
            if(minutes > 20*60*60){
                DecimalFormat df2 = new DecimalFormat("#.##");
                if(minutes > 20*60*60*24){
                    return (df2.format((double)minutes/20/60/60/24))+" dni";
                }else{
                    return (df2.format((double)minutes/20/60/60))+" godz.";
                }
            }else{
                return (minutes/20/60) + " min";
            }
        }else{
            return (minutes/20) + " sek.";
        }
    }

    @Override
    public void onDisable() {
        customEnchants.clear();
        TryDisable();
        VanishCommand.bossBar.removeAll();
    }

    private void TryDisable() {
        try{
            discordManager.EndBot();
        }catch(Exception ignored){

        }
        try{
            timeVoter.Disable();
        }catch(Exception ignored){

        }
        try{
            ModerrkowoDatabase.getInstance().unregisterDatabaseListener(instanceDatabaseListener);
        }catch(Exception ignored){

        }
        try {
            dataConfig.save(dataFile);
            Logger.logAdminLog("Zapisano data.yml");
        } catch (IOException e) {
            Logger.logAdminLog("Wystąpił błąd podczas zapisywania data.yml");
        }
        for(Enchantment enchantment : customEnchants.values()){
            try {
                Field byIdField = Enchantment.class.getDeclaredField("byKey");
                Field byNameField = Enchantment.class.getDeclaredField("byName");

                byIdField.setAccessible(true);
                byNameField.setAccessible(true);

                HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
                HashMap<Integer, Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);

                byId.remove(enchantment.getKey());
                byName.remove(enchantment.getName());
            } catch (Exception ignored) {

            }
        }
        if (instanceRynekManager != null) {
            try {
                instanceRynekManager.save();
            } catch (SQLException | ConnectionIsNotOpenedException | ConnectionReconnectException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static Main getInstance() {
        return instance;
    }
    public int getBorder(World world) {
        return (int) world.getWorldBorder().getSize();
    }
    public static String getServerName() {
        return ColorUtils.color(HexResolver.parseHexString(Main.getInstance().getConfig().getString("servername")));
    }
}
