package com.carlgo11.preventip;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.bukkit.event.Listener;

public class ReadConfig implements Listener{
    
    static public void readConfig(Main Main)
    {
        Main.blockip = Main.getConfig().getBoolean("block-ip");
        Main.blockhostname = Main.getConfig().getBoolean("block-hostname");
        Main.autoupdater = Main.getConfig().getBoolean("auto-update");
    }

}
