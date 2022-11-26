package de.buildersinc.fts.cooking.crafting;

import de.buildersinc.fts.cooking.enums.Items;
import de.buildersinc.fts.cooking.main.Cooking;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

import java.util.HashMap;


public class CraftingManager implements Listener {

    private Cooking plugin;
    private static HashMap<Player, Block> currentCraftingProcesses;

    public CraftingManager(Cooking plugin) {
        this.plugin = plugin;
        initVanillaBasedRecipes();
        currentCraftingProcesses = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        currentCraftingProcesses.remove(event.getPlayer());
    }

    private void initVanillaBasedRecipes() {
        plugin.getServer().removeRecipe(Material.BRICK.getKey());

        // Cookie
        NamespacedKey cookieKey = new NamespacedKey(plugin, "cookieKey");
        FurnaceRecipe cookie = new FurnaceRecipe(cookieKey, Items.COOKIE.getItemStack(), new RecipeChoice.ExactChoice(Items.COOKIE_DOUGH.getItemStack()), 1.00F, 20);
        plugin.getServer().addRecipe(cookie);

        // Sweet Cake
        NamespacedKey sweetCakeKey = new NamespacedKey(plugin, "sweetCakeKey");
        FurnaceRecipe sweetCake = new FurnaceRecipe(sweetCakeKey, Items.SWEET_PIE.getItemStack(), new RecipeChoice.ExactChoice(Items.SWEET_PIE_DOUGH.getItemStack()), 3.00F, 30);
        plugin.getServer().addRecipe(sweetCake);

        // Pumpkin pie
        NamespacedKey pumpkinPieKey = new NamespacedKey(plugin, "pumpkinPieKey");
        FurnaceRecipe pumpkinPie = new FurnaceRecipe(pumpkinPieKey, Items.PUMPKIN_PIE.getItemStack(), new RecipeChoice.ExactChoice(Items.PUMPKIN_PIE_DOUGH.getItemStack()), 3.00F, 30);
        plugin.getServer().addRecipe(pumpkinPie);

        // Normal brick
        NamespacedKey brickKey = new NamespacedKey(plugin, "brickKey");
        FurnaceRecipe brick = new FurnaceRecipe(brickKey, new ItemStack(Material.CLAY_BALL) , new RecipeChoice.ExactChoice(new ItemStack(Material.BRICK)), 1.00F, 45);
        plugin.getServer().addRecipe(brick);
    }

    public static Block getBlockFromPlayer(Player player) {
        return currentCraftingProcesses.get(player);
    }

    public static void addToProcessMap(Player player, Block block) {
        currentCraftingProcesses.put(player, block);
    }


}
