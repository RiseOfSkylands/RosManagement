package Commands;

import ROS.Player;
import com.rok.skyblock.Islands.BossBarItem;
import com.skylands.Globals;
import com.skylands.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;

public class Management_update implements CommandExecutor {
    //<command> [get,post]
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String type = args[0];
        try {
            if(type.equals("post")){
                ROS.Player.UpdateSQL();
                ROS.PlayerCustomBlock.UpdateSQL();
                sender.sendMessage(ChatColor.GREEN + "[RosManagement] Successful Post");
            }
            if(type.equals("get")){
                for(org.bukkit.entity.Player GamePlayer : Main.getPlugin().getServer().getOnlinePlayers()){
                    ROS.Player p = Globals.Players.get(GamePlayer.getUniqueId().toString());
                    p.Counters.bar.removePlayer(GamePlayer);
                }

                Globals.Players.clear();
                Globals.PlayerCustomBlocks.clear();
                Globals.CustomBlocks.clear();

                ROS.Player.GetSQL();
                ROS.PlayerCustomBlock.GetSQL();
                ROS.CustomBlock.GetSQL();
                sender.sendMessage(ChatColor.GREEN + "[RosManagement] Successful Get");
            }
        } catch (SQLException throwables) {
            sender.sendMessage(ChatColor.RED + "[RosManagement] Failed Update");
            throwables.printStackTrace();
        }
        return true;
    }
}
