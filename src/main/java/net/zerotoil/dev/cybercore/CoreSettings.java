package net.zerotoil.dev.cybercore;

import lombok.Getter;
import me.croabeast.beanslib.utility.LibUtils;
import org.apache.commons.lang3.SystemUtils;
import org.bukkit.ChatColor;

public class CoreSettings {

    private final CyberCore main;

    @Getter
    private String bootColor = ChatColor.translateAlternateColorCodes('&', "&8");

    private String[] bootLogo = new String[]{
            "&8╭━━━╮&7╱╱╱&8╭╮&7╱╱╱╱╱╱&8╭━━━╮",
            "&8┃╭━╮┃&7╱╱╱&8┃┃&7╱╱╱╱╱╱&8┃╭━╮┃",
            "&8┃┃&7╱&8╰╋╮&7╱&8╭┫╰━┳━━┳━┫┃&7╱&8╰╋━━┳━┳━━╮",
            "&8┃┃&7╱&8╭┫┃&7╱&8┃┃╭╮┃┃━┫╭┫┃&7╱&8╭┫╭╮┃╭┫┃━┫",
            "&8┃╰━╯┃╰━╯┃╰╯┃┃━┫┃┃╰━╯┃╰╯┃┃┃┃━┫",
            "&8╰━━━┻━╮╭┻━━┻━━┻╯╰━━━┻━━┻╯╰━━╯",
            "&7╱╱╱╱&8╭━╯┃ &7Author: &fKihsomray",
            "&7╱╱╱╱&8╰━━╯ &7Version: &7"
    };

    private String[] legacyBootLogo = new String[]{
            "&8_________  _________  __________",
            "&8\\_   ___ \\ \\_   ___ \\ \\______   \\",
            "&8/    \\  \\/ /    \\  \\/  |       _/",
            "&8\\     \\____\\     \\____ |    |   \\",
            "&8 \\______  / \\______  / |____|_  /",
            "&8        \\/         \\/         \\/"
    };


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

    private boolean isLegacyLogo() {
        return SystemUtils.OS_NAME.contains("Windows") && LibUtils.majorJavaVersion() < 12;
    }

    private String[] getLogo() {
        return isLegacyLogo() ? legacyBootLogo : bootLogo;
    }

    public String getStringLogo(boolean applyColor) {
        StringBuilder builder = new StringBuilder();

        for (String s : getLogo()) builder.append(s).append("\n");

        if (builder.length() > 2)
            builder = new StringBuilder(builder.substring(2));

        if (applyColor) return main.textSettings().colorize(null, null, builder.toString());
        return builder.toString();
    }

    public String[] getArrayLogo(boolean applyColor) {
        if (!applyColor) return getLogo();

        String[] string = new String[getLogo().length];

        for (int i = 0; i < getLogo().length; i++)
            string[i] = main.textSettings().colorize(null, null, getLogo()[i]);

        return string;
    }

    public String getBootBar() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 48; i++) {
            if (i == 0 && !isLegacyLogo()) builder.append(getBootColor());
            builder.append(isLegacyLogo() ? "-" : "―");
        }

        return builder + "";
    }

    public void sendBootHeader() {
        main.logger(getBootBar());
        main.logger(getArrayLogo(true));
        main.logger(getBootBar(), "&7");
    }

}
