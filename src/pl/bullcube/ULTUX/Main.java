package pl.bullcube.ULTUX;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private boolean disabled = false;

    public boolean isDisabled() {
        return disabled;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender && command.getName().equalsIgnoreCase("dspawners") && args[0].equalsIgnoreCase("toggle")){
            disabled = !disabled;
            if (disabled) sender.sendMessage(ChatColor.RED+"Drop from spawners disabled!");
            else sender.sendMessage(ChatColor.GREEN+"Ddrop from spawners enabled!");
            return true;
        }
        else return false;
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
