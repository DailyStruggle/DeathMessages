package DeathMessages.commands;

import DeathMessages.DeathMessages.DeathMessages;
import DeathMessages.bukkitTasks.OnDeathChecks;
import DeathMessages.bukkitTasks.SendMessage;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import DeathMessages.tools.Config;
import DeathMessages.tools.LocalPlaceholders;
import DeathMessages.types.Message;

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

			Boolean doOtherWorld= config.config.getBoolean("doOtherWorlds");
			Boolean doOtherDim = config.config.getBoolean("doOtherDimensions");
			Boolean perPlayer = config.config.getBoolean("randomPerPlayer");
			ConfigurationSection worlds = config.messages.getConfigurationSection("worlds");
			String worldName = player.getWorld().getName().replace("_nether","").replace("the_end","");
			if(!worlds.contains(worldName)) {
				worlds.set(worldName, "&a" + worldName);
			}
			worldName = worlds.getString(worldName);
			String dimStr = config.messages.getConfigurationSection("dimensions").getString(player.getWorld().getEnvironment().name());

			new OnDeathChecks(this.plugin, config, player, true).runTaskAsynchronously(this.plugin);

			Message[] res;
			switch(args.length) {
				case 0: {
					sender.sendMessage(config.messages.getString("badArgs"));
					return true;
				}
				default: {
					//if trying to specify message
					res = new Message[args.length];
					for( int i = 0; i<args.length; i++) {
						if(!config.messages.getConfigurationSection("messages").contains(args[i])) {
							sender.sendMessage(config.messages.getString("badArgs"));
							return true;
						}
						res[i] = config.getMessage(args[i], player);
					}
				}
			}

			for( Message m : res) {
				String death = LocalPlaceholders.fillPlaceHolders(
						m.death,
						player,
						config);
				String hover = LocalPlaceholders.fillPlaceHolders(
						m.hover,
						player,
						config);
				if(config.hasPAPI()) death = PlaceholderAPI.setPlaceholders(player, death);
				if(config.hasPAPI()) hover = PlaceholderAPI.setPlaceholders(player, hover);

				Message resMsg = new Message(death, hover, m.chance);

				for (World w : plugin.getServer().getWorlds()) {
					worldName = w.getName().replace("_nether","").replace("the_end","");
					if(!worlds.contains(worldName)) {
						worlds.set(worldName, "&a" + worldName);
					}

					//skip if player's world isn't the same as receiver's world, disregarding the difference between dimension names
					if( !doOtherWorld && !player.getWorld().getName().replace("_nether","").replace("the_end","").equals( worldName ) ) continue;

					//skip if player is in another dimension
					if( !doOtherDim && !player.getWorld().getEnvironment().equals( w.getEnvironment() ) ) continue;

					for (Player p : w.getPlayers()) {
						//skip if has perm
						if(p.isSleepingIgnored() || p.hasPermission("deathMsg.ignore")) continue;

						new SendMessage(this.plugin, config, resMsg, player, p).runTaskAsynchronously(this.plugin);
					}
				}
			}
		}
		return false;
	}
	
}
