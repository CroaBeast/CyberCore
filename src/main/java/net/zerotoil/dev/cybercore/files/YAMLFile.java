package net.zerotoil.dev.cybercore.files;

import net.zerotoil.dev.cybercore.CyberCore;
import net.zerotoil.dev.cybercore.files.configupdater.ConfigUpdater;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;
import java.util.Collections;

public class YAMLFile {

    private final CyberCore main;
    private final JavaPlugin plugin;
    private java.io.File configFile;
    private FileConfiguration dataConfig;
    private final String location;
    private final String name;

    public YAMLFile(CyberCore main, JavaPlugin plugin, String location) {
        this.main = main;
        this.plugin = plugin;
        this.location = location;
        this.name = location.replace(".yml", "");
        saveDefaultConfig();
        dataConfig = YamlConfiguration.loadConfiguration(getFile());
    }

    private java.io.File getFile() {
        return new java.io.File(plugin.getDataFolder(), location);
    }

    public FileConfiguration getConfig() {
        return dataConfig;
    }

    public void saveConfig() throws IOException {
        if (!((this.dataConfig == null) || (this.configFile == null))) {
            this.getConfig().save(this.configFile);
        }
    }

    public void updateConfig() {
        try {
            ConfigUpdater.update(plugin, location, getFile(), Collections.emptyList());
            if (main.isLegacy()) ConfigUpdater.update(plugin, location, getFile(), Collections.emptyList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadConfig();
    }

    public void reloadConfig() {
        dataConfig = YamlConfiguration.loadConfiguration(getFile());
    }

    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = getFile();
        }

        if (configFile.exists()) {
            return;
        }
        plugin.saveResource(location, false);
    }

}
