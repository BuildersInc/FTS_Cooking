package de.buildersinc.fts.cooking.main;


import de.buildersinc.fts.cooking.cmd.DebugCmd;
import de.buildersinc.fts.cooking.crafting.CraftingManager;
import de.buildersinc.fts.cooking.events.*;
import org.bukkit.plugin.java.JavaPlugin;


public final class Cooking extends JavaPlugin {

    private static Cooking plugin;
    private static CraftingManager craftingManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        craftingManager = new CraftingManager(this);
        craftingManager.loadRecipes();

        new DebugCmd(plugin);
        new BlockBreakListener(this);
        new ClickOnPotListener(this);
        new PotPlaceListener(this);
        new PotGuiListener(this);
        new GuiInventoryCloseEvent(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Cooking getPlugin() {
        return plugin;
    }

    public static CraftingManager getCraftingManager() {
        return craftingManager;
    }
}
