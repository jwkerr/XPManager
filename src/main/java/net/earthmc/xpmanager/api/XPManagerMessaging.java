package net.earthmc.xpmanager.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;

public class XPManagerMessaging {

    private static final Component PREFIX_COMPONENT = Component.text("XPM", TextColor.color(0x5096AA))
            .append(Component.text(" » ").color(NamedTextColor.DARK_GRAY));

    public static void sendSuccessMessage(CommandSender sender, String message) {
        sendMessage(sender, Component.text(message, NamedTextColor.GREEN));
    }

    public static void sendErrorMessage(CommandSender sender, String message) {
        sendMessage(sender, Component.text(message, NamedTextColor.RED));
    }

    private static void sendMessage(CommandSender sender, Component message) {
        sender.sendMessage(PREFIX_COMPONENT.append(message));
    }
}
