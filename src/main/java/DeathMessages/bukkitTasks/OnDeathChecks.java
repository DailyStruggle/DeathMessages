package DeathMessages.bukkitTasks;

import DeathMessages.DeathMessages.DeathMessages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import DeathMessages.tools.Config;

public class OnDeathChecks extends BukkitRunnable{
	private DeathMessages plugin;
	private Config config;
	private Player player;
	private Boolean bypassDeath = false;
	
	public OnDeathChecks(DeathMessages plugin, Config config, Player player) {
		this.plugin = plugin;
		this.config = config;
		this.player = player;

	}

	public OnDeathChecks(DeathMessages plugin, Config config, Player player, Boolean bypassDeath) {
		this.plugin = plugin;
		this.config = config;
		this.player = player;
		this.bypassDeath = bypassDeath;
	}

	@Override
	public void run() {

	}

}
