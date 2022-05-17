package net.zerotoil.dev.cybercore;

import me.croabeast.beanslib.utilities.TextKeys;
import net.zerotoil.dev.cybercore.files.Files;
import net.zerotoil.dev.cybercore.text.TextUtilities;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class CyberCore {

    private final JavaPlugin plugin;
    private final TextUtilities textUtilities;
    private final CoreSettings settings;

    private final long bootStart;

    private Files files;

    public CyberCore(JavaPlugin plugin) {
        this.plugin = plugin;
        textUtilities = new TextUtilities(plugin);
        settings = new CoreSettings(this);
        bootStart = System.currentTimeMillis();
    }

    public void loadStart(String... additionalFiles) {
        settings.sendBootHeader();
        loadFiles(additionalFiles);
    }

    public void loadFiles(String... additionalFiles) {
        files = new Files(this);
        files.load(additionalFiles);
    }

    public void loadFinish() {
        logger("&7Loaded " + themeColor() + plugin.getDescription().getPrefix() + " v" +
                plugin.getDescription().getVersion() + "&7 in &a" +
                (System.currentTimeMillis() - bootStart) + "ms&7.");
        settings.getBootBar();
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
    public TextUtilities getTextUtilities() {
        return textUtilities;
    }
    public CoreSettings getSettings() {
        return settings;
    }
    public Files files() {
        return files;
    }

    public int getMajorVersion() {
        return TextKeys.majorVersion();
    }

    public String getServerVersion() {
        return Bukkit.getBukkitVersion().split("-")[0];
    }

    public boolean isLegacy() {
        return getMajorVersion() > 12;
    }

    public String getFork() {
        return TextKeys.serverFork();
    }

    public void logger(String... message) {
        textUtilities.doLog(message);
    }

    public String themeColor() {
        return settings.getBootColor();
    }

    public String getAuthors() {
        return plugin.getDescription().getAuthors().toString().replace("[", "").replace("]", "");
    }

    public void sendMessage(CommandSender sender, String messageKey) {
        sendMessage(sender, messageKey, null, null);
    }

    public void sendMessage(CommandSender sender, String messageKey, String[] placeholders, String[] replacements) {

        // Adds brackets around placeholders
        if (placeholders != null)
            for (int i = 0; i < placeholders.length; i++)
                placeholders[i] = "{" + placeholders[i] + "}";

        // Sends message
        textUtilities.sendMessageList(sender, textUtilities.convertList(files.getConfig("lang"), "messages." + messageKey), placeholders, replacements);
    }

}
