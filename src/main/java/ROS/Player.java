package ROS;

import Functions.Block;
import MySQL.Action;
import com.rok.skyblock.Islands.ActionBarItem;
import com.rok.skyblock.Islands.BossBarItem;
import com.skylands.Globals;
import com.skylands.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class Player {
    public String UUID;
    public String Username;
    public String MID;
    public String INVENTORYID;
    public String Created;
    public CountData countData;
    public BossBarItem Counters;
    public ActionBarItem Actionbar;

    public boolean Debug = false;

    public Player(String UUID, String Username, String MID, String INVENTORYID, String Created, CountData countData){

        this.UUID = UUID;
        this.Username = Username;
        this.MID = MID;
        this.INVENTORYID = INVENTORYID;
        this.Created = Created;
        this.countData = countData;

    }

    public Player(org.bukkit.entity.Player gamePlayer){
        this.UUID = gamePlayer.getUniqueId().toString();
        this.Username = gamePlayer.getName();
        this.MID = java.util.UUID.randomUUID().toString();
        this.INVENTORYID = java.util.UUID.randomUUID().toString();
        this.Created = String.valueOf(System.currentTimeMillis());
        this.countData = new CountData();

    }

    public static Player getPlayerFromName(String name){
        for(Player p : Globals.Players.values()){
            if(p.Username.equals(name)){
                return p;
            }
        }
        return null;
    }

    public static Player getPlayerFromUUID(String uuid){
        for(Player p : Globals.Players.values()){
            if(p.UUID.equals(uuid)){
                return p;
            }
        }
        return null;
    }

    public PlayerCustomBlock getCustomBlock(ItemStack block){
        for(PlayerCustomBlock b : Globals.PlayerCustomBlocks.values()){
            if(Block.ContainsLore(block, "ID:" + b.itemid)){
                return b;
            }
        }
        return null;
    }

    public String getPrettyTime(){
        Timestamp timestamp = new Timestamp(Long.parseLong(Created));
        return timestamp.toString();
    }

    public Player Insert(){
        Globals.Players.put(UUID, this);
        return this;
    }

    public static void GetSQL() throws SQLException {
        //Get data from players table via MySQL
        Action d = new Action();
        ResultSet rs =  d.Select("SELECT * FROM players");
        while (rs.next()) {
            String SQLUUID = rs.getString("UUID");
            String SQLUsername = rs.getString("Username");
            String SQLMID = rs.getString("MID");
            String SQLINVENTORYID = rs.getString("INVENTORYID");
            String SQLCreated = rs.getString("Created");
            Long SQLvip = rs.getLong("vip");
            Long SQLpower = rs.getLong("power");
            Long SQLcorn = rs.getLong("corn");
            Long SQLwood = rs.getLong("wood");
            Long SQLstone = rs.getLong("stone");
            Long SQLgold = rs.getLong("gold");
            Long SQLgems = rs.getLong("gems");

            Player p = new Player(SQLUUID, SQLUsername, SQLMID, SQLINVENTORYID, SQLCreated,
                    new CountData(SQLcorn, SQLwood, SQLstone, SQLgold, SQLgems, SQLpower, SQLvip));
            Globals.Players.put(SQLUUID, p);
        }

    }

    public static void UpdateSQL() throws SQLException {
        Action d = new Action();
        //Update players table in MySQL table
        for(Player p : Globals.Players.values()){
            ResultSet rs =  d.Select("SELECT * FROM players WHERE UUID= '" + p.UUID + "'");
            //If player doesn't exist insert it
            if(!rs.next()){
                d.InsertUpdate("insert into players values (?,?,?,?,?,?,?,?,?,?,?,?)",
                    d.createVals(
                            p.UUID,
                            p.Username,
                            p.MID,
                            p.INVENTORYID,
                            p.Created,
                            p.countData.vip.toString(),
                            p.countData.power.toString(),
                            p.countData.corn.toString(),
                            p.countData.wood.toString(),
                            p.countData.stone.toString(),
                            p.countData.gold.toString(),
                            p.countData.gems.toString()

                    ), d.createObjs(
                            "str",
                            "str",
                            "str",
                            "str",
                            "str",
                            "long",
                            "long",
                            "long",
                            "long",
                            "long",
                            "long",
                            "long"
                ));

            }else{
                //Update player
                d.InsertUpdate("update players set Username = ?, MID = ?, INVENTORYID = ?, Created = ?, " +
                                "vip = ?, power = ?, corn = ?, wood = ?, stone = ?, gold = ?, gems = ? WHERE UUID = '" + p.UUID + "'",
                        d.createVals(
                                p.Username,
                                p.MID,
                                p.INVENTORYID,
                                p.Created,
                                p.countData.vip.toString(),
                                p.countData.power.toString(),
                                p.countData.corn.toString(),
                                p.countData.wood.toString(),
                                p.countData.stone.toString(),
                                p.countData.gold.toString(),
                                p.countData.gems.toString()
                        ), d.createObjs(
                                "str",
                                "str",
                                "str",
                                "str",
                                "long",
                                "long",
                                "long",
                                "long",
                                "long",
                                "long",
                                "long"
                        ));
            }
        }
    }
}
