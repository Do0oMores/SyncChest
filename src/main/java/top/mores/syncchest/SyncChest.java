package top.mores.syncchest;

import org.bukkit.plugin.java.JavaPlugin;
import top.mores.syncchest.listener.PlayerListener;

import java.io.File;

public final class SyncChest extends JavaPlugin {

    private static SyncChest instance;

    @Override
    public void onEnable() {
        instance = this;
        initFiles();
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

    private void initFiles(){
        File configFile = new File(getDataFolder(), "config.yml");
        File dataFolder = new File(getDataFolder(), "data");
        if(!configFile.exists()){
            boolean created = configFile.getParentFile().mkdirs();
            if(!created){
                getLogger().warning("Couldn't create config.yml");
                return;
            }
            saveResource("config.yml", false);
        }
        if(!dataFolder.exists()){
            boolean created = dataFolder.mkdirs();
            if(!created){
                getLogger().warning("Couldn't create data folder");
                return;
            }
        }
        reloadConfig();
    }
}
