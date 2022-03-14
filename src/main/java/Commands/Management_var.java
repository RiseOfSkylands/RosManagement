package Commands;

import ROS.Lib;
import com.skylands.Globals;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Management_var  implements CommandExecutor {
    //<command> [get,post]
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Lib.PrintArray(args);
        if(args[0].equals("disableUpdate")){
            Globals.disableUpdate = Boolean.getBoolean(args[1].toLowerCase());
            sender.sendMessage(ChatColor.YELLOW + "[RosManagement] disableUpdate = " + Globals.disableUpdate);
        }
        return true;
    }
}
