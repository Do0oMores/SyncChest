package top.mores.syncchest;

import org.bukkit.plugin.java.JavaPlugin;
import top.mores.syncchest.listener.PlayerListener;

public final class SyncChest extends JavaPlugin {

    private static SyncChest instance;

    @Override
    public void onEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
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
