package de.buildersinc.fts.cooking.events;

import com.jeff_media.customblockdata.CustomBlockData;
import de.buildersinc.fts.cooking.main.Cooking;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
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
        NamespacedKey nsk2 = new NamespacedKey(Cooking.getPlugin(), "ftsCookingCookingTime");
        Block block = event.getBlock();

        if (!itemStack.getItemMeta().getPersistentDataContainer().has(nsk)) return;
        switch (itemStack.getItemMeta().getPersistentDataContainer().get(nsk, PersistentDataType.STRING)) {
            case "simple_pot" -> updateBlockData(block, "simple_pot", nsk);
            case "good_pot" -> updateBlockData(block, "good_pot", nsk);
            case "super_pot" -> updateBlockData(block, "super_pot", nsk);
        }

        PersistentDataContainer container = new CustomBlockData(block, plugin);
        container.set(nsk2, PersistentDataType.INTEGER, 0);



    }

    private void updateBlockData(Block block, String potType, NamespacedKey nsk) {
        PersistentDataContainer container = new CustomBlockData(block, plugin);
        container.set(nsk, PersistentDataType.STRING, potType);

    }
}
