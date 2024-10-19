package top.mores.syncchest.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import top.mores.syncchest.SyncChest;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileUtil {

    private final FileConfiguration config = SyncChest.getInstance().getConfig();
    private final String filePath = config.getString("DataSourcesPath");
    private final String chestWorld = config.getString("ChestLocation.world");

    /**
     * 为玩家创建一个数据文件
     *
     * @param player 玩家
     */
    public void createDataFile(Player player) {
        File playerDataFile = getDataFile(player);
        try {
            String playerName = player.getName();
            if (playerDataFile.createNewFile()) {
                FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);
                playerData.set("PlayerName", playerName);
                playerData.set("data", null);
                playerData.save(playerDataFile);
                player.sendMessage(ChatColor.GREEN + String.format("已成功创建 %s 的数据文件", playerName));
            } else {
                player.sendMessage(ChatColor.RED + "创建数据文件时发生错误！");
            }
        } catch (IOException e) {
            e.fillInStackTrace();
            player.sendMessage(ChatColor.RED + "保存文件时发生错误！");
        }
    }

    public Location chestLocation() {
        World world = (chestWorld != null) ? Bukkit.getWorld(chestWorld) : null;

        // 如果世界存在，获取坐标，否则返回null
        if (world != null) {
            double chestX = config.getDouble("ChestLocation.x", 0.0);
            double chestY = config.getDouble("ChestLocation.y", 0.0);
            double chestZ = config.getDouble("ChestLocation.z", 0.0);
            return new Location(world, chestX, chestY, chestZ);
        }
        // 如果世界为空，则返回null
        return null;
    }

    public boolean onWorld(String worldName) {
        return worldName.equals(chestWorld);
    }

    public FileConfiguration getPlayerDataFile(Player player) {
        return YamlConfiguration.loadConfiguration(getDataFile(player));
    }

    public void savePlayerData(Player player, Inventory inventory) {
        List<Map<String, Object>> itemsData = Arrays.stream(inventory.getContents())
                .filter(Objects::nonNull)
                .map(ItemStackUtil::getItemStackMap)
                .toList();
        File playerDataFile = getDataFile(player);
        if (playerDataFile.exists()) {
            try {
                FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);
                playerData.set("data", itemsData);
                playerData.save(playerDataFile);
            } catch (IOException e) {
                e.fillInStackTrace();
                player.sendMessage(ChatColor.RED + "数据保存失败");
            }
        } else {
            createDataFile(player);
        }
    }

    private File getDataFile(Player player) {
        String playerUUID = player.getUniqueId().toString();
        return new File(filePath, playerUUID + ".yml");
    }
}
