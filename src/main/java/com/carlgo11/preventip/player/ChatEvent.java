package com.carlgo11.preventip.player;

import com.carlgo11.preventip.Main;
import com.carlgo11.preventip.player.language.Lang;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        if (!plugin.getConfig().getBoolean("ignore-chat")) {
            Player p = e.getPlayer();
            String msg = e.getMessage();
            if (!p.hasPermission("preventip.ignore")) {
                Pattern ipPattern = Pattern.compile("(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])");
                Pattern hostnamePattern = Pattern.compile("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");
                Matcher ipre = ipPattern.matcher(msg);
                Matcher hnre = hostnamePattern.matcher(msg);
                if (ipre.find() && plugin.getConfig().getBoolean("block-ip")) {
                    e.setCancelled(true);
                    plugin.action(p);
                } else if (hnre.find() && plugin.getConfig().getBoolean("block-hostname")) {
                    e.setCancelled(true);
                    plugin.action(p);
                }
            }
        }
    }
}
