package my.oljek.rc.object;

import my.oljek.rc.listener.custom.StaminaChangeLevel;
import my.oljek.rc.manager.SQLiteConnectionManager;
import my.oljek.rc.util.MysqlUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RealisticUser {

    private int levelStamina;
    private int expStamina;
    private SQLiteConnectionManager storage;
    private UUID uuid;

    public RealisticUser(UUID uuid, int levelStamina, int expStamina, SQLiteConnectionManager storage) {
        this.uuid = uuid;
        this.levelStamina = levelStamina;
        this.expStamina = expStamina;
        this.storage = storage;
    }

    public int getLevelStamina() {
        return levelStamina;
    }

    public int getExpStamina() {
        return expStamina;
    }

    public void setLevelStamina(int levelStamina) {
        this.levelStamina = levelStamina;
    }

    public void setExpStamina(int expStamina) {
        this.expStamina = expStamina;
    }

    public void checkLevel() {
        int needExpToNextLevel = getExpToNextLevel(levelStamina);
        int oldLevel = levelStamina;

        while (expStamina >= needExpToNextLevel) {
            expStamina -= needExpToNextLevel;
            levelStamina++;

            needExpToNextLevel = getExpToNextLevel(levelStamina);
        }

        Player p = Bukkit.getPlayer(uuid);

        if (levelStamina > oldLevel && p != null) {
            StaminaChangeLevel changeLevel = new StaminaChangeLevel(p, levelStamina);

            Bukkit.getPluginManager().callEvent(changeLevel);
        }

        if (levelStamina != oldLevel)
            save();
    }

    public int getExpToNextLevel(int level) {
        return level * 500;
    }

    public void save() {
        storage.update("user", false, MysqlUtil.toCollection(
                MysqlUtil.o("LevelStamina", levelStamina), MysqlUtil.o("ExpStamina", expStamina)),
                MysqlUtil.o("UUID", uuid.toString()));
    }

}
