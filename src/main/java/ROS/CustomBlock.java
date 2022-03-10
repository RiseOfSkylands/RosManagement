package ROS;

import Functions.Block;
import MySQL.Action;
import com.skylands.Globals;
import com.skylands.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class CustomBlock {
    public final int id;
    public final String name;
    public final String description;
    public final int level;
    public final int hour;
    public final int capacity;
    public final int power;
    public final String cost;
    public final int time;
    public final int hall;
    public final Material blockused;
    public final String display;
    public final String produces;

    public CustomBlock(int id, String name, String description, int level, int hour, int capacity, int power, String cost, int time, int hall, Material blockused, String display, String produces){

        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.hour = hour;
        this.capacity = capacity;
        this.power = power;
        this.cost = cost;
        this.time = time;
        this.hall = hall;
        this.blockused = blockused;
        this.display = display;
        this.produces = produces;
    }

    public ItemStack give(Player p){
        ItemStack block = new ItemStack(blockused);
        ItemMeta blockMeta = block.getItemMeta();

        blockMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', display));
        blockMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        ArrayList<String> blockLore = new ArrayList();

        blockLore.add(ChatColor.AQUA + description);
        if(level != 0) { blockLore.add(ChatColor.YELLOW + "Level" + ChatColor.GRAY + ":" + ChatColor.WHITE + level); }
        if(hour != 0) { blockLore.add(ChatColor.YELLOW + "Hourly Gain" + ChatColor.GRAY + ":" + ChatColor.WHITE + hour); }
        if(capacity != 999999999) { blockLore.add(ChatColor.RED + "Capacity" + ChatColor.GRAY + ":" + ChatColor.WHITE + capacity); }

        String blockID = UUID.randomUUID().toString();
        blockLore.add(ChatColor.DARK_GRAY + "ID:" + blockID);

        blockMeta.setLore(blockLore);
        block.setItemMeta(blockMeta);

        /*
        Action d = new Action();
        d.InsertUpdate("INSERT INTO builds (type, level, MID, itemid) VALUES (?, ?, ?, ?)",
                d.createVals(
                        name,
                        String.valueOf(level),
                        p.MID,
                        blockID

                ), d.createObjs(
                        "str",
                        "int",
                        "str",
                        "str"
                ));
                */
        PlayerCustomBlock b = new PlayerCustomBlock(Lib.random_int(53445,347594345), blockID, name, level, p.MID, false, new Location(Bukkit.getWorld(""), 0,0,0),
                Bukkit.getWorld("World"), 0L, false, 0D);
        Globals.PlayerCustomBlocks.put(b.id, b);
        return block;
    }

    public static void GetSQL() throws SQLException {
        //Get data from items table via MySQL
        Action d = new Action();
        ResultSet rs =  d.Select("SELECT * FROM items");
        while (rs.next()) {
            int SQLID = rs.getInt("id");
            String SQLName = rs.getString("name");
            String SQLDescription = rs.getString("description");
            int SQLLevel = rs.getInt("level");
            int SQLHour = rs.getInt("hour");
            int SQLCapacity = rs.getInt("capacity");
            int SQLPower = rs.getInt("power");
            String SQLCost = rs.getString("cost");
            int SQLTime = rs.getInt("time");
            int SQLHall = rs.getInt("hall");
            String SQLBlockUsed = rs.getString("blockused");
            String SQLDisplay = rs.getString("display");
            String SQLProduces = rs.getString("produces");

            CustomBlock p = new CustomBlock(SQLID, SQLName, SQLDescription, SQLLevel, SQLHour, SQLCapacity, SQLPower, SQLCost, SQLTime, SQLHall,
                    Material.valueOf(SQLBlockUsed.toUpperCase()), SQLDisplay, SQLProduces);
            Globals.CustomBlocks.put(SQLID, p);
        }

    }

}
