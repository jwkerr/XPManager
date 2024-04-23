package net.earthmc.xpmanager.command;

import net.earthmc.xpmanager.api.XPManagerMessaging;
import net.earthmc.xpmanager.command.handler.GetMethodHandler;
import net.earthmc.xpmanager.command.handler.StoreMethodHandler;
import net.earthmc.xpmanager.util.CommandUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

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
                if (!CommandUtil.hasPermissionOrError(player, "xpmanager.command.bottleadmin.get"))
                    return true;

                GetMethodHandler gmh = new GetMethodHandler(player, args, true);
                gmh.handleMethod();
                break;
            case "store":
                if (!CommandUtil.hasPermissionOrError(player, "xpmanager.command.bottleadmin.store"))
                    return true;

                StoreMethodHandler smh = new StoreMethodHandler(player, args, true);
                smh.handleMethod();
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
                return Stream.of("get", "store")
                        .filter(s -> s.startsWith(args[0].toLowerCase()))
                        .toList();

            case 2:
                if (args[0].equals("get")) {
                    return Stream.of("{quantity}")
                            .filter(s -> s.startsWith(args[1].toLowerCase()))
                            .toList();
                }

                if (args[0].equals("store")) {
                    return Stream.of("{amount}")
                            .filter(s -> s.startsWith(args[1].toLowerCase()))
                            .toList();
                }

            case 3:
                if (args[0].equals("store")) {
                    return Stream.of("{quantity}")
                            .filter(s -> s.startsWith(args[2].toLowerCase()))
                            .toList();
                }
        }

        return null;
    }
}
