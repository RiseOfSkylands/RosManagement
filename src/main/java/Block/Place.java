package Block;

import ROS.Lib;
import ROS.Player;
import ROS.PlayerCustomBlock;
import com.rok.skyblock.Schematic.BlockHitBox;
import com.skylands.Globals;
import com.skylands.HologramEdit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Map;

public class Place  implements Listener {
    @EventHandler
    public void Place(BlockPlaceEvent e) {
        Player p = Player.getPlayerFromUUID(e.getPlayer().getUniqueId().toString());

        if(PlayerCustomBlock.isCustomBlock(e.getItemInHand())){
            PlayerCustomBlock b = p.getCustomBlock(e.getItemInHand());
            if(!b.builddone){
                b.location = e.getBlockPlaced().getLocation();
                b.world = e.getBlockPlaced().getWorld();
                b.buildstart = System.currentTimeMillis();

                Globals.PlayerCustomBlocks.replace(Lib.getKeyFromPlayerCustomBlocks(b), b);

                HologramEdit.SetBuildTime(b.itemid);

            }else{
                b.enabled = true;
                b.location = e.getBlockPlaced().getLocation();
                b.world = e.getBlockPlaced().getWorld();

                Globals.PlayerCustomBlocks.replace(Lib.getKeyFromPlayerCustomBlocks(b), b);

                HologramEdit.SetStorage(b.itemid);
            }

            BlockHitBox bhb = new BlockHitBox(b.location);
            bhb.setBlock(b.getMaterial(), "miners0.schematic");


        }
    }
}
