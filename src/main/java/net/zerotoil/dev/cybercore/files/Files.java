package net.zerotoil.dev.cybercore.files;

import net.zerotoil.dev.cybercore.CyberCore;
import org.bukkit.configuration.Configuration;

import java.util.HashMap;

public class Files {

    private final CyberCore main;
    private final HashMap<String, YAMLFile> files = new HashMap<>();

    public Files(CyberCore main) {
        this.main = main;
    }

    public void load(String... additionalFiles) {
        load(true, true, additionalFiles);
    }

    public void load(boolean sendMessages, boolean defaultFiles, String... fileNames) {
        if (!files.isEmpty()) files.clear();
        if (sendMessages) main.logger(main.themeColor() + "Loading YAML files...");
        long startTime = System.currentTimeMillis();

        if (defaultFiles) {
            addFile("config", sendMessages);
            addFile("lang", sendMessages);
        }

        for (String string : fileNames) addFile(string, sendMessages);

        if (sendMessages) main.logger("&7Loaded &e" + files.size() + "&7 files in &a" + (System.currentTimeMillis() - startTime) + "ms&7.", "");
    }

    public void addFile(String file, boolean sendMessages) {
        files.put(file, new YAMLFile(main, main.getPlugin(), file + ".yml"));
        files.get(file).reloadConfig();
        if (sendMessages) main.logger("&7Loaded file &e" + file + ".yml&7.");
    }


    public HashMap<String, YAMLFile> getFiles() {
        return this.files;
    }
    public YAMLFile get(String file){
        return files.get(file);
    }
    public Configuration getConfig(String file) {
        return files.get(file).getConfig();
    }


}
