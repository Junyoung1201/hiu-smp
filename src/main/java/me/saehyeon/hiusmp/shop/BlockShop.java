package me.saehyeon.hiusmp.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BlockShop {
    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "블럭 상점 #1");
        player.openInventory(inv);

        ShopItem[] items = new ShopItem[] {
            new ShopItem("흙", Material.DIRT, 1,5),
            new ShopItem("조약돌", Material.COBBLESTONE, 1,5),
            new ShopItem("구운 돌", Material.STONE, 2,10),
            new ShopItem("심층암", Material.COBBLED_DEEPSLATE, 1,5),
            new ShopItem("벽돌", Material.BRICKS, 4,20),
            new ShopItem("네더 벽돌", Material.NETHER_BRICKS, 6,30),
            new ShopItem("얼음", Material.ICE, 4,20),
            new ShopItem("참나무 원목", Material.OAK_LOG, 3, 15),
            new ShopItem("자작나무 원목", Material.BIRCH_LOG, 3, 15),
            new ShopItem("가문비나무 원목", Material.SPRUCE_LOG, 3, 15),
            new ShopItem("아카시아 원목", Material.ACACIA_LOG, 3, 15),
            new ShopItem("벚꽃나무 원목", Material.CHERRY_LOG, 5, 25),
            new ShopItem("정글나무 원목", Material.JUNGLE_LOG, 3, 15),
            new ShopItem("짙은 참나무 원목", Material.DARK_OAK_LOG, 3, 15),
            new ShopItem("맹그로브 나무 원목", Material.DARK_OAK_LOG, 5, 25),
            new ShopItem("창백한 나무 원목", Material.PALE_OAK_LOG, 6, 30),
            new ShopItem("석영 블럭", Material.QUARTZ_BLOCK, 5, 25),

            new ShopItem("하얀색 콘크리트", Material.WHITE_CONCRETE, 10, 25),
            new ShopItem("밝은 회색 콘크리트", Material.LIGHT_GRAY_CONCRETE, 10, 25),
            new ShopItem("회색 콘크리트", Material.GRAY_CONCRETE, 10, 25),
            new ShopItem("검은색 콘크리트", Material.BLACK_CONCRETE, 10, 25),
            new ShopItem("빨간색 콘크리트", Material.RED_CONCRETE, 10, 25),
            new ShopItem("주황색 콘크리트", Material.ORANGE_CONCRETE, 10, 25),
            new ShopItem("갈색 콘크리트", Material.BROWN_CONCRETE, 10, 25),
            new ShopItem("노란색 콘크리트", Material.YELLOW_CONCRETE, 10, 25),
            new ShopItem("초록색 콘크리트", Material.LIME_CONCRETE, 10, 25),
            new ShopItem("녹색 콘크리트", Material.GREEN_CONCRETE, 10, 25),
            new ShopItem("청록색 콘크리트", Material.CYAN_CONCRETE, 10, 25),
            new ShopItem("하늘색 콘크리트", Material.LIGHT_BLUE_CONCRETE, 10, 25),
            new ShopItem("파란색 콘크리트", Material.BLUE_CONCRETE, 10, 25),
            new ShopItem("분홍색 콘크리트", Material.PINK_CONCRETE, 10, 25),
            new ShopItem("보라색 콘크리트", Material.PURPLE_CONCRETE, 10, 25),

            new ShopItem("발광석", Material.GLOWSTONE, 5, 25),
            new ShopItem("바다 랜턴", Material.SEA_LANTERN, 15, 75),
            new ShopItem("슬라임 블럭", Material.SLIME_BLOCK, 15, 75),
            new ShopItem("꿀 블럭", Material.HONEY_BLOCK, 4, 20),
            new ShopItem("흑요석", Material.OBSIDIAN, 10, 50),
            new ShopItem("하얀색 유리", Material.WHITE_STAINED_GLASS, 5, 25),
            new ShopItem("밝은 회색 유리", Material.LIGHT_GRAY_STAINED_GLASS, 5, 25),
            new ShopItem("회색 유리", Material.GRAY_STAINED_GLASS, 5, 25),
            new ShopItem("검은색 유리", Material.BLACK_STAINED_GLASS, 5, 25),
            new ShopItem("빨간색 유리", Material.RED_STAINED_GLASS, 5, 25),
            new ShopItem("주황색 유리", Material.ORANGE_STAINED_GLASS, 5, 25),
            new ShopItem("갈색 유리", Material.BROWN_STAINED_GLASS, 5, 25),
            new ShopItem("노란색 유리", Material.YELLOW_STAINED_GLASS, 5, 25),
            new ShopItem("초록색 유리", Material.LIME_STAINED_GLASS, 5, 25),
            new ShopItem("녹색 유리", Material.GREEN_STAINED_GLASS, 5, 25),
            new ShopItem("청록색 유리", Material.CYAN_STAINED_GLASS, 5, 25),
            new ShopItem("하늘색 유리", Material.LIGHT_BLUE_STAINED_GLASS, 5, 25),
            new ShopItem("파란색 유리", Material.BLUE_STAINED_GLASS, 5, 25),
            new ShopItem("분홍색 유리", Material.PINK_STAINED_GLASS, 5, 25),
            new ShopItem("보라색 유리", Material.PURPLE_STAINED_GLASS, 5, 25)
        };

        for(int i = 0 ; i < items.length; i++) {
            player.getOpenInventory().getTopInventory().setItem(i, items[i].toItemStack());
        }
    }
}
