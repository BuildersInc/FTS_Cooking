package de.buildersinc.fts.cooking.events;

import de.buildersinc.fts.cooking.main.Cooking;
import de.buildersinc.fts.cooking.utils.GuiTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class PotPlaceListener implements Listener {
    private Cooking plugin;

    public PotPlaceListener(Cooking plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlaceEvent(BlockPlaceEvent event) {
        ItemStack itemStack = event.getItemInHand();
        NamespacedKey nsk = new NamespacedKey(Cooking.getPlugin(), "ftsCooking");

        if (!itemStack.getItemMeta().getPersistentDataContainer().has(nsk)) return;
        switch (itemStack.getItemMeta().getPersistentDataContainer().get(nsk, PersistentDataType.STRING)) {
            case "simple_pot" -> event.getPlayer().openInventory(potInv(ChatColor.BLACK, "Einfacher Topf"));
            case "good_pot" -> event.getPlayer().openInventory(potInv(ChatColor.DARK_GRAY, "Guter Topf"));
            case "super_pot" -> event.getPlayer().openInventory(potInv(ChatColor.YELLOW, "Herausragender Topf"));
        }

    }
    private Inventory potInv(ChatColor color, String name) {
        Inventory inv = GuiTools.createChestGui(5, "pot", color + name, Material.BLACK_STAINED_GLASS_PANE, false);

        inv.setItem(10, new ItemStack(Material.AIR));
        inv.setItem(11, new ItemStack(Material.AIR));
        inv.setItem(12, new ItemStack(Material.AIR));

        inv.setItem(10 + 9, new ItemStack(Material.AIR));
        inv.setItem(11 + 9, new ItemStack(Material.AIR));
        inv.setItem(12 + 9, new ItemStack(Material.AIR));

        inv.setItem(10 + 18, new ItemStack(Material.AIR));
        inv.setItem(11 + 18, new ItemStack(Material.AIR));
        inv.setItem(12 + 18, new ItemStack(Material.AIR));

        inv.setItem(12 + 9 + 3, new ItemStack(Material.AIR));


        return inv;
    }

}
