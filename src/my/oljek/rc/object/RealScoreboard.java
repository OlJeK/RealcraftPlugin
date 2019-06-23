package my.oljek.rc.object;

import com.oljek.main.util.StringUtil;
import com.oljek.spigot.listener.custom.UpdateEvent;
import com.oljek.spigot.object.UpdateType;
import eu.haelexuis.utils.xoreboard.XoreBoard;
import eu.haelexuis.utils.xoreboard.XoreBoardPlayerSidebar;
import eu.haelexuis.utils.xoreboard.XoreBoardUtil;
import my.oljek.rc.util.MathUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class RealScoreboard implements Listener {

    private XoreBoard xoreBoard;
    private XoreBoardPlayerSidebar sidebar;
    private RealisticUser user;
    private Thirty thirty;

    public RealScoreboard(Player p, RealisticUser user, Thirty thirty) {
        this.user = user;
        this.thirty = thirty;
        xoreBoard = XoreBoardUtil.getNextXoreBoard();
        sidebar = xoreBoard.getSidebar(p);

        sidebar.setDisplayName("Test");
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getType() == UpdateType.SECOND) {
            HashMap<String, Integer> lines = new HashMap<>();

            int expStamina = user.getExpStamina();
            int needExpToNextLevel = user.getExpToNextLevel(user.getLevelStamina());
            int percentOfNextLevel = MathUtil.calculatePercentOfTwoNumber(expStamina, needExpToNextLevel);
            int levelStamina = user.getLevelStamina();
            int durationStamina = levelStamina * 6;
            int thirty = this.thirty.getThirty();
            int maxThirty = this.thirty.getMaxThirty();
            int percentOfThirst = MathUtil.calculatePercentOfTwoNumber(thirty, maxThirty);

            String percentStringOfNextLevel = MathUtil.getPercentString(expStamina, needExpToNextLevel);
            String percentStringOfThirst = MathUtil.getPercentString(thirty, maxThirty);

            lines.put("For next level stamina:", 11);
            lines.put(user.getExpStamina() + "/" + user.getExpToNextLevel(user.getLevelStamina()) + " EXP", 10);
            lines.put(StringUtil.inColor("  " + percentStringOfNextLevel + " &6(" + percentOfNextLevel + "%)"), 9);
            lines.put("", 8);
            lines.put("Level now:", 7);
            lines.put("  - " + levelStamina, 6);
            lines.put(" ", 5);
            lines.put("Duration of stamina:", 4);
            lines.put("  - " + durationStamina + " second", 3);
            lines.put("  ", 2);
            lines.put("Your level of thirst:", 1);
            lines.put(StringUtil.inColor("  " + percentStringOfThirst + " &6(" + percentOfThirst + "%)"), 0);

            sidebar.rewriteLines(lines);
            sidebar.showSidebar();
        }
    }

}
