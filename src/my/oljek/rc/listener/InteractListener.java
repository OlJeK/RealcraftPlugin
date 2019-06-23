package my.oljek.rc.listener;

import my.oljek.rc.object.ItemData;
import my.oljek.rc.object.Thirty;
import my.oljek.rc.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class InteractListener implements Listener {

    @EventHandler
    public void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        ItemStack stack = e.getItem();

        if (stack.getType() == Material.POTION) {
            PotionMeta meta = (PotionMeta) stack.getItemMeta();

            if (meta.getBasePotionData().getType() == PotionType.WATER) {
                Thirty thirty = UserListener.getThirty(p.getUniqueId());

                if (thirty == null) {
                    MessageUtil.sendActioBar(p, "&c&lAn error has occurred!");
                    return;
                }

                if (thirty.getThirty() == thirty.getMaxThirty()) {
                    MessageUtil.sendActioBar(p, "&c&lYou have no thirst!");
                    return;
                }

                boolean fillBottle = ItemData.getFillBottleItem().isSimilar(stack);
                int regenStandart = (fillBottle ? 75 : 35);
                int regenThirty = Math.min(thirty.getThirty() + regenStandart, thirty.getMaxThirty());
                int wasThirty = thirty.getThirty();
                int restoreThirty = regenThirty - wasThirty;

                thirty.setThirty(regenThirty);

                if (!fillBottle)
                    p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 30, 1));

                MessageUtil.sendActioBar(p, "&a&lYou recovered " + restoreThirty + " units of thirst" + (fillBottle ? "." : ". &c&lWater was poisoned!"));
            }
        }
    }

}
