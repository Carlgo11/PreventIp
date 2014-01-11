package com.carlgo11.preventip;

import com.carlgo11.preventip.commands.*;
import com.carlgo11.preventip.mcstats.CustomGraphs;
import com.carlgo11.preventip.mcstats.Metrics;
import com.carlgo11.preventip.player.*;
import com.carlgo11.preventip.player.language.Lang;
import com.carlgo11.preventip.player.language.loadLang;
import com.carlgo11.preventip.updater.Updater;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import static org.bukkit.Bukkit.getPluginManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    public static YamlConfiguration LANG;
    public static File LANG_FILE;
    public static String report;

    // config
    public boolean blockip;
    public boolean blockhostname;
    public boolean ignorehttp;
    public boolean autoupdater;
    public boolean updateavailable;
    public boolean debug;
    
    public Pattern ipPattern = Pattern.compile("(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])");
    public Pattern hostnamePattern = Pattern.compile("^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");
    public Pattern httpPattern = Pattern.compile("(^[(http)(https)]://)(^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$)");
    
    public void onEnable()
    {
        config();
        ReadConfig.readConfig(this);
        getPluginManager().registerEvents(new loadLang(this), this);
        updater();
        mcstats();
        getPluginManager().registerEvents(new ChatEvent(this), this);
        getPluginManager().registerEvents(new CommandEvent(this), this);
        commands();
        getLogger().log(Level.INFO, getDescription().getName() + " v" + getDescription().getVersion() + " enabled!");
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
    
    public void updater()
    {
        if (autoupdater) {
            Updater updater = new Updater(this, 52405, getFile(), Updater.UpdateType.DEFAULT, true);
            updateavailable = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;   

        }
    }
    
    public void mcstats()
    {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
            CustomGraphs.graphs(metrics, this);
        } catch (IOException ex) {
            this.getLogger().log(Level.WARNING, Lang.PREFIX + "Error Submitting stats!" + "Output: " + ex.toString());
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
    
    public void senderSendMessage(CommandSender p, String s)
    {
        p.sendMessage("" + Lang.PREFIX + " " + s);
    }
    
    public void debug(String s)
    {
        if(debug){
        getLogger().info("[Debug] " + s);
        }
    }
    
}
