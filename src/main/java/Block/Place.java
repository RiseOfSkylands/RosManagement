package Block;

import ROS.Lib;
import ROS.Player;
import ROS.PlayerCustomBlock;
import com.skylands.Globals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Map;

public class Place  implements Listener {
    @EventHandler
    public void BlockPlace(BlockPlaceEvent e) {
        Player p = Player.getPlayerFromUUID(e.getPlayer().getUniqueId().toString());

        if(PlayerCustomBlock.isCustomBlock(e.getItemInHand())){
            PlayerCustomBlock b = p.getCustomBlock(e.getItemInHand());
            if(!b.builddone){
                b.location = e.getBlockPlaced().getLocation();
                b.world = e.getBlockPlaced().getWorld();
                b.buildstart = System.currentTimeMillis();
            }else{
                b.enabled = true;
                b.location = e.getBlockPlaced().getLocation();
                b.world = e.getBlockPlaced().getWorld();
            }
            Globals.PlayerCustomBlocks.replace(Lib.getKeyFromPlayerCustomBlocks(b), b);
        }
    }
}
