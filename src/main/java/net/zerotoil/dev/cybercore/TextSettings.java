package net.zerotoil.dev.cybercore;

import me.croabeast.beanslib.BeansLib;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class TextSettings extends BeansLib {

    private final CyberCore core;
    /*
     * You need to initialize your BeansLib class with your plugin's instance.
     */
    private final JavaPlugin plugin;

    private String langPrefixKey = "{p}";
    private String langPrefix = "&8&lCCR &8» &r";
    private boolean hardSpacing = false;
    private boolean stripPrefix = false;

    public TextSettings(CyberCore core) {
        this.plugin = core.getPlugin();
        this.core = core;
    }

    @Override
    @NotNull
    public JavaPlugin getPlugin() {
        return plugin;
    }

    @Override
    @NotNull
    public String langPrefixKey() {
        return langPrefixKey;
    }

    /**
     * Sets prefix key.
     *
     * @param prefixKey Key to replace with
     */
    public void setPrefixKey(String prefixKey) {
        langPrefixKey = prefixKey;
    }

    @Override
    @NotNull
    public String langPrefix() {
        return langPrefix;
    }

    @Override
    public boolean fixColorLogger() {
        return true;
    }

    @Override
    public ConfigurationSection getWebhookSection() {
        return core.files().getConfig("lang").getConfigurationSection("webhooks");
    }

    /**
     * Sets prefix.
     *
     * @param prefix Prefix to replace with
     */
    public void setPrefix(String prefix) {
        langPrefix = prefix;
    }

    /**
     * Set hard spacing boolean
     *
     * @param hardSpacing True for removing extra spacing
     */
    public void setHardSpacing(boolean hardSpacing) {
        this.hardSpacing = hardSpacing;
    }

    @Override
    public boolean isStripPrefix() {
        return stripPrefix;
    }

    /**
     * Set prefix stripping during parsing
     *
     * @param stripPrefix True for prefix stripping
     */
    public void setStripPrefix(boolean stripPrefix) {
        this.stripPrefix = stripPrefix;
    }


}
