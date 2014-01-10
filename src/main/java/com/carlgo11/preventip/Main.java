package com.carlgo11.preventip;

import com.carlgo11.preventip.commands.*;
import com.carlgo11.preventip.player.*;
import com.carlgo11.preventip.player.language.Lang;
import com.carlgo11.preventip.player.language.loadLang;
import com.carlgo11.preventip.updater.Updater;
import java.io.File;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getPluginManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static YamlConfiguration LANG;
    public static File LANG_FILE;

    // config
    public boolean blockip;
    public boolean blockhostname;
    public boolean ignorehttp;
    public boolean autoupdater;
    public boolean updateavailable;

    public Pattern ipPattern = Pattern.compile("(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])");
    public Pattern hostnamePattern = Pattern.compile("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");
    public Pattern httpPattern = Pattern.compile("(^[(http)(https)]://)(^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$)");

    public void onEnable()
    {
        config();
        getPluginManager().registerEvents(new ChatEvent(this), this);
        getPluginManager().registerEvents(new CommandEvent(this), this);
        getPluginManager().registerEvents(new loadLang(this), this);
        commands();
        updater();
    }

    public void onDisable()
    {

    }
    public void commands()
    {
        getCommand("preventip").setExecutor(new CommandPreventIp(this));
    }

    public void config()
    {
        saveDefaultConfig();
    }
    
    public void updater(){
        if(autoupdater){
         Updater updater = new Updater(this, 49417, getFile(), Updater.UpdateType.DEFAULT, true);
         updateavailable = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;   
        }
    }

    public YamlConfiguration getLang()
    {
        return LANG;
    }

    public File getLangFile()
    {
        return LANG_FILE;
    }

    public void readConfig()
    {
        blockip = getConfig().getBoolean("block-ip");
        blockhostname = getConfig().getBoolean("block-hostname");
        autoupdater = getConfig().getBoolean("auto-update");
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

    public void action(final Player p)
    {
        String act = getConfig().getString("action");

        if (act.equals("warn")) {
            p.sendMessage("" + ChatColor.LIGHT_PURPLE + Lang.PREFIX + ChatColor.YELLOW + " " + getConfig().getString("warn-msg"));
            broadcastAbuse(p);

        } else if (act.equals("kick")) {

            Bukkit.getScheduler().runTask(this, new Runnable() {

                public void run()
                {
                    p.kickPlayer("" + ChatColor.GOLD + Lang.KICK_MSG + ChatColor.RESET + " " + getConfig().getString("kick-msg"));
                }
            });
            broadcastAbuse(p);

        } else if (act.equals("ban")) {
            Bukkit.getScheduler().runTask(this, new Runnable() {

                public void run()
                {
                    p.kickPlayer("" + ChatColor.GOLD + Lang.BAN_MSG + ChatColor.RESET + " " + getConfig().getString("ban-msg"));
                }
            });
            p.setBanned(true);
            broadcastAbuse(p);

        } else if (act.equals("custom")) {
            int listlength = getConfig().getList("custom-commands").toArray().length;
            Object[] list = getConfig().getList("custom-commands").toArray();
            for (int i = 0; i < listlength; i++) {
                String curlist = list[i].toString().replaceFirst("/", "");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), curlist);
            }
            broadcastAbuse(p);

        }
    }

    public void debug(String s)
    {
        getLogger().info("" + Lang.PREFIX + "[Debug] " + s);
    }

}
