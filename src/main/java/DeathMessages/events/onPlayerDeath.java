package DeathMessages.events;

import DeathMessages.DeathMessages.DeathMessages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import DeathMessages.types.DamageData;
import DeathMessages.types.Message;

public class onPlayerDeath implements Listener {
	private DeathMessages plugin;

	public onPlayerDeath(DeathMessages plugin){
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(event.getEntity().hasPermission("deathMsg.defaultMessage")) return;
		if(event.getEntity().hasPermission("deathMsg.ignore")) {
			event.setDeathMessage("");
			return;
		}
		String cause = event.getEntity().getPlayer().getLastDamageCause().getCause().toString();

		Long time = System.currentTimeMillis();

		DamageData lastCombat = this.plugin.getLastEntityDamageData(event.getEntity());

		Boolean inCombat = ( (lastCombat != null) && time < lastCombat.time + ( this.plugin.getPluginConfig().config.getLong("combatTime", 5)*1000 ) );

		Message msg = this.plugin.getPluginConfig().pickRandomMessage( event.getEntity() );



		this.plugin.setLastDeathData(event.getEntity(), new DamageData(event.getEntity().getLastDamageCause().getEntity(),event.getEntity()));
	}
}
