package DeathMessages.commands;

import DeathMessages.DeathMessages.DeathMessages;
import DeathMessages.bukkitTasks.AnnounceDeath;
import DeathMessages.tools.Config;
import DeathMessages.types.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Test implements CommandExecutor {
	private DeathMessages plugin;
	
	public Test(DeathMessages plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("deathMsg test")) {
			if(!(sender instanceof Player)){
				sender.sendMessage("[deathMsg] only players can use this command!");
				return true;
			}
			Player player = (Player)sender;
			Config config = this.plugin.getPluginConfig();

			ConfigurationSection worlds = config.messages.getConfigurationSection("worlds");

			Message[] res;
			switch(args.length) {
				case 0: {
					res = new Message[config.messageNames.size()];
					Integer i = 0;
					for( String msgName : config.messageNames){
						res[i] = config.getMessage(msgName);
						i++;
					}
					break;
				}
				default: {
					//if trying to specify message
					res = new Message[args.length];
					for( int i = 0; i<args.length; i++) {
						if(!config.messages.getConfigurationSection("messages").contains(args[i])) {
							sender.sendMessage(config.messages.getString("badArgs"));
							return true;
						}
						res[i] = config.getMessage(args[i]);
					}
				}
			}

			for( Message m : res) {
				new AnnounceDeath(this.plugin, player, player, m,true).runTaskAsynchronously(this.plugin);
			}
		}
		return true;
	}
}
