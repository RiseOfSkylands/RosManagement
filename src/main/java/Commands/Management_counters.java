package Commands;

import Enums.CountType;
import Enums.Sign;
import ROS.Player;
import com.skylands.Globals;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Management_counters implements CommandExecutor {
    ///<command> [player] [add,remove] [corn,wood,stone,gold,gems,power,vip] [amount]
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String player = args[0];
        String action = args[1].toUpperCase();
        String counter = args[2].toUpperCase();
        String amount = args[3];

        try{
            Player.getPlayerFromName(player).countData.edit(CountType.valueOf(counter), Sign.valueOf(action), Long.parseLong(amount));
            sender.sendMessage(ChatColor.GREEN + "[RosManagement] Successful Query");
        }catch (Exception ex){
            sender.sendMessage(ChatColor.RED + "[RosManagement] Failed Query");
            ex.printStackTrace();
        }
        return true;
    }
}
