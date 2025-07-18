package com.bitaspire.cybercore;

import me.croabeast.file.ConfigurableFile;
import me.croabeast.takion.TakionLib;
import me.croabeast.takion.logger.LogLevel;
import com.bitaspire.cybercore.file.FileManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

final class FileManagerImpl implements FileManager {

    private final Map<String, ConfigurableFile> files = new HashMap<>();

    private final CyberCore core;
    private final TakionLib lib;

    FileManagerImpl(CyberCore core) {
        this.core = core;
        this.lib = core.getLibrary();
    }

    @Override
    public void load(String name, boolean send) {
        ConfigurableFile file;
        try {
            (file = new ConfigurableFile(core.getPlugin(), name) {
                @Override
                public boolean isUpdatable() {
                    return FileManagerImpl.this.get("config").get("config.auto-update." + name, false);
                }
            }).saveDefaults();
            files.put(name, file);
            if (send)
                lib.getServerLogger().log("&7Loaded file &e" + name + ".yml&7 file.");
        } catch (Exception e) {
            if (send)
                lib.getServerLogger().log(LogLevel.ERROR,
                        "&cError while generating " + name + ".yml file",
                        e.getLocalizedMessage()
                );
        }
    }

    @Override
    public void loadAll(boolean send, boolean useDefaults, String... additions) {
        if (!files.isEmpty()) files.clear();

        if (send)
            lib.getServerLogger().log(core.getSettings().getBootColor() + "Loading YAML files...");

        long startTime = System.currentTimeMillis();

        if (useDefaults) {
            load("config", send);
            load("lang", send);
        }

        for (String string : additions) load(string, send);

        if (send)
            lib.getServerLogger().log(
                    "&7Loaded &e" + files.size() + "&7 files in &a" +
                            (System.currentTimeMillis() - startTime) + "ms&7.", "&7"
            );
    }

    @NotNull
    public ConfigurableFile get(String name) throws NullPointerException {
        if (!files.containsKey(name))
            throw new NullPointerException("Name " + name + " wasn't found");

        return files.get(name);
    }
}
