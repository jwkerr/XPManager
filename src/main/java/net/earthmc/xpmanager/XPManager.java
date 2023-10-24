package net.earthmc.xpmanager;

import net.earthmc.xpmanager.command.BottleAdminCommand;
import net.earthmc.xpmanager.command.BottleCommand;
import net.earthmc.xpmanager.listener.ExpBottleListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class XPManager extends JavaPlugin {
    public static Plugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        getCommand("bottle").setExecutor(new BottleCommand());
        getCommand("bottleadmin").setExecutor(new BottleAdminCommand());

        getServer().getPluginManager().registerEvents(new ExpBottleListener(), this);

        getLogger().info("XPManager enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("XPManager disabled");
    }
}
