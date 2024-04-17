package net.earthmc.xpmanager.object;

import org.bukkit.entity.Player;

public abstract class MethodHandler {

    public final Player player;

    public MethodHandler(Player player) {
        this.player = player;
    }

    public abstract void handleMethod();
}
