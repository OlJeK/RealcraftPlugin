package my.oljek.rc.util;

import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class StackUtil {

    public static ItemStack setDefaultKey(String key, NBTBase base, ItemStack stack) {
        net.minecraft.server.v1_12_R1.ItemStack minecraftStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound compound = (minecraftStack.hasTag() ? minecraftStack.getTag() : new NBTTagCompound());

        if (!compound.hasKey(key)) {
            compound.set(key, base);
            minecraftStack.setTag(compound);
        }

        return CraftItemStack.asBukkitCopy(minecraftStack);
    }

    public static ItemStack setKey(String key, NBTBase base, ItemStack stack) {
        net.minecraft.server.v1_12_R1.ItemStack minecraftStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound compound = (minecraftStack.hasTag() ? minecraftStack.getTag() : new NBTTagCompound());

        compound.set(key, base);
        minecraftStack.setTag(compound);

        return CraftItemStack.asBukkitCopy(minecraftStack);
    }

    public static NBTBase getKey(String key, ItemStack stack) {
        net.minecraft.server.v1_12_R1.ItemStack minecraftStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound compound = (minecraftStack.hasTag() ? minecraftStack.getTag() : new NBTTagCompound());

        return compound.get(key);
    }

    public static boolean hasKey(String key, ItemStack stack) {
        net.minecraft.server.v1_12_R1.ItemStack minecraftStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound compound = (minecraftStack.hasTag() ? minecraftStack.getTag() : new NBTTagCompound());

        return compound.hasKey(key);
    }

    public static ItemStack removeTag(ItemStack stack) {
        net.minecraft.server.v1_12_R1.ItemStack minecraftStack = CraftItemStack.asNMSCopy(stack);

        if (minecraftStack.hasTag())
            minecraftStack.setTag(null);

        return CraftItemStack.asBukkitCopy(minecraftStack);
    }

    public static boolean equalsItem(ItemStack stackOne, ItemStack stackTwo) {
        return stackOne.getType() == stackTwo.getType() && stackOne.hasItemMeta() && stackTwo.hasItemMeta() &&
                stackOne.getItemMeta().hasDisplayName() && stackTwo.getItemMeta().hasDisplayName() &&
                stackOne.getItemMeta().getDisplayName().equals(stackTwo.getItemMeta().getDisplayName()) &&
                stackOne.getItemMeta().hasLore() && stackTwo.getItemMeta().hasLore() &&
                stackOne.getItemMeta().getLore().equals(stackTwo.getItemMeta().getLore());
    }

}
