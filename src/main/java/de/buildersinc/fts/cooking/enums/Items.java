package de.buildersinc.fts.cooking.enums;

import de.buildersinc.fts.cooking.utils.GuiTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public enum Items {
    SALT(Material.BONE_MEAL, "Salz"),  // Crafting resource
    FLOUR(Material.BONE_MEAL, "Mehl"),  // Crafting resource
    CLEAN_WATER(Material.HONEY_BOTTLE, "Sauberes Wasser"),
    MELON_JUICE(Material.HONEY_BOTTLE, "Melonen Saft"),
    SWEET_FISH(Material.COOKED_COD, "Süßer Fisch"),
    SOUR_JAM(Material.HONEY_BOTTLE, "Saure Marmelade"),
    SWEET_JAM(Material.HONEY_BOTTLE, "Süße Marmelade"),
    HUMAN_FLESH(Material.ROTTEN_FLESH, "Menschenfleisch"),
    BEETROOT_SOUP(Material.BEETROOT_SOUP, "Rote beete Supper"),
    SHROOM_SOUP(Material.MUSHROOM_STEW, "Pilzsuppe"),
    RABBIT_SOUP(Material.RABBIT_STEW, "Kaninchen Suppe"),
    STEW(Material.RABBIT_STEW, "Eintopf"),
    ALGEA_SOUP(Material.MUSHROOM_STEW, "Algensuppe"),
    POTATO_SOUP(Material.MUSHROOM_STEW, "Kartoffelsuppe"),
    FISH_SOUP(Material.MUSHROOM_STEW, "Fischsuppe"),
    CHICKEN_SOUP(Material.MUSHROOM_STEW, "Hühnchen suppe"),
    PUMPKIN_PIE_DOUGH(Material.CLAY_BALL, "Kürbiskuchen Teig"),  // Crafting resource
    PUMPKIN_PIE(Material.PUMPKIN_PIE, "Kürbiskuchen"),
    SWEET_PIE_DOUGH(Material.CLAY_BALL, "Süßer Kuchen Teig"),  // Crafting resource
    SWEET_PIE(Material.PUMPKIN_PIE, "Süßer Kuchen"),
    COOKIE_DOUGH(Material.CLAY_BALL, "Keksteig"),  // Crafting resource
    COOKIE(Material.COOKIE, "Keks"),
    BACKED_POTATO(Material.BAKED_POTATO, "Gebackene Kartoffel"),
    BACKED_POTATO_WITH_CURD(Material.BAKED_POTATO, "Gebackene Kartoffel mit Quark"),
    SIMPLE_POT(Material.CAULDRON, "Einfacher Topf"),
    GOOD_POT(Material.CAULDRON, "Guter Topf"),
    SUPER_POT(Material.CAULDRON, "Perfekter Topf")
    ;

    private ItemStack itemStack;
    Items(Material material, String itemName) {
        this.itemStack = GuiTools.createItem("ftsCooking",
                ChatColor.GOLD + itemName,
                material,
                this.toString().toLowerCase(),
                false);

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
