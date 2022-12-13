package de.buildersinc.fts.cooking.events;

import de.buildersinc.fts.cooking.enums.Items;
import de.buildersinc.fts.cooking.main.Cooking;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {


    public BlockBreakListener(Cooking plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        if (event.getBlock().getType() != Material.DIORITE) return;
        if (!chance(5)) return;

        Block block = event.getBlock();
        block.getDrops(Items.SALT.getItemStack());

        event.getPlayer().getWorld().dropItemNaturally(block.getLocation(), Items.SALT.getItemStack());


    }

    private boolean chance(int base) {
        return ((int) (Math.random() * 100)) <= base;
    }
}
