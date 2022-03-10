package com.skylands;

import Block.Break;
import Block.Place;
import Commands.*;
import Player.Join;
import ROS.CustomBlock;
import ROS.Lib;
import ROS.PlayerCustomBlock;
import com.rok.skyblock.Islands.ActionBarItem;
import com.rok.skyblock.Islands.BossBarItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.Objects;

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
        this.getCommand("management-update").setTabCompleter(new TabComplete());

        this.getCommand("management-dump").setExecutor(new Management_dump());
        this.getCommand("management-dump").setTabCompleter(new TabComplete());

        this.getCommand("management-counters").setExecutor(new Management_counters());
        this.getCommand("management-counters").setTabCompleter(new TabComplete());

        this.getCommand("management-clear-players").setExecutor(new Management_clear_players());

        this.getCommand("management-custom").setExecutor(new Management_custom());
        this.getCommand("management-custom").setTabCompleter(new TabComplete());

        //Register Events
        Bukkit.getPluginManager().registerEvents(new Join(), this);
        Bukkit.getPluginManager().registerEvents(new Place(), this);
        Bukkit.getPluginManager().registerEvents(new Break(), this);

        JoinSpoof();
        UpdateCounters();
        UpdatePlayerCustomBlocks();

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
                if(p.Actionbar != null){
                    p.Actionbar.enabled = false;
                }
            }
        }
    }

    private void UpdatePlayerCustomBlocks(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                for(PlayerCustomBlock b : Globals.PlayerCustomBlocks.values()){
                    if(b.isTimeDone() && b.inLocation()) {
                        if(!b.builddone) { b.builddone = true; }
                        if(b.builddone && !b.enabled) { b.enabled = true; }

                        Globals.PlayerCustomBlocks.replace(Lib.getKeyFromPlayerCustomBlocks(b), b);
                    }
                }
            }
        }, 20, 20);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            public void run() {
                for(PlayerCustomBlock b : Globals.PlayerCustomBlocks.values()) {
                    CustomBlock cb = CustomBlock.getCustomBlock(b);
                    if(b.enabled && b.builddone && cb.hour != 0) {
                        if(b.storage <= cb.capacity){
                            b.storage = ((double)cb.hour/(double)3600) + b.storage;
                            Globals.PlayerCustomBlocks.replace(Lib.getKeyFromPlayerCustomBlocks(b), b);
                        }
                    }
                }
            }
        }, 0, 1200); //6000 5mins
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
                            if(p.Actionbar == null){
                                ActionBarItem abi = new ActionBarItem(GamePlayer, (double)p.countData.power, (double)p.countData.vip, true);
                                Globals.Players.get(p.UUID).Actionbar = abi;
                            }else{
                                p.Actionbar.power = (double)p.countData.power;
                                p.Actionbar.vip = (double)p.countData.vip;
                            }
                        }
                    }
                }
            }
        }, 20, 20);
    }
}
