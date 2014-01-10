package com.carlgo11.preventip.commands;

import com.carlgo11.preventip.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandPreventIp implements CommandExecutor {

    private Main lain;

    public CommandPreventIp(Main plug)
    {
        this.lain = plug;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        
        
        return true;
    }

}
