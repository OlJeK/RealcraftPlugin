package my.oljek.rc.listener;

import my.oljek.rc.RealcraftPlugin;
import my.oljek.rc.object.*;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserListener implements Listener {

    private Player p;
    private BossBar bossBar;
    private RealisticUser user;
    private Thirty thirty;
    private Stamina stamina;
    private RealScoreboard scoreboard;
    private InventoryBackpack inventoryBackpack;
    private static Map<UUID, Thirty> thirtyMap;

    static {
        thirtyMap = new HashMap<>();
    }

    public UserListener(RealcraftPlugin plugin, Player p) {
        this.p = p;

        bossBar = Bukkit.createBossBar("§bStamina", BarColor.GREEN, BarStyle.SOLID);

        if (!bossBar.getPlayers().contains(p))
            bossBar.addPlayer(p);

        user = plugin.getPlayerManager().getUser(p.getUniqueId());

        if (!thirtyMap.containsKey(p.getUniqueId())) {
            thirty = new Thirty(p, 100, 100);

            thirtyMap.put(p.getUniqueId(), thirty);
        } else
            thirty = thirtyMap.get(p.getUniqueId());

        stamina = new Stamina(bossBar, user, p);
        scoreboard = new RealScoreboard(p, user, thirty);
        inventoryBackpack = new InventoryBackpack(p);

        plugin.registerEvent(stamina);
        plugin.registerEvent(thirty);
        plugin.registerEvent(scoreboard);
        plugin.registerEvent(inventoryBackpack);
    }

    public void onDestroy() {
        bossBar.removeAll();
        user.save();

        PlayerInteractEvent.getHandlerList().unregister(thirty);
        PlayerInteractEvent.getHandlerList().unregister(stamina);
        PlayerInteractEvent.getHandlerList().unregister(scoreboard);
        PlayerInteractEvent.getHandlerList().unregister(inventoryBackpack);
    }

    public Player getPlayer() {
        return p;
    }

    public static Thirty getThirty(UUID uuid) {
        return thirtyMap.get(uuid);
    }

}
