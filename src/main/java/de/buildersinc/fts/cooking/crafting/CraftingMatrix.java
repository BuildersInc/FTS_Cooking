package de.buildersinc.fts.cooking.crafting;

import de.buildersinc.fts.cooking.exception.InvalidCraftingMatrix;
import de.buildersinc.fts.cooking.main.Cooking;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CraftingMatrix implements Comparable{

    private String[] craftingString;

    private ItemStack[] firstRowCrafting;
    private ItemStack[] secondRowCrafting;
    private ItemStack[] thirdRowCrafting;

    private List<List<ItemStack>> craftingMatrix;

    public CraftingMatrix() {
        this.firstRowCrafting = initCraftingArray(Material.AIR);
        this.secondRowCrafting = initCraftingArray(Material.AIR);
        this.thirdRowCrafting = initCraftingArray(Material.AIR);
    }

    public CraftingMatrix(String... recipe) {
        if (recipe.length > 3) {
            throw new InvalidCraftingMatrix("To many rows", recipe);
        }

        for (String s : recipe) {
            if (s.length() > 3) {
                throw new InvalidCraftingMatrix("Row " + s + " is too long " + s.length(), recipe);
            }
        }

        this.craftingString = recipe;
        this.firstRowCrafting = initCraftingArray(Material.AIR);
        this.secondRowCrafting = initCraftingArray(Material.AIR);
        this.thirdRowCrafting = initCraftingArray(Material.AIR);
    }

    public void setItem(char craftingStringChar, ItemStack itemStack) {
        for (int i = 0; i < craftingString.length; i++) {
            for (int c = 0; c < craftingString[i].length(); c++) {
                char charAt = craftingString[i].charAt(c);
                if (charAt == craftingStringChar) {
                    switch (i) {
                        case 0 -> firstRowCrafting[c] = itemStack;
                        case 1 -> secondRowCrafting[c] = itemStack;
                        case 2 -> thirdRowCrafting[c] = itemStack;
                    }
                }
            }
        }
    }

    public void setItem(int col, int row, ItemStack itemStack) {
        switch (row) {
            case 0 -> firstRowCrafting[col] = itemStack;
            case 1 -> secondRowCrafting[col] = itemStack;
            case 2 -> thirdRowCrafting[col] = itemStack;
        }
    }

    public void addRecipeAsMatrix(Boolean adToServer) {
        craftingMatrix = new ArrayList<>();

        List<ItemStack> firstRow = new ArrayList<>(Arrays.asList(firstRowCrafting));
        List<ItemStack> secondRow = new ArrayList<>(Arrays.asList(secondRowCrafting));
        List<ItemStack> thirdRow = new ArrayList<>(Arrays.asList(thirdRowCrafting));

        craftingMatrix.add(firstRow);
        craftingMatrix.add(secondRow);
        craftingMatrix.add(thirdRow);

        if (adToServer) {
            Cooking.addRecipe(this);
        }
    }

    private ItemStack[] initCraftingArray(Material material) {
        return new ItemStack[] {
                new ItemStack(material),
                new ItemStack(material),
                new ItemStack(material)
        };
    }

    public static CraftingMatrix inventoryToMatrix(Inventory inventory) {
        CraftingMatrix matrix = new CraftingMatrix();

        for (int col = 0, itemIndex = 10, row = 0; col < 3; col++, row++) {
            matrix.setItem(col, row, inventory.getItem(itemIndex + col + (9 * row)) == null ? new ItemStack(Material.AIR) : inventory.getItem(itemIndex + col + (9 * row)));

        }
        matrix.addRecipeAsMatrix(false);
        return matrix;
    }

    public List<List<ItemStack>> getCraftingMatrix() {
        return craftingMatrix;
    }




    @Override
    public int compareTo(@NotNull Object o) {
        if (!(o instanceof CraftingMatrix)) return -1;
        CraftingMatrix compare = (CraftingMatrix) o;
        int result = 0;

        for (int i = 0; i < craftingMatrix.size(); i++) {
            if (!craftingMatrix.get(i).containsAll(compare.getCraftingMatrix().get(i))) {
                result = -1;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (List<ItemStack> row : craftingMatrix) {
            builder.append(row).append("\n");
        }
        return builder.toString();
    }
}
