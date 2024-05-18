package net.earthmc.xpmanager.command.handler;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import net.earthmc.xpmanager.object.MethodHandler;
import net.earthmc.xpmanager.util.BottleUtil;
import net.earthmc.xpmanager.util.CommandUtil;
import net.earthmc.xpmanager.util.ExperienceUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StatsMethodHandler extends MethodHandler {

    private final String[] args;

    public StatsMethodHandler(Player player, String[] args) {
        super(player);

        this.args = args;
    }

    @Override
    public void handleMethod() {
        Player playerToCheck;
        if (args.length < 2) {
            playerToCheck = player;
        } else {
            if (!CommandUtil.hasPermissionOrError(player, "xpmanager.command.bottle.stats.other"))
                return;

            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                XPManagerMessaging.sendErrorMessage(this.player, "The specified player does not exist");
                return;
            }

            playerToCheck = player;
        }

        int nextLevel = playerToCheck.getLevel() + 1;
        int totalCurrentXP = ExperienceUtil.getTotalXP(playerToCheck);
        int totalGoalLevelXP = ExperienceUtil.getXPFromLevel(nextLevel);

        int remainingXP = totalGoalLevelXP - totalCurrentXP;
        int bottlesToGoal = (int) Math.ceil(remainingXP / 10.0);

        Component component = getStatsComponent(playerToCheck, totalCurrentXP, remainingXP, bottlesToGoal);

        player.sendMessage(component);
    }

    private Component getStatsComponent(Player playerToCheck, int totalCurrentXP, int remainingXP, int bottlesToGoal) {
        String username = playerToCheck.getName();

        return Component.text("------------ ", NamedTextColor.DARK_GRAY)
                .append(Component.text(username + "'s stats", TextColor.color(0x5096AA))
                .append(Component.text(" ------------", NamedTextColor.DARK_GRAY))
                .appendNewline()
                .append(Component.text("Total experience", TextColor.color(0x5096AA))
                .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                .append(Component.text(BottleUtil.getPrettyNumber(totalCurrentXP) + " XP or " + BottleUtil.getPrettyNumber(playerToCheck.getLevel()) + " levels", NamedTextColor.GREEN))
                .appendNewline()
                .append(Component.text("Experience until next level", TextColor.color(0x5096aa)))
                .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                .append(Component.text(BottleUtil.getPrettyNumber(remainingXP) + " XP or " + BottleUtil.getPrettyNumber(bottlesToGoal) + " bottles", NamedTextColor.GREEN))));
    }
}
