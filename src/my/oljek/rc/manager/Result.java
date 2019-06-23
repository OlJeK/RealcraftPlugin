package my.oljek.rc.manager;

import java.util.Map;

public class Result {

    private Map<String, Object> result;

    public Result(Map<String, Object> result) {
        this.result = result;
    }

    public int getInt(String key) {
        return (int) result.get(key);
    }

    public String getString(String key) {
        return (String) result.get(key);
    }

}
