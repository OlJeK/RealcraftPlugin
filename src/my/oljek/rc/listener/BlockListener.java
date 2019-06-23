package my.oljek.rc.listener;

import com.mojang.authlib.yggdrasil.response.User;
import my.oljek.rc.RealcraftPlugin;
import my.oljek.rc.object.ItemData;
import my.oljek.rc.object.UserConfig;
import my.oljek.rc.util.MathUtil;
import my.oljek.rc.util.MessageUtil;
import my.oljek.rc.util.StackUtil;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BlockListener implements Listener {

    private RealcraftPlugin plugin;

    public BlockListener(RealcraftPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        final Player p = e.getPlayer();
        final Block b = e.getBlock();
        final ItemStack itemInMainHand = p.getInventory().getItemInMainHand();

        if (p.getGameMode() == GameMode.CREATIVE)
            return;

        if (isWood(b.getType())) {
            switch (itemInMainHand.getType()) {
                case WOOD_AXE:
                case STONE_AXE:
                case IRON_AXE:
                case GOLD_AXE:
                case DIAMOND_AXE:
                    return;
            }

            e.setCancelled(true);
            MessageUtil.sendActioBar(p, "&c&lYou can`t break this block without axe!");
        }

        if (isStone(b.getType())) {
            switch (itemInMainHand.getType()) {
                case WOOD_PICKAXE:
                case GOLD_PICKAXE:
                case STONE_PICKAXE:
                case DIAMOND_PICKAXE:
                case IRON_PICKAXE:
                    return;
            }

            e.setCancelled(true);
            MessageUtil.sendActioBar(p, "&c&lYou can`t break this block without pickax!");
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlockPlaced();

        ItemStack stack = p.getInventory().getItemInOffHand();
        ItemStack hammer = ItemData.getHammerOfWoodworker();

        boolean isWood = isWood(b.getType());

        if (p.getGameMode() == GameMode.CREATIVE)
            return;

        if (isWood && stack != null && StackUtil.equalsItem(stack, hammer)) {
            stack = StackUtil.setDefaultKey("durabilityNow", new NBTTagInt(512), stack);
            int durability = ((NBTTagInt) StackUtil.getKey("durabilityNow", stack)).e();

            if (durability - 1 <= 0) {
                stack.setType(Material.AIR);
                p.getInventory().setItemInMainHand(stack);
                return;
            }

            stack = StackUtil.setKey("durabilityNow", new NBTTagInt(durability - 1), stack);
            int durabilityOfStack = 32 - MathUtil.calculateNumberOfPercent(MathUtil.calculatePercentOfTwoNumber(durability, 512), 32);

            stack.setDurability((short) durabilityOfStack);
            p.getInventory().setItemInOffHand(stack);
        } else if (isWood) {
            e.setCancelled(true);
            MessageUtil.sendActioBar(p, "&c&lYou must take a carpenter's hammer to place this block.");
        }
    }

    private boolean isWood(Material mat) {
        switch (mat) {
            case LOG:
            case WOOD:
            case LOG_2:
            case WOOD_STEP:
            case SPRUCE_WOOD_STAIRS:
            case WOOD_STAIRS:
            case BIRCH_WOOD_STAIRS:
            case JUNGLE_WOOD_STAIRS:
            case LADDER:
            case CHEST:
            case WORKBENCH:
            case TORCH:
            case FENCE:
            case FENCE_GATE:
            case ACACIA_FENCE_GATE:
            case SPRUCE_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
            case DARK_OAK_FENCE_GATE:
            case BIRCH_FENCE_GATE:
            case SIGN:
            case SIGN_POST:
            case WALL_SIGN:
            case BANNER:
            case STANDING_BANNER:
            case WALL_BANNER:
            case ARMOR_STAND:
            case BED:
            case BED_BLOCK:
            case WOODEN_DOOR:
            case WOOD_DOOR:
            case TRAP_DOOR:
            case SPRUCE_DOOR:
            case JUNGLE_DOOR:
            case IRON_DOOR:
            case BIRCH_DOOR:
            case ACACIA_DOOR:
            case DARK_OAK_DOOR:
            case WOOD_PLATE:
            case BOOKSHELF:
                return true;
        }

        return false;
    }

    private boolean isStone(Material mat) {
        switch (mat) {
            case EMERALD_ORE:
            case EMERALD_BLOCK:
            case NETHER_BRICK:
            case NETHER_BRICK_STAIRS:
            case SANDSTONE_STAIRS:
            case COBBLESTONE:
            case COBBLESTONE_STAIRS:
            case BRICK_STAIRS:
            case BRICK:
            case SMOOTH_BRICK:
            case SMOOTH_STAIRS:
            case REDSTONE_ORE:
            case REDSTONE:
            case RED_SANDSTONE_STAIRS:
            case DIAMOND_BLOCK:
            case DIAMOND_ORE:
            case OBSIDIAN:
            case MOSSY_COBBLESTONE:
            case STONE_SLAB2:
            case STEP:
            case DOUBLE_STEP:
            case IRON_BLOCK:
            case IRON_ORE:
            case GOLD_BLOCK:
            case GOLD_ORE:
            case COAL_ORE:
            case COAL_BLOCK:
            case STONE:
            case MONSTER_EGGS:
            case IRON_FENCE:
            case NETHER_FENCE:
            case ENCHANTMENT_TABLE:
            case ENDER_CHEST:
            case COBBLE_WALL:
            case ENDER_PORTAL_FRAME:
            case NETHER_WART_BLOCK:
            case RED_NETHER_BRICK:
            case BONE_BLOCK:
            case CONCRETE:
            case FURNACE:
            case RED_SANDSTONE:
            case PURPUR_BLOCK:
            case PURPUR_PILLAR:
            case PURPUR_STAIRS:
            case PURPUR_SLAB:
            case PURPUR_DOUBLE_SLAB:
            case END_BRICKS:
            case END_CRYSTAL:
            case ENDER_STONE:
            case PRISMARINE:
            case PRISMARINE_CRYSTALS:
            case STAINED_CLAY:
            case QUARTZ_BLOCK:
            case QUARTZ_ORE:
            case QUARTZ_STAIRS:
            case STONE_BUTTON:
            case GOLD_PLATE:
            case IRON_PLATE:
            case REDSTONE_BLOCK:
            case HOPPER:
            case DROPPER:
            case IRON_TRAPDOOR:
            case IRON_DOOR_BLOCK:
            case LEVER:
            case DISPENSER:
            case ANVIL:
            case PISTON_STICKY_BASE:
            case STONE_PLATE:
            case OBSERVER:
            case PISTON_BASE:
            case SANDSTONE:
            case LAPIS_BLOCK:
            case LAPIS_ORE:
            case MAGMA:
            case HARD_CLAY:
            case YELLOW_GLAZED_TERRACOTTA:
            case WHITE_GLAZED_TERRACOTTA:
            case BLACK_GLAZED_TERRACOTTA:
            case BLUE_GLAZED_TERRACOTTA:
            case BROWN_GLAZED_TERRACOTTA:
            case CYAN_GLAZED_TERRACOTTA:
            case GRAY_GLAZED_TERRACOTTA:
            case GREEN_GLAZED_TERRACOTTA:
            case LIGHT_BLUE_GLAZED_TERRACOTTA:
            case LIME_GLAZED_TERRACOTTA:
            case MAGENTA_GLAZED_TERRACOTTA:
            case ORANGE_GLAZED_TERRACOTTA:
            case PINK_GLAZED_TERRACOTTA:
            case PURPLE_GLAZED_TERRACOTTA:
            case RED_GLAZED_TERRACOTTA:
            case SILVER_GLAZED_TERRACOTTA:
            case SHULKER_SHELL:
            case BLACK_SHULKER_BOX:
            case SILVER_SHULKER_BOX:
            case BLUE_SHULKER_BOX:
            case BROWN_SHULKER_BOX:
            case CYAN_SHULKER_BOX:
            case GRAY_SHULKER_BOX:
            case GREEN_SHULKER_BOX:
            case LIGHT_BLUE_SHULKER_BOX:
            case LIME_SHULKER_BOX:
            case MAGENTA_SHULKER_BOX:
            case ORANGE_SHULKER_BOX:
            case PINK_SHULKER_BOX:
            case PURPLE_SHULKER_BOX:
            case RED_SHULKER_BOX:
            case WHITE_SHULKER_BOX:
            case YELLOW_SHULKER_BOX:
            case NOTE_BLOCK:
                return true;
        }

        return false;
    }

}
