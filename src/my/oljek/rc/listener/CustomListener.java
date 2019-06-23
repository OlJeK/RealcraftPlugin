package my.oljek.rc.listener;

import com.oljek.main.util.StringUtil;
import my.oljek.rc.listener.custom.StaminaChangeLevel;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomListener implements Listener {

    @EventHandler
    public void onStaminaChangeLevel(StaminaChangeLevel e) {
        Player p = e.getPlayer();
        int level = e.getLevel();

        p.sendTitle(StringUtil.inColor("&aYour stamina level changed to"), StringUtil.inColor("&6" + level), 20, 60, 20);
        p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1, 1);
    }

}
