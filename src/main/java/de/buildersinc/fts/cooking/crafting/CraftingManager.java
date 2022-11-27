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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.HashMap;


public class CraftingManager implements Listener {

    private Cooking plugin;
    private static HashMap<Player, Block> currentCraftingProcesses;

    public CraftingManager(Cooking plugin) {
        this.plugin = plugin;
        initVanillaBasedRecipes();
        initCustomRecipes();
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

    private void initCustomRecipes() {
        CraftingMatrix cleanWater = new CraftingMatrix("ara", "awa", "aaa");

        ItemStack bottle = new ItemStack(Material.POTION, 1);
        ItemMeta meta = bottle.getItemMeta();
        PotionMeta pmeta = (PotionMeta) meta;
        PotionData pdata = new PotionData(PotionType.WATER);
        pmeta.setBasePotionData(pdata);
        bottle.setItemMeta(meta);

        cleanWater.setItem('a', new ItemStack(Material.AIR));
        cleanWater.setItem('r', new ItemStack(Material.REDSTONE));
        cleanWater.setItem('w', bottle);
        cleanWater.setResult(Items.CLEAN_WATER.getItemStack());
        cleanWater.addRecipeAsMatrix(true);

        CraftingMatrix melonJuice = new CraftingMatrix("mmm", "hbh", "aaa");
        melonJuice.setItem('a', new ItemStack(Material.AIR));
        melonJuice.setItem('h', new ItemStack(Material.HONEYCOMB));
        melonJuice.setItem('m', new ItemStack(Material.MELON_SLICE));
        melonJuice.setItem('b', new ItemStack(Material.GLASS_BOTTLE));
        melonJuice.setResult(Items.MELON_JUICE.getItemStack());
        melonJuice.addRecipeAsMatrix(true);

        CraftingMatrix sweetFish = new CraftingMatrix("asa", "sfs", "asa");
        sweetFish.setItem('a', new ItemStack(Material.AIR));
        sweetFish.setItem('s', new ItemStack(Material.SUGAR));
        sweetFish.setItem('f', new ItemStack(Material.COOKED_COD));
        sweetFish.setResult(Items.SWEET_FISH.getItemStack());
        sweetFish.addRecipeAsMatrix(true);

        CraftingMatrix sourJam = new CraftingMatrix("aba", "bsb", "awa");
        sourJam.setItem('a', new ItemStack(Material.AIR));
        sourJam.setItem('b', new ItemStack(Material.SWEET_BERRIES));
        sourJam.setItem('w', new ItemStack(Material.WATER_BUCKET));
        sourJam.setItem('s', new ItemStack(Material.SUGAR));
        sourJam.setResult(Items.SOUR_JAM.getItemStack());
        sourJam.addRecipeAsMatrix(true);

        CraftingMatrix sweetJam = new CraftingMatrix("bbb", "bsb", "sws");
        sweetJam.setItem('b', new ItemStack(Material.SWEET_BERRIES));
        sweetJam.setItem('s', new ItemStack(Material.SUGAR));
        sweetJam.setItem('w', new ItemStack(Material.WATER_BUCKET));
        sweetJam.setResult(Items.SWEET_JAM.getItemStack());
        sweetJam.addRecipeAsMatrix(true);

        CraftingMatrix humanFlesh = new CraftingMatrix("srs", "rrr", "awa");
        humanFlesh.setItem('s', Items.SALT.getItemStack());
        humanFlesh.setItem('r', new ItemStack(Material.ROTTEN_FLESH));
        humanFlesh.setItem('a', new ItemStack(Material.AIR));
        humanFlesh.setItem('w', new ItemStack(Material.WATER_BUCKET));
        humanFlesh.setResult(Items.HUMAN_FLESH.getItemStack());
        humanFlesh.addRecipeAsMatrix(true);

        CraftingMatrix beetSoup = new CraftingMatrix("bbb", "bbb", "sws");
        beetSoup.setItem('b', new ItemStack(Material.BEETROOT));
        beetSoup.setItem('s', Items.SALT.getItemStack());
        beetSoup.setItem('w', new ItemStack(Material.WATER_BUCKET));
        beetSoup.setResult(Items.BEETROOT_SOUP.getItemStack());
        beetSoup.addRecipeAsMatrix(true);

        CraftingMatrix mushroomSoup = new CraftingMatrix("rsr", "bsb", "awa");
        mushroomSoup.setItem('r', new ItemStack(Material.RED_MUSHROOM));
        mushroomSoup.setItem('b', new ItemStack(Material.BROWN_MUSHROOM));
        mushroomSoup.setItem('s', Items.SALT.getItemStack());
        mushroomSoup.setItem('w', new ItemStack(Material.WATER_BUCKET));
        mushroomSoup.setItem('a', new ItemStack(Material.AIR));
        mushroomSoup.setResult(Items.SHROOM_SOUP.getItemStack());
        mushroomSoup.addRecipeAsMatrix(true);

    }

    public static Block getBlockFromPlayer(Player player) {
        return currentCraftingProcesses.get(player);
    }

    public static void addToProcessMap(Player player, Block block) {
        currentCraftingProcesses.put(player, block);
    }


}
