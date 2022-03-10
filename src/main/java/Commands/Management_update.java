package Commands;

import ROS.Player;
import com.skylands.Globals;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;

public class Management_update implements CommandExecutor {
    //<command> <player> <structure> <level>
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            ROS.Player.UpdateSQL();
            ROS.PlayerCustomBlock.UpdateSQL();
            sender.sendMessage(ChatColor.GREEN + "[RosManagement] Successfully Updated");
        } catch (SQLException throwables) {
            sender.sendMessage(ChatColor.RED + "[RosManagement] Failed Update");
            throwables.printStackTrace();
        }
        return true;
    }
}
