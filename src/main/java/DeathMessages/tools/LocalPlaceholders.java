package DeathMessages.tools;

import DeathMessages.DeathMessages.DeathMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class LocalPlaceholders {
	public static String fillPlaceHolders(String in, Player player, DeathMessages plugin) {
		Config config = plugin.getPluginConfig();
		ConfigurationSection worlds = config.messages.getConfigurationSection("worlds");
		ConfigurationSection entityTypes = config.messages.getConfigurationSection("entityTypes");
		String worldName = player.getWorld().getName().replace("_nether","").replace("the_end","");
		if(!worlds.contains(worldName)) {
			worlds.set(worldName, "&a" + worldName);
		}
		worldName = worlds.getString(worldName);

		String dimStr = config.messages.getConfigurationSection("dimensions").getString(player.getWorld().getEnvironment().name());

		String res = in;
		res = res.replace("[username]", player.getName());
		res = res.replace("[displayname]", player.getDisplayName());
		res = res.replace("[player_x]", ((Integer)player.getLocation().getBlockX()).toString());
		res = res.replace("[player_y]", ((Integer)player.getLocation().getBlockY()).toString());
		res = res.replace("[player_z]", ((Integer)player.getLocation().getBlockZ()).toString());
		if(worldName != null) res = res.replace("[world]", worldName.replace("_nether","").replace("_the_end",""));
		if(dimStr!=null) res = res.replace("[dimension]", dimStr);

		res = res.replace("[combat]","");

		res = LocalPlaceholders.fillColorCodes(res);
		return res;
	}

	public static String fillPlaceHolders(String in, Player player, Entity killer, DeathMessages plugin) {
		Config config = plugin.getPluginConfig();
		ConfigurationSection worlds = config.messages.getConfigurationSection("worlds");
		ConfigurationSection entityTypes = config.messages.getConfigurationSection("entityTypes");
		String worldName = player.getWorld().getName().replace("_nether","").replace("the_end","");
		String entityType = killer.getType().toString();
		if(!worlds.contains(worldName)) {
			worlds.set(worldName, "&a" + worldName);
		}
		if(!entityTypes.contains(entityType)) {
			entityTypes.set(entityType, "&fa " + entityType);
		}
		worldName = worlds.getString(worldName);
		entityType = entityTypes.getString(entityType);

		String dimStr = config.messages.getConfigurationSection("dimensions").getString(player.getWorld().getEnvironment().name());

		String res = in;
		res = res.replace("[username]", player.getName());
		res = res.replace("[displayname]", player.getDisplayName());
		res = res.replace("[player_x]", ((Integer)player.getLocation().getBlockX()).toString());
		res = res.replace("[player_y]", ((Integer)player.getLocation().getBlockY()).toString());
		res = res.replace("[player_z]", ((Integer)player.getLocation().getBlockZ()).toString());
		if(worldName != null) res = res.replace("[world]", worldName.replace("_nether","").replace("_the_end",""));
		if(dimStr!=null) res = res.replace("[dimension]", dimStr);

		if(killer instanceof  Player){
			Player k = (Player) killer;
			res = res.replace("[killerType]", entityType);
			res = res.replace("[killerName]", k.getName());
			res = res.replace("[killerDisplayName]", k.getDisplayName());
		}else{
			res = res.replace("[killerType]", entityType);
			if(killer.getName() != null)res = res.replace("[killerName]", killer.getName());
			else res = res.replace("killerName",entityType);
			res = res.replace("[killerDisplayName]", killer.getCustomName());
		}

		res = LocalPlaceholders.fillColorCodes(res);
		return res;
	}

	public static String fillColorCodes(String in) {
		if(in == null) return "";
		String res = in;
		res = res.replace("&0", ChatColor.BLACK.toString());
		res = res.replace("&1", ChatColor.DARK_BLUE.toString());
		res = res.replace("&2", ChatColor.DARK_GREEN.toString());
		res = res.replace("&3", ChatColor.DARK_AQUA.toString());
		res = res.replace("&4", ChatColor.DARK_RED.toString());
		res = res.replace("&5", ChatColor.DARK_PURPLE.toString());
		res = res.replace("&6", ChatColor.GOLD.toString());
		res = res.replace("&7", ChatColor.GRAY.toString());
		res = res.replace("&8", ChatColor.DARK_GRAY.toString());
		res = res.replace("&9", ChatColor.BLUE.toString());
		res = res.replace("&a", ChatColor.GREEN.toString());
		res = res.replace("&b", ChatColor.AQUA.toString());
		res = res.replace("&c", ChatColor.RED.toString());
		res = res.replace("&d", ChatColor.LIGHT_PURPLE.toString());
		res = res.replace("&e", ChatColor.YELLOW.toString());
		res = res.replace("&f", ChatColor.WHITE.toString());
		res = res.replace("&l", ChatColor.BOLD.toString());
		res = res.replace("&m", ChatColor.STRIKETHROUGH.toString());
		res = res.replace("&o", ChatColor.ITALIC.toString());
		return res;
	}
}
