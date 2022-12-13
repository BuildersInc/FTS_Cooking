package de.buildersinc.fts.cooking.utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;

public class BlockUtils {
    public static void setCauldronLevel(Block block, int level) {
        switch (level) {
            case 0 -> block.setType(Material.CAULDRON);
            case 1 -> {
                block.setType(Material.WATER_CAULDRON);
                Levelled minCauldronLevel = (Levelled) block.getBlockData();
                minCauldronLevel.setLevel(minCauldronLevel.getMinimumLevel());
                block.setBlockData(minCauldronLevel);
            }
            case 2 -> {
                block.setType(Material.WATER_CAULDRON);
                Levelled maxCauldronLevel = (Levelled) block.getBlockData();
                maxCauldronLevel.setLevel(3);
                block.setBlockData(maxCauldronLevel);
            }
        }
    }
}
