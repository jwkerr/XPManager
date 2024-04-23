package net.earthmc.xpmanager.listener;

import net.earthmc.xpmanager.util.BottleUtil;
import net.earthmc.xpmanager.util.ExperienceUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Random;

public class ProjectileLaunchListener implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player player))
            return;

        if (!(event.getEntity() instanceof ThrownExpBottle thrownBottle))
            return;

        if (BottleUtil.shouldThrowStoreBottles(player) == 1)
            return;

        ItemStack bottle = thrownBottle.getItem();
        if (!BottleUtil.isItemStoreBottle(bottle))
            return;

        event.setCancelled(true);

        PlayerInventory inventory = player.getInventory();
        ItemStack selectedItem = inventory.getItemInMainHand();
        selectedItem.setAmount(selectedItem.getAmount() - 1);
        inventory.setItemInMainHand(selectedItem);

        int amount = BottleUtil.getXPQuantityFromStoreBottle(bottle);
        ExperienceUtil.changeXP(player, amount);

        Random random = new Random();
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1f, random.nextFloat(0.55f, 1.25f));
    }
}
