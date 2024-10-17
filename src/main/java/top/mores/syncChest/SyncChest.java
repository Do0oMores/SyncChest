package top.mores.syncChest;

import org.bukkit.plugin.java.JavaPlugin;

public final class SyncChest extends JavaPlugin {

    private static SyncChest instance;

    @Override
    public void onEnable() {
        instance = this;
        System.out.println("SyncChest Plugin Enabled");
    }

    @Override
    public void onDisable() {
        System.out.println("SyncChest Plugin Disabled");
    }

    public static SyncChest getInstance() {
        return instance;
    }
}
