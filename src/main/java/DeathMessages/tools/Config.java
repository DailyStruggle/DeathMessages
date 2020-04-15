package DeathMessages.tools;

import DeathMessages.DeathMessages.DeathMessages;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import DeathMessages.types.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.*;

public class Config {
	public FileConfiguration config;
	public FileConfiguration messages;
	public String version;
	public List<String> messageNames;

	private Map<DamageCause, MessageList> dmgMessages; //message list for each cause of death

	private DeathMessages plugin;
	
	private Boolean hasPAPI = false;
	
	public Config(DeathMessages plugin) {
		this.plugin = plugin;
		String s = this.plugin.getServer().getClass().getPackage().getName();
		this.version = s.substring(s.lastIndexOf('.')+1);
	}
	
	
	public void refreshConfigs() {
		this.messageNames = new ArrayList<String>();
		this.dmgMessages = new HashMap<DamageCause, MessageList>();
		this.hasPAPI = false;

		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			this.hasPAPI = true;
		}
		
		//load config.yml file
		File f = new File(this.plugin.getDataFolder(), "config.yml");
		if(!f.exists()) {
			plugin.saveResource("config.yml", false);
		}
		this.config = YamlConfiguration.loadConfiguration(f);

		//load messages.yml file
		f = new File(this.plugin.getDataFolder(), "messages.yml");
		if(!f.exists()) {
			plugin.saveResource("messages.yml", false);
		}
		this.messages = YamlConfiguration.loadConfiguration(f);

		if( 	(this.messages.getDouble("version") < 1.0) ) {
			Bukkit.getConsoleSender().sendMessage("�b[deathMsg] old messages.yml detected. Getting a newer version");
			this.renameFileInPluginDir("messages.yml","messages.old.yml");
			
			this.plugin.saveResource("messages.yml", false);
			this.messages = YamlConfiguration.loadConfiguration(f);
		}
		if( 	(this.config.getDouble("version") < 1.0) ) {
			Bukkit.getConsoleSender().sendMessage("�b[deathMsg] old config.yml detected. Updating");
			
			updateConfig();
			
			f = new File(this.plugin.getDataFolder(), "config.yml");
			this.config = YamlConfiguration.loadConfiguration(f);
		}

		Set<String> allMessageNames = this.messages.getConfigurationSection("messages").getKeys(false);
		for(String messageName : allMessageNames){
			this.messageNames.add(messageName);
			ConfigurationSection message = this.messages.getConfigurationSection("messages").getConfigurationSection(messageName);
			List<String> causes = message.getStringList("causes");
			HashSet<String> types;
			if(message.contains("entityTypes")) {
				types = new HashSet<String>(message.getStringList("entityTypes"));
			} else types = null;

			HashSet<String> names;
			if(message.contains("entityNames")) {
				names = new HashSet<String>(message.getStringList("entityNames"));
			} else names = null;

			String death = message.getString("death");
			String hover = message.getString("hover");

			Double chance = message.getDouble("chance");

			for(String cause : causes){
				DamageCause damageCause = valueOf(cause);
				if(!this.dmgMessages.containsKey(damageCause)){
					this.dmgMessages.put(damageCause,new MessageList());
				}
				if(types != null && names != null) {
					this.dmgMessages.get(damageCause).put(new Message(death,hover,types,names,chance));
				}
				else if (types != null && names == null) {
					this.dmgMessages.get(damageCause).put(new Message(death,hover,types,chance));
				}
				else if (types == null && names == null) {
					this.dmgMessages.get(damageCause).put(new Message(death,hover,chance));
				}
			}
		}
	}
	
	public Boolean hasPAPI() {
		return this.hasPAPI;
	}

	public Message pickRandomMessage( Player player) {
		DamageCause cause = null;
		String entityName = null;
		EntityType entityType = null;
		if(player.getLastDamageCause() != null) {
			cause = player.getLastDamageCause().getCause();
			entityName = player.getLastDamageCause().getEntity().getName();
			entityType = player.getLastDamageCause().getEntityType();
		}


		//pick a message from a subset of damage causes

		return null;
	}

	public Message getMessage(String name, Player player) {
		Message res;
		ConfigurationSection msg = this.messages.getConfigurationSection("messages").getConfigurationSection(name);
		return new Message(msg.getString("death"), msg.getString("hover"), msg.getDouble("chance"));
	}
	
	//update config files based on version number
	private void updateConfig() {
		this.renameFileInPluginDir("config.yml","config.old.yml");
		plugin.saveResource("config.yml", false);
		Map<String, Object> oldValues = this.config.getValues(false);
		// Read default config to keep comments
		ArrayList<String> linesInDefaultConfig = new ArrayList<String>();
		try {
			Scanner scanner = new Scanner(
					new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "config.yml"));
			while (scanner.hasNextLine()) {
				linesInDefaultConfig.add(scanner.nextLine() + "");
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		ArrayList<String> newLines = new ArrayList<String>();
		for (String line : linesInDefaultConfig) {
			String newline = line;
			if (line.startsWith("version:")) {
				newline = "version: 1.0";
			} else {
				for (String node : oldValues.keySet()) {
					if (line.startsWith(node + ":")) {
						String quotes = "";
						newline = node + ": " + quotes + oldValues.get(node).toString() + quotes;
						break;
					}
				}
			}
			if (newline != null)
				newLines.add(newline);
		}

		FileWriter fw;
		String[] linesArray = newLines.toArray(new String[linesInDefaultConfig.size()]);
		try {
			fw = new FileWriter(plugin.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
			for (int i = 0; i < linesArray.length; i++) {
				fw.write(linesArray[i] + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}
	
	private void renameFileInPluginDir(String oldName, String newName) {
		File oldFile = new File(this.plugin.getDataFolder().getAbsolutePath() + File.separator + oldName);
		File newFile = new File(this.plugin.getDataFolder().getAbsolutePath() + File.separator + newName);
		try {
			Files.deleteIfExists(newFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		oldFile.getAbsoluteFile().renameTo(newFile.getAbsoluteFile());
	}
}
