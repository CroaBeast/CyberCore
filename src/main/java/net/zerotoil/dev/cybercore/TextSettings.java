package net.zerotoil.dev.cybercore;

import me.croabeast.beanslib.BeansLib;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class TextSettings extends BeansLib {

    private final CyberCore core;

    public TextSettings(CyberCore core) {
        super(core.getPlugin());
        this.core = core;

        setLangPrefixKey("{p}").setLangPrefix("&8&lCCR &8» &r");
        setColoredConsole(true);
    }

    @Override
    public ConfigurationSection getWebhookSection() {
        return core.files().getConfig("lang").getConfigurationSection("webhooks");
    }

    @Deprecated
    public TextSettings setPrefix(String prefix) {
        return (TextSettings) setLangPrefix(prefix);
    }

    @Deprecated
    public TextSettings setPrefixKey(String key) {
        return (TextSettings) setLangPrefixKey(key);
    }

    @Deprecated
    public String langPrefix() {
        return getLangPrefix();
    }

    @Deprecated
    public String langPrefixKey() {
        return getLangPrefixKey();
    }
}
