package com.carlgo11.preventip.player;

import com.carlgo11.preventip.Main;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvent implements Listener {

    Main plugin;

    public CommandEvent(Main plug)
    {
        super();
        this.plugin = plug;
    }

    @EventHandler
    public void PlayerCommand(PlayerCommandPreprocessEvent e)
    {
        if (!plugin.getConfig().getBoolean("ignore-commands")) {
            String msg = e.getMessage();
            String[] args = msg.split(" ");
            String cmd = args[0].toString();
            Player p = e.getPlayer();
            List<String> blockedcmds = plugin.getConfig().getStringList("blocked-cmds");
            if (blockedcmds.contains(cmd) || blockedcmds.contains("*")) {
                if (!p.hasPermission("preventip.ignore")) {
                    Pattern ipPattern = Pattern.compile("(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])");
                    Pattern hostnamePattern = Pattern.compile("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");
                    Matcher ipre = ipPattern.matcher(msg);
                    Matcher hnre = hostnamePattern.matcher(msg);
                    if (ipre.find() && plugin.blockip) {
                        e.setCancelled(true);
                        plugin.action(p);
                    } else if (hnre.find() && plugin.blockhostname) {
                        e.setCancelled(true);
                        plugin.action(p);
                    }
                }
            }

        }

    }
}
