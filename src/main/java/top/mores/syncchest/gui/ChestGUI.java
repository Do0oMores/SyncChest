package top.mores.syncchest.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ChestGUI {

    private String name;

    public String getName() {
        return this.name;
    }

    /**
     * 创建GUI
     *
     * @param player 打开GUI的玩家
     */
    public void createChest(Player player) {
        Inventory chest = Bukkit.createInventory(player, 9 * 9, ChatColor.RED + name);
        player.openInventory(chest);
    }
}
