package commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {
	private Map<String,String> subCommands = new HashMap<String,String>();
	
	public TabComplete() {
		//load commands and permission nodes into map
		subCommands.put("reload","sleep.reload");
		subCommands.put("wakeup","sleep.wakeup");
		subCommands.put("help","sleep.help");
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		if(args.length == 1 && sender.hasPermission("sleep.see")) 
		{
			//fill list based on command permission nodes
			List<String> res = new ArrayList<String>(); 
			for (Map.Entry<String, String> entry : subCommands.entrySet())
		        if(sender.hasPermission(entry.getValue())) res.add(entry.getKey());
			return res;
		}
		
		return null;
	}
}
