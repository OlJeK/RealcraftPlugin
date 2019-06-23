package my.oljek.rc.listener;

import com.oljek.spigot.listener.custom.UpdateEvent;
import com.oljek.spigot.object.UpdateType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UpdateListener implements Listener {

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getType() == UpdateType.SECOND) {
            for (Player p: Bukkit.getOnlinePlayers()) {
                if (p.getLocation().getY() >= 200) {
                    boolean activeSlow = false;
                    boolean activeWeakness = false;

                    for (PotionEffect effect: p.getActivePotionEffects()) {
                        if (effect.getType() == PotionEffectType.SLOW)
                            activeSlow = true;
                        else if (effect.getType() == PotionEffectType.WEAKNESS)
                            activeWeakness = true;
                    }

                    if (!activeSlow)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 1));

                    if (!activeWeakness)
                        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 40, 1));
                }
            }
        }
    }

}
