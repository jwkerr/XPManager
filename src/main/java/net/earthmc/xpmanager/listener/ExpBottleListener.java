package net.earthmc.xpmanager.listener;

import net.earthmc.xpmanager.XPManager;
import net.earthmc.xpmanager.util.InventoryUtil;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ExpBottleListener implements Listener {
    @EventHandler
    public void onExpBottle(ExpBottleEvent event) {
        ItemStack bottle = event.getEntity().getItem();

        if (InventoryUtil.isItemStoreBottle(bottle)) {
            int amount = getXPQuantityFromStoreBottle(bottle);

            event.setExperience(amount);
            return;
        }

        event.setExperience(10);
    }

    private int getXPQuantityFromStoreBottle(ItemStack bottle) {
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
}
