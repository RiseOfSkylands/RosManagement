package ROS;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class Lib {
    public static String[] GetArrayElements(ArrayList<String> arr)
    {
        Object[] objArr = arr.toArray(); // ArrayList to object array conversion
        String[] str = Arrays.copyOf(objArr, objArr.length, String[].class); // Object array to String array conversion
        return str;
    }
    public static int random_int(int Min, int Max)
    {
        return (int) (Math.random()*(Max-Min))+Min;
    }
}
