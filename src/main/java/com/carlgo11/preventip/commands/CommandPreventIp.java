package com.carlgo11.preventip.commands;

import com.carlgo11.preventip.Main;
import com.carlgo11.preventip.Report;
import com.carlgo11.preventip.pastebin.Pastebin;
import com.carlgo11.preventip.player.language.Lang;
import java.io.UnsupportedEncodingException;
import org.bukkit.ChatColor;
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
        if (args.length == 0) {
            help(cmd, sender);
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("support")) {
                support(sender);
            } else if (args[0].equalsIgnoreCase("report")) {
                report(sender);
            } else if (args[0].equalsIgnoreCase("reload")) {
                reload(sender);
            }else{
                help(cmd, sender);
            }
        }
        if (args.length > 1) {
            help(cmd, sender);
        }

        return true;
    }

    private void help(Command cmd, CommandSender sender)
    {
        String header = ChatColor.GREEN + "======== " + ChatColor.YELLOW + "[" + plugin.getDescription().getName() + "]" + ChatColor.GREEN + " ======== ";
        if (sender.hasPermission("preventip.preventip")) {
            sender.sendMessage(header);
            sender.sendMessage("");
            sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + cmd.getName() + " " + ChatColor.YELLOW + Lang.preventip_Main);
            sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + cmd.getName() + " Reload " + ChatColor.YELLOW + Lang.preventip_reload);
            sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + cmd.getName() + " Support " + ChatColor.YELLOW + Lang.preventip_support);
            sender.sendMessage(ChatColor.GRAY + "-  /" + ChatColor.RED + cmd.getName() + " Report " + ChatColor.YELLOW + Lang.preventip_report);
        } else {
            plugin.senderSendMessage(sender, "" + Lang.BADPERMS);
        }
    }

    private void support(CommandSender sender)
    {
        if (sender.hasPermission("preventip.preventip.support")) {
            try {
                String pastebin = Pastebin.makePaste(Report.Main(plugin), "PreventIp help-log", "text");
                String purelink = pastebin.toString().replace("http://pastebin.com/", "");
                sender.sendMessage("" + Lang.PREFIX + ChatColor.GREEN + "Thank you for choosing our support IRC!\nIf the helpers busy please post a question on bukkit.");
                sender.sendMessage(ChatColor.YELLOW + "Connect with this link: " + ChatColor.BLUE + "http://cajs.co.uk/link/preip-irc?&nick=preip_" + purelink);
                sender.sendMessage(ChatColor.YELLOW + "Here's your log: " + ChatColor.BLUE + pastebin + ChatColor.YELLOW + ".\nPlease give the developers the log.");
            } catch (UnsupportedEncodingException ex) {
                plugin.senderSendMessage(sender, "Error: " + ex.toString());
                plugin.debug(ex.toString());
            }
        } else {
            plugin.senderSendMessage(sender, "" + Lang.BADPERMS);
        }
    }

    private void report(CommandSender sender)
    {
        if (sender.hasPermission("preventip.preventip.report")) {
            try {
                String pastebin = Pastebin.makePaste(Report.Main(plugin), "PreventIp help-log", "text");
                sender.sendMessage("" + Lang.PREFIX + ChatColor.GREEN + "Here's your log: " + pastebin);
            } catch (UnsupportedEncodingException ex) {
                sender.sendMessage("Error: " + ex.toString());
                plugin.debug(ex.toString());
            }
        } else {
            plugin.senderSendMessage(sender, "" + Lang.BADPERMS);
        }
    }

    private void reload(CommandSender sender)
    {
        if (sender.hasPermission("preventip.preventip.reload")) {
            
        } else {
            plugin.senderSendMessage(sender, "" + Lang.BADPERMS);
        }
    }

}
