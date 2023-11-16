package net.earthmc.xpmanager.command;

import net.earthmc.xpmanager.XPManager;
import net.earthmc.xpmanager.api.XPManagerMessaging;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BottleToggle {
    public static void parseBottleToggle(Player player, String[] args) {
        if (args.length < 2) {
            XPManagerMessaging.sendErrorMessage(player, "No second argument was specified");
            return;
        }

        String method = args[1];
        switch (method) {
            case "thrown":
                if (!player.hasPermission("xpmanager.command.bottle.toggle.thrown")) {
                    XPManagerMessaging.sendErrorMessage(player, "You do not have permission to perform this action");
                    return;
                }

                toggleThrowableStoreBottles(player);
                break;
            default:
                XPManagerMessaging.sendErrorMessage(player, "Invalid second argument: " + method + ". Valid second arguments are {thrown}");
        }
    }

    private static void toggleThrowableStoreBottles(Player player) {
        PersistentDataContainer container = player.getPersistentDataContainer();

        boolean shouldThrowStoreBottles = true;
        NamespacedKey key = new NamespacedKey(XPManager.INSTANCE, "xpmanager-should-throw-store-bottles");
        if (container.has(key)) {
            shouldThrowStoreBottles = container.get(key, PersistentDataType.BOOLEAN);
        }

        container.set(key, PersistentDataType.BOOLEAN, !shouldThrowStoreBottles);

        if (shouldThrowStoreBottles) {
            XPManagerMessaging.sendSuccessMessage(player, "Store bottles will now be instantly used on consumption");
        } else {
            XPManagerMessaging.sendSuccessMessage(player, "Store bottles will now be thrown on consumption");
        }
    }
}
