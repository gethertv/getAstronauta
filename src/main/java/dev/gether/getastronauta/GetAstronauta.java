package dev.gether.getaustronauta;

import dev.gether.getaustronauta.bstats.Metrics;
import dev.gether.getaustronauta.cmd.GetAstronautaCmd;
import dev.gether.getaustronauta.config.Config;
import dev.gether.getaustronauta.config.DatabaseConfig;
import dev.gether.getaustronauta.config.LangConfig;
import dev.gether.getaustronauta.config.RuneConfig;
import dev.gether.getaustronauta.database.Database;
import dev.gether.getaustronauta.database.DatabaseFactory;
import dev.gether.getaustronauta.listener.ConnectListener;
import dev.gether.getaustronauta.listener.InventoryClickListener;
import dev.gether.getaustronauta.listener.InventoryCloseListener;
import dev.gether.getaustronauta.listener.PlayerInteractListener;
import dev.gether.getaustronauta.listener.rune.EntityDamageListener;
import dev.gether.getaustronauta.listener.rune.PlayerItemDamageListener;
import dev.gether.getaustronauta.rune.RuneManager;
import dev.gether.getaustronauta.spin.SpinManager;
import dev.gether.getaustronauta.user.UserManager;
import dev.gether.getaustronauta.user.UserService;
import dev.gether.getaustronauta.utils.ConsoleColor;
import dev.gether.getaustronauta.utils.MessageUtil;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.litecommands.bukkit.tools.BukkitPlayerArgument;
import dev.rollczi.litecommands.platform.LiteSender;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.stream.Stream;

public final class GetAustronauta extends JavaPlugin {

    private static GetAustronauta instance;

    // config section
    private DatabaseConfig databaseConfig;
    private Config config;
    private RuneConfig runeConfig;
    private LangConfig langConfig;

    // database
    private Database database;
    // manager
    private UserManager userManager;
    private RuneManager runeManager;
    private SpinManager spinManager;


    // commands
    LiteCommands<CommandSender> liteCommands;
    public void loadConfig() {
        databaseConfig = ConfigManager.create(DatabaseConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer());
            it.withBindFile(new File(getDataFolder(), "database.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
        config = ConfigManager.create(Config.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(getDataFolder(), "config.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
        langConfig = ConfigManager.create(LangConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(getDataFolder(), "lang.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
        runeConfig = ConfigManager.create(RuneConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
            it.withBindFile(new File(getDataFolder(), "runes.yml"));
            it.withRemoveOrphans(true);
            it.saveDefaults();
            it.load(true);
        });
    }


    @Override
    public void onEnable() {
        instance = this;

        // implement config
        loadConfig();

        // init database
        // connecting to database and create table
        // if not correct return false and disable the plugin
        if(!initDatabase()) return;

        // service s
        UserService userService = new UserService(this, database);

        // manager
        runeManager = new RuneManager(runeConfig, config);
        userManager = new UserManager(userService, runeConfig, langConfig, config);
        spinManager = new SpinManager(config, runeConfig, langConfig);


        // register listeners
        Stream.of(
                new ConnectListener(this, userManager),
                new PlayerInteractListener(runeManager, userManager),
                new InventoryClickListener(config, userManager, spinManager),
                new InventoryCloseListener(spinManager),
                // rune listeners
                new EntityDamageListener(userManager, runeManager, config),
                new PlayerItemDamageListener(userManager, runeManager)
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        // register cmd
        registerCmd(userManager, runeManager);

        // implement all online users
        userManager.loadOnlineUsers();

        // run task autosave user into database
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> { userManager.saveAllUsers(); }, 20L*120, 20L*120);

        Metrics metrics = new Metrics(this, 19808);

    }
    @Override
    public void onDisable() {


        if(database!=null) {
            userManager.saveAllUsers();
            database.disconnect();
        }


        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);

        // unregister cmd
        this.liteCommands.getPlatform().unregisterAll();

    }

    private boolean initDatabase() {
        database = DatabaseFactory.createDatabase(databaseConfig);
        database.connect();
        // check is connected
        // if not then close the plugin
        if(!database.isConnected()) {
            MessageUtil.sendLoggerInro("Can't connecting to database!", ConsoleColor.RED);
            getServer().getPluginManager().disablePlugin(this);
            return false;
        }
        // create database
        database.createTable();
        // check all table exists
        database.ensureAllColumnsExist();
        return true;

    }

    public void registerCmd(UserManager userManager, RuneManager runeManager)
    {

        liteCommands = LiteBukkitFactory.builder(this.getServer(), "getastronauta")
                .commandInstance(new GetAstronautaCmd(userManager, runeManager))
                .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>("Gracz nie jest online!"))
                .argument(Player.class, new BukkitPlayerArgument<>(this.getServer(), "Gracz nie jest online!"))
                .register();

    }
    public void reloadPlugin(LiteSender liteSender) {
        databaseConfig.load();
        config.load();
        langConfig.load();
        runeConfig.load();
        MessageUtil.sendMessage(liteSender, "&aPomyslnie przeladowano plugin!");
    }
    public static GetAustronauta getInstance() {
        return instance;
    }

    public RuneConfig getRuneConfig() {
        return runeConfig;
    }
}
