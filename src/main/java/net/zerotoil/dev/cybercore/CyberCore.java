package net.zerotoil.dev.cybercore;

import me.croabeast.beanslib.utility.LibUtils;
import me.croabeast.beanslib.utility.TextUtils;
import net.zerotoil.dev.cybercore.files.FileManager;
import net.zerotoil.dev.cybercore.objects.Lag;
import net.zerotoil.dev.cybercore.utilities.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

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
        textUtilities = new TextSettings(this);
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
        return LibUtils.majorVersion();
    }

    /**
     * Gets full version of the server.
     *
     * @return Full version of the server
     */
    public static String getServerVersion() {
        return getFork().split(" ")[1];
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
    public static String getFork() {
        return LibUtils.serverFork();
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
        return (plugin.getDescription().getAuthors() + "").replace("[", "").replace("]", "");
    }

    /**
     * Send a message to a player or console
     * if the player is null. The player's
     * placeholders and chat colors will be
     * applied in the process.
     *
     * @param sender Sender to send to
     * @param messageKey Message in lang.yml
     * @return true always
     */
    public boolean sendMessage(@Nullable CommandSender sender, @NotNull String messageKey) {
        return sendMessage(sender, messageKey, null);
    }

    /**
     * Send a message to a player or console
     * if the player is null. The player's
     * placeholders and chat colors will be
     * applied in the process.
     *
     * Additional placeholders may be parsed
     * if included in the placeholders field.
     *
     * @param sender Sender to send to
     * @param messageKey Message in lang.yml
     * @param placeholders Array of placeholders
     * @param replacements Array of replacements for placeholders
     * @return true always
     */
    public boolean sendMessage(@Nullable CommandSender sender, @NotNull String messageKey, @Nullable String[] placeholders, @Nullable String... replacements) {
        if (placeholders != null) placeholders = Arrays.copyOf(placeholders, placeholders.length);
        if (replacements != null) replacements = Arrays.copyOf(replacements, replacements.length);
        return sendMessage(
                sender,
                "lang",
                "messages." + messageKey,
                placeholders,
                replacements
        );
    }

    /**
     * Send a message to a player or console
     * if the player is null. The player's
     * placeholders and chat colors will be
     * applied in the process.
     *
     * Additional placeholders may be parsed
     * if included in the placeholders field.
     *
     * @param sender Sender to send to
     * @param file YAML file in question
     * @param path Path in YAML file
     * @param placeholders Array of placeholders
     * @param replacements Array of replacements for placeholders
     * @return true always
     */
    public boolean sendMessage(@Nullable CommandSender sender, @NotNull String file, @NotNull String path, @Nullable String[] placeholders, @Nullable String... replacements) {
        Player player = sender instanceof Player ? (Player) sender : null;
        if (placeholders != null) placeholders = Arrays.copyOf(placeholders, placeholders.length);
        if (replacements != null) replacements = Arrays.copyOf(replacements, replacements.length);

        String[] split = path.split("\\.", 2);

        textUtilities.sendMessageList(
                sender,
                TextUtils.toList(files.getConfig(file).getConfigurationSection(split[0]), split.length == 2 ? split[1] : null),
                PlayerUtils.applyPlaceholderBraces(player != null ? TextUtils.combineArrays(PlayerUtils.getPlPlaceholders(), placeholders) : placeholders),
                player != null ? TextUtils.combineArrays(PlayerUtils.getPlReplacements(player), replacements) : replacements
        );
        return true;
    }

    /**
     * Gets a message from lang.yml. The
     * player's placeholders and chat colors
     * will be applied in the process.
     *
     * @param sender Sender in question
     * @param messageKey Message in lang.yml
     * @return true always
     */
    public String getMessage(@Nullable CommandSender sender, @NotNull String messageKey) {
        return getMessage(sender, messageKey, null);
    }

    /**
     * Gets a message from lang.yml. The
     * player's placeholders and chat colors
     * will be applied in the process.
     *
     * Additional placeholders may be parsed
     * if included in the placeholders field.
     *
     * @param sender Player in question
     * @param messageKey Message in lang.yml
     * @param placeholders Array of placeholders
     * @param replacements Array of replacements for placeholders
     * @return Message from lang
     */
    public String getMessage(@Nullable CommandSender sender, @NotNull String messageKey, @Nullable String[] placeholders, @Nullable String... replacements) {
        return getMessage(sender, "lang", "messages." + messageKey, placeholders, replacements);
    }

    /**
     * Gets a message from lang.yml. The
     * player's placeholders and chat colors
     * will be applied in the process.
     *
     * Additional placeholders may be parsed
     * if included in the placeholders field.
     *
     * @param sender Sender to send to
     * @param file YAML file in question
     * @param path Path in YAML file
     * @param placeholders Array of placeholders
     * @param replacements Array of replacements for placeholders
     * @return Message from lang
     */
    public String getMessage(@Nullable CommandSender sender, @NotNull String file, @NotNull String path, @Nullable String[] placeholders, @Nullable String... replacements) {
        Player player = sender instanceof Player ? (Player) sender : null;
        return getLangValue(
                player,
                file,
                path,
                PlayerUtils.applyPlaceholderBraces(player != null ? TextUtils.combineArrays(PlayerUtils.getPlPlaceholders(), placeholders) : placeholders),
                player != null ? TextUtils.combineArrays(PlayerUtils.getPlReplacements(player), replacements) : replacements
        );
    }

    /**
     * Gets a value from a loaded yaml file
     * with colors and placeholders parsed.
     *
     * @param sender Sender to send to
     * @param file YAML file in question
     * @param path Path in YAML file
     * @param placeholders Array of placeholders
     * @param replacements Array of placeholder replacements
     * @return String within the file
     */
    public String getLangValue(@Nullable CommandSender sender, @NotNull String file, @NotNull String path, @Nullable String[] placeholders, @Nullable String[] replacements) {
        return TextUtils.replaceInsensitiveEach(getLangValue(sender, file, path), placeholders, replacements);
    }

    /**
     * Gets a value from a loaded yaml file with
     * colors applied.
     *
     * @param sender Sender to send to
     * @param file YAML file in question
     * @param path Path in YAML file
     * @return String within the file
     */
    public String getLangValue(@Nullable CommandSender sender, @NotNull String file, @NotNull String path) {

        final Player player = sender instanceof Player ? (Player) sender : null;
        return textUtilities.colorize(player, player, getLangValue(file, path));

    }

    /**
     * Gets a raw value from a loaded yaml file.
     *
     * @param file YAML file in question
     * @param path Path in YAML file
     * @return String within the file
     */
    public String getLangValue(@NotNull String file, @NotNull String path) {
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
    public static boolean restrictVersions(int minVersion, int maxVersion, @NotNull String pluginPrefix, String pluginVersion) {
        if (getMajorVersion() < minVersion)
            Bukkit.getLogger().severe(pluginPrefix + " v" + pluginVersion + " does not support 1." + getMajorVersion() + ".x and older!");
        else if (getMajorVersion() > maxVersion)
            Bukkit.getLogger().severe(pluginPrefix + " v" + pluginVersion + " does not support 1." + getMajorVersion() + ".x and newer. Please update!");
        else return true;
        return false;

    }

    public String getPreviousVersion() {
        return files.getConfig("plugin-data").getString("version.previous", plugin.getDescription().getVersion());
    }

}
