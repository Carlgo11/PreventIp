package com.carlgo11.preventip.commands;

import com.carlgo11.preventip.Main;
import com.carlgo11.preventip.pastebin.API;
import java.io.UnsupportedEncodingException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandPreventIp implements CommandExecutor {

    private Main plugin;

    public CommandPreventIp(Main plug)
    {
        this.plugin = plug;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if(args.length == 0){
            return false;
        }
        if(args.length == 1){
            if(args[0].equalsIgnoreCase("support")){
                if(sender.hasPermission("preventip.support")){
                    try{
                    sender.sendMessage(API.makePaste(plugin.report, "hello_world", "text"));
                    }catch(Exception ex){
                        sender.sendMessage("Error: "+ex.toString());
                        plugin.debug(ex.toString());
                        //error
                    }
                }else{
                    //badperms
                }
            }
        }
        if(args.length > 1){
            return false;
        }
        
        return true;
    }

}
