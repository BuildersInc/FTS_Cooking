package de.buildersinc.fts.cooking.events;

import de.buildersinc.fts.cooking.crafting.CraftingManager;
import de.buildersinc.fts.cooking.main.Cooking;

import de.buildersinc.fts.cooking.utils.DummyHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuiInventoryCloseEvent implements Listener {
    private final Cooking plugin;
    public GuiInventoryCloseEvent(Cooking plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void GuiClose(InventoryCloseEvent event) {
         if (event.getInventory().getHolder() instanceof DummyHolder) {
             CraftingManager.getProcessMap().remove(event.getPlayer());
             Player player = (Player) event.getPlayer();
             List<ItemStack> itemsToDrop = getItemsInCraftingInterface(event.getInventory());
             for (ItemStack item : itemsToDrop) {
                 if (item != null) {
                     player.getWorld().dropItemNaturally(player.getLocation(), item);
                 }
             }
         }
    }

    private List<ItemStack> getItemsInCraftingInterface(Inventory inv) {
        List<ItemStack> returnList = new ArrayList<>();
        int itemIndex;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                itemIndex = 10 + col + (9 * row);
                returnList.add(inv.getItem(itemIndex));
            }
        }
        return returnList;
    }

}
