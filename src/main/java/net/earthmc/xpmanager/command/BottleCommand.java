package net.earthmc.xpmanager.command;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class BottleCommand implements TabExecutor {
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
            case "convert":
                if (!player.hasPermission("xpmanager.command.bottle.convert")) {
                    XPManagerMessaging.sendErrorMessage(player, "You do not have permission to perform this action");
                    return true;
                }

                BottleConvert.convertStandardBottlesToStoreBottle(player);
                break;
            case "get":
                if (!player.hasPermission("xpmanager.command.bottle.get")) {
                    XPManagerMessaging.sendErrorMessage(player, "You do not have permission to perform this action");
                    return true;
                }

                BottleGet.parseBottleGet(player, args, false);
                break;
            case "store":
                if (!player.hasPermission("xpmanager.command.bottle.store")) {
                    XPManagerMessaging.sendErrorMessage(player, "You do not have permission to perform this action");
                    return true;
                }

                BottleStore.parseBottleStore(player, args, false);
                break;
            case "until":
                if (!player.hasPermission("xpmanager.command.bottle.until")) {
                    XPManagerMessaging.sendErrorMessage(player, "You do not have permission to perform this action");
                    return true;
                }

                BottleUntil.parseBottleUntil(player, args);
                break;
            default:
                XPManagerMessaging.sendErrorMessage(player, "Invalid first argument: " + method + ". Valid first arguments are {convert/get/store/until}");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length) {
            case 1:
                return List.of("convert", "get", "store", "until");

            case 2:
                if (args[0].equals("get")) {
                    return List.of("max", "{quantity}");
                }

                if (args[0].equals("store")) {
                    return List.of("max", "{amount}");
                }

                if (args[0].equals("until")) {
                    return List.of("{level}");
                }

            case 3:
                if (args[0].equals("store")) {
                    return List.of("max", "{quantity}");
                }
        }

        return null;
    }
}
