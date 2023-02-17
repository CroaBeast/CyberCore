package net.zerotoil.dev.cybercore;

import me.croabeast.beanslib.BeansLib;
import org.bukkit.configuration.ConfigurationSection;

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
}
