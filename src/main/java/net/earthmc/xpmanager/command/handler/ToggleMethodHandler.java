package net.earthmc.xpmanager.command.handler;

import net.earthmc.xpmanager.XPManager;
import net.earthmc.xpmanager.api.XPManagerMessaging;
import net.earthmc.xpmanager.object.MethodHandler;
import net.earthmc.xpmanager.util.CommandUtil;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ToggleMethodHandler extends MethodHandler {

    private final String[] args;

    public ToggleMethodHandler(Player player, String[] args) {
        super(player);

        this.args = args;
    }

    @Override
    public void handleMethod() {
        if (args.length < 2) {
            XPManagerMessaging.sendErrorMessage(player, "No second argument was specified");
            return;
        }

        String method = args[1];
        switch (method) {
            case "thrown":
                if (!CommandUtil.hasPermissionOrError(player, "xpmanager.command.bottle.toggle.thrown"))
                    return;

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
            shouldThrowStoreBottles = container.get(key, PersistentDataType.BYTE) >= 1;
        }

        container.set(key, PersistentDataType.BYTE, shouldThrowStoreBottles ? (byte) 0 : (byte) 1);

        if (shouldThrowStoreBottles) {
            XPManagerMessaging.sendSuccessMessage(player, "Store bottles will now be instantly used on consumption");
        } else {
            XPManagerMessaging.sendSuccessMessage(player, "Store bottles will now be thrown on consumption");
        }
    }
}
