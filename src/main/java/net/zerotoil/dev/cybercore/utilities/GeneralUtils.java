package net.zerotoil.dev.cybercore.utilities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GeneralUtils {

    public static <T> T[] combineArrays(T[] array1, T[] array2) {
        if (array1 == null) return Arrays.copyOf(array2, array2.length);
        if (array2 == null) return Arrays.copyOf(array1, array1.length);
        List<T> resultList = new ArrayList<>(array1.length + array2.length);
        Collections.addAll(resultList, array1);
        Collections.addAll(resultList, array2);

        @SuppressWarnings("unchecked")
        //the type cast is safe as the array1 has the type T[]
        T[] resultArray = (T[]) Array.newInstance(array1.getClass().getComponentType(), 0);
        return resultList.toArray(resultArray);
    }

}
