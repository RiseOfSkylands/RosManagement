package com.skylands;

import ROS.Lib;
import ROS.PlayerCustomBlock;
import me.filoghost.holographicdisplays.api.beta.hologram.Hologram;
import me.filoghost.holographicdisplays.api.beta.hologram.VisibilitySettings;
import me.filoghost.holographicdisplays.api.beta.hologram.line.HologramLine;
import org.bukkit.ChatColor;

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
}
