package Commands;

import com.skylands.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;

import java.io.File;

public class Management_reload  implements CommandExecutor {
    //<command> [get,post]
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Main.getPlugin().getServer().getPluginManager().getPlugins()[1]
        try {
            Main.getPlugin().getServer().getPluginManager().loadPlugin(new File(Main.getPlugin().getServer().getPluginsFolder().getAbsolutePath() + "\"RosManagement.jar\""));
        } catch (InvalidPluginException e) {
            e.printStackTrace();
        } catch (InvalidDescriptionException e) {
            e.printStackTrace();
        }
        return true;
    }
}
