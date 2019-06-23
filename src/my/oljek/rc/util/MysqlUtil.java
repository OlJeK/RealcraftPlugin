package my.oljek.rc.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MysqlUtil {

    public static Collection<Object[]> toCollection(Object[]... objects) {
        List<Object[]> objectList = new ArrayList<>();

        for (Object[] obj: objects)
            objectList.add(obj);

        return objectList;
    }

    public static Object[] o(String s, Object o) {
        return new Object[]{s, o};
    }

    public static String[] s(String s, String s2) {
        return new String[]{s, s2};
    }

}
