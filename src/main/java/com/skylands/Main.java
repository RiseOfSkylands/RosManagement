package com.skylands;

import Commands.*;
import Player.Join;
import com.rok.skyblock.Islands.BossBarItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Main extends JavaPlugin {
    private static Main plugin;

    public static Plugin getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Globals.disabling = false;

        //Load Config
        Globals.folder = getDataFolder().toString();
        Globals.cfg = getConfig();
        loadConfiguration();

        //Get SQL Data
        try {
            ROS.PlayerCustomBlock.GetSQL();
            ROS.CustomBlock.GetSQL();
            ROS.Player.GetSQL();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //Register COmmands
        this.getCommand("management-update").setExecutor(new Management_update());
        this.getCommand("management-dump").setExecutor(new Management_dump());
        this.getCommand("management-counters").setExecutor(new Management_counters());
        this.getCommand("management-clear-players").setExecutor(new Management_clear_players());
        this.getCommand("management-custom").setExecutor(new Management_custom());

        //Register Events
        Bukkit.getPluginManager().registerEvents(new Join(), this);

        JoinSpoof();
        UpdateCounters();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Globals.disabling = true;

        try {
            ROS.Player.UpdateSQL();
            ROS.PlayerCustomBlock.UpdateSQL();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        RemoveCounters();
    }

    public void JoinSpoof(){
        for(Player GamePlayer : getServer().getOnlinePlayers()){
            if(!Globals.Players.containsKey(GamePlayer.getUniqueId().toString())){
                new ROS.Player(GamePlayer).Insert();
            }
        }
    }

    public void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void RemoveCounters(){
        for(Player GamePlayer : getServer().getOnlinePlayers()){
            if(Globals.Players.containsKey(GamePlayer.getUniqueId().toString())){
                ROS.Player p = Globals.Players.get(GamePlayer.getUniqueId().toString());
                if(p.Counters != null){
                    p.Counters.bar.removePlayer(GamePlayer);
                }
            }
        }
    }
    private void UpdateCounters(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                for(Player GamePlayer : getServer().getOnlinePlayers()){
                    if(Globals.Players.containsKey(GamePlayer.getUniqueId().toString())){
                        if(!Globals.disabling){
                            ROS.Player p = Globals.Players.get(GamePlayer.getUniqueId().toString());
                            if(p.Counters == null){
                                BossBarItem bbi = new BossBarItem(GamePlayer, p.countData.corn, p.countData.wood,
                                        p.countData.stone, p.countData.gold, p.countData.gems);
                                Globals.Players.get(p.UUID).Counters = bbi;
                                bbi.sendBossBar();
                            }else{
                                p.Counters.corn = p.countData.corn;
                                p.Counters.stone = p.countData.stone;
                                p.Counters.wood = p.countData.wood;
                                p.Counters.gold = p.countData.gold;
                                p.Counters.gem = p.countData.gems;
                                p.Counters.updateBossBar();
                            }
                        }
                    }
                }
            }
        }, 20, 20);
    }
}
