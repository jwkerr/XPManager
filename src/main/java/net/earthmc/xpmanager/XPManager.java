package net.earthmc.xpmanager;

import net.earthmc.xpmanager.command.BottleAdminCommand;
import net.earthmc.xpmanager.command.BottleCommand;
import net.earthmc.xpmanager.listener.ExpBottleListener;
import net.earthmc.xpmanager.listener.ProjectileLaunchListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class XPManager extends JavaPlugin {

    public static Plugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        initCommands();
        initListeners();

        getLogger().info("XPManager enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("XPManager disabled");
    }

    private void initCommands() {
        getCommand("bottle").setExecutor(new BottleCommand());
        getCommand("bottleadmin").setExecutor(new BottleAdminCommand());
    }

    private void initListeners() {
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new ExpBottleListener(), this);
        pm.registerEvents(new ProjectileLaunchListener(), this);
    }
}
