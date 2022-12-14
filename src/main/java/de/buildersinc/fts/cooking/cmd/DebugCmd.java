package de.buildersinc.fts.cooking.cmd;

import de.buildersinc.fts.cooking.enums.Items;
import de.buildersinc.fts.cooking.main.Cooking;
import de.buildersinc.fts.cooking.utils.GuiTools;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DebugCmd implements CommandExecutor {

    public DebugCmd(Cooking plugin) {
        Objects.requireNonNull(plugin.getCommand("debug")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        if (!player.isOp()) return false;
        Inventory debugInv = GuiTools.createChestGui(5, "Debug", "debug", Material.GLASS_PANE, false, false);
        int i = 0;
        for (Items item : Items.values()) {
            debugInv.setItem(i++, item.getItemStack());
        }
        player.openInventory(debugInv);

        return false;
    }
}
