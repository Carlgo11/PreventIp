package com.carlgo11.preventip;

import com.carlgo11.preventip.player.*;
import com.carlgo11.preventip.player.language.Lang;
import com.carlgo11.preventip.player.language.loadLang;
import java.io.File;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getPluginManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static YamlConfiguration LANG;
    public static File LANG_FILE;

    public void onEnable()
    {
        config();
        getPluginManager().registerEvents(new ChatEvent(this), this);
        getPluginManager().registerEvents(new CommandEvent(this), this);
        getPluginManager().registerEvents(new loadLang(this), this);

    }

    public void onDisalbe()
    {

    }

    public void config()
    {
        saveDefaultConfig();
    }

    public YamlConfiguration getLang()
    {
        return LANG;
    }

    public File getLangFile()
    {
        return LANG_FILE;
    }

    public void broadcastAbuse(Player p)
    {
        if (!getConfig().getString("broadcast-msg").isEmpty()) {
            String prebroadcast = getConfig().getString("broadcast-msg");
            String broadcast = ChatColor.translateAlternateColorCodes('&', prebroadcast);
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (pl != p) {
                    pl.sendMessage("" + Lang.PREFIX + ChatColor.YELLOW + "" + p.getName() + broadcast);
                }
            }

        }
    }

    public void action(Player p)
    {
        String act = getConfig().getString("action");

        if (act.equals("warn")) {
            p.sendMessage("" + ChatColor.LIGHT_PURPLE + Lang.PREFIXn + ChatColor.YELLOW + " " + getConfig().getString("warn-msg"));
            broadcastAbuse(p);

        } else if (act.equals("kick")) {
            p.kickPlayer("" + ChatColor.GOLD + Lang.PREFIXn + ChatColor.RESET + " " + getConfig().getString("kick-msg"));
            broadcastAbuse(p);

        } else if (act.equals("ban")) {
            p.kickPlayer("" + ChatColor.GOLD + Lang.PREFIXn + ChatColor.RESET + " " + getConfig().getString("ban-msg"));
            p.setBanned(true);
            broadcastAbuse(p);

        } else if (act.equals("custom")) {
            int listlength = getConfig().getList("custom-commands").toArray().length;
            Object[] list = getConfig().getList("custom-commands").toArray();
            for (int i = 0; i < listlength; i++) {
                String curlist = list[i].toString().replaceFirst("/", "");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), curlist);
            }
            p.setBanned(true);
            broadcastAbuse(p);

        }
    }

    public void debug(String s)
    {
        getLogger().info("" + Lang.PREFIX + "[Debug] " + s);
    }

}
