package Block;

import ROS.CustomBlock;
import ROS.Lib;
import ROS.Player;
import ROS.PlayerCustomBlock;
import com.skylands.Globals;
import com.skylands.HologramEdit;
import com.skylands.Main;
import me.filoghost.holographicdisplays.api.beta.hologram.Hologram;
import me.filoghost.holographicdisplays.api.beta.hologram.line.HologramLine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class Break implements Listener {
    @EventHandler
    public void Break(BlockBreakEvent e) {
        Player p = Player.getPlayerFromUUID(e.getPlayer().getUniqueId().toString());

        for(PlayerCustomBlock b : Globals.PlayerCustomBlocks.values()){
            if(b.location.getX() == e.getBlock().getLocation().getX() &&
                b.location.getY() == e.getBlock().getLocation().getY() &&
                b.location.getZ() == e.getBlock().getLocation().getZ()){
                if(e.getBlock().getType().equals(CustomBlock.getCustomBlock(b).blockused)){
                    if(b.buildstart != 0 && !b.builddone){
                        e.setCancelled(true);
                    }else{
                        Location loc = new Location(Bukkit.getWorld("World"),0,0,0);
                        b.enabled = false;
                        b.location = loc;
                        b.world = Bukkit.getWorld("World");
                        Globals.PlayerCustomBlocks.replace(Lib.getKeyFromPlayerCustomBlocks(b), b);

                        HologramEdit.Clear(b.itemid);

                        for (CustomBlock customBlock : Globals.CustomBlocks.values()){
                            if(customBlock.name.toLowerCase().equals(b.name.toLowerCase()) && customBlock.level == b.level){
                                e.getPlayer().getInventory().addItem(customBlock.giveExisting(b));
                            }
                        }
                    }
                }
            }
        }


    }
}
