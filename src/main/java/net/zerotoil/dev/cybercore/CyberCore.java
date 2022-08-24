package net.zerotoil.dev.cybercore;

import me.croabeast.beanslib.utilities.TextKeys;
import net.zerotoil.dev.cybercore.files.FileManager;
import net.zerotoil.dev.cybercore.objects.Lag;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class CyberCore {

    private final JavaPlugin plugin;
    private final TextSettings textUtilities;
    private final CoreSettings settings;

    private final long bootStart;

    private FileManager files;

    /**
     * Initialize your plugin with the core API.
     * This should be your first step.
     *
     * After this you can adjust the settings by
     * using coreSettings(). Be sure to set the boot
     * color, author, version, boot logos.
     *
     * Be sure to call the method loadStart() whilst
     * mentioning any additional files you want to
     * load. Default files: config.yml & lang.yml.
     *
     * @param plugin Main instance
     */
    public CyberCore(JavaPlugin plugin) {
        this.plugin = plugin;
        textUtilities = new TextSettings(plugin);
        settings = new CoreSettings(this);
        bootStart = System.currentTimeMillis();
    }

    /**
     * Fully loads the core, displays boot message,
     * and loads any additional files.
     *
     * Default files: config.yml & lang.yml.
     *
     * @param additionalFiles Additional files to load
     */
    public void loadStart(String... additionalFiles) {
        loadStart(true, additionalFiles);
    }

    /**
     * Fully loads the core, displays boot message,
     * and loads any additional files.
     *
     * Default files: config.yml & lang.yml.
     *
     * @param loadFiles Should files be loaded
     * @param additionalFiles Additional files to load
     */
    public void loadStart(boolean loadFiles, String... additionalFiles) {
        settings.sendBootHeader();
        if (loadFiles) loadFiles(false, additionalFiles);
        loadTPS();
    }

    /**
     * Loads files into cache for easy access.
     *
     * Default files: config.yml & lang.yml.
     *
     * @param bootHeader Should boot header be displayed
     * @param additionalFiles Additional files to load
     */
    public void loadFiles(boolean bootHeader, String... additionalFiles) {
        if (bootHeader) settings.sendBootHeader();
        files = new FileManager(this);
        files.load(additionalFiles);
    }

    private void loadTPS() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Lag(), 100L, 1L);
    }

    /**
     * Displays the finishing message to the load of
     * you plugin.
     */
    public void loadFinish() {
        logger("&7Loaded " + themeColor() + plugin.getDescription().getPrefix() + " v" +
                plugin.getDescription().getVersion() + "&7 in &a" +
                (System.currentTimeMillis() - bootStart) + "ms&7.");
        logger(settings.getBootBar());
    }

    /**
     * Gets instance of plugin attached to the core.
     *
     * @return Plugin that's attached to core
     */
    public JavaPlugin getPlugin() {
        return plugin;
    }

    /**
     * Gets the text settings.
     *
     * @return Text settings
     */
    public TextSettings textSettings() {
        return textUtilities;
    }

    /**
     * Gets the core settings.
     *
     * @return Core settings
     */
    public CoreSettings coreSettings() {
        return settings;
    }

    /**
     * Gets the file Manager.
     *
     * @return File manager
     */
    public FileManager files() {
        return files;
    }

    /**
     * Gets the major version of the server.
     *
     * @return Major version of the server
     */
    public static int getMajorVersion() {
        return TextKeys.majorVersion();
    }

    /**
     * Gets full version of the server.
     *
     * @return Full version of the server
     */
    public static String getServerVersion() {
        return Bukkit.getBukkitVersion().split("-")[0];
    }

    /**
     * Gets if the server is legacy.
     *
     * @return True if legacy
     */
    public static boolean isLegacy() {
        return getMajorVersion() > 12;
    }

    /**
     * Gets the fork of the server.
     *
     * @return Fork of the server
     */
    public String getFork() {
        return TextKeys.serverFork();
    }

    /**
     * Logs a message with correct prefix.
     *
     * @param message Message to log
     */
    public void logger(String... message) {
        textUtilities.doLog(message);
    }

    /**
     * Gets the boot color code character.
     *
     * @return Boot color code
     */
    public String themeColor() {
        return settings.getBootColor();
    }

    /**
     * Gets the author(s) of the plugin.
     *
     * @return Author(s) of the plugin
     */
    public String getAuthors() {
        return plugin.getDescription().getAuthors().toString().replace("[", "").replace("]", "");
    }

    /**
     * Sends a message to specified sender
     *
     * @param sender Sender to send to
     * @param messageKey Message key in lang.yml
     */
    public void sendMessage(CommandSender sender, String messageKey) {
        sendMessage(sender, messageKey, null);
    }

    /**
     * Sends a message to specified sender
     *
     * @param sender Sender to send to
     * @param messageKey Message key in lang.yml
     * @param placeholders Array of placeholders
     * @param replacements Array of placeholder replacements
     */
    public void sendMessage(CommandSender sender, String messageKey, String[] placeholders, String... replacements) {
        sendMessage(sender, files.getConfig("lang").getConfigurationSection("messages"), messageKey, placeholders, replacements);
    }

    /**
     * Sends a message to specified sender
     *
     * @param sender Sender to send to
     * @param section Config section to message get from
     * @param messageKey Message key in lang.yml
     * @param placeholders Array of placeholders
     * @param replacements Array of placeholder replacements
     */
    public void sendMessage(CommandSender sender, ConfigurationSection section, String messageKey, String[] placeholders, String... replacements) {

        // Adds brackets around placeholders
        if (placeholders != null)
            for (int i = 0; i < placeholders.length; i++)
                placeholders[i] = "{" + placeholders[i] + "}";

        // Sends message
        textUtilities.sendMessageList(sender, me.croabeast.beanslib.utilities.TextUtils.toList(section, messageKey), placeholders, replacements);
    }

    /**
     * Gets a value from a loaded yaml file with
     * colors and placeholders parsed.
     *
     * @param player Player in question
     * @param file Name of file to get from
     * @param path Path within file
     * @param placeholders Array of placeholders
     * @param replacements Array of placeholder replacements
     * @return String within the file
     */
    public String getLangValue(Player player, String file, String path, String[] placeholders, String[] replacements) {

        return me.croabeast.beanslib.utilities.TextUtils.replaceInsensitiveEach(getLangValue(player, file, path), placeholders, replacements);

    }

    /**
     * Gets a value from a loaded yaml file with
     * colors applied.
     *
     * @param player Player in question
     * @param file Name of file to get from
     * @param path Path within file
     * @return String within the file
     */
    public String getLangValue(Player player, String file, String path) {

        return textUtilities.colorize(player, getLangValue(file, path));

    }

    /**
     * Gets a raw value from a loaded yaml file.
     *
     * @param file Name of file to get from
     * @param path Path within file
     * @return String within the file
     */
    public String getLangValue(String file, String path) {

        return files.getConfig(file).getString(path);

    }

    /**
     * Used to check if server can run this plugin.
     * Will return true if possible to run.
     *
     * @param minVersion Minimum version
     * @param maxVersion Maximum version
     * @param pluginPrefix Plugin prefix
     * @param pluginVersion Plugin version
     * @return True if within bounds
     */
    public static boolean restrictVersions(int minVersion, int maxVersion, String pluginPrefix, String pluginVersion) {
        if (getMajorVersion() < minVersion)
            Bukkit.getLogger().severe(pluginPrefix + " v" + pluginVersion + " does not support 1." + getMajorVersion() + ".x and older!");
        else if (getMajorVersion() > maxVersion)
            Bukkit.getLogger().severe(pluginPrefix + " v" + pluginVersion + " does not support 1." + getMajorVersion() + ".x and newer. Please update!");
        else return true;
        return false;

    }

}
