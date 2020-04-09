package DeathMessages;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import events.onPlayerDeath;
import tools.Config;

public class DeathMessages extends JavaPlugin implements Listener{
	private Config config = new Config(this);
	
	@Override
	public void onEnable() {
	
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new onPlayerDeath(), this);
		
	}
	
	@Override
	public void onDisable() {
	
	}
	
	public Config getPluginConfig() {
		return this.config;
	}
}
