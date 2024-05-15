package net.earthmc.xpmanager.listener;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import net.earthmc.xpmanager.util.BottleUtil;
import net.earthmc.xpmanager.util.ExperienceUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ProjectileLaunchListener implements Listener {

    @EventHandler
    public void onProjectileLaunch(PlayerLaunchProjectileEvent event) {
        Player player = event.getPlayer();

        if (!(event.getProjectile() instanceof ThrownExpBottle thrownBottle))
            return;

        if (BottleUtil.shouldThrowStoreBottles(player) == 1)
            return;

        ItemStack bottle = thrownBottle.getItem();
        if (!BottleUtil.isItemStoreBottle(bottle))
            return;

        event.setCancelled(true);

        ItemStack item = event.getItemStack();
        item.setAmount(item.getAmount() - 1);

        int amount = BottleUtil.getXPQuantityFromStoreBottle(bottle);
        ExperienceUtil.changeXP(player, amount);

        Random random = new Random();
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.1f, random.nextFloat(0.55f, 1.25f));
    }
}
