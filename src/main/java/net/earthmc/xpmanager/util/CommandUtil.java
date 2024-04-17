package net.earthmc.xpmanager.util;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import org.bukkit.entity.Player;

public class CommandUtil {

    public static boolean hasPermissionOrError(Player player, String permission) {
        if (!player.hasPermission(permission)) {
            XPManagerMessaging.sendErrorMessage(player, "You do not have permission to perform this action");
            return false;
        }

        return true;
    }
}
