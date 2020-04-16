package DeathMessages.events;

import DeathMessages.DeathMessages.DeathMessages;
import DeathMessages.bukkitTasks.OnDeathChecks;
import DeathMessages.types.DamageData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class onPlayerDeath implements Listener {
	private DeathMessages plugin;

	public onPlayerDeath(DeathMessages plugin){
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(event.getEntity().hasPermission("deathMsg.defaultMessage")) return;
		//event.setDeathMessage("");
		switch(event.getEntity().getLastDamageCause().getCause()){
			case ENTITY_ATTACK:
			case ENTITY_EXPLOSION:
			case ENTITY_SWEEP_ATTACK:
			case THORNS:
			case PROJECTILE:
			{
				break;
			}
			default:
			{
				new OnDeathChecks(this.plugin, (Player)event.getEntity(), event.getEntity().getLastDamageCause().getCause()).runTaskAsynchronously(this.plugin);
			}
		}
	}
}
