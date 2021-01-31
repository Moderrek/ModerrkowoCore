package pl.moderr.moderrkowo.core;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.Contract;
import pl.moderr.moderrkowo.core.antylogout.ModerrAntyLogout;
import pl.moderr.moderrkowo.core.commands.admin.*;
import pl.moderr.moderrkowo.core.commands.player.CraftingDzialkaCommand;
import pl.moderr.moderrkowo.core.commands.player.DiscordCommand;
import pl.moderr.moderrkowo.core.commands.player.RegulaminCommand;
import pl.moderr.moderrkowo.core.commands.player.SpawnCommand;
import pl.moderr.moderrkowo.core.commands.player.messages.HelpopCommand;
import pl.moderr.moderrkowo.core.commands.player.messages.MessageCommand;
import pl.moderr.moderrkowo.core.commands.player.messages.ReplyCommand;
import pl.moderr.moderrkowo.core.commands.player.pogoda.PogodaCommand;
import pl.moderr.moderrkowo.core.commands.player.teleport.*;
import pl.moderr.moderrkowo.core.cuboids.ModerrCuboids;
import pl.moderr.moderrkowo.core.listeners.*;
import pl.moderr.moderrkowo.core.utils.ChatUtil;
import pl.moderr.moderrkowo.core.utils.ColorUtils;
import pl.moderr.moderrkowo.core.utils.HexResolver;
import pl.moderr.moderrkowo.core.utils.Logger;
import pl.moderr.moderrkowo.database.ModerrkowoDatabase;
import pl.moderr.moderrkowo.database.data.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public final class Main extends JavaPlugin {

    private static Main instance;

    public ModerrCuboids instanceCuboids;
    public DatabaseListener instanceDatabaseListener;
    public ModerrAntyLogout instanceAntyLogout;
    //public RynekManager instanceRynekManager;

    public File dataFile = new File(getDataFolder(), "data.yml");
    public FileConfiguration dataConfig;

    @Override
    public void onEnable() {
        // START
        long start = System.currentTimeMillis();
        Logger.logPluginMessage("Wczytywanie pluginu...");

        // Constructor
        instance = this;

        // Config
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        getConfig().options().copyHeader(true);
        saveConfig();

        //<editor-fold> Listeners
        try {
            instanceDatabaseListener = new DatabaseListener();
            ModerrkowoDatabase.getInstance().registerDatabaseListener(instanceDatabaseListener);
        } catch (Exception ignored) {
        }
        Bukkit.getPluginManager().registerEvents(new PortalListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new MotdListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new MendingRemover(), this);
        Bukkit.getPluginManager().registerEvents(new CropBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new AVillagerCommand(), this);
        Bukkit.getPluginManager().registerEvents(new PogodaCommand(), this);
        instanceAntyLogout = new ModerrAntyLogout();
        Bukkit.getPluginManager().registerEvents(instanceAntyLogout, this);
        Logger.logPluginMessage("Wczytano listenery");
        //</editor-fold> Listeners

        //<editor-fold> Commands
        Objects.requireNonNull(getCommand("alogi")).setExecutor(new ALOGI_Command());
        Objects.requireNonNull(getCommand("ahelpop")).setExecutor(new AHelpopCommand());
        Objects.requireNonNull(getCommand("adminchat")).setExecutor(new AdminChatCommand());
        Objects.requireNonNull(getCommand("ainvsee")).setExecutor(new AInvseeCommand());
        Objects.requireNonNull(getCommand("asendalert")).setExecutor(new ASendAlertCommand());
        Objects.requireNonNull(getCommand("aclearchat")).setExecutor(new ClearChatCommand());
        Objects.requireNonNull(getCommand("aendersee")).setExecutor(new AEnderseeCommand());
        Objects.requireNonNull(getCommand("avanish")).setExecutor(new AVanishCommand());
        Objects.requireNonNull(getCommand("avillager")).setExecutor(new AVillagerCommand());
        Objects.requireNonNull(getCommand("anazwa")).setExecutor(new ANazwaCommand());
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
        Objects.requireNonNull(getCommand("akick")).setExecutor(new AKickCommand());
        Objects.requireNonNull(getCommand("craftingdzialki")).setExecutor(new CraftingDzialkaCommand());
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(getCommand("asetspawn")).setExecutor(new ASetSpawnCommand());
        Logger.logAdminChat("Wczytano komendy");
        //</editor-fold> Commands

        //<editor-fold> Cuboids
        instanceCuboids = new ModerrCuboids();
        instanceCuboids.Start();
        Logger.logAdminChat("Wczytano działki");
        //</editor-fold> Cuboids

        //<editor-fold> AutoMessage
        ArrayList<String> message = new ArrayList<>();
        message.add("&eJeżeli chcesz aby zmienić pogodę na ładną użyj &c/pogoda!");
        message.add("&eTwój przyjaciel jest daleko? Użyjcie &c/tpa");
        message.add("&eWidzisz buga? Prosimy zgłoś go na &c/helpop");
        message.add("&eJeżeli chcesz napisać do kogoś prywatną wiadomość użyj &c/msg!");
        message.add("&eAby szybko komuś odpisać na prywatną wiadomość użyj &c/r");
        message.add("&eTwoje miejsce śmierci zawsze będzie napisane na chacie!");
        message.add("&ePotrzebujesz działki? &cMusisz ją wytworzyć /craftingdzialki");
        message.add("&ePortal endu jest wyłączony!");
        message.add("&ePo uderzeniu moba/gracza wyświetla się ilośc zadanych serc");
        message.add("&eNa naszym serwerze możesz sobie ustawić dom! &c/ustawdom");
        message.add("&eAby prze teleportować się do domu użyj &c/dom");
        message.add("&eJeżeli chcesz wesprzeć nasz serwer wejdź na &chttps://moderr.pl/dotacja");
        message.add("&eJeżeli chcesz wbić na naszego Discord'a &c/discord");
        message.add("&eNie jesteś zapoznany z regulaminem? &c/regulamin");
        message.add("&eGdy jesteś podczas walki nie możesz się wylogować!");
        message.add("&eGodziny walki trwają od &c18-8");
        message.add("&eAby prze teleportować się na spawna wpisz &c/spawn");
        message.add("&eNa spawnie możesz znaleźć wieśniaków z zadaniami &c/spawn");
        message.add("&cZaklęcie naprawy jest wyłączone na serwerze!");
        message.add("&ePortal do kresu jest tymczasowo zamknięty. Będzie otwarty pod koniec 1 edycji");
        message.add("&eTeleportacje między graczami są ograniczone do &c1500m");
        message.add("&ePodczas walki nie możesz się wylogować!");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            String messageS = message.get(new Random().nextInt(message.size()));
            Bukkit.broadcastMessage(ColorUtils.color("&8[&b❄&8] " + messageS));
        }, 0, 20 * 60 * 2);
        Logger.logPluginMessage("Wczytano AutoMessage");
        //</editor-fold> AutoMessage

        //<editor-fold> Data.yml
        if (!dataFile.exists()) {
            saveResource("data.yml", false);
        }
        dataFile = new File(getDataFolder(), "data.yml");
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
        Logger.logPluginMessage("Wczytano data.yml");
        //</editor-fold> Data.yml

        //<editor-fold> Scoreboard
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (User user : ModerrkowoDatabase.getInstance().getUserManager().getAllUsers()) {
                ScoreboardManager sm = Bukkit.getScoreboardManager();
                Scoreboard scoreboard = sm.getNewScoreboard();
                Objective objective;
                objective = scoreboard.registerNewObjective(user.getName(), "dummy", ColorUtils.color("&6&lModerrkowo"));
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                Score score1 = objective.getScore(" ");
                Score score2 = objective.getScore(ColorUtils.color("&e" + ChatUtil.getPrefix(Objects.requireNonNull(user.getPlayer())) + " &8(&c" + Objects.requireNonNull(user.getPlayer()).getStatistic(Statistic.DEATHS) + " ☠&8)"));
                Score score3 = objective.getScore(ColorUtils.color("&fPortfel: &6" + 0 + " zł"));
                Score score4 = objective.getScore("     ");
                Score score5 = objective.getScore(ColorUtils.color("&emoderrkowo.pl"));
                score1.setScore(-1);
                score2.setScore(-2);
                score3.setScore(-3);
                score4.setScore(-4);
                score5.setScore(-5);
                user.getPlayer().setScoreboard(scoreboard);
            }
        }, 0, 20 * 15);
        //</editor-fold> Scoreboard

        Logger.logPluginMessage("Wczytano plugin [CORE] w &8(&a" + (System.currentTimeMillis() - start) + "ms&8)");
        // END
    }

    @Override
    public void onDisable() {
        try {
            dataConfig.save(dataFile);
            Logger.logAdminLog("Zapisano data.yml");
        } catch (IOException e) {
            e.printStackTrace();
            Logger.logAdminLog("Wystąpił błąd podczas zapisywania data.yml");
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
