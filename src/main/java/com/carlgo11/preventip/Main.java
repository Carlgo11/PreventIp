package com.carlgo11.preventip;

import com.carlgo11.preventip.player.language.Lang;
import java.io.File;
import org.bukkit.Bukkit;
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
    }

    public void onDisalbe()
    {

    }
    
    public void config(){
        File config = new File(getDataFolder(), "config.yml");
        if (!config.exists()) {
            saveDefaultConfig();
            System.out.println("[" + getDescription().getName() + "] " + "No config.yml detected, config.yml created.");
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
        if (!getConfig().getString("broadcast").equals("")) {
            String prebroadcast = getConfig().getString("broadcast");
            String broadcast = ChatColor.translateAlternateColorCodes('&', prebroadcast);
            Bukkit.broadcastMessage("" + Lang.PREFIX + ChatColor.YELLOW + "" + p.getName() + broadcast);
        }
    }
    
    public void debug(String s){
        getLogger().info("" + Lang.PREFIX + "[Debug] " + s);
    }

}
