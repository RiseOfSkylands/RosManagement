package ROS;

import Functions.Block;
import MySQL.Action;
import com.skylands.Globals;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class PlayerCustomBlock {
    public int id;
    public String itemid;
    public String name;
    public int level;
    public String MID;
    public boolean enabled;
    public Location location;
    public World world;
    public Long buildstart;
    public boolean builddone;
    public Double storage;

    public PlayerCustomBlock(int id, String itemid, String name, int level, String MID, boolean enabled, Location location, World world,
                             Long buildstart, boolean builddone, Double storage){
        this.id = id;
        this.itemid = itemid;
        this.name = name;
        this.level = level;
        this.MID = MID;
        this.enabled = enabled;
        this.location = location;
        this.world = world;
        this.buildstart = buildstart;
        this.builddone = builddone;
        this.storage = storage;
    }

    public Long getTimeLeft() {
        Long timeleft = buildstart + Objects.requireNonNull(CustomBlock.getCustomBlock(this)).time - System.currentTimeMillis();
        if(timeleft <= 0){
            return 0L;
        }
        return timeleft;
    }

    public boolean isTimeDone(){
        if(getTimeLeft() <= 0L && location.getX() != 0 && location.getY() != 0 && location.getZ() != 0 && location.getWorld() != null && world != null){
            return true;
        }
        return false;
    }

    public String getPrettyTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd:hh:mm");
        Date date = new Date(getTimeLeft());
        String time = simpleDateFormat.format(date);
        return time;
    }

    public boolean inLocation(){
        try{
            Material type = world.getBlockAt(location).getType();
            if(type.equals(CustomBlock.getCustomBlock(this).blockused)) { return true; }

        }catch (Exception ex) { return false; }
        return false;
        /*
        if(location.getX() != 0 && location.getY() != 0 && location.getZ() != 0 && world != null && Bukkit.getWorld(String.valueOf(world)) != null){

            Material type = world.getBlockAt(location).getType();
            /*
            Material type = Bukkit.getWorld(String.valueOf(world)).getBlockAt(
                    Integer.parseInt(new DecimalFormat("#").format(location.getX())),
                    Integer.parseInt(new DecimalFormat("#").format(location.getY())),
                    Integer.parseInt(new DecimalFormat("#").format(location.getZ()))).getType();

            System.out.println("t-"+type.toString());
            System.out.println("o-"+CustomBlock.getCustomBlock(this).blockused.toString());
            if(type.equals(CustomBlock.getCustomBlock(this).blockused)) { return true; } else { return false; }
        }else{
            return false;
        }
        */
    }

    public Material getMaterial(){
        for(CustomBlock b : Globals.CustomBlocks.values()){
            if(b.name.equals(name) && b.level == level){
                return b.blockused;
            }
        }
        return null;
    }

    public static boolean isCustomBlock(ItemStack block){
        for(PlayerCustomBlock b : Globals.PlayerCustomBlocks.values()){
            if(Block.ContainsLore(block, "ID:" + b.itemid)){
                return true;
            }
        }
        return false;
    }

    public static void GetSQL() throws SQLException {
        //Get data from items table via MySQL
        Action d = new Action();
        ResultSet rs =  d.Select("SELECT * FROM builds");
        while (rs.next()) {
            int SQLID = rs.getInt("id");
            String SQLItemID = rs.getString("itemid");
            String SQLName = rs.getString("type");
            int SQLLevel = rs.getInt("level");
            String SQLMID = rs.getString("MID");
            boolean SQLEnabled = rs.getBoolean("enabled");
            String SQLLocation = rs.getString("location");
            String SQLWorld = rs.getString("world");
            Long SQLBuildStart = rs.getLong("buildstart");
            boolean SQLBuildDone = rs.getBoolean("builddone");
            Double SQLStorage = rs.getDouble("storage");

            Location loc = null;
            World world = null;
            if(!SQLLocation.equals("0") && !SQLWorld.equals("0")){
                String[] lstLoc = SQLLocation.split(",");
                Double x = Double.parseDouble(lstLoc[0]);
                Double y = Double.parseDouble(lstLoc[1]);
                Double z = Double.parseDouble(lstLoc[2]);

                loc = new Location(Bukkit.getWorld(SQLWorld), x, y, z);
                world = Bukkit.getWorld(SQLWorld);
            }else { loc = new Location(Bukkit.getWorld(""), 0,0,0); world = Bukkit.getWorld("World"); }

            PlayerCustomBlock p = new PlayerCustomBlock(SQLID, SQLItemID, SQLName, SQLLevel, SQLMID, SQLEnabled, loc, world, SQLBuildStart, SQLBuildDone, SQLStorage);
            Globals.PlayerCustomBlocks.put(SQLID, p);
        }

    }

    public static void UpdateSQL() throws SQLException {
        Action d = new Action();
        //Update builds table in MySQL table
        for(PlayerCustomBlock b : Globals.PlayerCustomBlocks.values()){
            ResultSet rs =  d.Select("SELECT * FROM builds WHERE itemid= '" + b.itemid + "'");
            //If player doesn't exist insert it
            if(!rs.next()){
                d.InsertUpdate("insert into builds values (null,?,?,?,?,?,?,?,?,?,?)",
                        d.createVals(
                                b.itemid,
                                b.name,
                                String.valueOf(b.level),
                                b.MID,
                                String.valueOf(b.enabled),
                                (b.location.getX() + "," + b.location.getY() + "," + b.location.getZ()),
                                b.world.getName().toString(),
                                b.buildstart.toString(),
                                String.valueOf(b.builddone),
                                b.storage.toString()

                        ), d.createObjs(
                                "str",
                                "str",
                                "int",
                                "str",
                                "bool",
                                "str",
                                "str",
                                "long",
                                "bool",
                                "double"
                        ));

            }else{
                d.InsertUpdate("update builds set itemid = ?, type = ?, level = ?, " +
                                "enabled = ?, location = ?, world = ?, buildstart = ?, builddone = ?, storage = ? WHERE itemid = '" + b.itemid + "'",

                        d.createVals(
                                b.itemid,
                                b.name,
                                String.valueOf(b.level),
                                String.valueOf(b.enabled),
                                (b.location.getX() + "," + b.location.getY() + "," + b.location.getZ()),
                                b.world.getName().toString(),
                                b.buildstart.toString(),
                                String.valueOf(b.builddone),
                                b.storage.toString()
                        ), d.createObjs(
                                "str",
                                "str",
                                "int",
                                "bool",
                                "str",
                                "str",
                                "long",
                                "bool",
                                "double"
                        ));
            }
        }
    }
}
