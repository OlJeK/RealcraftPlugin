package my.oljek.rc.manager;

import java.util.List;
import java.util.Map;

public class MysqlResult {

    private List<Map<String, Object>> results;
    private int cursor;

    public MysqlResult(List<Map<String, Object>> results) {
        this.results = results;
    }

    public int getSize() {
        return results.size();
    }

    public boolean hasNext() {
        return Math.max(1, results.size() - 1) < cursor;
    }

    public Result getNext() {
        return new Result(results.get(cursor++));
    }

}
