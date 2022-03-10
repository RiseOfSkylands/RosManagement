package Block;

import ROS.CustomBlock;
import ROS.Lib;
import ROS.Player;
import ROS.PlayerCustomBlock;
import com.skylands.Globals;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class Break implements Listener {
    @EventHandler
    public void BlockPlace(BlockPlaceEvent e) {
        Player p = Player.getPlayerFromUUID(e.getPlayer().getUniqueId().toString());

        for(PlayerCustomBlock b : Globals.PlayerCustomBlocks.values()){
            if(b.location.getX() == e.getBlock().getLocation().getX() &&
                b.location.getY() == e.getBlock().getLocation().getY() &&
                b.location.getZ() == e.getBlock().getLocation().getZ()){
                if(e.getBlock().getType().equals(CustomBlock.getCustomBlock(b).blockused)){
                    Location loc = new Location(Bukkit.getWorld("World"),0,0,0);
                    b.enabled = false;
                    b.location = loc;
                    b.world = Bukkit.getWorld("World");
                    Globals.PlayerCustomBlocks.replace(Lib.getKeyFromPlayerCustomBlocks(b), b);
                }
            }
        }


    }
}
