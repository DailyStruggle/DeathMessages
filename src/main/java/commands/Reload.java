package commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import DeathMessages.DeathMessages;
import me.clip.placeholderapi.PlaceholderAPI;
import tools.Config;

public class Reload implements CommandExecutor {
	private DeathMessages plugin;
	
	public Reload(DeathMessages plugin, Config config) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender.hasPermission("deathMsg.reload"))
		{
			String str = "§a[DeathMsg] reloading.";
			Bukkit.getConsoleSender().sendMessage(str);
			if(sender instanceof Player) {
				if(this.plugin.getPluginConfig().hasPAPI()) str = PlaceholderAPI.setPlaceholders((Player)sender, str);
				sender.sendMessage(str);
			}
			
			Config config = plugin.getPluginConfig();
			config.refreshConfigs();
			
			str = "§b[DeathMsg] successfully reloaded.";
			Bukkit.getConsoleSender().sendMessage(str);
			if(sender instanceof Player) {
				if(this.plugin.getPluginConfig().hasPAPI()) str = PlaceholderAPI.setPlaceholders((Player)sender, str);
				sender.sendMessage(str);
			}
			
			return true;
		}
		return false;
	}
}
