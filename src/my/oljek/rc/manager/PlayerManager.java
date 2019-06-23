package my.oljek.rc.manager;

import my.oljek.rc.object.RealisticUser;

import java.util.UUID;

public interface PlayerManager {

    RealisticUser getUser(UUID uuid);

    void createUser(UUID uuid);

}
