package pl.bullcube.DropableSpawners.ULTUX;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class Events implements Listener {


    @EventHandler
    public void BlockBreakEvent (BlockBreakEvent event){
        if (event.getPlayer().getGameMode().equals(GameMode.SURVIVAL) && event.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)
           && event.getBlock().getType().equals(Material.SPAWNER)){
            event.setExpToDrop(0);
            CreatureSpawner spawner =  (CreatureSpawner) event.getBlock().getState();
            String entity = spawner.toString();
            ItemStack spawnerItem = new ItemStack(Material.SPAWNER);
            ItemMeta meta = spawnerItem.getItemMeta();
            meta.setDisplayName(ChatColor.BLUE+spawner.getSpawnedType().toString());
            spawnerItem.setItemMeta(meta);
            event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), spawnerItem);
        }
    }
    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event){
        if (event.getBlockPlaced().getType().equals(Material.SPAWNER) && event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)){
            String name = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName();
            if (name.contains("§9")) {
                try {
                    EntityType entity = EntityType.valueOf(name.split("§9")[1]);
                    CreatureSpawner spawner = ((CreatureSpawner) event.getBlockPlaced().getState());
                    spawner.setSpawnedType(entity);
                    spawner.setMaxNearbyEntities(15);
                    spawner.setMinSpawnDelay(80);
                    spawner.setMaxSpawnDelay(120);
                    spawner.setRequiredPlayerRange(32);
                    spawner.setDelay(-1);
                    spawner.setSpawnCount(3);
                    spawner.update();
                } catch (IllegalArgumentException e) {

                    Bukkit.getServer().getConsoleSender().sendMessage(name);
                    e.printStackTrace();
                }
            }
            else if (!event.getPlayer().isOp()) {
                event.getPlayer().sendMessage(ChatColor.RED+"NIE OSZUKUJ BO BAN!");
            }

        }
    }
}
