package net.zerotoil.dev.cybercore.utilities;

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
     * Combines two arrays into a new array of the same type.
     *
     * @author Kihsomray
     * @since 1.3
     *
     * @param array First array
     * @param additionalArrays Any additional arrays
     * @param <T> Type of array (must be same)
     * @return New array of combined values
     */
    public static <T> T[] combineArrays(@NotNull T[] array, @Nullable T[]... additionalArrays) {
        if (additionalArrays == null) return Arrays.copyOf(array, array.length);
        List<T> resultList = new ArrayList<>();
        Collections.addAll(resultList, array);
        for (T[] a : additionalArrays) {
            if (a == null) continue;
            Collections.addAll(resultList, a);
        }
        @SuppressWarnings("unchecked")
        //the type cast is safe as the array1 has the type T[]
        T[] resultArray = (T[]) Array.newInstance(array.getClass().getComponentType(), 0);
        return resultList.toArray(resultArray);
    }

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
     */
    public static int getJavaVersion() {
        String version = System.getProperty("java.version");
        if (version.startsWith("1."))
            version = version.substring(2, 3);
        else {
            int dot = version.indexOf(".");
            if (dot != -1) version = version.substring(0, dot);
        }
        return Integer.parseInt(version);
    }


}
