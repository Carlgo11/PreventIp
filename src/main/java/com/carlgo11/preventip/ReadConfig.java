package com.carlgo11.preventip;

import org.bukkit.event.Listener;

public class ReadConfig implements Listener{
    
    static public void readConfig(Main Main)
    {
        Main.blockip = Main.getConfig().getBoolean("block-ip");
        Main.blockhostname = Main.getConfig().getBoolean("block-hostname");
        Main.autoupdater = Main.getConfig().getBoolean("auto-update");
        Main.debug = Main.getConfig().getBoolean("debug");
    }

}
