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

    private String langPrefixKey = "<p>";
    private String langPrefix = "&8&lCCR &8» &r";
    private boolean hardSpacing = false;
    private boolean stripPrefix = false;

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
        return langPrefixKey;
    }

    public void setPrefixKey(String prefixKey) {
        langPrefixKey = prefixKey;
    }

    @Override
    @NotNull
    public String langPrefix() {
        return langPrefix;
    }

    public void setPrefix(String prefix) {
        langPrefix = prefix;
    }

    @Override
    public boolean isHardSpacing() {
        return hardSpacing;
    }

    public void setHardSpacing(boolean hardSpacing) {
        this.hardSpacing = hardSpacing;
    }

    @Override
    public boolean isStripPrefix() {
        return stripPrefix;
    }

    public void setStripPrefix(boolean stripPrefix) {
        this.stripPrefix = stripPrefix;
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
