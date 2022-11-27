package de.buildersinc.fts.cooking.events;

import de.buildersinc.fts.cooking.crafting.CraftingManager;
import de.buildersinc.fts.cooking.crafting.CraftingMatrix;
import de.buildersinc.fts.cooking.main.Cooking;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class PotGuiListener implements Listener {
    private Cooking plugin;

    public PotGuiListener(Cooking plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null) return;
        if (!itemStack.hasItemMeta()) return;

        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Cooking.getPlugin(), "pot");
        String id = container.get(key, PersistentDataType.STRING);
        if (id == null) return;

        if (id.equalsIgnoreCase("placeHolder")) {
            event.setCancelled(true);
        }

        System.out.println(CraftingMatrix.inventoryToMatrix(event.getClickedInventory()));

    }

}
