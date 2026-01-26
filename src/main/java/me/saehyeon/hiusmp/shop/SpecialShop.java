package me.saehyeon.hiusmp.shop;

import me.saehyeon.hiusmp.Constants;
import me.saehyeon.hiusmp.items.ExpBooster;
import me.saehyeon.hiusmp.items.InstantLobbyBackPaper;
import me.saehyeon.hiusmp.items.InventorySavePaper;
import me.saehyeon.hiusmp.items.NightVisionBottle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

import static me.saehyeon.hiusmp.Constants.items.*;

public class SpecialShop {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "스페셜 상점");

        ExtraShopItem[] items = new ExtraShopItem[] {
            new ExtraShopItem(Material.PAPER, SI_INVENTORY_SAVE_PAPER_DISPLAY_NAME, 500, 700)
                    .registerRealItem(InventorySavePaper.getItem())
                    .setLore(Arrays.asList("§f지니고 있으면 인벤토리의 아이템과 레벨을 보호합니다.","")),

            new ExtraShopItem(Material.PAPER, SI_LOBBY_BACK_PAPER_DISPLAY_NAME, 100, 250)
                    .registerRealItem(InstantLobbyBackPaper.getItem())
                    .setLore(Arrays.asList("§7우클릭§f하면 로비로 즉시 이동합니다.","")),

            new ExtraShopItem(NightVisionBottle.getItem().getType(), SI_NIGHT_VISION_BOTTLE_DISPLAY_NAME, 250, 750)
                    .registerRealItem(NightVisionBottle.getItem())
                    .setLore(Arrays.asList("§7우클릭§f하면 §7"+((int)(Constants.items.SI_NIGHT_VISION_TICK / 20 / 60))+"분의 야간투시§f를 부여받습니다.","§f서버를 나가면 즉시 야간투시가 사라집니다.","")),


            new ExtraShopItem(ExpBooster.getItem().getType(), SI_EXP_BOOSTER_DISPLAY_NAME, 1000, 4500)
                    .registerRealItem(ExpBooster.getItem())
                    .setLore(Arrays.asList("§7우클릭§f하면 §7"+((int)(Constants.items.SI_EXP_BOOSTER_TICK / 20 / 60))+"분§f 동안 경험치 2배 부스팅을 적용합니다.","§f서버를 나가도 사라지지 않습니다.",""))
        };

        player.openInventory(inv);

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toShopItem());
        }
    }
}
