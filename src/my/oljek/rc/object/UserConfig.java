package my.oljek.rc.object;

import com.oljek.spigot.manager.ConfigManager;
import my.oljek.rc.RealcraftPlugin;
import my.oljek.rc.util.LocationUtil;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class UserConfig {

    private RealcraftPlugin plugin;
    private String name;
    private List<Location> torchPlaces;
    private ConfigManager cfg;

    public UserConfig(RealcraftPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;

        ConfigManager.createFolder(plugin, "users");

        cfg = new ConfigManager(plugin, "users/" + name);

        addDefault();
        update();
    }

    public String getName() {
        return name;
    }

    public List<Location> getTorchPlaces() {
        return torchPlaces;
    }

    public void addTorch(Location loc) {
        torchPlaces.add(loc);
    }

    public void removeTorch(Location loc) {
        if (containsTorch(loc))
            torchPlaces.remove(loc);
    }

    public boolean containsTorch(Location loc) {
        return torchPlaces.contains(loc);
    }

    public void update() {
        torchPlaces = new ArrayList<>();

        for (String location: cfg.getConfiguration().getStringList("blockPlace")) {
            Location loc = LocationUtil.getLocationFromString(location);

            torchPlaces.add(loc);
        }
    }

    public void save() {
        List<String> locationStringList = new ArrayList<>();

        for (Location loc: torchPlaces)
            locationStringList.add(LocationUtil.getStringFromLocation(loc));

        cfg.getConfiguration().set("blockPlace", locationStringList);
        cfg.save();
    }

    private void addDefault() {
        cfg.getConfiguration().addDefault("blockPlace", new ArrayList<>());
        cfg.getConfiguration().options().copyDefaults(true);
        cfg.save();
    }

}
