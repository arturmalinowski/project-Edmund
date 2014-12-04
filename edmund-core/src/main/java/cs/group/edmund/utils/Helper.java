package cs.group.edmund.utils;

import java.util.ArrayList;
import java.util.HashSet;

public class Helper {

    public static ArrayList<String> removeDuplicates(ArrayList<String> list) {
        HashSet hs = new HashSet();
        hs.addAll(list);
        list.clear();
        list.addAll(hs);
        return list;
    }
}
