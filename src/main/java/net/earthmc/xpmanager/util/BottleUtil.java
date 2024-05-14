package net.earthmc.xpmanager.util;

import de.tr7zw.changeme.nbtapi.NBTItem;
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

    public static void givePlayerStoreBottleQuantity(Player player, int xpAmount, int quantity) {
        ItemStack bottles = new ItemStack(Material.EXPERIENCE_BOTTLE, quantity);

        ItemMeta meta = bottles.getItemMeta();

        NamespacedKey key = new NamespacedKey(XPManager.INSTANCE, "xpmanager-store-amount");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, xpAmount);
        bottles.setItemMeta(meta);

        bottles.lore(getStoreLoreComponent(xpAmount));

        HashMap<Integer, ItemStack> remainingItems = player.getInventory().addItem(bottles);
        if (remainingItems.isEmpty()) {
            return;
        }

        for (ItemStack item : remainingItems.values()) {
            player.getWorld().dropItem(player.getLocation(), item);
        }
    }

    public static boolean isItemStoreBottle(ItemStack item) {
        if (item.getType() != Material.EXPERIENCE_BOTTLE) {
            return false;
        }

        NamespacedKey key = new NamespacedKey(XPManager.INSTANCE, "xpmanager-store-amount");

        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(key, PersistentDataType.INTEGER)) {
            return true;
        }

        NBTItem nbti = new NBTItem(item);
        return nbti.hasTag(BOTTLED_EXP_TAG);
    }

    /**
     *
     * @param bottle Experience bottle to check the store data container of
     * @return The amount of XP stored in the bottle, ignores ItemStack amount
     */
    public static int getXPQuantityFromStoreBottle(ItemStack bottle) {
        NamespacedKey key = new NamespacedKey(XPManager.INSTANCE, "xpmanager-store-amount");
        PersistentDataContainer container = bottle.getItemMeta().getPersistentDataContainer();

        if (container.has(key)) {
            return container.get(key, PersistentDataType.INTEGER);
        }

        NBTItem nbti = new NBTItem(bottle);
        if (nbti.hasTag(BOTTLED_EXP_TAG)) {
            return nbti.getInteger(BOTTLED_EXP_TAG);
        }

        return 0;
    }

    public static byte shouldThrowStoreBottles(Player player) {
        NamespacedKey key = new NamespacedKey(XPManager.INSTANCE, "xpmanager-should-throw-store-bottles");
        PersistentDataContainer container = player.getPersistentDataContainer();

        if (container.has(key)) {
            return container.get(key, PersistentDataType.BYTE);
        }

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
