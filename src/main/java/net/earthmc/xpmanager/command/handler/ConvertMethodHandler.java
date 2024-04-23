package net.earthmc.xpmanager.command.handler;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import net.earthmc.xpmanager.object.MethodHandler;
import net.earthmc.xpmanager.util.BottleUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ConvertMethodHandler extends MethodHandler {

    public ConvertMethodHandler(Player player) {
        super(player);
    }

    @Override
    public void handleMethod() {
        int numXP = 0;
        int numBottles = 0;

        PlayerInventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null) continue;

            if (itemStack.getType().equals(Material.EXPERIENCE_BOTTLE)) {
                int amount = itemStack.getAmount();
                if (BottleUtil.isItemStoreBottle(itemStack)) {
                    numXP += BottleUtil.getXPQuantityFromStoreBottle(itemStack) * amount;
                } else {
                    numXP += amount * 10;
                }

                numBottles += amount;
                inventory.setItem(i, null);
            }
        }

        if (numBottles == 0) {
            XPManagerMessaging.sendErrorMessage(player, "There are no valid experience bottles in your inventory");
            return;
        }

        StoreMethodHandler.givePlayerStoreBottleQuantity(player, numXP, 1);
        XPManagerMessaging.sendSuccessMessage(player, "Successfully converted " + BottleUtil.getPrettyNumber(numBottles) + " bottles to a store bottle containing " + BottleUtil.getPrettyNumber(numXP) + " XP");
    }
}
