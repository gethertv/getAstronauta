package dev.gether.getastronauta;

import dev.gether.getastronauta.bstats.Metrics;
import dev.gether.getastronauta.cmd.GetAstronautaCmd;
import dev.gether.getastronauta.config.Config;
import dev.gether.getastronauta.config.DatabaseConfig;
import dev.gether.getastronauta.config.LangConfig;
import dev.gether.getastronauta.config.RuneConfig;
import dev.gether.getastronauta.database.Database;
import dev.gether.getastronauta.database.DatabaseFactory;
import dev.gether.getastronauta.hook.HookManager;
import dev.gether.getastronauta.listener.ConnectListener;
import dev.gether.getastronauta.listener.InventoryClickListener;
import dev.gether.getastronauta.listener.InventoryCloseListener;
import dev.gether.getastronauta.listener.PlayerInteractListener;
import dev.gether.getastronauta.listener.rune.BreakBlockListener;
import dev.gether.getastronauta.listener.rune.EntityDamageListener;
import dev.gether.getastronauta.listener.rune.PlayerItemDamageListener;
import dev.gether.getastronauta.rune.RuneManager;
import dev.gether.getastronauta.spin.SpinManager;
import dev.gether.getastronauta.user.UserManager;
import dev.gether.getastronauta.user.UserService;
import dev.gether.getastronauta.utils.ConsoleColor;
import dev.gether.getastronauta.utils.MessageUtil;
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

public final class GetAstronauta extends JavaPlugin {

    private static GetAstronauta instance;

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

    // hooks
    private HookManager hookManager;

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
                new PlayerItemDamageListener(userManager, runeManager),
                new BreakBlockListener(config, userManager, runeManager)
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));

        // register cmd
        registerCmd(userManager, runeManager);

        // implement all online users
        userManager.loadOnlineUsers();

        // implement hooks
        // todo: add support to FunnyGuilds
        hookManager = new HookManager(this, userManager, runeManager);

        // run task autosave user into database
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> { userManager.saveAllUsers(); }, 20L*120, 20L*120);

        Metrics metrics = new Metrics(this, 20144);

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
    public static GetAstronauta getInstance() {
        return instance;
    }

    public RuneConfig getRuneConfig() {
        return runeConfig;
    }
}
