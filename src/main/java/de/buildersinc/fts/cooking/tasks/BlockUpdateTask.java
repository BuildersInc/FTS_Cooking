package de.buildersinc.fts.cooking.tasks;

import com.jeff_media.customblockdata.CustomBlockData;
import de.buildersinc.fts.cooking.main.Cooking;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BlockUpdateTask {
    private Block block;


    public BlockUpdateTask(Block block) {
        this.block = block;
    }

    public void startTask() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Cooking.getPlugin(), this::task, 1, 20);
    }

    private void task() {
        NamespacedKey nsk = new NamespacedKey(Cooking.getPlugin(), "ftsCookingCookingTime");
        PersistentDataContainer container = new CustomBlockData(this.block, Cooking.getPlugin());

        int time = container.get(nsk, PersistentDataType.INTEGER);
        container.set(nsk, PersistentDataType.INTEGER, time + 1);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("Hallo" + time);
        }
    }
}
