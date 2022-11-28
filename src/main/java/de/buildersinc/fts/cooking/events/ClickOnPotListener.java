package de.buildersinc.fts.cooking.events;

import com.jeff_media.customblockdata.CustomBlockData;
import de.buildersinc.fts.cooking.crafting.CraftingManager;
import de.buildersinc.fts.cooking.enums.Items;
import de.buildersinc.fts.cooking.main.Cooking;
import de.buildersinc.fts.cooking.utils.GuiTools;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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

        NamespacedKey nsk = new NamespacedKey(plugin, "ftsCooking");
        PersistentDataContainer container = new CustomBlockData(event.getClickedBlock(), plugin);

        String potType = container.get(nsk, PersistentDataType.STRING);

        if (potType == null) return;

        switch (potType.toLowerCase()) {
            case "simple_pot" -> event.getPlayer().openInventory(potInv(ChatColor.BLACK, "Einfacher Topf"));
            case "good_pot" ->  event.getPlayer().openInventory(potInv(ChatColor.DARK_GRAY, "Guter Topf"));
            case "super_pot" -> event.getPlayer().openInventory(potInv(ChatColor.YELLOW, "Perfekter Topf"));
        }

        CraftingManager.addToProcessMap(event.getPlayer(), event.getClickedBlock());

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

        inv.setItem(12 + 9 + 2, GuiTools.createItem("pot", "Kochen Starten", Material.RED_STAINED_GLASS_PANE, "startCooking", true));


        return inv;
    }
}
