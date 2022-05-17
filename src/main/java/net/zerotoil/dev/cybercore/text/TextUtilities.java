package net.zerotoil.dev.cybercore.text;

import me.croabeast.beanslib.BeansLib;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextUtilities extends BeansLib {

    /*
     * You need to initialize your BeansLib class with your plugin's instance.
     */
    private final JavaPlugin plugin;

    public TextUtilities(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    @NotNull
    protected JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    @NotNull
    public String langPrefixKey() {
        return plugin.getConfig().getString("lang.prefix-key", "<key>");
    }

    @Override
    @NotNull
    public String langPrefix() {
        return plugin.getConfig().getString("lang.main-prefix", " My Plugin owo");
    }

    @Override
    public boolean isHardSpacing() {
        return plugin.getConfig().getBoolean("options.hard-spacing");
    }

    @Override
    public boolean isStripPrefix() {
        return plugin.getConfig().getBoolean("options.strip-prefix");
    }

    public static int getJavaVersion() {
        String version = System.getProperty("java.version");
        if (version.startsWith("1."))
            version = version.substring(2, 3);
        else {
            int dot = version.indexOf(".");
            if (dot != -1) version = version.substring(0, dot);
        }
        return Integer.parseInt(version);
    }

    // converts message to list
    public List<String> convertList(Configuration config, String path) {

        if (!config.isSet(path)) return new ArrayList<>(Arrays.asList(""));

        // if already list
        if (config.isList(path)) return config.getStringList(path);

        // if single string
        List<String> list = new ArrayList<>();
        if (!config.getString(path).contains("[Ljava.lang.String")) list.add(config.getString(path));
        return list;

    }

}
