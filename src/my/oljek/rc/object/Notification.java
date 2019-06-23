package my.oljek.rc.object;

import jdk.nashorn.internal.objects.annotations.Getter;
import my.oljek.rc.RealcraftPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Function;

public abstract class Notification {

    static final String NOTIFICATION_PERMISSION_NODE = "chatty.notification.";
    static final Function<String, String> COLORIZE
            = (string) -> string == null ? null : ChatColor.translateAlternateColorCodes('&', string);

    private final BukkitTask bukkitTask;
    private final boolean permission;

    Notification(double delay, boolean permission) {
        bukkitTask = Bukkit.getScheduler().runTaskTimer(RealcraftPlugin.getInstance(), Notification.this::run, (long) 0 , (long) 1);
        this.permission = permission;
    }

    public void cancel() {
        if (bukkitTask != null)
            bukkitTask.cancel();

    }

    public abstract void run();

    public boolean isPermission() {
        return permission;
    }

}