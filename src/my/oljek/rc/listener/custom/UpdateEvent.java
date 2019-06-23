package my.oljek.rc.listener.custom;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdateEvent extends Event {

    private UpdateType type;
    private static HandlerList handlerList = new HandlerList();

    public UpdateEvent(UpdateType type) {
        this.type = type;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public HandlerList getHandlerList() {
        return handlerList;
    }

    public UpdateType getType() {
        return type;
    }
}
