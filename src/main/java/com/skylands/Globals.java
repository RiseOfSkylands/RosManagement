package com.skylands;

import MySQL.Action;
import ROS.CustomBlock;
import ROS.Inventory;
import ROS.Player;
import ROS.PlayerCustomBlock;
import me.filoghost.holographicdisplays.api.beta.hologram.Hologram;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Globals {
    public static Map<String, Player> Players = new HashMap<String, Player>();
    public static Map<Integer, CustomBlock> CustomBlocks = new HashMap<Integer, CustomBlock>();
    public static Map<Integer, PlayerCustomBlock> PlayerCustomBlocks = new HashMap<Integer, PlayerCustomBlock>();
    public static Map<String, Hologram> PlayerCustomBlockHolograms = new HashMap<String, Hologram>();
    public static Map<String, Hologram> DebugHolograms = new HashMap<String, Hologram>();
    public static Map<String, Inventory> Inventories = new HashMap<String, Inventory>();

    public static boolean disableUpdate = false;

    //Plugin
    public static FileConfiguration cfg;
    public static String folder;
    public static boolean disabling = false;

    //Custom Item list


}
