package pl.bullcube.DropableSpawners.ULTUX;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends JavaPlugin {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player && sender.isOp()){
            if (command.getName().equalsIgnoreCase("spawner")){
                if (args.length == 1){
                    ArrayList<String> complete = new ArrayList<>();
                    complete.add("getDelay");
                    complete.add("getMaxSpawnDelay");
                    complete.add("getMinSpawnDelay");
                    complete.add("getSpawnedType");
                    complete.add("getMaxNearbyEntities");
                    complete.add("getRequiredPlayerRange");
                    complete.add("getSpawnCount");
                    complete.add("getSpawnRange");
                    complete.add("setDelay");
                    complete.add("setMaxSpawnDelay");
                    complete.add("setMinSpawnDelay");
                    complete.add("setSpawnedType");
                    complete.add("setMaxNearbyEntities");
                    complete.add("setRequiredPlayerRange");
                    complete.add("setSpawnCount");
                    complete.add("setSpawnRange");
                    ArrayList<String> partial = new ArrayList();
                    for (int i = 0; i < complete.size(); i++){
                        if (complete.get(i).toLowerCase().contains(args[0].toLowerCase())) partial.add(complete.get(i));
                    }
                    return partial;
                }
                if (args.length == 2 && args[0].equalsIgnoreCase("setSpawnedType")){
                    ArrayList<String> complete = new ArrayList<>();
                    Field[] fields = EntityType.class.getFields();
                    for (int i = 0; i < fields.length; i++){
                        complete.add(fields[i].getName());
                    }
                    ArrayList<String> partial = new ArrayList<>();
                    for (int i = 0; i < complete.size(); i++){
                        if (complete.get(i).toLowerCase().contains(args[1].toLowerCase())) partial.add(complete.get(i));
                    }
                    return partial;
                }
            }
        }
        return null;
    }

    private boolean disabled = false;

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender && command.getName().equalsIgnoreCase("dspawners") && args[0].equalsIgnoreCase("toggle")){
            disabled = !disabled;
            if (disabled) sender.sendMessage(ChatColor.RED+"Drop from spawners disabled!");
            else sender.sendMessage(ChatColor.GREEN+"Drop from spawners enabled!");
            return true;
        }
        if (command.getName().equalsIgnoreCase("spawner") && sender instanceof Player && sender.isOp()){
            HashSet<Material> materials = new HashSet<>();
            materials.add(Material.AIR);
            materials.add(Material.GLASS_PANE);
            materials.add(Material.CAVE_AIR);
            materials.add(Material.VOID_AIR);
            materials.add(Material.GLASS);
            Block spawner = ((LivingEntity) sender).getTargetBlock(materials, 50);
           if (spawner.getType().equals(Material.SPAWNER)){
               CreatureSpawner cs = (CreatureSpawner) spawner.getState();
               if (args.length == 1) {
                   if (args[0].equalsIgnoreCase("getDelay")) sender.sendMessage(cs.getDelay() + "");
                   else if (args[0].equalsIgnoreCase("getMaxSpawnDelay"))
                       sender.sendMessage(cs.getMaxSpawnDelay() + "");
                   else if (args[0].equalsIgnoreCase("getMinSpawnDelay"))
                       sender.sendMessage(cs.getMinSpawnDelay() + "");
                   else if (args[0].equalsIgnoreCase("getSpawnedType")) sender.sendMessage(cs.getSpawnedType() + "");
                   else if (args[0].equalsIgnoreCase("getMaxNearbyEntities"))
                       sender.sendMessage(cs.getMaxNearbyEntities() + "");
                   else if (args[0].equalsIgnoreCase("getRequiredPlayerRange"))
                       sender.sendMessage(cs.getRequiredPlayerRange() + "");
                   else if (args[0].equalsIgnoreCase("getSpawnCount")) sender.sendMessage(cs.getSpawnCount() + "");
                   else if (args[0].equalsIgnoreCase("getSpawnRange")) sender.sendMessage(cs.getSpawnRange() + "");
               }
               else if (args.length == 2) {
                    try {
                        if (args[0].equalsIgnoreCase("setDelay")) cs.setDelay(Integer.parseInt(args[1]));
                        else if (args[0].equalsIgnoreCase("setMaxSpawnDelay"))
                            cs.setMaxSpawnDelay(Integer.parseInt(args[1]));
                        else if (args[0].equalsIgnoreCase("setMinSpawnDelay"))
                            cs.setMinSpawnDelay(Integer.parseInt(args[1]));
                        else if (args[0].equalsIgnoreCase("setMaxNearbyEntities"))
                            cs.setMaxNearbyEntities(Integer.parseInt(args[1]));
                        else if (args[0].equalsIgnoreCase("setRequiredPlayerRange"))
                            cs.setRequiredPlayerRange(Integer.parseInt(args[1]));
                        else if (args[0].equalsIgnoreCase("setSpawnCount")) cs.setSpawnCount(Integer.parseInt(args[1]));
                        else if (args[0].equalsIgnoreCase("setSpawnRange")) cs.setSpawnRange(Integer.parseInt(args[1]));
                    } catch (Exception e){
                        sender.sendMessage("A number expected.");
                    }
                    try {
                        if (args[0].equalsIgnoreCase("setSpawnedType")) cs.setSpawnedType(EntityType.valueOf(args[1].toUpperCase()));

                    } catch (Exception e){
                        sender.sendMessage("A proper mob name Expected");
                    }
                   cs.update();
               }
           }
           else {
               sender.sendMessage("You are not currently targeting spawner, targeted block: "+spawner.getType().toString());
           }
        }
        return false;
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"Plugin disabled");
    }

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN+"Plugin enabled");
        getServer().getPluginManager().registerEvents(new Events(), this);
    }
}
