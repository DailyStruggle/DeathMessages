package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import DeathMessages.DeathMessages;
import types.Message;

public class Config {
	public FileConfiguration config;
	public FileConfiguration messages;
	public String version;
	
	private Map<DamageCause,ArrayList<Message>> dmgMessages; //message list for each cause of death
	private Map<DamageCause,ArrayList<Double>> chanceRanges; // matching chance ranges for the message lists
	
	private DeathMessages plugin;
	
	private Boolean hasPAPI;
	
	public Config(DeathMessages plugin) {
		this.plugin = plugin;
		String s = this.plugin.getServer().getClass().getPackage().getName();
		this.version = s.substring(s.lastIndexOf('.')+1);
	}
	
	
	public void refreshConfigs() {
		this.hasPAPI = false;
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			this.hasPAPI = true;
		}
		
		//load config.yml file
		File f = new File(this.plugin.getDataFolder(), "config.yml");
		if(f.exists())
			this.config = YamlConfiguration.loadConfiguration(f);
		else
		{
			plugin.saveResource("config.yml", false);
			this.config = YamlConfiguration.loadConfiguration(f);
		}
		
		//load messages.yml file
		f = new File(this.plugin.getDataFolder(), "messages.yml");
		if(f.exists())
			this.messages = YamlConfiguration.loadConfiguration(f);
		else
		{
			plugin.saveResource("messages.yml", false);
			this.messages = YamlConfiguration.loadConfiguration(f);
		}
		
		if( 	(this.messages.getDouble("version") < 1.0) ) {
			Bukkit.getConsoleSender().sendMessage("§b[OnePlayerSleep] old messages.yml detected. Getting a newer version");
			this.renameFileInPluginDir("messages.yml","messages.old.yml");
			
			this.plugin.saveResource("messages.yml", false);
			this.messages = YamlConfiguration.loadConfiguration(f);
		}
		if( 	(this.config.getDouble("version") < 1.0) ) {
			Bukkit.getConsoleSender().sendMessage("§b[OnePlayerSleep] old config.yml detected. Updating");
			
			updateConfig();
			
			f = new File(this.plugin.getDataFolder(), "config.yml");
			this.config = YamlConfiguration.loadConfiguration(f);
		}
	}
	
	public Boolean hasPAPI() {
		return this.hasPAPI;
	}
	
	public Message pickRandomMessage() {
		
		return null;
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
				newline = "version: 1.4";
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
