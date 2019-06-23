package my.oljek.rc.object;

import com.oljek.spigot.listener.custom.UpdateEvent;
import com.oljek.spigot.object.UpdateType;
import my.oljek.rc.util.MathUtil;
import my.oljek.rc.util.MessageUtil;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Stamina implements Listener {

    private BossBar bar;
    private RealisticUser user;
    private Player p;
    private boolean canSprint;
    private int maxTimeSprint;
    private int remainedTimeForSprint;
    private long startResumption;
    private long lastAddExp;

    public Stamina(BossBar bar, RealisticUser user, Player p) {
        this.bar = bar;
        this.user = user;
        this.p = p;

        maxTimeSprint = user.getLevelStamina() * 6;
        remainedTimeForSprint = maxTimeSprint;
        canSprint = true;
    }

    @EventHandler
    public void onToggleSprintF(PlayerToggleSprintEvent e) {
        Player p = e.getPlayer();

        if (this.p != p)
            return;

        maxTimeSprint = user.getLevelStamina() * 6;

        if (e.isSprinting() && !canSprint) {
            stopSprint();
            e.setCancelled(true);
            return;
        }

        if (e.isSprinting()) {
            if (remainedTimeForSprint == 0) {
                stopSprint();

                canSprint = false;
            }
        }
    }

    @EventHandler
    public void onUpdateSprint(UpdateEvent e) {
        if (e.getType() == UpdateType.SECOND) {
            int percentOfProgress = MathUtil.calculatePercentOfTwoNumber(remainedTimeForSprint, maxTimeSprint);
            double dateProgressBar = MathUtil.calculateNumberOfPercent(percentOfProgress, 1.F);

            bar.setProgress(dateProgressBar);
            checkColorBossBar();

            if (!p.isSprinting()) {
                if (remainedTimeForSprint == 0) {
                    if (startResumption == 0) {
                        startResumption = System.currentTimeMillis();
                        return;
                    }

                    if (MathUtil.getDifferenceTime(startResumption) >= (maxTimeSprint * 10) / 100) {
                        remainedTimeForSprint++;
                        canSprint = true;
                    } else
                        return;
                }

                if (remainedTimeForSprint >= maxTimeSprint)
                    return;

                remainedTimeForSprint++;
            } else {
                if (remainedTimeForSprint == 0) {
                    stopSprint();

                    canSprint = false;
                }
                else {
                    remainedTimeForSprint--;

                    if (MathUtil.getDifferenceTime(lastAddExp) >= 1 || lastAddExp == 0) {
                        lastAddExp = System.currentTimeMillis();

                        user.setExpStamina(user.getExpStamina() + 1);
                        user.checkLevel();
                    }

                    canSprint = true;
                }
            }
        }
    }

    private void stopSprint() {
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1, 1));
        p.setSprinting(false);
        MessageUtil.sendActioBar(p, "&c&lYou can`t sprint!");
    }

    private void checkColorBossBar() {
        if (bar.getProgress() >= 0.75)
            bar.setColor(BarColor.GREEN);
        else if (bar.getProgress() >= 0.5)
            bar.setColor(BarColor.YELLOW);
        else if (bar.getProgress() >= 0.25)
            bar.setColor(BarColor.PURPLE);
        else
            bar.setColor(BarColor.RED);
    }

}
