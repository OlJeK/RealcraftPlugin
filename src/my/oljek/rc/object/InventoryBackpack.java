package my.oljek.rc.object;

import com.oljek.spigot.listener.custom.UpdateEvent;
import com.oljek.spigot.object.UpdateType;
import com.oljek.spigot.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryBackpack implements Listener {

    private Player p;

    public InventoryBackpack(Player p) {
        this.p = p;
    }

    @EventHandler
    public void onUpdate(UpdateEvent e) {
        if (e.getType() == UpdateType.SECOND) {
            ItemStack[] contents = p.getInventory().getContents();

            for (int i = 0; i < contents.length; i++) {
                if (contents[i] == null || contents[i].getType() == Material.AIR)
                    continue;

                ItemStack stack = contents[i].clone();

                stack.setAmount(1);

                if (stack.isSimilar(ItemData.getGlassBlockedSlot()))
                    if (i < 9 || i > 26)
                        p.getInventory().setItem(i, ItemUtil.create(Material.AIR).getStack());
            }

            if (p.getInventory().contains(Material.ELYTRA)) {
                while (p.getInventory().contains(ItemData.getGlassBlockedSlot()))
                    p.getInventory().remove(ItemData.getGlassBlockedSlot());
            } else {
                for (int i = 9; i <= 26; i++) {
                    ItemStack item = p.getInventory().getItem(i);

                    if (item != null && item.getType() != Material.AIR && !item.isSimilar(ItemData.getGlassBlockedSlot()))
                        p.getWorld().dropItemNaturally(p.getLocation(), item);

                    p.getInventory().setItem(i, ItemData.getGlassBlockedSlot());
                }
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Inventory inv = e.getClickedInventory();

            if (inv == null)
                return;

            if (inv.getType() == InventoryType.PLAYER) {
                ItemStack stackClicked = e.getCurrentItem();

                stackClicked.setAmount(1);

                if (stackClicked.isSimilar(ItemData.getGlassBlockedSlot()))
                    e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        Item item = e.getItem();
        ItemStack stack = item.getItemStack().clone();

        stack.setAmount(1);

        if (stack.isSimilar(ItemData.getGlassBlockedSlot())) {
            e.setCancelled(true);

            item.remove();
        }
    }

}
