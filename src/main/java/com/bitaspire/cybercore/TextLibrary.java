package com.bitaspire.cybercore;

import me.croabeast.takion.TakionLib;
import me.croabeast.takion.logger.TakionLogger;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.TreeMap;

final class TextLibrary extends TakionLib {

    private final CyberCore core;

    TextLibrary(CyberCore core) {
        super(core.getPlugin());
        this.core = core;

        setLangPrefix("&8&lCCR &8» &r");
        setLangPrefixKey("{p}");

        setLogger(new TakionLogger(this) {
            @Override
            public boolean isColored() {
                return core.getFileManager().get("config").get("config.console-color", true);
            }
        });
        setServerLogger(new TakionLogger(this, false) {
            @Override
            public boolean isColored() {
                return core.getFileManager().get("config").get("config.console-color", true);
            }
        });
    }

    @NotNull
    public TreeMap<String, ConfigurationSection> getLoadedWebhooks() {
        return loadMapFromConfiguration(core.getFileManager().get("lang").getSection("webhooks"));
    }
}
