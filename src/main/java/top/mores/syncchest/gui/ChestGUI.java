package top.mores.syncchest.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import top.mores.syncchest.utils.FileUtil;
import top.mores.syncchest.utils.ItemStackUtil;

import java.util.List;
import java.util.Map;

public class ChestGUI {

    FileUtil fileUtil = new FileUtil();

    /**
     * 创建GUI
     *
     * @param player 打开GUI的玩家
     */
    public void createChest(Player player) {
        String chestName = String.format(ChatColor.GREEN + " %s 的跨服箱子", player.getName());
        Inventory chest = Bukkit.createInventory(player, 9 * 9, ChatColor.RED + chestName);
        for (ItemStack item : getItems(player)) {
            chest.addItem(item);
        }
        player.openInventory(chest);
    }

    /**
     * 从文件中读取物品数据
     *
     * @param player 玩家
     * @return 物品组
     */
    public ItemStack[] getItems(Player player) {
        FileConfiguration playerData = fileUtil.getPlayerDataFile(player);
        List<Map<String, Object>> itemsList = playerData.contains("data") ?
                (List<Map<String, Object>>) playerData.getList("data") : null;
        return (itemsList != null) ? ItemStackUtil.getItemStacksFromConfig(itemsList) : new ItemStack[0];
    }
}
