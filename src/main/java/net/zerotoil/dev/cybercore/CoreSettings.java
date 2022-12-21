package net.zerotoil.dev.cybercore;

import net.zerotoil.dev.cybercore.utilities.GeneralUtils;
import org.apache.commons.lang.SystemUtils;
import org.bukkit.ChatColor;

public class CoreSettings {

    private final CyberCore main;

    private String bootColor = ChatColor.translateAlternateColorCodes('&', "&8");
    private String[] bootLogo = new String[]{
            "&8╭━━━╮&7╱╱╱&8╭╮&7╱╱╱╱╱╱&8╭━━━╮",
            "&8┃╭━╮┃&7╱╱╱&8┃┃&7╱╱╱╱╱╱&8┃╭━╮┃",
            "&8┃┃&7╱&8╰╋╮&7╱&8╭┫╰━┳━━┳━┫┃&7╱&8╰╋━━┳━┳━━╮",
            "&8┃┃&7╱&8╭┫┃&7╱&8┃┃╭╮┃┃━┫╭┫┃&7╱&8╭┫╭╮┃╭┫┃━┫",
            "&8┃╰━╯┃╰━╯┃╰╯┃┃━┫┃┃╰━╯┃╰╯┃┃┃┃━┫",
            "&8╰━━━┻━╮╭┻━━┻━━┻╯╰━━━┻━━┻╯╰━━╯",
            "&7╱╱╱╱&8╭━╯┃ &7Author: &fKihsomray",
            "&7╱╱╱╱&8╰━━╯ &7Version: &7"};
    private String[] legacyBootLogo = new String[]{
            "&8_________  _________  __________",
            "&8\\_   ___ \\ \\_   ___ \\ \\______   \\",
            "&8/    \\  \\/ /    \\  \\/  |       _/",
            "&8\\     \\____\\     \\____ |    |   \\",
            "&8 \\______  / \\______  / |____|_  /",
            "&8        \\/         \\/         \\/"};


    public CoreSettings(CyberCore main) {
        this.main = main;
    }

    public void setBootColor(char chatColorChar) {
        this.bootColor = ChatColor.translateAlternateColorCodes('&', "&" + chatColorChar);
    }

    public void setBootLogo(String... logo) {
        bootLogo = logo;
    }

    public void setLegacyBootLogo(String... logo) {
        legacyBootLogo = logo;
    }

    public String getStringLogo(boolean applyColor) {
        String string = "";
        if (isLegacyLogo()) for (String s : legacyBootLogo) string += s + "\n";
        else for (String s : bootLogo) string += s + "\n";

        if (string.length() > 2) string = string.substring(2);
        if (applyColor) return main.textSettings().colorize(null, null, string);
        return string;
    }

    public String[] getArrayLogo(boolean applyColor) {
        if (!applyColor) {
            if (isLegacyLogo()) return legacyBootLogo;
            return bootLogo;
        }
        String[] string;
        if (isLegacyLogo()) {
            string = new String[legacyBootLogo.length];
            for (int i = 0; i < legacyBootLogo.length; i++) string[i] = main.textSettings().colorize(null, null, legacyBootLogo[i]);
        } else {
            string = new String[bootLogo.length];
            for (int i = 0; i < bootLogo.length; i++) string[i] = main.textSettings().colorize(null, null, bootLogo[i]);
        }
        return string;
    }

    public String getBootBar() {
        String string = "&8-----------------------------------------------";
        if (!isLegacyLogo()) string = bootColor + "―――――――――――――――――――――――――――――――――――――――――――――――";
        return string;
    }

    public String getBootColor() {
        return bootColor;
    }

    public void sendBootHeader() {
        main.logger(getBootBar());
        main.logger(getArrayLogo(true));
        main.logger(getBootBar(), "");
    }

    private boolean isLegacyLogo() {
        return SystemUtils.OS_NAME.contains("Windows") && GeneralUtils.getJavaVersion() < 12;
    }

}
