package net.earthmc.xpmanager.command.handler;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import net.earthmc.xpmanager.object.MethodHandler;
import net.earthmc.xpmanager.util.BottleUtil;
import net.earthmc.xpmanager.util.CommandUtil;
import net.earthmc.xpmanager.util.ExperienceUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MendMethodHandler extends MethodHandler {

    private final String[] args;

    public MendMethodHandler(Player player, String[] args) {
        super(player);

        this.args = args;
    }

    @Override
    public void handleMethod() {
        List<ItemStack> items = new ArrayList<>();

        int totalDamage = 0;
        if (args.length < 2) { // Player wants to repair the item they are holding
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            ItemMeta meta = itemInHand.getItemMeta();

            if (!(meta instanceof Damageable damageable) || !(damageable.hasEnchant(Enchantment.MENDING))) {
                XPManagerMessaging.sendErrorMessage(player, "You are not holding a valid item");
                return;
            }

            int damage = damageable.getDamage();
            if (damage == 0) {
                XPManagerMessaging.sendErrorMessage(player, "This item is not damaged");
                return;
            }

            totalDamage = damage;

            items.add(itemInHand);
        } else if (args[1].equalsIgnoreCase("all")) { // Player wants to repair all valid items in their inventory
            if (!CommandUtil.hasPermissionOrError(player, "xpmanager.command.bottle.mend.all"))
                return;

            for (ItemStack item : player.getInventory()) {
                if (item == null) continue;

                ItemMeta meta = item.getItemMeta();

                if (!(meta instanceof Damageable damageable) || !(damageable.hasEnchant(Enchantment.MENDING)))
                    continue;

                int damage = damageable.getDamage();
                if (damage == 0) continue;

                totalDamage += damage;

                items.add(item);
            }
        }

        if (totalDamage == 0) {
            XPManagerMessaging.sendErrorMessage(player, "None of your items are able to be mended");
            return;
        }

        int amount = (int) Math.ceil((double) totalDamage / 2); // https://minecraft.wiki/w/Mending#Usage
        if (!(ExperienceUtil.getTotalXP(player) >= amount)) {
            XPManagerMessaging.sendErrorMessage(player, "You do not have enough experience");
            return;
        }

        for (ItemStack item : items) {
            Damageable damageable = (Damageable) item.getItemMeta();
            damageable.setDamage(0);

            item.setItemMeta(damageable);
        }

        ExperienceUtil.changeXP(player, -amount);

        int numItems = items.size();
        XPManagerMessaging.sendSuccessMessage(player, "Successfully repaired " + numItems + " item/s using " + BottleUtil.getPrettyNumber(amount) + " XP");
    }
}
