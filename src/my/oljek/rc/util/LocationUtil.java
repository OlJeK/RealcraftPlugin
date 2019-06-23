package my.oljek.rc.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

    public static Location getLocationFromString(String str) {
        final String[] splitDate = str.split(";");

        String nameWorld = splitDate[0];
        int x = Integer.parseInt(splitDate[1]);
        int y = Integer.parseInt(splitDate[2]);
        int z = Integer.parseInt(splitDate[3]);

        return new Location(Bukkit.getWorld(nameWorld), x, y, z);
    }

    public static String getStringFromLocation(Location loc) {
        return loc.getWorld().getName() + ";" + loc.getBlockX() + ";" + loc.getBlockY() + ";" + loc.getBlockZ();
    }

}
