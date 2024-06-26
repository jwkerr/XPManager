package net.earthmc.xpmanager.command.handler;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import net.earthmc.xpmanager.object.MethodHandler;
import net.earthmc.xpmanager.util.BottleUtil;
import net.earthmc.xpmanager.util.ExperienceUtil;
import org.bukkit.entity.Player;

public class StoreMethodHandler extends MethodHandler {

    private final String[] args;
    private final boolean isAdmin;

    public StoreMethodHandler(Player player, String[] args, boolean isAdmin) {
        super(player);

        this.args = args;
        this.isAdmin = isAdmin;
    }

    @Override
    public void handleMethod() {
        if (args.length < 2) {
            XPManagerMessaging.sendErrorMessage(player, "Command usage: /bottle store 1395 64");
            return;
        }

        int currentXP = ExperienceUtil.getTotalXP(player);

        int amount;
        if (args[1].equals("max") && !isAdmin) {
            amount = currentXP;
        } else {
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                XPManagerMessaging.sendErrorMessage(player, "Specified XP amount is not a valid number");
                return;
            }
        }

        if (amount < 1) {
            XPManagerMessaging.sendErrorMessage(player, "Specified XP amount must be greater than 0");
            return;
        }

        if (!isAdmin && amount > currentXP) {
            XPManagerMessaging.sendErrorMessage(player, "Specified XP amount must be less than or equal to your current XP");
            return;
        }

        Integer quantity = null;
        if (args.length > 2) {
            if (args[2].equals("max") && !isAdmin) {
                quantity = currentXP / amount;
            } else {
                try {
                    quantity = Integer.parseInt(args[2]);

                    if (quantity < 1) {
                        XPManagerMessaging.sendErrorMessage(player, "Specified bottle quantity must be greater than 0");
                        return;
                    }
                } catch (NumberFormatException e) {
                    XPManagerMessaging.sendErrorMessage(player, "Specified bottle quantity is invalid");
                    return;
                }
            }
        }

        if (quantity == null || quantity < 1)
            quantity = 1;

        int totalXP;
        try {
            totalXP = Math.multiplyExact(amount, quantity);
        } catch (ArithmeticException e) {
            XPManagerMessaging.sendErrorMessage(player, "Your input caused an integer overflow and was not accepted");
            return;
        }

        if (isAdmin) {
            BottleUtil.givePlayerStoreBottleQuantity(player, amount, quantity);

            XPManagerMessaging.sendSuccessMessage(player, "Successfully stored " + BottleUtil.getPrettyNumber(totalXP) + " XP");
            return;
        }

        if (totalXP > currentXP) {
            XPManagerMessaging.sendErrorMessage(player, "You do not have enough experience");
            return;
        }

        ExperienceUtil.changeXP(player, -totalXP);

        BottleUtil.givePlayerStoreBottleQuantity(player, amount, quantity);
        XPManagerMessaging.sendSuccessMessage(player, "Successfully stored " + BottleUtil.getPrettyNumber(totalXP) + " XP");
    }
}
