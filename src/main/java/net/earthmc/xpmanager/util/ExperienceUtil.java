package net.earthmc.xpmanager.util;

import org.bukkit.entity.Player;

public class ExperienceUtil {

    /**
     * Calculate a player's total experience based on level and progress to next.
     *
     * @param player the Player
     * @return the amount of experience the Player has
     *
     * @see <a href=https://minecraft.wiki/w/Experience#Leveling_up>Experience#Leveling_up</a>
     */
    public static int getTotalXP(Player player) {
        return getXPFromLevel(player.getLevel()) + Math.round(getXPToNext(player.getLevel()) * player.getExp());
    }

    /**
     * Calculate total experience based on level.
     *
     * @param level the level
     * @return the total experience calculated
     *
     * @see <a href=https://minecraft.wiki/w/Experience#Leveling_up>Experience#Leveling_up</a>
     */
    public static int getXPFromLevel(int level) {
        if (level > 30) {
            return (int) (4.5 * level * level - 162.5 * level + 2220);
        }

        if (level > 15) {
            return (int) (2.5 * level * level - 40.5 * level + 360);
        }

        return level * level + 6 * level;
    }

    /**
     * Calculate level (including progress to next level) based on total experience.
     *
     * @param xp the total experience
     * @return the level calculated
     */
    public static double getLevelFromXP(long xp) {
        int level = getIntLevelFromXP(xp);

        // Get remaining XP progressing towards next level. Cast to float for next bit of math.
        float remainder = xp - (float) getXPFromLevel(level);

        // Get level progress with float precision.
        float progress = remainder / getXPToNext(level);

        // Slap both numbers together and call it a day. While it shouldn't be possible for progress
        // to be an invalid value (value < 0 || 1 <= value)
        return ((double) level) + progress;
    }

    /**
     * Calculate level based on total experience.
     *
     * @param xp the total experience
     * @return the level calculated
     */
    public static int getIntLevelFromXP(long xp) {
        if (xp > 1395) {
            return (int) ((Math.sqrt(72 * xp - 54215D) + 325) / 18);
        }
        if (xp > 315) {
            return (int) (Math.sqrt(40 * xp - 7839D) / 10 + 8.1);
        }
        if (xp > 0) {
            return (int) (Math.sqrt(xp + 9D) - 3);
        }
        return 0;
    }

    /**
     * Get the total amount of experience required to progress to the next level.
     *
     * @param level the current level
     *
     * @see <a href=https://minecraft.wiki/w/Experience#Leveling_up>Experience#Leveling_up</a>
     */
    private static int getXPToNext(int level) {
        if (level >= 30) {
            // Simplified formula. Internal: 112 + (level - 30) * 9
            return level * 9 - 158;
        }

        if (level >= 15) {
            // Simplified formula. Internal: 37 + (level - 15) * 5
            return level * 5 - 38;
        }

        // Internal: 7 + level * 2
        return level * 2 + 7;
    }

    /**
     * Change a Player's experience.
     *
     * <p>This method is preferred over {@link Player#giveExp(int)}.
     * <br>In older versions the method does not take differences in exp per level into account.
     * This leads to overlevelling when granting players large amounts of experience.
     * <br>In modern versions, while differing amounts of experience per level are accounted for, the
     * approach used is loop-heavy and requires an excessive number of calculations, which makes it
     * quite slow.
     *
     * @param player the Player affected
     * @param xp the amount of experience to add or remove
     */
    public static void changeXP(Player player, int xp) {
        xp += getTotalXP(player);

        if (xp < 0) {
            xp = 0;
        }

        double levelAndXP = getLevelFromXP(xp);
        int level = (int) levelAndXP;
        player.setLevel(level);
        player.setExp((float) (levelAndXP - level));
    }
}
