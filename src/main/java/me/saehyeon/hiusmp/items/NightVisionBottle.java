package me.saehyeon.hiusmp.items;

import me.saehyeon.hiusmp.Constants;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import java.util.Arrays;

import static me.saehyeon.hiusmp.Constants.items.SI_NIGHT_VISION_BOTTLE_DISPLAY_NAME;

public class NightVisionBottle {
    public static ItemStack getItem() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setDisplayName(SI_NIGHT_VISION_BOTTLE_DISPLAY_NAME);
        meta.setLore(Arrays.asList(
                "§7우클릭§f하여 §7"+((int)(Constants.items.SI_NIGHT_VISION_TICK / 20 / 60))+"분§f 동안 야간투시를 부여받습니다.",
                "§f서버를 나가면 즉시 야간투시가 종료됩니다."
        ));
        meta.setBasePotionType(PotionType.NIGHT_VISION);
        meta.addEnchant(Enchantment.EFFICIENCY,1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        return item;
    }
}
