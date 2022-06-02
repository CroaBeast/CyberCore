package net.zerotoil.dev.cybercore;

import me.croabeast.beanslib.BeansLib;
import me.croabeast.beanslib.utilities.TextKeys;
import net.zerotoil.dev.cybercore.files.Files;
import net.zerotoil.dev.cybercore.objects.Lag;
import net.zerotoil.dev.cybercore.utilities.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class CyberCore {

    private final JavaPlugin plugin;
    private final TextUtils textUtilities;
    private final CoreSettings settings;

    private final long bootStart;

    private Files files;

    public CyberCore(JavaPlugin plugin) {
        this.plugin = plugin;
        textUtilities = new TextUtils(plugin);
        settings = new CoreSettings(this);
        bootStart = System.currentTimeMillis();
    }

    public void loadStart(String... additionalFiles) {
        loadStart(true, additionalFiles);
    }

    public void loadStart(boolean loadFiles, String... additionalFiles) {
        settings.sendBootHeader();
        if (loadFiles) loadFiles(false, additionalFiles);
        loadTPS();
    }

    public void loadFiles(boolean bootHeader, String... additionalFiles) {
        if (bootHeader) settings.sendBootHeader();
        files = new Files(this);
        files.load(additionalFiles);
    }

    private void loadTPS() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Lag(), 100L, 1L);
    }

    public void loadFinish() {
        logger("&7Loaded " + themeColor() + plugin.getDescription().getPrefix() + " v" +
                plugin.getDescription().getVersion() + "&7 in &a" +
                (System.currentTimeMillis() - bootStart) + "ms&7.");
        logger(settings.getBootBar());
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
    public TextUtils getTextUtilities() {
        return textUtilities;
    }
    public CoreSettings getSettings() {
        return settings;
    }
    public Files files() {
        return files;
    }

    public static int getMajorVersion() {
        return TextKeys.majorVersion();
    }

    public static String getServerVersion() {
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
        sendMessage(sender, messageKey, null);
    }

    public void sendMessage(CommandSender sender, String messageKey, String[] placeholders, String... replacements) {

        // Adds brackets around placeholders
        if (placeholders != null)
            for (int i = 0; i < placeholders.length; i++)
                placeholders[i] = "{" + placeholders[i] + "}";

        // Sends message
        textUtilities.sendMessageList(sender, textUtilities.convertList(files.getConfig("lang"), "messages." + messageKey), placeholders, replacements);
    }

    // With color & placeholders
    public String getLangValue(Player player, String file, String path, String[] placeholders, String[] replacements) {

        return BeansLib.replaceInsensitiveEach(getLangValue(player, file, path), placeholders, replacements);

    }

    // With color
    public String getLangValue(Player player, String file, String path) {

        return textUtilities.colorize(player, getLangValue(file, path));

    }

    // Without color
    public String getLangValue(String file, String path) {

        return files.getConfig(file).getString(path);

    }

    public static boolean restrictVersions(int minVersion, int maxVersion, String pluginPrefix, String version) {
        if (getMajorVersion() < minVersion)
            Bukkit.getLogger().severe(pluginPrefix + " v" + version + " does not support 1." + getMajorVersion() + ".x and older!");
        else if (getMajorVersion() > maxVersion)
            Bukkit.getLogger().severe(pluginPrefix + " v" + version + " does not support 1." + getMajorVersion() + ".x and newer. Please update!");
        else return true;
        return false;

    }

}
