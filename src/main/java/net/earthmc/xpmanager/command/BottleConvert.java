package net.earthmc.xpmanager.command;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import net.earthmc.xpmanager.util.InventoryUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BottleConvert {
    public static void convertStandardBottlesToStoreBottle(Player player) {
        int numBottles = 0;

        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null) {
                continue;
            }

            if (itemStack.getType().equals(Material.EXPERIENCE_BOTTLE) && !InventoryUtil.isItemStoreBottle(itemStack)) {
                numBottles += itemStack.getAmount();
                inventory.setItem(i, null);
            }
        }

        if (numBottles == 0) {
            XPManagerMessaging.sendErrorMessage(player, "There are no valid experience bottles in your inventory");
            return;
        }

        int numXP = numBottles * 10;

        BottleStore.givePlayerStoreBottleQuantity(player, numXP, 1);
        XPManagerMessaging.sendSuccessMessage(player, "Successfully converted " + numBottles + " bottles to a store bottle containing " + numXP + " XP");
    }
}
