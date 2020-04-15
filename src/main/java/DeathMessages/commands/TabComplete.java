package DeathMessages.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import DeathMessages.tools.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabComplete implements TabCompleter {
	private Map<String,String> subCommands = new HashMap<String,String>();
	
	private Config config;
	
	public TabComplete(Config config) {
		//load DeathMessages.commands and permission nodes into map
		subCommands.put("reload","deathMsg.reload");
		subCommands.put("test","deathMsg.test");
		subCommands.put("help","deathMsg.help");
		this.config = config;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args) {
		List<String> res = null;
		switch(args.length){
			case 1: {
				res = new ArrayList<String>();
				List<String> subCom = new ArrayList<String>();
				//fill list based on command permission nodes
				for (Map.Entry<String, String> entry : subCommands.entrySet()) {
					if (sender.hasPermission(entry.getValue()))
						subCom.add(entry.getKey());
				}
				StringUtil.copyPartialMatches(args[0],subCom,res);
				return res;
			}
			default: {
				switch(args[0]){
					case "test":{
						res = new ArrayList<String>();
						StringUtil.copyPartialMatches(args[args.length-1],this.config.messageNames,res);
						return res;
					}
					default: {
						return null;
					}
				}
			}
		}
	}
}
