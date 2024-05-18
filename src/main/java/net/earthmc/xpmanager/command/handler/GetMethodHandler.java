package net.earthmc.xpmanager.command.handler;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import net.earthmc.xpmanager.object.MethodHandler;
import net.earthmc.xpmanager.util.BottleUtil;
import net.earthmc.xpmanager.util.ExperienceUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class GetMethodHandler extends MethodHandler {

    private final String[] args;
    private final boolean isAdmin;

    public GetMethodHandler(Player player, String[] args, boolean isAdmin) {
        super(player);

        this.args = args;
        this.isAdmin = isAdmin;
    }

    @Override
    public void handleMethod() {
        if (args.length < 2) {
            XPManagerMessaging.sendErrorMessage(player, "Command usage: /bottle get 196");
            return;
        }

        int quantity;
        if (args[1].equals("max") && !isAdmin) {
            quantity = (int) Math.floor(ExperienceUtil.getTotalXP(player) / 10.0);
        } else {
            try {
                quantity = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                XPManagerMessaging.sendErrorMessage(player, "Specified quantity is not a valid number");
                return;
            }
        }

        if (quantity < 1) {
            XPManagerMessaging.sendErrorMessage(player, "Specified quantity must be greater than 0");
            return;
        }

        if (isAdmin) {
            givePlayerStandardBottleQuantity(player, quantity);

            XPManagerMessaging.sendSuccessMessage(player, "Successfully created " + BottleUtil.getPrettyNumber(quantity) + " bottles");
            return;
        }

        int xpAmount = quantity * 10;
        int currentXP = ExperienceUtil.getTotalXP(player);
        if (xpAmount > currentXP || xpAmount <= 0) {
            XPManagerMessaging.sendErrorMessage(player, "You do not have enough experience");
            return;
        }

        ExperienceUtil.changeXP(player, -xpAmount);

        givePlayerStandardBottleQuantity(player, quantity);
        XPManagerMessaging.sendSuccessMessage(player, "Successfully created " + BottleUtil.getPrettyNumber(quantity) + " bottles");
    }

    private static void givePlayerStandardBottleQuantity(Player player, int quantity) {
        ItemStack bottles = new ItemStack(Material.EXPERIENCE_BOTTLE, quantity);

        HashMap<Integer, ItemStack> remainingItems = player.getInventory().addItem(bottles);
        if (remainingItems.isEmpty())
            return;

        for (ItemStack item : remainingItems.values()) {
            player.getWorld().dropItem(player.getLocation(), item);
        }
    }
}
