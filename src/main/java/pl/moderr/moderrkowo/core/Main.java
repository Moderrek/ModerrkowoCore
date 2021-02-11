package pl.moderr.moderrkowo.core;

import com.sk89q.worldguard.WorldGuard;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.moderr.moderrkowo.core.commands.user.information.*;
import pl.moderr.moderrkowo.core.custom.PaySign;
import pl.moderr.moderrkowo.core.custom.antylogout.AntyLogoutManager;
import pl.moderr.moderrkowo.core.commands.admin.*;
import pl.moderr.moderrkowo.core.commands.user.messages.HelpopCommand;
import pl.moderr.moderrkowo.core.commands.user.messages.MessageCommand;
import pl.moderr.moderrkowo.core.commands.user.messages.ReplyCommand;
import pl.moderr.moderrkowo.core.commands.user.weather.PogodaCommand;
import pl.moderr.moderrkowo.core.commands.user.teleportation.*;
import pl.moderr.moderrkowo.core.cuboids.CuboidsManager;
import pl.moderr.moderrkowo.core.custom.enchantments.HammerEnchantment;
import pl.moderr.moderrkowo.core.custom.events.drop.DropEvent;
import pl.moderr.moderrkowo.core.economy.*;
import pl.moderr.moderrkowo.core.listeners.*;
import pl.moderr.moderrkowo.core.utils.ChatUtil;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.HexResolver;
import pl.moderr.moderrkowo.core.utils.Logger;
import pl.moderr.moderrkowo.core.custom.villagers.VillagerManager;
import pl.moderr.moderrkowo.core.custom.villagers.data.*;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.data.PlayerVillagerData;
import pl.moderr.moderrkowo.database.data.User;
import pl.moderr.moderrkowo.database.exceptions.ConnectionIsNotOpenedException;
import pl.moderr.moderrkowo.database.exceptions.ConnectionReconnectException;

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

    private static Main instance;

    public CuboidsManager instanceCuboids;
    public AntyLogoutManager instanceAntyLogout;
    public RynekManager instanceRynekManager;
    public SprzedazManager instanceSprzedazManager;
    public VillagerManager villagerManager;

    public DatabaseListener instanceDatabaseListener;

    public DropEvent dropEvent;

    public HammerEnchantment hammerEnch = null;

    public File dataFile = new File(getDataFolder(), "data.yml");
    public FileConfiguration dataConfig;

    @Override
    public void onEnable() {
        // START
        long start = System.currentTimeMillis();
        Logger.logPluginMessage("Wczytywanie pluginu...");

        // Constructor
        instance = this;

        //<editor-fold> Config
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveConfig();
        Logger.logPluginMessage("Wczytano config");
        //</editor-fold> Config

        hammerEnch = new HammerEnchantment();
        Bukkit.getPluginManager().registerEvents(hammerEnch, this);
        loadEnchantments(hammerEnch);
        Logger.logPluginMessage("Załadowano Szeroki Trzon");

        //<editor-fold> Listeners
        try {
            instanceDatabaseListener = new DatabaseListener();
            ModerrkowoDatabase.getInstance().registerDatabaseListener(instanceDatabaseListener);
        } catch (Exception ignored) { }
        Bukkit.getPluginManager().registerEvents(new PortalListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new MotdListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new MendingRemover(), this);
        Bukkit.getPluginManager().registerEvents(new CropBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new VillagerCommand(), this);
        Bukkit.getPluginManager().registerEvents(new PogodaCommand(), this);
        Bukkit.getPluginManager().registerEvents(new RespawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new TNTListener(), this);
        instanceAntyLogout = new AntyLogoutManager();
        Bukkit.getPluginManager().registerEvents(instanceAntyLogout, this);
        Bukkit.getPluginManager().registerEvents(new FishListener(), this);
        Bukkit.getPluginManager().registerEvents(new PaySign(), this);
        Logger.logPluginMessage("Wczytano listenery");
        //</editor-fold> Listeners

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
        getCommand("aquest").setExecutor(new QuestCommand());
        Logger.logPluginMessage("Wczytano komendy");
        //</editor-fold> Commands

        //<editor-fold> AutoMessage
        ArrayList<String> message = new ArrayList<>();
        message.add("&7Jeżeli chcesz aby zmienić pogodę na ładną użyj &c/pogoda!");
        message.add("&7Twój przyjaciel jest daleko? Użyjcie &c/tpa");
        message.add("&7Widzisz buga? Prosimy zgłoś go na &c/helpop");
        message.add("&7Jeżeli chcesz napisać do kogoś prywatną wiadomość użyj &c/msg!");
        message.add("&7Aby szybko komuś odpisać na prywatną wiadomość użyj &c/r");
        message.add("&7Twoje miejsce śmierci zawsze będzie napisane na chacie!");
        message.add("&7Potrzebujesz działki? Musisz ją wytworzyć &c/craftingdzialki");
        message.add("&7Portal endu jest wyłączony!");
        message.add("&7Po uderzeniu moba/gracza wyświetla się ilośc zadanych serc");
        message.add("&7Na naszym serwerze możesz sobie ustawić dom! &c/ustawdom");
        message.add("&7Aby prze teleportować się do domu użyj &c/dom");
        message.add("&7Jeżeli chcesz wesprzeć nasz serwer wejdź na &chttps://moderr.pl/dotacja");
        message.add("&7Jeżeli chcesz wbić na naszego Discord'a &c/discord");
        message.add("&7Nie jesteś zapoznany z regulaminem? &c/regulamin");
        message.add("&7Gdy jesteś podczas walki nie możesz się wylogować!");
        message.add("&7Godziny walki trwają od &c18-8");
        message.add("&7Aby prze teleportować się na spawna wpisz &c/spawn");
        message.add("&7Na spawnie możesz znaleźć wieśniaków z zadaniami &c/spawn");
        message.add("&7Zaklęcie naprawy jest wyłączone na serwerze!");
        message.add("&7Portal do kresu jest tymczasowo zamknięty. Będzie otwarty pod koniec 1 edycji");
        message.add("&7Podczas walki nie możesz się wylogować!");
        message.add("&7Aby zarobić wykonuj zadania i wystawiaj przedmioty na rynku");
        message.add("&7Przedmioty możesz sprzedawać pod &c/rynek wystaw <cena>");
        message.add("&7Przedmioty do kupna znajdziesz pod &c/rynek");
        message.add("&7Chcesz przelać pieniądze do przyjaciela? &c/przelej");
        message.add("&7Questy możesz znaleźć na spawnie!");
        message.add("&7Łowiąc możesz zarabiać, i zyskać rzadkie przedmioty!");
        message.add("&7TNT nie można wytworzyć, można je tylko zdobyć w zadaniu");
        message.add("&7Jeżeli masz działkę użyj &c/dzialka");
        message.add("&7Chcesz zarobić? Rób zadania i wystawiaj na &c/rynek");
        message.add("&7Chcesz kogoś zgłosić &c/helpop");
        message.add("&7Średnio co godzinę na losowych koordynatach pojawia się zrzut!");
        message.add("&7Robiąc zadania możesz zdobywać złotówki.");
        message.add("&7Aby zobaczyć zmiany wpisz &c/zmiany");
        message.add("&7Aby zmienić pogodę na ładną wpisz &c/pogoda");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            String messageS = message.get(new Random().nextInt(message.size()));
            Bukkit.broadcastMessage(ColorUtils.color("&8[&6❄&8] " + messageS));
        }, 0, 20 * 60 * 2);
        Logger.logPluginMessage("Wczytano AutoMessage");
        //</editor-fold> AutoMessage

        //<editor-fold> Data.yml
        if (!dataFile.exists()) {
            saveResource("data.yml", false);
        }
        dataFile = new File(getDataFolder(), "data.yml");
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        Logger.logPluginMessage("Wczytano rekord graczy");
        //</editor-fold> Data.yml

        //<editor-fold> Cuboids
        instanceCuboids = new CuboidsManager();
        instanceCuboids.Start();
        Logger.logPluginMessage("Wczytano działki");
        //</editor-fold> Cuboids

        //<editor-fold> EventDrop
        dropEvent = new DropEvent();
        //</editor-fold> EventDrop

       villagerManager = new VillagerManager();

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
                objective = scoreboard.registerNewObjective(user.getName(), "dummy", ColorUtils.color("&6&lModerrkowo"));
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
                Score score2 = objective.getScore(ColorUtils.color("&e" + ChatUtil.getPrefix(Objects.requireNonNull(user.getPlayer()))));
                Score score3 = objective.getScore(ColorUtils.color("&fPortfel: &6" + ChatUtil.getMoney(user.getBank().money)));
                Score score4 = objective.getScore(ColorUtils.color("&fCzas gry: &a" + getTicksToTime(user.getPlayer().getStatistic(Statistic.PLAY_ONE_MINUTE))));
                if(data == null) {
                    Score score5 = objective.getScore(ColorUtils.color("&fAktywny quest: &abrak"));
                    Score score6 = objective.getScore("  ");
                    score1.setScore(-1);
                    score2.setScore(-2);
                    score3.setScore(-3);
                    score4.setScore(-4);
                    score5.setScore(-5);
                    score6.setScore(-6);
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
                }
                user.getPlayer().setScoreboard(scoreboard);
            }
        }, 0, 20 * 10);
        Logger.logPluginMessage("Wczytano scoreboard'y");
        //</editor-fold> Scoreboard

        //<editor-fold> Sprzedaz
        instanceSprzedazManager = new SprzedazManager();
        Logger.logPluginMessage("Wczytano sprzedaż");
        //</editor-fold> Sprzedaz

        //<editor-fold> Rynek
        instanceRynekManager = new RynekManager();
        Bukkit.getPluginManager().registerEvents(instanceRynekManager, this);
        Objects.requireNonNull(getCommand("rynek")).setExecutor(new RynekCommand());
        Logger.logPluginMessage("Wczytano rynek");
        //</editor-fold> Rynek

        Logger.logPluginMessage("Wczytano plugin [CORE] w &8(&a" + (System.currentTimeMillis() - start) + "ms&8)");
        // END
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
            Logger.logPluginMessage("Zarejestrowano " + enchantment.getName());
        }else{
            Logger.logPluginMessage("Wystąpił bład przy rejestrowaniu " + enchantment.getName());
        }
    }

    @Contract(pure = true)
    public @NotNull String getTicksToTime(int minutes){
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
        ModerrkowoDatabase.getInstance().unregisterDatabaseListener(instanceDatabaseListener);
        try {
            dataConfig.save(dataFile);
            Logger.logAdminLog("Zapisano data.yml");
        } catch (IOException e) {
            Logger.logAdminLog("Wystąpił błąd podczas zapisywania data.yml");
        }
        try {
            Field byIdField = Enchantment.class.getDeclaredField("byKey");
            Field byNameField = Enchantment.class.getDeclaredField("byName");

            byIdField.setAccessible(true);
            byNameField.setAccessible(true);

            HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
            HashMap<Integer, Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);

            if (byId.containsKey(hammerEnch.getKey())) {
                byId.remove(hammerEnch.getKey());
            }

            if (byName.containsKey(hammerEnch.getName())) {
                byName.remove(hammerEnch.getName());
            }
        } catch (Exception ignored) {
        }
        if (instanceRynekManager != null) {
            try {
                instanceRynekManager.save();
            } catch (SQLException | ConnectionIsNotOpenedException | ConnectionReconnectException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Contract(pure = true)
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
