package commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import tools.Config;

public class TabComplete implements TabCompleter {
	private Map<String,String> subCommands = new HashMap<String,String>();
	
	private Config config;
	
	public TabComplete(Config config) {
		//load commands and permission nodes into map
		subCommands.put("reload","deathMsg.reload");
		subCommands.put("test","deathMsg.test");
		subCommands.put("help","deathMsg.help");
		this.config = config;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		if(args.length == 1 && sender.hasPermission("deathMsg.see")) 
		{
			//fill list based on command permission nodes
			List<String> res = new ArrayList<String>(); 
			for (Map.Entry<String, String> entry : subCommands.entrySet())
		        if(sender.hasPermission(entry.getValue())) res.add(entry.getKey());
			return res;
		}
		if(args.length == 2 && args[1].equalsIgnoreCase("test") && sender.hasPermission("deathMsg.test")) 
		{
			//fill list based on message names
			List<String> res = new ArrayList<String>(); 
			for (Map.Entry<String, String> entry : subCommands.entrySet())
		        if(sender.hasPermission(entry.getValue())) res.add(entry.getKey());
			return res;
		}
		
		return null;
	}
}
