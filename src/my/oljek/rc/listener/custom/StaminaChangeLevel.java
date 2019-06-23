package my.oljek.rc.listener.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class StaminaChangeLevel extends PlayerEvent {

    private int level;
    private static final HandlerList handlerList = new HandlerList();

    public StaminaChangeLevel(Player who, int level) {
        super(who);

        this.level = level;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public int getLevel() {
        return level;
    }

}
