package Functions;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Block {
    public static String getDisplayName(ItemStack b){
        //BaseComponent[] arry = (BaseComponent[]) b.getItemMeta().getDisplayNameComponent();
        //return ChatColor.stripColor(arry[0].toPlainText());
        return "";
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
