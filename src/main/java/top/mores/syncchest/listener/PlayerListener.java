package top.mores.syncchest.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryView;
import top.mores.syncchest.gui.ChestGUI;
import top.mores.syncchest.utils.FileUtil;

import java.util.Objects;

public class PlayerListener implements Listener {

    FileUtil fileUtil = new FileUtil();
    ChestGUI chestGUI = new ChestGUI();

    @EventHandler
    public void onPlayerOpenChest(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (fileUtil.onWorld(player.getWorld().getName()) &&
                event.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
                Objects.requireNonNull(event.getClickedBlock()).getType().equals(Material.CHEST)
        ) {
            Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
            if (location.equals(fileUtil.chestLocation())) {
                event.setCancelled(true);
                chestGUI.createChest(player);
            }
        }
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent event) {
        Entity entity = event.getPlayer();
        if (entity instanceof Player player) {
            InventoryView view = event.getView();
            if (ChatColor.stripColor(view.getTitle()).equals(String.format(" %s 的跨服箱子", player.getName()))) {
                fileUtil.savePlayerData(player, event.getInventory());
            }
        }
    }
}
