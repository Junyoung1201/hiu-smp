package me.saehyeon.hiusmp;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Constants {
    public static void init() {
        locations.LOBBY = new Location(Bukkit.getWorld("world"), 0, 0, 0,0,0);
        locations.PARKOUR_SPAWN = new Location(Bukkit.getWorld("world"),0, -57 ,0);
        locations.PARKOUR_POS1 = new Location(Bukkit.getWorld("world"),7, -50, 51);
        locations.PARKOUR_POS2 = new Location(Bukkit.getWorld("world"),-35, -63, -2);

        locations.TOWN_SPAWN_POS1 = new Location(Bukkit.getWorld("town"), 3, -64, 3);
        locations.TOWN_SPAWN_POS2 = new Location(Bukkit.getWorld("town"), -3, 319, -3);
        locations.TOWN_SPAWN = new Location(Bukkit.getWorld("town"), 0.5f, -12, 0.5f);
    }

    public static class shop {
        public static int SHOP_ITEM_CUSTOM_MODEL_DATA = 1001;
        public static int EXTRA_SHOP_ITEM_CUSTOM_MODE_DATA = 1002;
    }

    public static class costs {
        public static int HOME_SET_COST = 500;
    }

    public static class locations {
        public static Location LOBBY = null;
        public static Location PARKOUR_SPAWN = null;
        public static Location PARKOUR_POS1 = null;
        public static Location PARKOUR_POS2 = null;
        public static Location TOWN_SPAWN_POS1 = null;
        public static Location TOWN_SPAWN_POS2 = null;
        public static Location TOWN_SPAWN = null;
    }
}
