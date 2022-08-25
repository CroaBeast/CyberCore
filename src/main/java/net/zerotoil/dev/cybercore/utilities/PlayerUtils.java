package net.zerotoil.dev.cybercore.utilities;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerUtils {

    private final static String[] plPlaceholders = new String[]{
            "player",
            "playerDisplayName",
            "playerUUID"
    };

    /**
     * Get the player's placeholders.
     *
     * @return Array of placeholders
     */
    public static String[] getPlPlaceholders() {
        return plPlaceholders;
    }

    /**
     * Get the player's placeholder replacements.
     *
     * @return Array of placeholder replacements
     */
    public static String[] getPlReplacements(Player player) {
        return new String[] {
                player.getName(),
                player.getDisplayName(),
                player.getUniqueId().toString()
        };
    }

    /**
     * replace placeholders within a message
     * with corresponding replacements.
     *
     * Use BeansLIB's replace placeholders instead.
     *
     * @param string String to replace placeholders
     * @param placeholders Array of placeholders
     * @param replacements Array of replacements
     * @return Replacements/placeholders applied to string
     */
    @Deprecated
    public static String replacePlaceholders(String string, String[] placeholders, String... replacements) {
        if (replacements.length < placeholders.length) throw new IllegalArgumentException();

        for (int i = 0; i < placeholders.length; i++) {
            string = string.replace("{" + placeholders[i] + "}", replacements[i]);
        }

        return string;
    }

    /**
     * Apply curly brackets (braces) around all
     * placeholders within the string array.
     *
     * @param placeholders Array of placeholder values
     * @return same array with placeholder applied
     */
    public static String[] applyPlaceholderBraces(@Nullable String[] placeholders) {
        if (placeholders == null) return null;
        for (int i = 0; i < placeholders.length; i++) placeholders[i] = "{" + placeholders[i] + "}";
        return placeholders;
    }

}
