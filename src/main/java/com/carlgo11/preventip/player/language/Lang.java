package com.carlgo11.preventip.player.language;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Lang {
    
    PREFIX("prefix", "&d[PreventIP]&r"),
    KICK_MSG("kickmsg", "You have been Kicked: "),
    BAN_MSG("banmsg", "You have been Banned: "),
    BADPERMS("badperms", "&cYou don't have permission to execute that command."),
    preventip_Main("preventip", "Shows all available commands."),
    preventip_reload("preventip_reload", "Reloads the plugin."),
    preventip_support("preventip_support", "Contact support."),
    preventip_report("preventip_report", "Upload a report to pastebin.");
    
    private String path;
    private String def;
    private static YamlConfiguration LANG;
 
    /**
    * Lang enum constructor.
    * @param path The string path.
    * @param start The default string.
    */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }
 
    /**
    * Set the {@code YamlConfiguration} to use.
    * @param config The config to set.
    */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }
 
    @Override
    public String toString() {
        if (this == PREFIX)
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }
 
    /**
    * Get the default value of the path.
    * @return The default value of the path.
    */
    public String getDefault() {
        return this.def;
    }
 
    /**
    * Get the path to the string.
    * @return The path to the string.
    */
    public String getPath() {
        return this.path;
    }
}