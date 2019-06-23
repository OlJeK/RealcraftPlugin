package my.oljek.rc.object;

import com.oljek.spigot.listener.custom.UpdateEvent;
import com.oljek.spigot.object.UpdateType;
import my.oljek.rc.util.MathUtil;
import my.oljek.rc.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Thirty implements Listener {

    private Player p;
    private int thirty;
    private int maxThirty;
    private int multiplier;
    private long startWalking;
    private long lastTimeThirstyRemove;

    public Thirty(Player p, int thirty, int maxThirty) {
        this.p = p;
        this.thirty = thirty;
        this.maxThirty = maxThirty;

        multiplier = 1;
        startWalking = System.currentTimeMillis();
    }

    public int getThirty() {
        return thirty;
    }

    public int getMaxThirty() {
        return maxThirty;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public void setThirty(int thirty) {
        this.thirty = thirty;
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getType() == UpdateType.SECOND) {
            if (MathUtil.getDifferenceTime(startWalking) >= 5) {
                multiplier = 1;

                thirtyReduceWithTime(15);
            } else {
                thirtyReduceWithTime(15);
            }
        }
    }

    private void thirtyReduceWithTime(int time) {
        if (lastTimeThirstyRemove == 0)
            lastTimeThirstyRemove = System.currentTimeMillis();
        else if (MathUtil.getDifferenceTime(lastTimeThirstyRemove) >= time) {
            lastTimeThirstyRemove = System.currentTimeMillis();
            thirtyReduce();
        }
    }

    private void thirtyReduce() {
        if (thirty > 0)
            thirty -= 1 * multiplier;
    }

    @EventHandler
    public void onUpdateThirty(UpdateEvent e) {
        if (e.getType() == UpdateType.SECOND) {
            if (thirty <= 0) {
                p.damage(1);
                MessageUtil.sendActioBar(p, "&c&lYou`re thirsty, you need a water!");
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onToggle(PlayerToggleSprintEvent e) {
        Player p = e.getPlayer();

        if (p != this.p)
            return;

        if (e.isSprinting() && thirty <= 0) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1, 1));
            p.setSprinting(false);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();

        if (this.p != p)
            return;

        if (from.getBlockZ() == to.getBlockZ() && from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY())
            return;

        multiplier = 2;
        startWalking = System.currentTimeMillis();
    }

}
