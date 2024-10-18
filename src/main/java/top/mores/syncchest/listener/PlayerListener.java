package top.mores.syncchest.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import top.mores.syncchest.gui.ChestGUI;
import top.mores.syncchest.utils.FileUtil;

import java.util.Objects;

public class PlayerListener implements Listener {

    FileUtil fileUtil = new FileUtil();
    ChestGUI chestGUI = new ChestGUI();

    @EventHandler
    public void onPlayerClickBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Location location = Objects.requireNonNull(event.getClickedBlock()).getLocation();
        if (fileUtil.onWorld(player) &&
                event.getAction().equals(Action.RIGHT_CLICK_BLOCK) &&
                Objects.requireNonNull(event.getClickedBlock()).getType().equals(Material.CHEST) &&
                location.equals(fileUtil.chestLocation())
        ) {
            event.setCancelled(true);
            chestGUI.createChest(player);
        }
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent event) {
        Entity entity = event.getPlayer();
        if (entity instanceof Player player) {
            Inventory inventory = event.getInventory();
            Location location = inventory.getLocation();
            if (location != null && location.equals(fileUtil.chestLocation())) {
                fileUtil.savePlayerData(player, inventory);
            }
        }
    }
}
