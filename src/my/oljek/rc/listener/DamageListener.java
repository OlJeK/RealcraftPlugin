package my.oljek.rc.listener;

import com.oljek.spigot.util.ItemUtil;
import my.oljek.rc.util.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && !(e.getEntity() instanceof Player)) {
            Player p = (Player) e.getDamager();

            if (e.getEntity() instanceof Chicken)
                return;

            ItemStack stackInHand = p.getInventory().getItemInMainHand();

            switch (stackInHand.getType()) {
                case WOOD_SWORD:
                case STONE_SWORD:
                case GOLD_SWORD:
                case IRON_SWORD:
                case DIAMOND_SWORD:
                case BOW:
                case WOOD_AXE:
                case IRON_AXE:
                case GOLD_AXE:
                case STONE_AXE:
                case DIAMOND_AXE:
                    return;
            }

            e.setCancelled(true);
            MessageUtil.sendActioBar(p, "&c&lYou can`t damage mobs without weapon!");
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        e.getDrops().add(ItemUtil.create(Material.LEATHER).getStack());
    }

}
