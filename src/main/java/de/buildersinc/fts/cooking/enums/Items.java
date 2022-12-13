package de.buildersinc.fts.cooking.enums;

import de.buildersinc.fts.cooking.utils.GuiTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public enum Items {
    SALT(Material.BONE_MEAL, "Salz", 0),  // Crafting resource
    FLOUR(Material.BONE_MEAL, "Mehl", 0),  // Crafting resource
    CLEAN_WATER(Material.HONEY_BOTTLE, "Sauberes Wasser", 5),
    MELON_JUICE(Material.HONEY_BOTTLE, "Melonen Saft", 15),
    SWEET_FISH(Material.COOKED_COD, "Süßer Fisch", 20),
    SOUR_JAM(Material.HONEY_BOTTLE, "Saure Marmelade", 20),
    SWEET_JAM(Material.HONEY_BOTTLE, "Süße Marmelade", 30),
    HUMAN_FLESH(Material.ROTTEN_FLESH, "Menschenfleisch", 15),
    BEETROOT_SOUP(Material.BEETROOT_SOUP, "Rote beete Supper", 25),
    SHROOM_SOUP(Material.MUSHROOM_STEW, "Pilzsuppe", 25),
    RABBIT_SOUP(Material.RABBIT_STEW, "Kaninchen Suppe", 45),
    STEW(Material.RABBIT_STEW, "Eintopf", 45),
    ALGEA_SOUP(Material.MUSHROOM_STEW, "Algensuppe", 15),
    POTATO_SOUP(Material.MUSHROOM_STEW, "Kartoffelsuppe", 20),
    FISH_SOUP(Material.MUSHROOM_STEW, "Fischsuppe", 40),
    CHICKEN_SOUP(Material.MUSHROOM_STEW, "Hühnchen suppe", 45),
    PUMPKIN_PIE_DOUGH(Material.CLAY_BALL, "Kürbiskuchen Teig", 0),  // Crafting resource
    PUMPKIN_PIE(Material.PUMPKIN_PIE, "Kürbiskuchen", 0),  // #Ofen
    SWEET_PIE_DOUGH(Material.CLAY_BALL, "Süßer Kuchen Teig", 0),  // Crafting resource
    SWEET_PIE(Material.PUMPKIN_PIE, "Süßer Kuchen", 0), // #Ofen
    COOKIE_DOUGH(Material.CLAY_BALL, "Keksteig", 0),  // Crafting resource #CraftingTable
    COOKIE(Material.COOKIE, "Keks", 0), // #Ofen
    BACKED_POTATO(Material.BAKED_POTATO, "Gebackene Kartoffel", 0), // #Ofen
    BACKED_POTATO_WITH_CURD(Material.BAKED_POTATO, "Gebackene Kartoffel mit Quark", 0), // #CraftingTable
    SIMPLE_POT(Material.CAULDRON, "Einfacher Topf", 0),
    GOOD_POT(Material.CAULDRON, "Guter Topf", 0),
    SUPER_POT(Material.CAULDRON, "Perfekter Topf", 0)
    ;

    private final int cookingTime;
    private ItemStack itemStack;
    Items(Material material, String itemName, int cookingTime) {
        this.cookingTime = cookingTime;
        this.itemStack = GuiTools.createItem("ftsCooking",
                ChatColor.GOLD + itemName,
                material,
                this.toString().toUpperCase(),
                false);

    }

    public int getCookingTime() {
        return cookingTime;
    }
    public ItemStack getItemStack() {
        return itemStack;
    }
    public ItemStack getItemStack(int amount) {
        ItemStack is = itemStack.clone();
        is.setAmount(amount);
        return is;
    }
}
