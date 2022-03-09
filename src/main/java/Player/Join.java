package Player;

import ROS.Player;
import com.rok.skyblock.Islands.BossBarItem;
import com.skylands.Globals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;

public class Join implements Listener {
    @EventHandler
    public void Join(PlayerJoinEvent e) {
        if(!Globals.Players.containsKey(e.getPlayer().getUniqueId().toString())){
            new Player(e.getPlayer()).Insert();
        }
        Player p = Globals.Players.get(e.getPlayer().getUniqueId().toString());

        BossBarItem bbi = new BossBarItem(e.getPlayer(), p.countData.corn, p.countData.wood,
                p.countData.stone, p.countData.gold, p.countData.gems);

        Globals.Players.get(p.UUID).Counters = bbi;
        bbi.sendBossBar();
    }
}