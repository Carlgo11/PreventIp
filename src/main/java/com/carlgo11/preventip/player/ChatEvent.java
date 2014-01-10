package com.carlgo11.preventip.player;

import com.carlgo11.preventip.Main;
import com.carlgo11.preventip.player.language.Lang;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    Main plugin;

    public ChatEvent(Main plug)
    {
        super();
        this.plugin = plug;
    }

    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent e)
    {
        Player p = e.getPlayer();
        String m = e.getMessage();
        if (!p.hasPermission("preventip.ignore")) {
            Pattern pattern = Pattern.compile("(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])");
            Matcher re = pattern.matcher(m);
            if (re.find()) {
                String action = plugin.getConfig().getString("action");

                if (action.equals("cancel")) {
                    p.sendMessage("" + ChatColor.LIGHT_PURPLE + "[PreIp] " + ChatColor.YELLOW + plugin.getConfig().getString("cancel-msg"));
                    plugin.broadcastAbuse(p);
                }

                if (action.equals("kick")) {
                    p.kickPlayer("" + ChatColor.GOLD + Lang.KICK_MSG + ChatColor.RESET + plugin.getConfig().getString("kick-msg"));
                    plugin.broadcastAbuse(p);
                }

                if (action.equals("ban")) {
                    p.kickPlayer(""+ ChatColor.GOLD + Lang.BAN_MSG + ChatColor.RESET + plugin.getConfig().getString("ban-msg"));
                    p.setBanned(true);
                    plugin.broadcastAbuse(p);
                }
                e.setCancelled(true);
            }
        }
    }
}
