package my.oljek.rc.object;

import com.oljek.spigot.util.ItemUtil;
import my.oljek.rc.RealcraftPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class Recipe {

    private RealcraftPlugin plugin;

    public Recipe(RealcraftPlugin plugin) {
        this.plugin = plugin;
    }

    public void addRecipes() {
        ShapelessRecipe fillBottle = new ShapelessRecipe(new NamespacedKey(plugin, "fillBottle"), ItemData.getFillBottleItem());
        ShapelessRecipe hammerWoodWorker = new ShapelessRecipe(new NamespacedKey(plugin, "hammerWoodWorker"), ItemData.getHammerOfWoodworker());
        FurnaceRecipe flesh = new FurnaceRecipe(ItemUtil.create(Material.COOKED_BEEF).getStack(), Material.ROTTEN_FLESH);

        fillBottle.addIngredient(Material.POTION);
        fillBottle.addIngredient(Material.SAND);
        fillBottle.addIngredient(Material.COAL);

        hammerWoodWorker.addIngredient(Material.GOLD_AXE);
        hammerWoodWorker.addIngredient(Material.LOG);

        Bukkit.addRecipe(fillBottle);
        Bukkit.addRecipe(hammerWoodWorker);
        Bukkit.addRecipe(flesh);
    }

}
