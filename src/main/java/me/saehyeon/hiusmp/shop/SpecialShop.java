package me.saehyeon.hiusmp.shop;

import me.saehyeon.hiusmp.Constants;
import me.saehyeon.hiusmp.items.InstantLobbyBackPaper;
import me.saehyeon.hiusmp.items.InventorySavePaper;
import me.saehyeon.hiusmp.items.NightVisionBottle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class SpecialShop {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "스페셜 상점");

        ExtraShopItem[] items = new ExtraShopItem[] {
            new ExtraShopItem(Material.PAPER, "§e인벤세이브 스크롤", 500, 700)
                    .registerRealItem(InventorySavePaper.getItem())
                    .setLore(Arrays.asList("§f지니고 있으면 인벤토리의 아이템과 레벨을 보호합니다.","")),

            new ExtraShopItem(Material.PAPER, "§f§l로비 귀환서", 100, 250)
                    .registerRealItem(InstantLobbyBackPaper.getItem())
                    .setLore(Arrays.asList("§7우클릭§f하면 로비로 즉시 이동합니다.","")),

            new ExtraShopItem(NightVisionBottle.getItem().getType(), "§b야간투시", 250, 750)
                    .registerRealItem(NightVisionBottle.getItem())
                    .setLore(Arrays.asList("§7우클릭§f하면 §7"+((int)(Constants.items.SI_NIGHT_VISION_TICK / 20 / 60))+"분의 야간투시§f를 부여받습니다.","§f서버를 나가면 즉시 야간투시가 사라집니다.",""))
        };

        player.openInventory(inv);

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toShopItem());
        }
    }
}
