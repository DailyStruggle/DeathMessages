package DeathMessages.bukkitTasks;

import DeathMessages.DeathMessages.DeathMessages;
import DeathMessages.tools.LocalPlaceholders;
import DeathMessages.types.DamageData;
import DeathMessages.types.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnDeathChecks extends BukkitRunnable{
	private DeathMessages plugin;
	private Player player;
	private Entity attacker = null;
	EntityDamageEvent.DamageCause cause;
	
	public OnDeathChecks(DeathMessages plugin, Player player, Entity attacker, EntityDamageEvent.DamageCause cause) {
		this.plugin = plugin;
		this.player = player;
		this.attacker = attacker;
		this.cause = cause;
	}

	public OnDeathChecks(DeathMessages plugin, Player player, EntityDamageEvent.DamageCause cause) {
		this.plugin = plugin;
		this.player = player;
		this.cause = cause;
	}

	@Override
	public void run() {
		DamageData lastCombat = this.plugin.getLastEntityDamageData(this.player);

		Long time = System.currentTimeMillis();

		Boolean inCombat = ( (lastCombat != null) && ( ( time - lastCombat.time < this.plugin.getPluginConfig().config.getLong("combatTime")*1000) ) );

		Message msg;
		if(this.attacker != null) {
			if(this.attacker instanceof Player){
				msg = this.plugin.getPluginConfig().pickRandomMessage(this.cause, ((Player)attacker).getType(), ((Player)attacker).getName());
			}else msg = this.plugin.getPluginConfig().pickRandomMessage(this.cause, attacker.getType(), attacker.getName());
		}
		else msg = this.plugin.getPluginConfig().pickRandomMessage(this.cause, null, null);

		if(inCombat) this.attacker = lastCombat.otherGuy;

		new AnnounceDeath(this.plugin,this.player,this.attacker,msg,inCombat).runTaskAsynchronously(this.plugin);
	}
}
