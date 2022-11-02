package Functions;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Block {
    public static String getDisplayName(ItemStack b){
        String arry = b.getItemMeta().getDisplayNameComponent().toString();
        return ChatColor.stripColor(arry);
    }
    public static boolean ContainsLore(ItemStack b, String val){
        if(b.getItemMeta().getLore() != null){
            ArrayList<String> lore = (ArrayList<String>) b.getItemMeta().getLore();
            for(String l : lore){
                if(ChatColor.stripColor(l).contains(val)){
                    return true;
                }
            }
        }
        return false;
    }
}
