package my.oljek.rc.listener;

import my.oljek.rc.RealcraftPlugin;
import my.oljek.rc.manager.MysqlResult;
import my.oljek.rc.manager.PlayerManager;
import my.oljek.rc.manager.SQLiteConnectionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationListener implements Listener {

    private RealcraftPlugin plugin;
    private List<UserListener> users;

    public AuthorizationListener(RealcraftPlugin plugin) {
        this.plugin = plugin;
        users = new ArrayList<>();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        final PlayerManager playerManager = plugin.getPlayerManager();

        if (playerManager.getUser(p.getUniqueId()) == null)
            playerManager.createUser(p.getUniqueId());

        final UserListener userListener = new UserListener(plugin, p);

        Bukkit.getPluginManager().registerEvents(userListener, plugin);
        users.add(userListener);

        SQLiteConnectionManager connectionManager = RealcraftPlugin.getInstance().getSQLiteConnectionManager();

        MysqlResult result = connectionManager.selectResult("money", 1, new Object[]{"UUID", p.getUniqueId()});

        if (!result.hasNext()) {
            connectionManager.insert("money", false, p.getUniqueId().toString(), 0);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        UserListener deleteUser = null;

        for (UserListener userListener: users) {
            if (userListener.getPlayer() == p) {
                deleteUser = userListener;

                deleteUser.onDestroy();
            }
        }

        PlayerInteractEvent.getHandlerList().unregister(deleteUser);
        users.remove(deleteUser);
    }

    public void destroyAll() {
        users.forEach((s) -> s.onDestroy());
    }

    public void addUser(Player p) {
        final UserListener userListener = new UserListener(plugin, p);
        Bukkit.getPluginManager().registerEvents(userListener, plugin);
        users.add(userListener);
    }

}
