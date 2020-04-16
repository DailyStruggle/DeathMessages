package DeathMessages.DeathMessages;

import DeathMessages.commands.*;
import DeathMessages.events.onEntityDamage;
import DeathMessages.events.onPlayerDeath;
import DeathMessages.tools.Config;
import DeathMessages.tools.Metrics;
import DeathMessages.tools.PAPI_expansion;
import DeathMessages.types.DamageData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DeathMessages extends JavaPlugin implements Listener{
	private Config config;

	private Map<Player, DamageData> lastDeathData;
	private Map<Player, DamageData> lastEntityDamageData;

	@Override
	public void onEnable() {
		this.config = new Config(this);
		this.config.refreshConfigs();

		lastDeathData = new HashMap<Player, DamageData>();
		lastEntityDamageData = new HashMap<Player, DamageData>();


		getCommand("deathMsg").setExecutor(new DeathMsg(this));
		getCommand("deathMsg help").setExecutor(new Help());
		getCommand("deathMsg Reload").setExecutor(new Reload(this, this.config));
		getCommand("deathMsg test").setExecutor(new Test(this));

		getCommand("deathMsg").setTabCompleter(new TabComplete(this.config));

		getServer().getPluginManager().registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new onPlayerDeath(this), this);
		getServer().getPluginManager().registerEvents(new onEntityDamage(this), this);

		if(this.config.hasPAPI()) {
			new PAPI_expansion().register();
		}

		int pluginId = 7097; // <-- Replace with the id of your plugin!
		Metrics metrics = new Metrics(this, pluginId);
	}
	
	@Override
	public void onDisable() {
	
	}
	
	public Config getPluginConfig() {
		return this.config;
	}

	public DamageData getLastDeathData(Player p ) {
		return this.lastDeathData.get(p);
	}

	public void setLastDeathData( Player p, DamageData d ) {
		if(this.lastDeathData.containsKey(p)) this.lastDeathData.remove(p);
		this.lastDeathData.put(p,d);
	}

	public DamageData getLastEntityDamageData(Player p ) {
		if(this.lastEntityDamageData.containsKey(p)) return this.lastEntityDamageData.get(p);
		else return null;
	}

	public void setLastEntityDamageData( Player p, @Nullable DamageData d ) {
		this.lastEntityDamageData.remove(p);
		this.lastEntityDamageData.put(p,d);
	}
}
