package net.zerotoil.dev.cybercore;

import me.croabeast.beanslib.BeansLib;
import net.zerotoil.dev.cybercore.files.YAMLFile;
import org.bukkit.configuration.ConfigurationSection;

public class TextSettings extends BeansLib {

    private final CyberCore core;

    public TextSettings(CyberCore core) {
        super(core.getPlugin());
        this.core = core;

        setLangPrefixKey("{p}").setLangPrefix("&8&lCCR &8» &r");

        setColoredConsole(new YAMLFile(core, core.getPlugin(), "config.yml").getConfig().getBoolean("config.console-color", true));
        setWebhookSection(new YAMLFile(core, core.getPlugin(), "lang.yml").getConfig().getConfigurationSection("webhooks"));
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
