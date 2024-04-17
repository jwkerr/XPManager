package net.earthmc.xpmanager.command.handler;

import net.earthmc.xpmanager.object.MethodHandler;
import net.earthmc.xpmanager.util.ExperienceUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class StatsMethodHandler extends MethodHandler {

    public StatsMethodHandler(Player player) {
        super(player);
    }

    @Override
    public void handleMethod() {
        int nextLevel = player.getLevel() + 1;
        int totalCurrentXP = ExperienceUtil.getTotalXP(player);
        int totalGoalLevelXP = ExperienceUtil.getXPFromLevel(nextLevel);

        int remainingXP = totalGoalLevelXP - totalCurrentXP;
        int bottlesToGoal = (int) Math.ceil(remainingXP / 10.0);

        Component component = Component.text("Total experience", TextColor.color(0x5096AA))
                .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                .append(Component.text(totalCurrentXP + " XP or " + player.getLevel() + " levels", NamedTextColor.GREEN))
                .appendNewline()
                .append(Component.text("Experience until next level", TextColor.color(0x5096aa)))
                .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                .append(Component.text(remainingXP + " XP or " + bottlesToGoal + " bottles", NamedTextColor.GREEN));

        player.sendMessage(component);
    }
}
