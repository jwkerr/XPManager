package net.earthmc.xpmanager.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XPManagerMessaging {
    private static Component getPrefixComponent() {
        return Component.text()
                .content("XPM").color(TextColor.color(0x5096AA))
                .append(Component.text(" » ").color(NamedTextColor.DARK_GRAY))
                .build();
    }

    public static void sendSuccessMessage(Player player, String message) {
        player.sendMessage(getPrefixComponent()
                .append(Component.text(message).color(NamedTextColor.GREEN).decorate(TextDecoration.ITALIC)));
    }

    public static void sendErrorMessage(Player player, String message) {
        player.sendMessage(getPrefixComponent()
                .append(Component.text(message).color(NamedTextColor.RED).decorate(TextDecoration.ITALIC)));
    }

    public static void sendErrorMessage(CommandSender sender, String message) {
        sender.sendMessage(getPrefixComponent()
                .append(Component.text(message).color(NamedTextColor.RED).decorate(TextDecoration.ITALIC)));
    }
}
