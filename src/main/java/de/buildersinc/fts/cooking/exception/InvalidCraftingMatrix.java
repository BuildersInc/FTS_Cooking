package de.buildersinc.fts.cooking.exception;

public class InvalidCraftingMatrix extends RuntimeException {
    public InvalidCraftingMatrix(String cause, String... recipe) {
        super(buildMessage(recipe) + " " + cause);
    }

    private static String buildMessage(String... recipe) {
        StringBuilder builder = new StringBuilder();
        for (String s : recipe) {
            builder.append(s);
            builder.append(" | ");
        }
        builder.append("\n");
        return builder.toString();
    }
}
