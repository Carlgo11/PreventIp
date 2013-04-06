package org.carlgo11.Main;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
  implements Listener
{
	 
	
	
  public void onEnable()
  {
    getServer().getPluginManager().registerEvents(this, this);

    Logger logger = getLogger();

    getConfig().options().copyDefaults(true);
    getConfig().options().copyHeader(true);
    saveConfig();

    logger.info("[PreventIp] PreventIp " + getDescription().getVersion() + " is enabled!");
  }

  public void onDisable()
  {
    Logger logger = getLogger();

    reloadConfig();
    saveConfig();

    logger.info("[PreventIp] PreventIp " + getDescription().getVersion() + " is disabled!");
  }
  public boolean on = true;
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
	  if(sender.hasPermission("PreventIp.*") || sender.hasPermission("PreventIp.Command.Preventip")){
	  if(cmd.getName().equalsIgnoreCase("preventIp")){
		  if(on == true){
		  sender.sendMessage(ChatColor.LIGHT_PURPLE + "[PreventIp] " + ChatColor.YELLOW + "PreventIp Disabled!");
		  on = false;
		  
		  } else {
			  sender.sendMessage(ChatColor.LIGHT_PURPLE + "[PreventIp] " + ChatColor.YELLOW + "PreventIp Enabled!");
			 on = true; 
		  }
	  }
		  
	  } else {
		  sender.sendMessage(ChatColor.LIGHT_PURPLE + "[PreventIp] " + ChatColor.RED + "Error: You don't have permission for that command!");
	  }
	return true;
  }
  
  
  
  @EventHandler
  public void PlayerChat(AsyncPlayerChatEvent event)
  {
	  if(on == true){
    Player player = event.getPlayer();
    String in = event.getMessage();
    if (player.hasPermission("PreventIp.send") || player.hasPermission("PreventIp.*"))
    {
      event.setMessage(in);
    }
    else
    {
      Pattern pattern = Pattern.compile("(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])");
      Matcher matcher = pattern.matcher(in);
      if (matcher.find())
      {
        String action = getConfig().getString("action");

        if (action.equals("block"))
        {
          event.setCancelled(true);
          player.sendMessage(ChatColor.LIGHT_PURPLE + "[PreventIp] " + ChatColor.YELLOW + getConfig().getString("block-message"));
          if (getConfig().getString("broadcast").equals("true"))
          {
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[PreventIp] " + ChatColor.YELLOW + event.getPlayer().getName() + " tried to advertise another server!");
          }

        }

        if (action.equals("kick"))
        {
          event.setCancelled(true);
          player.kickPlayer(ChatColor.GOLD + "You have been Kicked: " + ChatColor.RESET + getConfig().getString("kick-message"));

          if (getConfig().getString("broadcast").equals("true"))
          {
        	  Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[PreventIp] " + ChatColor.YELLOW + event.getPlayer().getName() + " tried to advertise another server! And got kicked...");
          }

        }

        if (action.equals("ban"))
        {
          event.setCancelled(true);
          player.setBanned(true);
          player.kickPlayer(ChatColor.GOLD + "You have been Banned: " + ChatColor.RESET + getConfig().getString("ban-message"));

          if (getConfig().getString("broadcast").equals("true"))
          {
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[PreventIp] " + ChatColor.YELLOW + event.getPlayer().getName() + " tried to advertise another server! And got banned...");
          }
        }
      }
    }
	  }
  }
  
  @EventHandler
  public void onCommandPreprocess(PlayerCommandPreprocessEvent event ){
	  if(on == true){
    Player player = event.getPlayer();
    String in = event.getMessage();
    if (player.hasPermission("PreventIp.send")|| player.hasPermission("PreventIp.*"))
    {
      event.setMessage(in);
    }
    else
    {
      Pattern pattern = Pattern.compile("(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])");
      Matcher matcher = pattern.matcher(in);
      if (matcher.find())
      {
        String action = getConfig().getString("action");

        if (action.equals("block"))
        {
          event.setCancelled(true);
          player.sendMessage(ChatColor.LIGHT_PURPLE + "[PreventIp] " + ChatColor.GOLD + getConfig().getString("block-message"));
          if (getConfig().getString("broadcast").equals("true"))
          {
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[PreventIp] " + ChatColor.YELLOW + event.getPlayer().getName() + " tried to advertise another server!");
          }

        }

        if (action.equals("kick"))
        {
          event.setCancelled(true);
          player.kickPlayer(ChatColor.GOLD + "You have been Kicked: " + ChatColor.RESET + getConfig().getString("kick-message"));

          if (getConfig().getString("broadcast").equals("true"))
          {
        	  Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[PreventIp] " + ChatColor.YELLOW + event.getPlayer().getName() + " tried to advertise another server! And got kicked...");
          }

        }

        if (action.equals("ban"))
        {
          event.setCancelled(true);
          player.setBanned(true);
          player.kickPlayer(ChatColor.GOLD + "You have been Banned: " + ChatColor.RESET + getConfig().getString("ban-message"));

          if (getConfig().getString("broadcast").equals("true"))
          {
            Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[PreventIp] " + ChatColor.YELLOW + event.getPlayer().getName() + " tried to advertise another server! And got banned...");
          }
        }
      }
    }
	  }
  }
}