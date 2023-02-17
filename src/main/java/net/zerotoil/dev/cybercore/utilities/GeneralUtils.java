package net.zerotoil.dev.cybercore.utilities;

import me.croabeast.beanslib.utility.LibUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GeneralUtils {

    /**
     * Gets a random double.
     *
     * @return Randomly generated double
     */
    public static double randomDouble() {
        return ThreadLocalRandom.current().nextDouble();
    }

    /**
     * Gets a random double within a bound.
     *
     * @param bound Bounds to generate within
     * @return Randomly generated double
     */
    public static double randomDouble(double bound) {
        return ThreadLocalRandom.current().nextDouble(bound);
    }

    /**
     * Gets a random integer.
     *
     * @return Randomly generated integer
     */
    public static int randomInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    /**
     * Gets a random integer within a bound.
     *
     * @param bound Bounds to generate within
     * @return Randomly generated integer
     */
    public static int randomInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    /**
     * Gets a random long.
     *
     * @return Randomly generated long
     */
    public static long randomLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    /**
     * Gets a random long within a bound.
     *
     * @param bound Bounds to generate within
     * @return Randomly generated long
     */
    public static long randomLong(long bound) {
        return ThreadLocalRandom.current().nextLong(bound);
    }

    /**
     * Version of Java that is currently running.
     *
     * @return Current version of java
     * @deprecated See {@link LibUtils#majorJavaVersion()}
     */
    @Deprecated
    public static int getJavaVersion() {
        return LibUtils.majorJavaVersion();
    }
}
