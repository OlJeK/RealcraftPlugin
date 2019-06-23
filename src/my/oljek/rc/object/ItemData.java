package my.oljek.rc.object;

import com.oljek.spigot.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class ItemData {

    public static ItemStack getFillBottleItem() {
        ItemStack fillBottle = ItemUtil.create(Material.POTION)
                .setName("&bBottle of purified water")
                .addLore("",
                        "&bRight-click to drink",
                        "&aRestores thirst")
                .getStack();

        PotionMeta meta = (PotionMeta) fillBottle.getItemMeta();

        meta.setBasePotionData(new PotionData(PotionType.WATER));
        fillBottle.setItemMeta(meta);

        return fillBottle;
    }

    public static ItemStack getHammerOfWoodworker() {
        ItemStack hammer = ItemUtil.create(Material.GOLD_AXE)
                .setName("&6Hammer of woodworker")
                .addLore("",
                        "&eYou can place wooden blocks with use",
                        "&ethis hammer.")
                .getStack();

        hammer.setDurability((short) 0);

        return hammer;
    }

    public static ItemStack getGlassBlockedSlot() {
        ItemStack glassBlockedSlot = ItemUtil.create(Material.STAINED_GLASS_PANE)
                .setDurabillity(14)
                .setName("&cSlot is blocked!")
                .addLore("",
                        "&eTo unlock this slot, you must",
                        "&eput elytra in your inventory")
                .getStack();

        return glassBlockedSlot;
    }

}
