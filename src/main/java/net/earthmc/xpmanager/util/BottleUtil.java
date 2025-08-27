package net.earthmc.xpmanager.util;

import de.tr7zw.changeme.nbtapi.NBT;
import net.earthmc.xpmanager.XPManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BottleUtil {

    public static final String BOTTLED_EXP_TAG = "StoredBottledExp";
    public static final NamespacedKey STORE_KEY = new NamespacedKey(XPManager.getInstance(), "xpmanager-store-amount");

    public static void givePlayerStoreBottleQuantity(Player player, int xpAmount, int quantity) {
        ItemStack bottles = new ItemStack(Material.EXPERIENCE_BOTTLE, quantity);

        ItemMeta meta = bottles.getItemMeta();

        meta.getPersistentDataContainer().set(STORE_KEY, PersistentDataType.INTEGER, xpAmount);
        bottles.setItemMeta(meta);

        bottles.lore(getStoreLoreComponent(xpAmount));

        HashMap<Integer, ItemStack> remainingItems = player.getInventory().addItem(bottles);
        if (remainingItems.isEmpty())
            return;

        for (ItemStack item : remainingItems.values()) {
            player.getWorld().dropItem(player.getLocation(), item);
        }
    }

    public static boolean isItemStoreBottle(ItemStack item) {
        if (item.getType() != Material.EXPERIENCE_BOTTLE)
            return false;

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(STORE_KEY, PersistentDataType.INTEGER))
            return true;

        return NBT.get(item, nbti -> {
            return nbti.hasTag(BOTTLED_EXP_TAG);
        });
    }

    /**
     *
     * @param bottle Experience bottle to check the store data container of
     * @return The amount of XP stored in the bottle, ignores ItemStack amount
     */
    public static int getXPQuantityFromStoreBottle(ItemStack bottle) {
        PersistentDataContainer container = bottle.getItemMeta().getPersistentDataContainer();

        final Integer storedAmount = container.get(STORE_KEY, PersistentDataType.INTEGER);
        if (storedAmount != null) {
            return storedAmount;
        }

        final Integer legacyAmount = NBT.get(bottle, nbti -> {
            return nbti.getInteger(BOTTLED_EXP_TAG);
        });

        if (legacyAmount != null) {
            return legacyAmount;
        }

        return 0;
    }

    public static byte shouldThrowStoreBottles(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();

        if (container.has(STORE_KEY))
            return container.get(STORE_KEY, PersistentDataType.BYTE);

        return 1;
    }

    public static String getPrettyNumber(int number) {
        return String.format("%,d", number);
    }

    private static List<Component> getStoreLoreComponent(int amount) {
        Component component = Component.text("Stored XP: ", NamedTextColor.DARK_GRAY)
                .append(Component.text(BottleUtil.getPrettyNumber(amount), TextColor.color(0x5096AA)));

        return Collections.singletonList(component);
    }
}
