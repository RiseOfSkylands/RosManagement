package com.skylands;

import ROS.Lib;
import me.filoghost.holographicdisplays.api.beta.hologram.Hologram;
import me.filoghost.holographicdisplays.api.beta.hologram.line.HologramLine;
import org.bukkit.ChatColor;

public class HologramEdit {
    private static double xViewOffset = 0.5;
    private static double yViewOffset = 2.3;
    private static double zViewOffset = 0.5;

    private static Hologram SetViewOffset(Hologram holo){
        double x = holo.getPosition().getBlockX() + xViewOffset;
        double y = holo.getPosition().getBlockY() + yViewOffset;
        double z = holo.getPosition().getBlockZ() + zViewOffset;
        holo.setPosition(holo.getPosition().getWorldIfLoaded(), x, y, z);
        return holo;
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
            Hologram holo = SetViewOffset(Main.getHoloAPI().createHologram(Globals.PlayerCustomBlocks.get(Lib.getKeyFromPlayerCustomBlocks(itemid)).location));
            HologramLine a = holo.getLines().appendText(ChatColor.GRAY + "["+
                    ChatColor.YELLOW + "Building" + ChatColor.GRAY + "]" +
                    ChatColor.RED + "Time Left" + ChatColor.WHITE + ": " +
                    ChatColor.YELLOW + Globals.PlayerCustomBlocks.get(Lib.getKeyFromPlayerCustomBlocks(itemid)).getPrettyTime());
            Globals.PlayerCustomBlockHolograms.put(itemid, holo);
        }
    }
}
