package ROS;

import MySQL.Action;
import com.skylands.Globals;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Inventory {
    public int id;
    public String INVENTORYID;
    public int pages;
    public String items;

    public Inventory(int id, String INVENTORYID, int pages, String items) {
        this.id = id;
        this.INVENTORYID = INVENTORYID;
        this.pages = pages;
        this.items = items;
    }

    public Inventory(String INVENTORYID) {
        this.id = 0;
        this.INVENTORYID = INVENTORYID;
        this.pages = 1;
        this.items = "";
    }

    public Inventory Insert(){
        Globals.Inventories.put(INVENTORYID, this);
        return this;
    }

    public static void GetSQL() throws SQLException {
        //Get data from players table via MySQL
        Action d = new Action();
        ResultSet rs =  d.Select("SELECT * FROM inventories");
        while (rs.next()) {
            int SQLID = rs.getInt("id");
            String SQLINVENTORYID = rs.getString("INVENTORYID");
            int SQLPages = rs.getInt("pages");
            String SQLItems = rs.getString("items");

            Inventory i = new Inventory(SQLID, SQLINVENTORYID, SQLPages, SQLItems);
            Globals.Inventories.put(SQLINVENTORYID, i);
        }

    }

    public static void UpdateSQL() throws SQLException {
        Action d = new Action();
        //Update players table in MySQL table
        for(Inventory i : Globals.Inventories.values()){
            ResultSet rs =  d.Select("SELECT * FROM inventories WHERE INVENTORYID= '" + i.INVENTORYID + "'");
            //If player doesn't exist insert it
            if(!rs.next()){
                d.InsertUpdate("insert into inventories values (null,?,?,?)",
                        d.createVals(
                                i.INVENTORYID,
                                String.valueOf(i.pages),
                                i.items
                        ), d.createObjs(
                                "str",
                                "int",
                                "str"
                        ));
            }else{
                //Update player
                d.InsertUpdate("update inventories set pages = ?, items = ? WHERE INVENTORYID = '" + i.INVENTORYID + "'",
                        d.createVals(
                                i.INVENTORYID,
                                String.valueOf(i.pages),
                                i.items
                        ), d.createObjs(
                                "str",
                                "int",
                                "str"
                        ));
            }
        }
    }
}
