package com.skylands;

import ROS.Lib;
import ROS.Player;
import ROS.PlayerCustomBlock;
import me.filoghost.holographicdisplays.api.beta.hologram.Hologram;
import me.filoghost.holographicdisplays.api.beta.hologram.VisibilitySettings;
import me.filoghost.holographicdisplays.api.beta.hologram.line.HologramLine;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class HologramEdit {
    private static double xViewOffset = 0.5;
    private static double yViewOffset = 2.3;
    private static double zViewOffset = 0.5;

    private static double xDebugViewOffset = 1.5;
    private static double yDebugViewOffset = 5.8;
    private static double zDebugViewOffset = 0.5;

    private static Hologram SetViewOffset(Hologram holo, String type){
        if(type.equals("default")){
            double x = holo.getPosition().getBlockX() + xViewOffset;
            double y = holo.getPosition().getBlockY() + yViewOffset;
            double z = holo.getPosition().getBlockZ() + zViewOffset;
            holo.setPosition(holo.getPosition().getWorldIfLoaded(), x, y, z);
            return holo;
        }else if(type.equals("debug")){
            double x = holo.getPosition().getBlockX() + xDebugViewOffset;
            double y = holo.getPosition().getBlockY() + yDebugViewOffset;
            double z = holo.getPosition().getBlockZ() + zDebugViewOffset;

            holo.setPosition(holo.getPosition().getWorldIfLoaded(), x, y, z);
            return holo;
        }else if(type.equals("debugPlayer")){
            double x = holo.getPosition().getX() + xDebugViewOffset;
            double y = holo.getPosition().getY() + yDebugViewOffset;
            double z = holo.getPosition().getZ() + zDebugViewOffset;
            holo.setPosition(holo.getPosition().getWorldIfLoaded(), x, y, z);

        }
        return null;
    }

    public static void Clear(String itemid){
        if(Globals.PlayerCustomBlockHolograms.containsKey(itemid)) {
            Hologram holo = Globals.PlayerCustomBlockHolograms.get(itemid);
            holo.getLines().clear();
            Globals.PlayerCustomBlockHolograms.put(itemid, holo);
        }
    }

    public static void SetBuildTime(String itemid){
        if(Globals.PlayerCustomBlockHolograms.containsKey(itemid)) {
            Hologram holo = Globals.PlayerCustomBlockHolograms.get(itemid);
            holo.getLines().clear();
            holo.getLines().appendText(ChatColor.GRAY + "[" +
                    ChatColor.YELLOW + "Building" + ChatColor.GRAY + "]" +
                    ChatColor.RED + "Time Left" + ChatColor.WHITE + ": " +
                    ChatColor.YELLOW + Globals.PlayerCustomBlocks.get(Lib.getKeyFromPlayerCustomBlocks(itemid)).getPrettyTime());
        }else{
            Hologram holo = SetViewOffset(Main.getHoloAPI().createHologram(Globals.PlayerCustomBlocks.get(Lib.getKeyFromPlayerCustomBlocks(itemid)).location), "default");
            HologramLine a = holo.getLines().appendText(ChatColor.GRAY + "["+
                    ChatColor.YELLOW + "Building" + ChatColor.GRAY + "]" +
                    ChatColor.RED + "Time Left" + ChatColor.WHITE + ": " +
                    ChatColor.YELLOW + Globals.PlayerCustomBlocks.get(Lib.getKeyFromPlayerCustomBlocks(itemid)).getPrettyTime());
            Globals.PlayerCustomBlockHolograms.put(itemid, holo);
        }
    }

    public static void SetBlockDebug(PlayerCustomBlock b){
        if(!Globals.DebugHolograms.containsKey(b.itemid)){
            Hologram holo = SetViewOffset(Main.getHoloAPI().createHologram(Globals.PlayerCustomBlocks.get(Lib.getKeyFromPlayerCustomBlocks(b.itemid)).location), "debug");
            holo.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.HIDDEN);

            holo.getLines().appendText("ID : " + String.valueOf(b.id));
            holo.getLines().appendText("ItemID : " + b.itemid);
            holo.getLines().appendText("Name : " + b.name);
            holo.getLines().appendText("Level : " + String.valueOf(b.level));
            holo.getLines().appendText("MID : " + b.MID);
            holo.getLines().appendText("Enabled : " + String.valueOf(b.enabled));
            holo.getLines().appendText("Location : " + b.location.getX() + "," + b.location.getY() + "," + b.location.getZ());
            holo.getLines().appendText("World : " + b.world.getName());
            holo.getLines().appendText("BuildStart : " + String.valueOf(b.buildstart));
            holo.getLines().appendText("BuildDone : " + String.valueOf(b.builddone));
            holo.getLines().appendText("Storage : " + String.valueOf(b.storage));

            Globals.DebugHolograms.put(b.itemid, holo);
        }else{
            Hologram holo = Globals.DebugHolograms.get(b.itemid);
            holo.getLines().clear();
            holo.getLines().appendText("ID : " + String.valueOf(b.id));
            holo.getLines().appendText("ItemID : " + b.itemid);
            holo.getLines().appendText("Name : " + b.name);
            holo.getLines().appendText("Level : " + String.valueOf(b.level));
            holo.getLines().appendText("MID : " + b.MID);
            holo.getLines().appendText("Enabled : " + String.valueOf(b.enabled));
            holo.getLines().appendText("Location : " + b.location.getX() + "," + b.location.getY() + "," + b.location.getZ());
            holo.getLines().appendText("World : " + b.world.getName());
            holo.getLines().appendText("BuildStart : " + String.valueOf(b.buildstart));
            holo.getLines().appendText("BuildDone : " + String.valueOf(b.builddone));
            holo.getLines().appendText("Storage : " + String.valueOf(b.storage));
        }
    }

    public static void SetPlayerDebug(Player p){
        if(!Globals.DebugHolograms.containsKey(p.UUID)){
            Hologram holo = SetViewOffset(Main.getHoloAPI().createHologram(Main.getPlugin().getServer().getPlayer(p.Username).getLocation()), "debug");
            holo.getVisibilitySettings().setGlobalVisibility(VisibilitySettings.Visibility.HIDDEN);

            holo.getLines().appendText("Username : " + p.Username);
            holo.getLines().appendText("UUID : " + p.UUID);
            holo.getLines().appendText("MID : " + p.MID);
            holo.getLines().appendText("InventoryID : " + p.INVENTORYID);
            holo.getLines().appendText("Created : " + p.Created);
            holo.getLines().appendText("Created : " + p.getPrettyTime());
            holo.getLines().appendText("Debug : " + p.Debug);
            holo.getLines().appendText("Corn : " + p.countData.corn);
            holo.getLines().appendText("Wood : " + p.countData.wood);
            holo.getLines().appendText("Stone : " + p.countData.stone);
            holo.getLines().appendText("Gold : " + p.countData.gold);
            holo.getLines().appendText("Gems : " + p.countData.gems);
            holo.getLines().appendText("Power : " + p.countData.power);
            holo.getLines().appendText("Vip : " + p.countData.vip);

            Globals.DebugHolograms.put(p.UUID, holo);
        }else{
            Hologram holo = Globals.DebugHolograms.get(p.UUID);
            Location ploc = Main.getPlugin().getServer().getPlayer(p.Username).getLocation();
            ploc.setX(ploc.getX() + xDebugViewOffset);
            ploc.setY(ploc.getY() + yDebugViewOffset);
            ploc.setZ(ploc.getZ() + zDebugViewOffset);

            holo.setPosition(ploc);
            //holo = SetViewOffset(holo, "debugPlayer");
            holo.getLines().clear();
            holo.getLines().appendText("Username : " + p.Username);
            holo.getLines().appendText("UUID : " + p.UUID);
            holo.getLines().appendText("MID : " + p.MID);
            holo.getLines().appendText("InventoryID : " + p.INVENTORYID);
            holo.getLines().appendText("Created : " + p.Created);
            holo.getLines().appendText("Created : " + p.getPrettyTime());
            holo.getLines().appendText("Debug : " + p.Debug);
            holo.getLines().appendText("Corn : " + p.countData.corn);
            holo.getLines().appendText("Wood : " + p.countData.wood);
            holo.getLines().appendText("Stone : " + p.countData.stone);
            holo.getLines().appendText("Gold : " + p.countData.gold);
            holo.getLines().appendText("Gems : " + p.countData.gems);
            holo.getLines().appendText("Power : " + p.countData.power);
            holo.getLines().appendText("Vip : " + p.countData.vip);

        }
    }
}
