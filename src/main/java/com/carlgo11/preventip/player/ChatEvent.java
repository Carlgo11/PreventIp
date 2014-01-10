package com.carlgo11.preventip.player;

import com.carlgo11.preventip.Main;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
            String[] args = msg.split(" ");
            Boolean match = false;
            if (!p.hasPermission("preventip.ignore")) {
                Pattern ipPattern = Pattern.compile("(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])");
                Pattern hostnamePattern = Pattern.compile("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");
                Pattern httpPattern = Pattern.compile("http://^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");
                for (int i = 0; i < args.length; i++) {
                    Matcher ipre = ipPattern.matcher(args[i].toString());
                    Matcher hnre = hostnamePattern.matcher(args[i].toString());
                    Matcher hpre = httpPattern.matcher(args[i].toString());
                    if (hnre.find() && !plugin.blockhostname) {
                        if (!hpre.find() || plugin.ignorehttp) {
                            match = true;
                            break;
                        }
                    }
                    if (ipre.find() && !plugin.blockip) {
                        match = true;
                    }
                }
                if (match) {
                    e.setCancelled(true);
                    plugin.action(p);
                }
            }
        }
    }
}
