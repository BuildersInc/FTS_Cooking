package de.buildersinc.fts.cooking.utils;

import de.buildersinc.fts.cooking.main.Cooking;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class GuiTools {
    /**
     * Creates an Item
     *
     * @param keyString keyName in Namespace
     * @param label     Name of the Item
     * @param material  material of the Item
     * @param id        CustomID to identify
     * @param glint     item glows
     * @return created Item
     */
    public static ItemStack createItem(String keyString, String label, Material material, String id, boolean glint) {
        NamespacedKey key = new NamespacedKey(Cooking.getPlugin(), keyString);
        Component name = Component.text(label);
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(name);

        if (glint) {
            meta.addEnchant(Enchantment.MENDING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, id);
        item.setItemMeta(meta);

        return item;

    }

    public static ItemStack createItemWithLore(String keyString, String label, Material material, String id, boolean glint, HashMap<ChatColor, String> lore) {
        NamespacedKey key = new NamespacedKey(Cooking.getPlugin(), keyString);
        Component name = Component.text(label);
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(name);

        if (glint) {
            meta.addEnchant(Enchantment.MENDING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, id);
        List<Component> loreList = new ArrayList<>();


        for (Map.Entry<ChatColor, String> entry : lore.entrySet()) {
            ChatColor color = entry.getKey();
            String text = entry.getValue();

            loreList.add(Component.text(color + text));
        }

        meta.lore(loreList);

        item.setItemMeta(meta);

        return item;

    }


    /**
     * Creates an inventory with a filler material
     *
     * @param height          the height of the Inventory
     * @param nameOfInventory title of the Interface
     * @param material        material of the filler item
     * @return chestGui
     */
    public static Inventory createChestGui(int height, String nameSpace, String nameOfInventory, Material material, boolean glint, boolean check) {
        Inventory inv;
        if (check) {
            inv = Bukkit.createInventory(new DummyHolder(), 9 * height, Component.text(nameOfInventory));
        } else {
            inv = Bukkit.createInventory(null, 9 * height, Component.text(nameOfInventory));
        }
        ItemStack placeholder = nonAirPlaceholder(material, nameSpace, glint);
        for (int i = 0; i < height * 9; i++) {
            inv.setItem(i, placeholder);
        }
        return inv;
    }

    /**
     * Creates a Placeholder Item that is just there to block a slot
     *
     * @param material the Item material that is used
     * @return the modified Item
     */
    private static ItemStack nonAirPlaceholder(Material material, String nameSpace, boolean glint) {
        NamespacedKey key = new NamespacedKey(Cooking.getPlugin(), nameSpace);
        ItemStack itemStack = new ItemStack(material);

        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(Component.text(" "));
        if (glint) {
            meta.addEnchant(Enchantment.MENDING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "placeHolder");
        itemStack.setItemMeta(meta);

        return itemStack;

    }
}
