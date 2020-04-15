package DeathMessages.bukkitTasks;

import DeathMessages.DeathMessages.DeathMessages;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import DeathMessages.tools.Config;
import DeathMessages.tools.LocalPlaceholders;
import DeathMessages.types.Message;


//send message to a player
//choose a message if per-player randomization is active
public class SendMessage extends BukkitRunnable{
	private DeathMessages plugin;
	private Config config;
	private Message message;
	private Player sourcePlayer;
	private Player targetPlayer;
	boolean doRandom;
	
	public SendMessage(DeathMessages plugin, Config config, Player sourcePlayer, Player targetPlayer) {
		this.plugin = plugin;
		this.config = config;
		this.sourcePlayer = sourcePlayer;
		this.targetPlayer = targetPlayer;
		this.doRandom = true;
	}
	
	public SendMessage(DeathMessages plugin, Config config, Message message, Player sourcePlayer, Player targetPlayer) {
		this.plugin = plugin;
		this.config = config;
		this.message = message;
		this.sourcePlayer = sourcePlayer;
		this.targetPlayer = targetPlayer;
		this.doRandom = false;
	}
	
	@Override
	public void run() {
		if(this.targetPlayer.hasPermission("deathMsg.ignore")) {
			return;
		}
		String death = LocalPlaceholders.fillPlaceHolders(
				this.message.death,
				this.sourcePlayer,
				this.config);
		String hover = LocalPlaceholders.fillPlaceHolders(
				this.message.hover,
				this.sourcePlayer,
				this.config);
		if(this.config.hasPAPI()) death = PlaceholderAPI.setPlaceholders(this.targetPlayer, death);
		if(this.config.hasPAPI()) death = PlaceholderAPI.setPlaceholders(this.targetPlayer, hover);
		this.message = new Message(death, hover, this.message.chance);
		TextComponent msg = new TextComponent(death);
		if(this.sourcePlayer == this.targetPlayer ) msg.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( this.message.hover ).create()));

		this.targetPlayer.spigot().sendMessage(msg);
	}
}
