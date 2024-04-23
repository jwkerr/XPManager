package net.earthmc.xpmanager.command.handler;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import net.earthmc.xpmanager.object.MethodHandler;
import net.earthmc.xpmanager.util.BottleUtil;
import net.earthmc.xpmanager.util.ExperienceUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class MendMethodHandler extends MethodHandler {

    public MendMethodHandler(Player player) {
        super(player);
    }

    @Override
    public void handleMethod() {
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        ItemMeta meta = itemInHand.getItemMeta();

        if (!(meta instanceof Damageable damageable)) {
            XPManagerMessaging.sendErrorMessage(player, "You are not holding a valid item");
            return;
        }

        int damage = damageable.getDamage();
        if (!damageable.hasDamage() || damage == 1) {
            XPManagerMessaging.sendErrorMessage(player, "This item is not damaged");
            return;
        }

        int amount = (damage / 2) + 1; // Add one extra to get the last bit
        if (!(ExperienceUtil.getTotalXP(player) >= amount)) {
            XPManagerMessaging.sendErrorMessage(player, "You do not have enough experience");
            return;
        }

        StoreMethodHandler.givePlayerStoreBottleQuantity(player, amount, 1);
        XPManagerMessaging.sendSuccessMessage(player, "Successfully stored " + BottleUtil.getPrettyNumber(amount) + " XP");
    }
}
