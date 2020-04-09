package types;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Message {
	public TextComponent msg; //message to give to player
	public String hoverText; 
	public String clickResponse;
	public Double chance;
	
	public Message(String death, String hover, String clickResponse, Double chance) {
		this.msg = new TextComponent(death);
		this.msg.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( hover ).create()));
		this.msg.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sleepwakeup"));
		this.clickResponse = clickResponse;
		this.hoverText = hover;
		this.chance = chance;
	}
}
