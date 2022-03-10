package ROS;

import com.skylands.Globals;

import java.lang.reflect.Field;
import java.util.*;

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
    public static int getKeyFromPlayerCustomBlocks(PlayerCustomBlock b) {
        for (Integer n : Globals.PlayerCustomBlocks.keySet()){
            PlayerCustomBlock cb = Globals.PlayerCustomBlocks.get(n);
            if(cb.MID.equals(b.MID) && cb.itemid.equals(b.itemid) && cb.name.equals(b.name) && cb.level == b.level){
                return n;
            }
        }
        return 0;
    }
    public static int getKeyFromPlayerCustomBlocks(String itemid) {
        for (Integer n : Globals.PlayerCustomBlocks.keySet()){
            PlayerCustomBlock cb = Globals.PlayerCustomBlocks.get(n);
            if(cb.itemid.equals(itemid)){
                return n;
            }
        }
        return 0;
    }
}
