package Commands;

import ROS.Lib;
import ROS.Player;
import com.skylands.Globals;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Management_debug implements CommandExecutor {
    ///<command>
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof org.bukkit.entity.Player){
            Player p = Player.getPlayerFromUUID(((org.bukkit.entity.Player) sender).getPlayer().getUniqueId().toString());

            p.Debug = !p.Debug;

            Globals.Players.replace(p.UUID, p);
        }
        return true;
    }
}
