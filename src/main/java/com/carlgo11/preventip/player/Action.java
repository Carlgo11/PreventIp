package com.carlgo11.preventip.player;

import com.carlgo11.preventip.Main;
import com.carlgo11.preventip.player.language.Lang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Action {
    
    static public void action(final Player p, final Main Main)
    {
        String act = Main.getConfig().getString("action");
        
        if (act.equals("warn")) {
            p.sendMessage("" + ChatColor.LIGHT_PURPLE + Lang.PREFIX + ChatColor.YELLOW + " " + Main.getConfig().getString("warn-msg"));
            Main.broadcastAbuse(p);
            
        } else if (act.equals("kick")) {
            
            Bukkit.getScheduler().runTask(Main, new Runnable() {
                
                public void run()
                {
                    p.kickPlayer("" + ChatColor.GOLD + Lang.KICK_MSG + ChatColor.RESET + " " + Main.getConfig().getString("kick-msg"));
                }
            });
            Main.broadcastAbuse(p);
            
        } else if (act.equals("ban")) {
            Bukkit.getScheduler().runTask(Main, new Runnable() {
                
                public void run()
                {
                    p.kickPlayer("" + ChatColor.GOLD + Lang.BAN_MSG + ChatColor.RESET + " " + Main.getConfig().getString("ban-msg"));
                }
            });
            p.setBanned(true);
            Main.broadcastAbuse(p);
            
        } else if (act.equals("custom")) {
            int listlength = Main.getConfig().getList("custom-commands").toArray().length;
            Object[] list = Main.getConfig().getList("custom-commands").toArray();
            for (int i = 0; i < listlength; i++) {
                String curlist = list[i].toString().replaceFirst("/", "");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), curlist);
            }
            Main.broadcastAbuse(p);
            
        }
    }
}
