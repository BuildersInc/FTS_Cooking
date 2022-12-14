package de.buildersinc.fts.cooking.tasks;

import com.jeff_media.customblockdata.CustomBlockData;
import de.buildersinc.fts.cooking.crafting.CraftingManager;
import de.buildersinc.fts.cooking.enums.Items;
import de.buildersinc.fts.cooking.main.Cooking;
import de.buildersinc.fts.cooking.utils.BlockUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.Lightable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


public class BlockUpdateTask {
    private final Block block;
    private boolean isRunning;
    private int pid;
    private int speedMultiplier;
    private final NamespacedKey potType;
    private final NamespacedKey resultItem;
    private final NamespacedKey resultTrue;
    private final NamespacedKey cookingTime;
    private final NamespacedKey craftingAmount;
    private final NamespacedKey finishedAmount;




    public BlockUpdateTask(Block block) {
        this.resultItem = new NamespacedKey(Cooking.getPlugin(), "ftsCookingCookingResult");
        this.resultTrue = new NamespacedKey(Cooking.getPlugin(), "ftsCookingCookingFinish");
        this.cookingTime = new NamespacedKey(Cooking.getPlugin(), "ftsCookingCookingTime");
        this.craftingAmount = new NamespacedKey(Cooking.getPlugin(), "ftsCookingCookingAmount");
        this.finishedAmount = new NamespacedKey(Cooking.getPlugin(), "ftsCookingCookingFinishAmount");
        this.potType = new NamespacedKey(Cooking.getPlugin(), "ftsCooking");


        this.isRunning = false;
        this.block = block;


        this.speedMultiplier = switch (new CustomBlockData(this.block, Cooking.getPlugin()).get(potType, PersistentDataType.STRING)) {
            case "simple_pot" -> 1;
            case "good_pot" -> 2;
            case "super_pot" -> 3;
            default -> 1;
        };

    }

    public void startTask() {

        pid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Cooking.getPlugin(), this::task, 1, 20);
        BlockUtils.setCauldronLevel(block, 2);

        this.isRunning = true;
    }

    public void stopTask() {
        PersistentDataContainer container = new CustomBlockData(this.block, Cooking.getPlugin());

        Bukkit.getScheduler().cancelTask(pid);
        container.set(cookingTime, PersistentDataType.INTEGER, 0);
        BlockUtils.setCauldronLevel(block, 1);

        CraftingManager.getProcessMap().remove(block);

    }

    public Block getBlock() {
        return block;
    }

    public boolean isRunning() {
        return isRunning;
    }


    private void task() {

        PersistentDataContainer container = new CustomBlockData(this.block, Cooking.getPlugin());
        Block blockDown = block.getLocation().subtract(0, 1, 0).getBlock();
        if (blockDown.getType() == Material.CAMPFIRE && !((Lightable) blockDown.getBlockData()).isLit()) {
            return;
        }
        String resultString = container.get(resultItem, PersistentDataType.STRING);
        if (resultString != null) {
            int time = container.get(cookingTime, PersistentDataType.INTEGER);
            container.set(cookingTime, PersistentDataType.INTEGER, time + speedMultiplier);
            Items item = Items.valueOf(resultString.toUpperCase());
            if (time >= item.getCookingTime()) {
                container.set(resultTrue, PersistentDataType.INTEGER, 1);

                int amount = container.get(craftingAmount, PersistentDataType.INTEGER);

                if (amount > 0) {
                    container.set(craftingAmount, PersistentDataType.INTEGER, amount - 1);
                    container.set(cookingTime, PersistentDataType.INTEGER, 0);

                    container.set(finishedAmount, PersistentDataType.INTEGER, container.get(finishedAmount, PersistentDataType.INTEGER) == null ? 1 : container.get(finishedAmount, PersistentDataType.INTEGER) + 1);
                } else {
                    stopTask();
                }
            }
        }
    }


}
