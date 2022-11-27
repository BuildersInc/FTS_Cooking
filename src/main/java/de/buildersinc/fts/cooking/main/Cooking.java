package de.buildersinc.fts.cooking.main;


import de.buildersinc.fts.cooking.cmd.DebugCmd;
import de.buildersinc.fts.cooking.crafting.CraftingManager;
import de.buildersinc.fts.cooking.crafting.CraftingMatrix;
import de.buildersinc.fts.cooking.events.BlockBreakListener;
import de.buildersinc.fts.cooking.events.ClickOnPotListener;
import de.buildersinc.fts.cooking.events.PotGuiListener;
import de.buildersinc.fts.cooking.events.PotPlaceListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Cooking extends JavaPlugin {

    private static Cooking plugin;
    private static List<CraftingMatrix> customCrafting;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        customCrafting = new ArrayList<>();

        new DebugCmd(plugin);
        new BlockBreakListener(this);
        new ClickOnPotListener(this);
        new PotPlaceListener(this);
        new PotGuiListener(this);
        new CraftingManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Cooking getPlugin() {
        return plugin;
    }

    public static void addRecipe(CraftingMatrix matrix) {
        customCrafting.add(matrix);
        plugin.getLogger().info(matrix + "was added");
    }

    public static List<CraftingMatrix> getCustomCrafting() {
        return customCrafting;
    }
}
