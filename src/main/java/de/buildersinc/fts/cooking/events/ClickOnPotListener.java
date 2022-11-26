package de.buildersinc.fts.cooking.events;

import de.buildersinc.fts.cooking.enums.Items;
import de.buildersinc.fts.cooking.main.Cooking;
import de.buildersinc.fts.cooking.utils.GuiTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickOnPotListener implements Listener {
    private Cooking plugin;

    public ClickOnPotListener(Cooking plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getClickedBlock().getType() != Material.CAULDRON) return;

        Inventory debugInv = GuiTools.createChestGui(5, "Debug", "debug", Material.GLASS_PANE, false);
        int i = 0;
        for (Items item : Items.values()) {
            debugInv.setItem(i++, item.getItemStack());
        }
        event.getPlayer().openInventory(debugInv);


    }
}
