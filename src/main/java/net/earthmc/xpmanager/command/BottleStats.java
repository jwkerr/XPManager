package net.earthmc.xpmanager.command;

import net.earthmc.xpmanager.util.ExperienceUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;

public class BottleStats {
    public static void parseBottleStats(Player player) {
        int nextLevel = player.getLevel() + 1;
        int totalCurrentXP = ExperienceUtil.getTotalXP(player);
        int totalGoalLevelXP = ExperienceUtil.getXPFromLevel(nextLevel);

        int remainingXP = totalGoalLevelXP - totalCurrentXP;
        int bottlesToGoal = (int) Math.ceil(remainingXP / 10.0);

        Component component = Component.empty()
                .append(Component.text("Total experience", TextColor.color(0x5096AA)))
                .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                .append(Component.text(totalCurrentXP + " XP or " + player.getLevel() + " levels\n", NamedTextColor.GREEN))
                .append(Component.text("Experience until next level", TextColor.color(0x5096aa)))
                .append(Component.text(": ", NamedTextColor.DARK_GRAY))
                .append(Component.text(remainingXP + " XP or " + bottlesToGoal + " bottles", NamedTextColor.GREEN));

        player.sendMessage(component);
    }
}
