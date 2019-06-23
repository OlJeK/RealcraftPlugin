package my.oljek.rc;

import com.oljek.main.manager.ConnectionManager;
import com.oljek.spigot.command.custom.CommandHandler;
import my.oljek.rc.command.MoneyCommand;
import my.oljek.rc.listener.*;
import my.oljek.rc.manager.PlayerManager;
import my.oljek.rc.manager.SQLiteConnectionManager;
import my.oljek.rc.object.MessageListener;
import my.oljek.rc.object.QueryQueue;
import my.oljek.rc.object.Recipe;
import my.oljek.rc.object.SimplePlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RealcraftPlugin extends JavaPlugin {

    private SQLiteConnectionManager connectionManager;
    private PlayerManager playerManager;
    private AuthorizationListener authorizationListener;
    private QueryQueue queryQueue;
    private static RealcraftPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        connectionManager = new SQLiteConnectionManager("Realcraft");
        playerManager = new SimplePlayerManager(connectionManager);

        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        createTable();
        registerEvents();

        for (Player p: Bukkit.getOnlinePlayers()) {
            if (playerManager.getUser(p.getUniqueId()) == null)
                playerManager.createUser(p.getUniqueId());

            authorizationListener.addUser(p);
        }

        Recipe recipe = new Recipe(this);

        recipe.addRecipes();

        CommandHandler.registerCommand(new MoneyCommand());

        MessageListener messageListener = new MessageListener();
        Thread thread = new Thread(messageListener);

        thread.start();

        queryQueue = new QueryQueue();

        new BukkitRunnable() {

            @Override
            public void run() {
                queryQueue.run();
            }

        }.runTaskTimer(this, 0, 20);
    }

    @Override
    public void onDisable() {
        authorizationListener.destroyAll();

        MessageListener.stop();
    }

    public static RealcraftPlugin getInstance() {
        return instance;
    }

    private void registerEvents() {
        authorizationListener = new AuthorizationListener(this);

        Bukkit.getPluginManager().registerEvents(new BlockListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CustomListener(), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new DamageListener(), this);
        Bukkit.getPluginManager().registerEvents(new UpdateListener(), this);
        Bukkit.getPluginManager().registerEvents(authorizationListener, this);
    }

    private void createTable() {
        connectionManager.createTable("user", false, ConnectionManager.s("UUID", "VARCHAR(100)"),
                ConnectionManager.s("LevelStamina", "INTEGER"),
                ConnectionManager.s("ExpStamina", "INTEGER"));

        connectionManager.createTable("money", false, ConnectionManager.s("UUID", "VARCHAR(100)"),
                ConnectionManager.s("money", "INTEGER"));
    }

    public my.oljek.rc.manager.SQLiteConnectionManager getSQLiteConnectionManager() {
        return connectionManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void registerEvent(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public QueryQueue getQueryQueue() {
        return queryQueue;
    }

}
