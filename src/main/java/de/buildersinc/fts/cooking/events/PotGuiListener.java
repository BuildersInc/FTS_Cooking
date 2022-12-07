package de.buildersinc.fts.cooking.events;

import com.jeff_media.customblockdata.CustomBlockData;
import de.buildersinc.fts.cooking.crafting.CraftingManager;
import de.buildersinc.fts.cooking.crafting.CraftingMatrix;
import de.buildersinc.fts.cooking.main.Cooking;
import de.buildersinc.fts.cooking.tasks.BlockUpdateTask;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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




        BlockUpdateTask blockUpdateTask = CraftingManager.getTaskFromBlock(CraftingManager.getBlockFromPlayer(player));
        Block block = blockUpdateTask.getBlock();

        if (block == null) return;


        CraftingMatrix invMatrix = CraftingMatrix.inventoryToMatrix(event.getInventory());
        CraftingMatrix resultMatrix = checkResult(invMatrix);
        PersistentDataContainer blockData = new CustomBlockData(block, Cooking.getPlugin());


        if (event.getSlot() == 12 + 9 + 3) {
            NamespacedKey resultFinish = new NamespacedKey(Cooking.getPlugin(), "ftsCookingCookingFinish");
            NamespacedKey resultCount = new NamespacedKey(Cooking.getPlugin(), "ftsCookingCookingFinishAmount");
            blockData.set(resultCount, PersistentDataType.INTEGER, 0);
            blockData.set(resultFinish, PersistentDataType.INTEGER, 0);

        }
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null) return;
        if (!itemStack.hasItemMeta()) return;

        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(Cooking.getPlugin(), "pot");
        String id = container.get(key, PersistentDataType.STRING);
        Block blockDown = block.getLocation().subtract(0, 1, 0).getBlock();

        if (id != null) {
            event.setCancelled(true);

            if (id.equalsIgnoreCase("startCooking") && blockDown.getType() == Material.CAMPFIRE && !((Lightable) blockDown.getBlockData()).isLit()) {
                if (resultMatrix != null) {
                    NamespacedKey resultItem = new NamespacedKey(Cooking.getPlugin(), "ftsCookingCookingResult");
                    NamespacedKey resultCount = new NamespacedKey(Cooking.getPlugin(), "ftsCookingCookingAmount");

                    blockData.set(resultCount, PersistentDataType.INTEGER, blockData.get(resultCount, PersistentDataType.INTEGER) == null ? 1 : blockData.get(resultCount, PersistentDataType.INTEGER) + 1);


                    ItemMeta resultItemMeta = resultMatrix.getResult().getItemMeta();
                    String itemString = resultItemMeta.getPersistentDataContainer().get(new NamespacedKey(plugin, "ftsCooking"), PersistentDataType.STRING);
                    blockData.set(resultItem, PersistentDataType.STRING, itemString);
                    clearCraftingField(event.getInventory());
                    if (!blockUpdateTask.isRunning()) {
                        blockUpdateTask.startTask();
                    }
                }
            }
        }
    }
//           if (blockDown.getType() == Material.CAMPFIRE && !((Lightable) blockDown.getBlockData()).isLit()) {
//        return;
//    }

    private CraftingMatrix checkResult(CraftingMatrix invMatrix) {
        for (CraftingMatrix matrix : Cooking.getCraftingManager().getCustomCrafting()) {
            if (matrix.compareTo(invMatrix) == 0) {
                return matrix;
            }
        }
        return null;
    }

    private void clearCraftingField(Inventory inventory) {
        int itemIndex;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                itemIndex = 10 + col + (9 * row);
                ItemStack item = inventory.getItem(itemIndex);
                if (item != null) {
                    item.setAmount(item.getAmount() - 1);
                }
            }
        }
    }

}
