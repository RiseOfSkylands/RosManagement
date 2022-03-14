package Player;

import ROS.PlayerCustomBlock;
import com.rok.skyblock.Schematic.BlockHitBox;
import com.skylands.Globals;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Interact implements Listener {
    @EventHandler
    public void Interact(PlayerInteractEvent e) {
        Player GamePlayer = e.getPlayer();
        Action action = e.getAction();

        for(PlayerCustomBlock b : Globals.PlayerCustomBlocks.values()){
            BlockHitBox bnb = new BlockHitBox(b.location);

            if(bnb.isClicked(e.getClickedBlock().getLocation())){
                //Show GUI

            }
        }

        /*if (action == Action.PHYSICAL || e.getItem() == null || !e.isBlockInHand()) {
            return;
        }

        switch(e.getItem().getType()){

        } */

    }
}
