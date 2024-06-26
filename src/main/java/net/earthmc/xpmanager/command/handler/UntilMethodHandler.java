package net.earthmc.xpmanager.command.handler;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import net.earthmc.xpmanager.object.MethodHandler;
import net.earthmc.xpmanager.util.BottleUtil;
import net.earthmc.xpmanager.util.ExperienceUtil;
import org.bukkit.entity.Player;

public class UntilMethodHandler extends MethodHandler {

    private final String[] args;

    public UntilMethodHandler(Player player, String[] args) {
        super(player);

        this.args = args;
    }

    @Override
    public void handleMethod() {
        if (args.length < 2) {
            XPManagerMessaging.sendErrorMessage(player, "Command usage: /bottle until 155");
            return;
        }

        int goalLevel;
        try {
            goalLevel = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            XPManagerMessaging.sendErrorMessage(player, "Specified level is not a valid number");
            return;
        }

        if (goalLevel < 1) {
            XPManagerMessaging.sendErrorMessage(player, "Specified level must be greater than 0");
            return;
        }

        int currentLevel = player.getLevel();
        if (goalLevel <= currentLevel) {
            XPManagerMessaging.sendErrorMessage(player, "Specified level must be greater than your current level");
            return;
        }

        int totalCurrentXP = ExperienceUtil.getTotalXP(player);
        int totalGoalLevelXP = ExperienceUtil.getXPFromLevel(goalLevel);

        int remainingXP = totalGoalLevelXP - totalCurrentXP;
        int bottlesToGoal = (int) Math.ceil(remainingXP / 10.0);

        XPManagerMessaging.sendSuccessMessage(player, "You need " + BottleUtil.getPrettyNumber(remainingXP) + " XP or " + BottleUtil.getPrettyNumber(bottlesToGoal) + " bottles to reach level " + BottleUtil.getPrettyNumber(goalLevel));
    }
}
