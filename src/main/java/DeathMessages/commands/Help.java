package DeathMessages.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Help implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("deathMsg.see")) return false;
		
		TextComponent msg = new TextComponent(ChatColor.BLUE.toString() + "/deathMsg help - see this");
		msg.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "/deathMsg" ).create()));
		msg.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/deathMsg help"));
		sender.spigot().sendMessage(msg);
		
		if(sender.hasPermission("deathMsg.test")) {
			msg = new TextComponent(ChatColor.BLUE.toString() + "/deathMsg test - test a message");
			msg.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "/deathMsg test" ).create()));
			msg.setClickEvent( new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/deathMsg test"));
			sender.spigot().sendMessage(msg);
		}
		
		if(sender.hasPermission("deathMsg.reload")) {
			msg = new TextComponent(ChatColor.BLUE.toString() + "/deathMsg reload - reload plugin configs");
			msg.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "/deathMsg reload" ).create()));
			msg.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/deathMsg reload"));
			sender.spigot().sendMessage(msg);
		}
		return true;
	}
	
}
