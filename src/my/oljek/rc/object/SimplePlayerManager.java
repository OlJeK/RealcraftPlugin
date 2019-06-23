package my.oljek.rc.object;

import com.oljek.main.manager.ConnectionManager;
import my.oljek.rc.manager.PlayerManager;
import my.oljek.rc.manager.SQLiteConnectionManager;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SimplePlayerManager implements PlayerManager {

    private SQLiteConnectionManager connectionManager;

    public SimplePlayerManager(SQLiteConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public RealisticUser getUser(UUID uuid) {
        List<Map<String, Object>> result = connectionManager.select("user", 1, ConnectionManager.o("UUID", uuid.toString()));

        if (result.size() == 0)
            return null;

        Map<String, Object> map = result.get(0);
        int levelStamina = (int) map.get("LevelStamina");
        int expStamina = (int) map.get("ExpStamina");

        return new RealisticUser(uuid, levelStamina, expStamina, connectionManager);
    }

    @Override
    public void createUser(UUID uuid) {
        System.out.println("tset132");

        connectionManager.insert("user", false, uuid.toString(), 1, 0);
    }

}
