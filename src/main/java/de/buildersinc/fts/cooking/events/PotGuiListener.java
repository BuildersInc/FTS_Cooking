package de.buildersinc.fts.cooking.events;

import de.buildersinc.fts.cooking.crafting.CraftingManager;
import de.buildersinc.fts.cooking.crafting.CraftingMatrix;
import de.buildersinc.fts.cooking.main.Cooking;
import de.buildersinc.fts.cooking.tasks.BlockUpdateTask;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
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

        CraftingMatrix invMatrix = CraftingMatrix.inventoryToMatrix(event.getInventory());
        if (checkResult(invMatrix, event.getInventory())) {
            clearCraftingField(event.getInventory());
        }

        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null) return;
        if (!itemStack.hasItemMeta()) return;

        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Cooking.getPlugin(), "pot");
        String id = container.get(key, PersistentDataType.STRING);
        if (id != null) {
            event.setCancelled(true);
            if (id.equalsIgnoreCase("startCooking")) {
                Block block = CraftingManager.getBlockFromPlayer(player);
                BlockUpdateTask blockUpdateTask = new BlockUpdateTask(block);
                blockUpdateTask.startTask();
            }
        }
    }

    private boolean checkResult(CraftingMatrix invMatrix, Inventory inventory) {
        for (CraftingMatrix matrix : Cooking.getCraftingManager().getCustomCrafting()) {
            if (matrix.compareTo(invMatrix) == 0) {
                inventory.setItem(24, matrix.getResult());
                return true;
            }
        }
        return false;
    }

    private void clearCraftingField(Inventory inventory) {
        int itemIndex;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                itemIndex = 10 + col + (9 * row);
                inventory.setItem(itemIndex, new ItemStack(Material.AIR));
            }
        }
    }

}
