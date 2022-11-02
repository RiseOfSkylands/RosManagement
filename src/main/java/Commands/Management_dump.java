package Commands;

import ROS.CustomBlock;
import ROS.Inventory;
import ROS.Player;
import ROS.PlayerCustomBlock;
import com.skylands.Globals;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Management_dump implements CommandExecutor {
    //<command> [players,customblocks,customplayerblocks]
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //System.out.println(gson.toJson(Globals.Players.values()));
        //sender.sendMessage(gson.toJson(Globals.Players.values()));

        String type = args[0].toLowerCase();
        try{
            switch(type){
                case "vars":
                    sender.sendMessage("Vars"
                            + "\n      bDisableUpdate:" + Globals.disableUpdate
                            + "\n      Disabling:" + Globals.disabling

                    );
                    break;
                case "players":
                    for(Player p : Globals.Players.values()){
                        sender.sendMessage(p.UUID + "\n      " + p.Username
                                + "\n      MID:" + p.MID
                                + "\n      InventoryID:" + p.INVENTORYID
                                + "\n      Created:" + p.Created
                                + "\n      Corn:" + p.countData.corn
                                + "\n      Wood:" + p.countData.wood
                                + "\n      Stone:" + p.countData.stone
                                + "\n      Gold:" + p.countData.gold
                                + "\n      Gems:" + p.countData.gems
                                + "\n      Vip:" + p.countData.vip
                                + "\n      Power:" + p.countData.power
                        );
                    }
                    break;
                case "customblocks":
                    for(CustomBlock b : Globals.CustomBlocks.values()){
                        sender.sendMessage(b.id + "\n      " + b.name
                                + "\n      Description:" + b.description
                                + "\n      Level:" + b.level
                                + "\n      Hour:" + b.hour
                                + "\n      Capacity:" + b.capacity
                                + "\n      Power:" + b.power
                                + "\n      Cost:" + b.cost
                                + "\n      Time:" + b.time
                                + "\n      Hall:" + b.hall
                                + "\n      BlockUsed:" + b.blockused.toString()
                                + "\n      Display:" + b.display
                                + "\n      Produces:" + b.produces
                        );
                    }
                    break;
                case "customplayerblocks":
                    for(PlayerCustomBlock b : Globals.PlayerCustomBlocks.values()){
                        sender.sendMessage(b.id + "\n      " + b.itemid
                                + "\n      Name:" + b.name
                                + "\n      Level:" + b.level
                                + "\n      MID:" + b.MID
                                + "\n      Enabled:" + b.enabled
                                + "\n      Location:" + b.location.getX() + "," + b.location.getY() + "," + b.location.getZ()
                                + "\n      World:" + b.world.getName()
                                + "\n      BuildStart:" + b.buildstart
                                + "\n      BuildDone:" + b.builddone
                                + "\n      Storage:" + b.storage
                        );
                    }
                    break;
                case "inventories":
                    for(Inventory i : Globals.Inventories.values()){
                        sender.sendMessage(i.id + "\n      " + i.INVENTORYID
                                + "\n      Pages:" + i.pages
                                + "\n      Items:" + i.items
                        );
                    }
                    break;
            }
            sender.sendMessage(ChatColor.GREEN + "[RosManagement] Successfully Dumped");
        }catch (Exception ex){
            sender.sendMessage(ChatColor.RED + "[RosManagement] Failed Dumped");
            ex.printStackTrace();
        }

        return true;
    }
}
