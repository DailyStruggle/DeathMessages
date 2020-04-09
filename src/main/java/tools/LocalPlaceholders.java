package tools;

import org.bukkit.ChatColor;

public class LocalPlaceholders {
	public static String fillPlaceHolders(String in, String username, String displayname, String worldname, String dimensionname) {
		if(in.isEmpty()) return in;
		String res = in;
		res = res.replace("[username]", username);
		res = res.replace("[displayname]", displayname);
		if(worldname != null) res = res.replace("[world]", worldname.replace("_nether","").replace("_the_end",""));
		if(dimensionname!=null) res = res.replace("[dimension]", dimensionname);
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
