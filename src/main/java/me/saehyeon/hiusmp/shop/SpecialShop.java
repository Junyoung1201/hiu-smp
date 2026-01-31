package me.saehyeon.hiusmp.shop;

import me.saehyeon.hiusmp.Constants;
import me.saehyeon.hiusmp.items.ExpBooster;
import me.saehyeon.hiusmp.items.InstantLobbyBackPaper;
import me.saehyeon.hiusmp.items.InventorySavePaper;
import me.saehyeon.hiusmp.items.NightVisionBottle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static me.saehyeon.hiusmp.Constants.items.*;

public class SpecialShop {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "스페셜 상점");

        // 인첸트북 아이템 - 수선
        ItemStack mendingBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta mendingBookMeta = (EnchantmentStorageMeta) mendingBook.getItemMeta();
        mendingBookMeta.addStoredEnchant(Enchantment.MENDING, 1, false);
        mendingBook.setItemMeta(mendingBookMeta);

        // 인첸트북 아이템 - 내구도 3
        ItemStack unbreakingBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta unbreakingBookMeta = (EnchantmentStorageMeta) unbreakingBook.getItemMeta();
        unbreakingBookMeta.addStoredEnchant(Enchantment.UNBREAKING, 3, false);
        unbreakingBook.setItemMeta(unbreakingBookMeta);

        // 인첸트북 아이템 - 보호 4
        ItemStack protectionBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta protectionBookMeta = (EnchantmentStorageMeta) protectionBook.getItemMeta();
        protectionBookMeta.addStoredEnchant(Enchantment.PROTECTION, 4, false);
        protectionBook.setItemMeta(protectionBookMeta);

        // 인첸트북 아이템 - 효율 4
        ItemStack effectiveBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta effectiveBookMeta = (EnchantmentStorageMeta) effectiveBook.getItemMeta();
        effectiveBookMeta.addStoredEnchant(Enchantment.EFFICIENCY, 4, false);
        effectiveBook.setItemMeta(effectiveBookMeta);

        // 인첸트북 아이템 - 신속한 잠행 3
        ItemStack quietSneakingBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta quietSneakingBookMeta = (EnchantmentStorageMeta) quietSneakingBook.getItemMeta();
        quietSneakingBookMeta.addStoredEnchant(Enchantment.SWIFT_SNEAK, 3, false);
        quietSneakingBook.setItemMeta(quietSneakingBookMeta);

        // 인첸트북 아이템 - 행운 3
        ItemStack fortuneBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta fortuneBookMeta = (EnchantmentStorageMeta) fortuneBook.getItemMeta();
        fortuneBookMeta.addStoredEnchant(Enchantment.FORTUNE, 3, false);
        fortuneBook.setItemMeta(fortuneBookMeta);

        // 인첸트북 아이템 - 날카로움 4
        ItemStack sharpnessBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta sharpnessBookMeta = (EnchantmentStorageMeta) sharpnessBook.getItemMeta();
        sharpnessBookMeta.addStoredEnchant(Enchantment.SHARPNESS, 4, false);
        sharpnessBook.setItemMeta(sharpnessBookMeta);

        // 인첸트북 아이템 - 가시 3
        ItemStack thornBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta thornBookMeta = (EnchantmentStorageMeta) thornBook.getItemMeta();
        thornBookMeta.addStoredEnchant(Enchantment.THORNS, 3, false);
        thornBook.setItemMeta(thornBookMeta);

        // 인첸트북 아이템 - 약탈 3
        ItemStack lootingBook = new ItemStack(Material.ENCHANTED_BOOK, 1);
        EnchantmentStorageMeta lootingBookMeta = (EnchantmentStorageMeta) lootingBook.getItemMeta();
        lootingBookMeta.addStoredEnchant(Enchantment.LOOTING, 3, false);
        lootingBook.setItemMeta(lootingBookMeta);

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
                    .setLore(Arrays.asList("§7우클릭§f하면 §7"+((int)(Constants.items.SI_EXP_BOOSTER_TICK / 20 / 60))+"분§f 동안 경험치 2배 부스팅을 적용합니다.","§f서버를 나가도 사라지지 않습니다.","")),

            new ExtraShopItem(mendingBook.getType(), "§b수선 인첸트 북", 0, 250000)
                    .registerRealItem(mendingBook)
                    .setLore(Arrays.asList("§f수선 인첸트 북입니다.")),

            new ExtraShopItem(unbreakingBook.getType(), "§b내구성 3 인첸트 북 ", 0,70000)
                    .registerRealItem(unbreakingBook)
                    .setLore(Arrays.asList("§f내구성 3 인첸트 북입니다.")),

            new ExtraShopItem(protectionBook.getType(), "§b보호 4 인첸트 북", 0,90000)
                    .registerRealItem(protectionBook)
                    .setLore(Arrays.asList("§f보호 4 인첸트 북입니다.")),

            new ExtraShopItem(effectiveBook.getType(), "§b효율 4 인첸트 북", 0,85000)
                    .registerRealItem(effectiveBook)
                    .setLore(Arrays.asList("§f효율 4 인첸트 북입니다.")),

            new ExtraShopItem(quietSneakingBook.getType(), "§b신속한 잠행 3 인첸트 북", 0,140000)
                    .registerRealItem(quietSneakingBook)
                    .setLore(Arrays.asList("§f신속한 잠행 3 인첸트 북입니다.")),

            new ExtraShopItem(fortuneBook.getType(), "§b행운 3 인첸트 북", 0,180000)
                    .registerRealItem(fortuneBook)
                    .setLore(Arrays.asList("§f행운 3 인첸트 북입니다.")),

            new ExtraShopItem(sharpnessBook.getType(), "§b날카로움 4 인첸트 북", 0,150000)
                    .registerRealItem(sharpnessBook)
                    .setLore(Arrays.asList("§f날카로움 4 인첸트 북입니다.")),

            new ExtraShopItem(thornBook.getType(), "§b가시 3 인첸트 북", 0,180000)
                    .registerRealItem(thornBook)
                    .setLore(Arrays.asList("§f가시 3 인첸트 북입니다.")),

            new ExtraShopItem(lootingBook.getType(), "§b약탈 3 인첸트 북", 0,140000)
                    .registerRealItem(lootingBook)
                    .setLore(Arrays.asList("§f약탈 3 인첸트 북입니다."))
        };

        player.openInventory(inv);

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toShopItem());
        }
    }
}
