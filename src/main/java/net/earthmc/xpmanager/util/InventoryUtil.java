package net.earthmc.xpmanager.util;

import net.earthmc.xpmanager.XPManager;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InventoryUtil {
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
}
