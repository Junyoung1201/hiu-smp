package me.saehyeon.hiusmp.features;

import me.saehyeon.hiusmp.Main;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Home {
    private static YamlConfiguration homeData = new YamlConfiguration();
    private static File homeDataFile = new File(Main.ins.getDataFolder(), "home.yml");

    public static void setHome(Player player, int slotIndex, Location loc) {
        List<Location> homeList = (List<Location>) homeData.get(player.getUniqueId().toString(), Arrays.asList(null,null,null,null));
        homeList.set(slotIndex-1, loc);
        homeData.set(player.getUniqueId().toString(), homeList);
        player.sendMessage("§7슬롯 #"+slotIndex+"§f에 현재 위치를 저장했습니다.");

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, SoundCategory.MASTER, 0.7f,1.5f);
    }

    public static void load() {
        try {
            if(!homeDataFile.exists()) {
                homeDataFile.createNewFile();
            }

            homeData.load(homeDataFile);
            Main.ins.getLogger().info("홈 데이터 로드함.");

        } catch (Exception e) {
            Main.ins.getLogger().warning("home.yml 로드 실패");
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            Main.ins.getDataFolder().mkdir();
            homeData.save(homeDataFile);

        } catch (Exception e) {
            Main.ins.getLogger().warning("home.yml 저장 실패");
            e.printStackTrace();
        }
    }

    public static void openWarpGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "집");

        ItemStack descItem = new ItemStack(Material.NAME_TAG);
        ItemMeta descItemMeta = descItem.getItemMeta();
        descItemMeta.setDisplayName("§f이동할 집을 선택합니다.");
        descItemMeta.setLore(Arrays.asList("§f텔레포트할 집 슬롯을 클릭하세요. →","§f집 이동 마다 §650 히유코인§f을 소모합니다."));
        descItem.setItemMeta(descItemMeta);

        player.openInventory(inv);

        inv.setItem(10, descItem);

        updateHomeBanner(player);
    }

    public static void openSetHomeGUI(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "집 설정");

        ItemStack descItem = new ItemStack(Material.NAME_TAG);
        ItemMeta descItemMeta = descItem.getItemMeta();
        descItemMeta.setDisplayName("§f현재 위치를 집으로 저장합니다.");
        descItemMeta.setLore(Arrays.asList("§f위치를 저장할 슬롯을 클릭하세요. →","§f집 설정 시 마다 §650 히유코인§f을 소모합니다."));
        descItem.setItemMeta(descItemMeta);

        player.openInventory(inv);

        inv.setItem(10, descItem);

        updateHomeBanner(player);
    }

    public static void updateHomeBanner(Player player) {
        List<Location> homes = (List<Location>) homeData.get(player.getUniqueId().toString(), Arrays.asList(null,null,null,null));

        for(int i = 0; i < 4; i++) {

            ItemStack banner = new ItemStack(Material.WHITE_BANNER, 1);
            ItemMeta meta = banner.getItemMeta();

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.setDisplayName("§f슬롯 #"+(i+1));

            if(homes.size() > i && homes.get(i) != null) {

                Location homeLoc = homes.get(i);

                double x = homeLoc.getBlockX();
                double y = homeLoc.getBlockY();
                double z = homeLoc.getBlockZ();

                meta.setLore(Arrays.asList("§7"+homeLoc.getWorld().getName()+", x: "+x+", y: "+y+", z: "+z));

            } else {
                banner.setType(Material.LIGHT_GRAY_BANNER);
                meta.setLore(Arrays.asList("§7지정되지 않음."));
            }

            banner.setItemMeta(meta);
            player.getOpenInventory().setItem(12+i,banner);
        }
    }
}
