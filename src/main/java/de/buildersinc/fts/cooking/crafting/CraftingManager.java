package de.buildersinc.fts.cooking.crafting;

import de.buildersinc.fts.cooking.enums.Items;
import de.buildersinc.fts.cooking.main.Cooking;
import de.buildersinc.fts.cooking.tasks.BlockUpdateTask;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CraftingManager implements Listener {

    private final Cooking plugin;
    private List<CraftingMatrix> customCrafting;
    private static HashMap<Player, Block> playerBlockMap;
    private static HashMap<Block, BlockUpdateTask> blockBlockUpdateTaskMap;

    public CraftingManager(Cooking plugin) {
        this.plugin = plugin;
        playerBlockMap = new HashMap<>();
        blockBlockUpdateTaskMap = new HashMap<>();
        this.customCrafting = new ArrayList<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

    }
    public void loadRecipes() {
        initVanillaBasedRecipes();
        initCustomRecipes();
    }


    private void initVanillaBasedRecipes() {
        plugin.getServer().removeRecipe(Material.BRICK.getKey());
        plugin.getServer().removeRecipe(Material.COOKIE.getKey());
        plugin.getServer().removeRecipe(Material.PUMPKIN_PIE.getKey());

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

        ShapedRecipe betterPotato = new ShapedRecipe(new NamespacedKey(plugin, "bp"), Items.BACKED_POTATO_WITH_CURD.getItemStack());
        betterPotato.shape("kek", "spz", "ama");
        betterPotato.setIngredient('k', new ItemStack(Material.KELP));
        betterPotato.setIngredient('e', new ItemStack(Material.EGG));
        betterPotato.setIngredient('z', new ItemStack(Material.SUGAR));
        betterPotato.setIngredient('p', new ItemStack(Material.POTATO));
        betterPotato.setIngredient('s', Items.SALT.getItemStack());
        betterPotato.setIngredient('a', new ItemStack(Material.AIR));
        betterPotato.setIngredient('m', new ItemStack(Material.MILK_BUCKET));
        plugin.getServer().addRecipe(betterPotato);

        ShapedRecipe flour = new ShapedRecipe(new NamespacedKey(plugin, "flour"), Items.FLOUR.getItemStack());
        flour.shape("sws", "www", "sws");
        flour.setIngredient('s', new ItemStack(Material.WHEAT_SEEDS));
        flour.setIngredient('w', new ItemStack(Material.WHEAT));
        plugin.getServer().addRecipe(flour);

        ShapedRecipe pumpkinPieDough = new ShapedRecipe(new NamespacedKey(plugin, "pdd"), Items.PUMPKIN_PIE_DOUGH.getItemStack());
        pumpkinPieDough.shape("wsw", "epe", "zfz");
        pumpkinPieDough.setIngredient('w', new ItemStack(Material.WATER_BUCKET));
        pumpkinPieDough.setIngredient('z', new ItemStack(Material.SUGAR));
        pumpkinPieDough.setIngredient('e', new ItemStack(Material.EGG));
        pumpkinPieDough.setIngredient('p', new ItemStack(Material.PUMPKIN));
        pumpkinPieDough.setIngredient('s', Items.SALT.getItemStack());
        pumpkinPieDough.setIngredient('f', Items.FLOUR.getItemStack());
        plugin.getServer().addRecipe(pumpkinPieDough);

        ShapedRecipe sweetCakeDough = new ShapedRecipe(new NamespacedKey(plugin, "scd"), Items.SWEET_PIE.getItemStack());
        sweetCakeDough.shape("bsb", "fmf", "eme");
        sweetCakeDough.setIngredient('b', new ItemStack(Material.SWEET_BERRIES));
        sweetCakeDough.setIngredient('s', new ItemStack(Material.SUGAR));
        sweetCakeDough.setIngredient('m', new ItemStack(Material.MILK_BUCKET));
        sweetCakeDough.setIngredient('f', Items.FLOUR.getItemStack());
        sweetCakeDough.setIngredient('e', new ItemStack(Material.EGG));
        plugin.getServer().addRecipe(sweetCakeDough);

        ShapedRecipe cookieDough = new ShapedRecipe(new NamespacedKey(plugin, "cd"),Items.COOKIE_DOUGH.getItemStack());
        cookieDough.shape("wbw", "cwc", "sfs");
        cookieDough.setIngredient('w', new ItemStack(Material.WHEAT));
        cookieDough.setIngredient('c', new ItemStack(Material.COCOA_BEANS));
        cookieDough.setIngredient('b', new ItemStack(Material.WATER_BUCKET));
        cookieDough.setIngredient('s', new ItemStack(Material.SUGAR));
        cookieDough.setIngredient('f', Items.FLOUR.getItemStack());
        plugin.getServer().addRecipe(cookieDough);

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

        CraftingMatrix rabbitSoup = new CraftingMatrix("srq", "pcp", "jwk");
        rabbitSoup.setItem('s', Items.SALT.getItemStack());
        rabbitSoup.setItem('r', new ItemStack(Material.RABBIT));
        rabbitSoup.setItem('q', new ItemStack(Material.SUGAR));
        rabbitSoup.setItem('p', new ItemStack(Material.POTATO));
        rabbitSoup.setItem('c', new ItemStack(Material.CARROT));
        rabbitSoup.setItem('j', new ItemStack(Material.BROWN_MUSHROOM));
        rabbitSoup.setItem('w', new ItemStack(Material.WATER_BUCKET));
        rabbitSoup.setItem('k', new ItemStack(Material.RED_MUSHROOM));
        rabbitSoup.setResult(Items.RABBIT_SOUP.getItemStack());
        rabbitSoup.addRecipeAsMatrix(true);

        CraftingMatrix stew = new CraftingMatrix("hsh", "cpc", "gwg");
        stew.setItem('h', new ItemStack(Material.PORKCHOP));
        stew.setItem('s', new ItemStack(Material.SUGAR));
        stew.setItem('c', new ItemStack(Material.BEEF));
        stew.setItem('p', new ItemStack(Material.POTATO));
        stew.setItem('g', Items.SALT.getItemStack());
        stew.setItem('w', new ItemStack(Material.WATER_BUCKET));
        stew.setResult(Items.STEW.getItemStack());
        stew.addRecipeAsMatrix(true);

        CraftingMatrix algeaSpoup = new CraftingMatrix("aga", "glg", "sws");
        algeaSpoup.setItem('a', new ItemStack(Material.AIR));
        algeaSpoup.setItem('g', new ItemStack(Material.KELP));
        algeaSpoup.setItem('l', new ItemStack(Material.SEAGRASS));
        algeaSpoup.setItem('s', Items.SALT.getItemStack());
        algeaSpoup.setItem('w', new ItemStack(Material.WATER_BUCKET));
        algeaSpoup.setResult(Items.ALGEA_SOUP.getItemStack());
        algeaSpoup.addRecipeAsMatrix(true);

        CraftingMatrix potatoSoup = new CraftingMatrix("asa", "apa", "pwp");
        potatoSoup.setItem('a', new ItemStack(Material.AIR));
        potatoSoup.setItem('s', new ItemStack(Material.SUGAR));
        potatoSoup.setItem('p', new ItemStack(Material.POTATO));
        potatoSoup.setItem('w', new ItemStack(Material.WATER_BUCKET));
        potatoSoup.setResult(Items.POTATO_SOUP.getItemStack());
        potatoSoup.addRecipeAsMatrix(true);

        CraftingMatrix fishSoup = new CraftingMatrix("cks", "skc", "gwj");
        fishSoup.setItem('c', new ItemStack(Material.COD));
        fishSoup.setItem('s', new ItemStack(Material.SALMON));
        fishSoup.setItem('k', new ItemStack(Material.KELP));
        fishSoup.setItem('g', new ItemStack(Material.SUGAR));
        fishSoup.setItem('w', new ItemStack(Material.WATER_BUCKET));
        fishSoup.setItem('j', Items.SALT.getItemStack());
        fishSoup.setResult(Items.FISH_SOUP.getItemStack());
        fishSoup.addRecipeAsMatrix(true);

        CraftingMatrix chickenSoup = new CraftingMatrix("scs", "php", "hwh");
        chickenSoup.setItem('s', new ItemStack(Material.SUGAR));
        chickenSoup.setItem('c', new ItemStack(Material.CARROT));
        chickenSoup.setItem('p', new ItemStack(Material.POTATO));
        chickenSoup.setItem('h', new ItemStack(Material.CHICKEN));
        chickenSoup.setItem('w', new ItemStack(Material.WATER_BUCKET));
        chickenSoup.setResult(Items.CHICKEN_SOUP.getItemStack());
        chickenSoup.addRecipeAsMatrix(true);

    }

    public static BlockUpdateTask getTaskFromBlock(Block block) {
        return blockBlockUpdateTaskMap.get(block);
    }

    public static HashMap<Block, BlockUpdateTask> getProcessMap() {
        return blockBlockUpdateTaskMap;
    }

    public static void addPlayerBlock(Player player, Block block) {
        playerBlockMap.put(player, block);
    }
    public static Block getBlockFromPlayer(Player player) {
        return playerBlockMap.get(player);
    }

    public static void addToProcessMap(Block block, BlockUpdateTask task) {
        blockBlockUpdateTaskMap.put(block, task);
    }

    public void addRecipe(CraftingMatrix matrix) {
        customCrafting.add(matrix);
        plugin.getLogger().info(matrix + "was added");
    }

    public List<CraftingMatrix> getCustomCrafting() {
        return customCrafting;
    }
}
