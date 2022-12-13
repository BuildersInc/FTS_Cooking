package de.buildersinc.fts.cooking.utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

/**
 * <pre>
 * Custom holder in order to identify a custom inventory
 * ==> if (inv.getHolder() instance of DummyHolder)
 *          ==> True -> Inventory is custom
 *          ==> False -> Inventory is not Custom
 * </pre>
 */
public class DummyHolder implements InventoryHolder {

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
