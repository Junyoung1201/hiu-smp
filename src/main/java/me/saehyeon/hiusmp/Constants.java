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
        public static final int SHOP_ITEM_CUSTOM_MODEL_DATA = 1001;
        public static final int EXTRA_SHOP_ITEM_CUSTOM_MODE_DATA = 1002;

        public static final String SHOP_SELECT_GUI_TITLE = "상점 선택";

        public static final String BLOCK_SHOP_ICON_NAME = "§f블럭 상점";
        public static final String MINER_SHOP_ICON_NAME = "§f광물 상점";
        public static final String FARM_SHOP_ICON_NAME = "§f농작물 상점";
        public static final String MONSTER_SHOP_ICON_NAME = "§f잡템 상점";
        public static final String TREASURE_SHOP_ICON_NAME = "§f희귀품 상점";
        public static final String SPECIAL_SHOP_ICON_NAME = "§d스페셜 상점";
    }

    public static class timers {
        public static final long CUSTOM_NAME_TAG_CLEANER_TIMER_INTERVAL = 20L*60L;
    }

    public static class costs {
        public static final int HOME_SET_COST = 500;
        public static final int CUSTOM_NAME_CHANGE_COST = 6500;
    }

    public static class items {
        public static final int SI_NIGHT_VISION_TICK = 20*10*60;
        public static final long SI_EXP_BOOSTER_TICK = 20L * 60L * 10L;
        public static final String SI_LOBBY_BACK_PAPER_DISPLAY_NAME = "§f§l로비 귀환서";
        public static final String SI_INVENTORY_SAVE_PAPER_DISPLAY_NAME = "§e인벤세이브 스크롤";
        public static final String SI_NIGHT_VISION_BOTTLE_DISPLAY_NAME = "§b야간투시";
        public static final String SI_EXP_BOOSTER_DISPLAY_NAME = "§a§l경험치 부스터";
    }

    public static class locations {
        public static Location LOBBY = null;
        public static Location PARKOUR_SPAWN = null;
        public static Location PARKOUR_POS1 = null;
        public static Location PARKOUR_POS2 = null;
        public static Location TOWN_SPAWN_POS1 = null;
        public static Location TOWN_SPAWN_POS2 = null;
        public static Location TOWN_SPAWN = null;
        public static Location WILD_SPAWN = null;
    }
}
