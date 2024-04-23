package net.earthmc.xpmanager.util;

import net.earthmc.xpmanager.XPManager;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BottleUtil {

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

        net.minecraft.world.item.ItemStack itemStack = CraftItemStack.asNMSCopy(item);

        CompoundTag tag = itemStack.getTag();
        if (tag == null) {
            return false;
        }

        return tag.contains("StoredBottledExp");
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

        net.minecraft.world.item.ItemStack itemStack = CraftItemStack.asNMSCopy(bottle);

        CompoundTag tag = itemStack.getTag();
        if (tag != null) {
            return tag.getInt("StoredBottledExp");
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
}
