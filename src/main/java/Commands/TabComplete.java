package Commands;

import ROS.CountData;
import ROS.CustomBlock;
import com.skylands.Globals;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<String>();
        if (command.getName().equalsIgnoreCase("management-update")) {
            if (args.length == 1) {
                list.add("get");
                list.add("post");
                return list;
            }
            if(args.length > 1){
                list.add("");
                return list;
            }
        }
        if (command.getName().equalsIgnoreCase("management-dump")) {
            if (args.length == 1) {
                list.add("players");
                list.add("customblocks");
                list.add("customplayerblocks");
                return list;
            }
            if(args.length > 1){
                list.add("");
                return list;
            }
        } ///<command> [player] [name] [level]
        if (command.getName().equalsIgnoreCase("management-custom")) {
            if (args.length == 2) {
                for(CustomBlock b : Globals.CustomBlocks.values()){
                    list.add(b.name);
                }
                return list;
            }
            if (args.length == 3) {
                for(CustomBlock b : Globals.CustomBlocks.values()){
                    if(b.name.equals(args[1])){
                        list.add(String.valueOf(b.level));
                    }
                }
                return list;
            }
            if(args.length > 3){
                list.add("");
                return list;
            }
        }
        if (command.getName().equalsIgnoreCase("management-counters")) {
            if (args.length == 2) {
                list.add("add");
                list.add("rem");
                return list;
            }
            if (args.length == 3) {
                list.add("corn");
                list.add("wood");
                list.add("stone");
                list.add("gold");
                list.add("gems");
                list.add("power");
                list.add("vip");
                return list;
            }
            if(args.length == 4){
                list.add("0");
                return list;
            }
            if(args.length > 4){
                list.add("");
                return list;
            }
        }
        return null;
    }
}
