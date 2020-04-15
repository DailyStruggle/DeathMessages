package DeathMessages.types;

import java.util.HashSet;

public class Message {
	public String death;
	public String hover;
	public HashSet<String> types;
	public HashSet<String> names;
	public Double chance;
	
	public Message(String death, Double chance) {
		this.names = new HashSet<String>();
		this.death = death;
		this.hover = null;
		this.chance = chance;
	}

	public Message(String death, HashSet<String> types, HashSet<String> names, Double chance) {
		this.names = (HashSet<String>) names.clone();
		this.death = death;
		this.hover = null;
		this.chance = chance;
	}

	public Message(String death, String hover, Double chance) {
		this.death = death;
		this.hover = hover;
		this.chance = chance;
	}

	public Message(String death, String hover, HashSet<String> types, Double chance) {
		this.types = (HashSet<String>) types.clone();
		this.death = death;
		this.hover = hover;
		this.chance = chance;
	}

	public Message(String death, String hover, HashSet<String> types, HashSet<String> names, Double chance) {
		this.names = (HashSet<String>) names.clone();
		this.types = (HashSet<String>) types.clone();
		this.death = death;
		this.hover = hover;
		this.chance = chance;
	}
}
