package Commands;

import ROS.CustomBlock;
import ROS.Player;
import com.skylands.Globals;
import com.skylands.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class Management_custom implements CommandExecutor {
    ///<command>  /<command> [player] [name] [level]
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String player = args[0];
        String name = args[1];
        int level = Integer.parseInt(args[2]);

        if(Player.getPlayerFromName(player) == null){
            sender.sendMessage(ChatColor.RED + "[RosManagement] Player doesn't exist!");
            return false;
        }

        try{
            for (CustomBlock customBlock : Globals.CustomBlocks.values()){
                if(customBlock.name.toLowerCase().equals(name.toLowerCase()) && customBlock.level == level){
                    if(Main.getPlugin().getServer().getPlayer(player) != null){
                        Main.getPlugin().getServer().getPlayer(player).getInventory().addItem(customBlock.give(Player.getPlayerFromName(player)));
                    }
                }
            }
            sender.sendMessage(ChatColor.GREEN + "[RosManagement] Successful Query");
        }catch (Exception ex){
            sender.sendMessage(ChatColor.RED + "[RosManagement] Failed Query");
            ex.printStackTrace();
        }

        return true;
    }
}
