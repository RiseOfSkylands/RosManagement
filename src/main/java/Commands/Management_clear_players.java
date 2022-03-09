package Commands;

import com.skylands.Globals;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Management_clear_players implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Globals.Players.clear();
        sender.sendMessage(ChatColor.GREEN + "[RosManagement] Successful Clear");
        return true;
    }
}