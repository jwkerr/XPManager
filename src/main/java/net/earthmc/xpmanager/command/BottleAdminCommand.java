package net.earthmc.xpmanager.command;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BottleAdminCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            XPManagerMessaging.sendErrorMessage(sender, "Only players can use this command");
            return true;
        }

        if (args.length == 0) {
            XPManagerMessaging.sendErrorMessage(player, "No arguments were specified");
            return true;
        }

        String method = args[0];
        switch (method) {
            case "get":
                if (!player.hasPermission("xpmanager.command.bottleadmin.get")) {
                    XPManagerMessaging.sendErrorMessage(player, "You do not have permission to perform this action");
                    return true;
                }

                BottleGet.parseBottleGet(player, args, true);
                break;
            case "store":
                if (!player.hasPermission("xpmanager.command.bottleadmin.store")) {
                    XPManagerMessaging.sendErrorMessage(player, "You do not have permission to perform this action");
                    return true;
                }

                BottleStore.parseBottleStore(player, args, true);
                break;
            default:
                XPManagerMessaging.sendErrorMessage(player, "Invalid first argument: " + method + ". Valid first arguments are {get/store}");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length) {
            case 1:
                return Arrays.asList("get", "store");

            case 2:
                if (args[0].equals("get")) {
                    return Collections.singletonList("{quantity}");
                }

                if (args[0].equals("store")) {
                    return Collections.singletonList("{amount}");
                }

            case 3:
                if (args[0].equals("store")) {
                    return Collections.singletonList("{quantity}");
                }
        }

        return null;
    }
}
