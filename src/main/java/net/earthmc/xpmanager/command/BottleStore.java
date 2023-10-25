package net.earthmc.xpmanager.command;

import net.earthmc.xpmanager.XPManager;
import net.earthmc.xpmanager.api.XPManagerMessaging;
import net.earthmc.xpmanager.util.ExperienceUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class BottleStore {
    public static void parseBottleStore(Player player, String[] args, boolean isAdmin) {
        if (args.length < 2) {
            XPManagerMessaging.sendErrorMessage(player, "Command usage: /bottle store 1395 64");
            return;
        }

        int amount;
        if (args[1].equals("max") && !isAdmin) {
            amount = ExperienceUtil.getTotalXP(player);
        } else {
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                XPManagerMessaging.sendErrorMessage(player, "Specified XP amount is not a valid number");
                return;
            }
        }

        Integer quantity = null;
        if (args.length > 2) {
            try {
                quantity = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                XPManagerMessaging.sendErrorMessage(player, "Specified bottle quantity is invalid");
                return;
            }
        }

        if (amount < 1) {
            XPManagerMessaging.sendErrorMessage(player, "Specified XP amount must be greater than 0");
            return;
        }

        if (quantity == null)
            quantity = 1;

        if (isAdmin) {
            givePlayerStoreBottleQuantity(player, amount, quantity);
            return;
        }

        int currentXP = ExperienceUtil.getTotalXP(player);
        if (amount * quantity > currentXP) {
            XPManagerMessaging.sendErrorMessage(player, "You do not have enough experience");
            return;
        }

        ExperienceUtil.changeXP(player, -(amount * quantity));

        givePlayerStoreBottleQuantity(player, amount, quantity);
        XPManagerMessaging.sendSuccessMessage(player, "Successfully stored " + (amount * quantity) + " XP");
    }

    public static void givePlayerStoreBottleQuantity(Player player, int xpAmount, int quantity) {
        ItemStack bottles = new ItemStack(Material.EXPERIENCE_BOTTLE, quantity);

        ItemMeta meta = bottles.getItemMeta();

        NamespacedKey key = new NamespacedKey(XPManager.INSTANCE, "xpmanager-store-amount");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, xpAmount);
        bottles.setItemMeta(meta);

        bottles.lore(getStoreLoreComponent(xpAmount));

        HashMap<Integer, ItemStack> remainingItems = player.getInventory().addItem(bottles);
        if (remainingItems.isEmpty()) {
            return;
        }

        for (ItemStack item : remainingItems.values()) {
            player.getWorld().dropItem(player.getLocation(), item);
        }
    }

    private static List<Component> getStoreLoreComponent(int amount) {
        Component component = Component.text()
                .content("Stored XP: ").color(NamedTextColor.DARK_GRAY)
                .append(Component.text(amount).color(TextColor.color(0x5096AA)))
                .build();

        return Collections.singletonList(component);
    }
}
