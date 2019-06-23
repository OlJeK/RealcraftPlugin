package my.oljek.rc.object;

import my.oljek.rc.RealcraftPlugin;
import my.oljek.rc.manager.SQLiteConnectionManager;
import my.oljek.rc.manager.Storage;
import my.oljek.rc.util.MysqlUtil;

import java.lang.reflect.Field;
import java.util.*;

public class SQLiteStorage implements Storage {

    private SQLiteConnectionManager SQLiteConnectionManager;

    public SQLiteStorage(RealcraftPlugin plugin) {
        SQLiteConnectionManager = plugin.getSQLiteConnectionManager();
    }

    @Override
    public void save(Object obj, String table) {
//        Class classObj = obj.getClass();
//
//        Map<String, Object> objects = new HashMap<>();
//
//        try {
//            for (Field field : classObj.getDeclaredFields())
//                objects.put(field.getName(), field.get(obj));
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        List<String[]> keys = new ArrayList<>();
//        Object objReplace = null;
//
//        List<Object[]> objectsInteger = new ArrayList<>();
//        List<Object[]> objectsString = new ArrayList<>();
//
//        for (Map.Entry<String, Object> entry: objects.entrySet()) {
//            String key = entry.getKey();
//            Object object = entry.getValue();
//
//            if (keyReplace != null) {
//                if (key.equals(keyReplace)) {
//                    if (object instanceof UUID)
//                        objReplace = object.toString();
//                    else
//                        objReplace = object;
//                }
//            }
//
//            if (object instanceof Integer) {
//                objectsInteger.add(new Object[]{key, object});
//                keys.add(new String[] {key, "INTEGER"});
//            } else {
//                objectsString.add(new Object[]{key, object});
//                keys.add(new String[] {key, "VARCHAR(100)"});
//            }
//        }
//
//        SQLiteConnectionManager.createTable(table, false, keys);
//
//        if (objReplace != null) {
//            Object[][] dosfsdo = new Object[objectsInteger.size() + objectsString.size()][2];
//
//            for (int i = 0; i < objectsInteger.size(); i++) {
//                String key = (String) objectsInteger.get(i)[0];
//                Object object = objectsInteger.get(i)[1];
//
//                dosfsdo[i][1] = key;
//                dosfsdo[i][2] = object;
//            }
//
//            for (int i = objectsInteger.size(); i < objectsInteger.size() + objectsString.size(); i++) {
//                String key = (String) objectsString.get(i)[0];
//                String object = (String) objectsString.get(i)[1];
//
//                dosfsdo[i][1] = key;
//                dosfsdo[i][2] = object;
//            }
//
//            SQLiteConnectionManager.update(table, false, MysqlUtil.toCollection(dosfsdo), new Object[] {keyReplace, objReplace});
//        } else {
//            for (int i = 0; i < objectsInteger.size(); i++) {
//                String key = (String) objectsInteger.get(i)[0];
//                Object object = objectsInteger.get(i)[1];
//
//                dosfsdo[i][1] = key;
//                dosfsdo[i][2] = object;
//            }
//
//            for (int i = objectsInteger.size(); i < objectsInteger.size() + objectsString.size(); i++) {
//                String key = (String) objectsString.get(i)[0];
//                String object = (String) objectsString.get(i)[1];
//
//                dosfsdo[i][1] = key;
//                dosfsdo[i][2] = object;
//            }
//        }
    }

}
