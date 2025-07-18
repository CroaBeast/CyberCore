package com.bitaspire.cybercore;

import lombok.AccessLevel;
import lombok.Getter;
import me.croabeast.takion.TakionLib;
import com.bitaspire.cybercore.file.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

@Getter
public final class CyberCore {

    private final JavaPlugin plugin;
    private final TakionLib library;

    private final FileManager fileManager;
    private final CoreSettings settings;

    @Getter(AccessLevel.NONE)
    private final long bootStart;

    public CyberCore(JavaPlugin plugin) {
        this.plugin = plugin;

        fileManager = new FileManagerImpl(this);
        library = new TextLibrary(this);
        settings = new CoreSettings(this);

        bootStart = System.currentTimeMillis();
    }

    private void loadTPS() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, Lag.initialize(), 100L, 1L);
    }

    public void loadFiles(boolean header, String... additionalFiles) {
        if (header) settings.sendBootHeader();
        fileManager.loadAll(additionalFiles);
    }

    public void loadStart(boolean loadFiles, String additionalFiles) {
        settings.sendBootHeader();
        if (loadFiles) loadFiles(false, additionalFiles);
        loadTPS();
    }

    public void loadStart(String additionalFiles) {
        loadStart(true, additionalFiles);
    }

    public void loadFinish() {
        final PluginDescriptionFile desc = plugin.getDescription();

        library.getServerLogger().log(
                "&7Loaded " + settings.getBootColor() + desc.getPrefix() +
                        " v" + desc.getVersion() + "&7 in &a" +
                        (System.currentTimeMillis() - bootStart) + "ms&7."
        );
        library.getServerLogger().log(settings.getBootBar());
    }

    @ApiStatus.ScheduledForRemoval(inVersion = "2.0")
    @Deprecated
    public void logger(String... lines) {
        library.getServerLogger().log(lines);
    }
}
