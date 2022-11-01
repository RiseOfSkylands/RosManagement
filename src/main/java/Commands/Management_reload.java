package Commands;

import com.skylands.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;

import java.io.File;
import java.util.Arrays;

@Deprecated
public class Management_reload  implements CommandExecutor {
    //<command> [get,post]
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Reload minecraft server plugin
        try{
            //Bukkit.getServer().getPluginManager().clearPlugin(Main.getPlugin());
            Bukkit.getServer().getPluginManager().loadPlugin(new File(Main.getPlugin().getDataFolder(), "RosManagement.jar"));
            sender.sendMessage(ChatColor.GREEN + "Plugin reloaded!");
            return true;

        }catch (Exception e){
            sender.sendMessage(ChatColor.RED + "Plugin reload failed!");
            return false;
        }
    }
}
