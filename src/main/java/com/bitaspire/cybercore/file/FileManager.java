package com.bitaspire.cybercore.file;

import me.croabeast.file.ConfigurableFile;
import org.jetbrains.annotations.NotNull;

public interface FileManager {

    void load(String name, boolean send);

    void loadAll(boolean send, boolean useDefaults, String... additions);

    default void loadAll(String... additions) {
        loadAll(true, true, additions);
    }

    @NotNull
    ConfigurableFile get(String name) throws NullPointerException;
}
