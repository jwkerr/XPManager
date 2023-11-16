package net.earthmc.xpmanager.listener;

import net.earthmc.xpmanager.util.BottleUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.inventory.ItemStack;

public class ExpBottleListener implements Listener {
    @EventHandler
    public void onExpBottle(ExpBottleEvent event) {
        ItemStack bottle = event.getEntity().getItem();

        if (BottleUtil.isItemStoreBottle(bottle)) {
            int amount = BottleUtil.getXPQuantityFromStoreBottle(bottle);

            event.setExperience(amount);
            return;
        }

        event.setExperience(10);
    }
}
