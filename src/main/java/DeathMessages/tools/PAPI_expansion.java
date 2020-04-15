package DeathMessages.tools;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import DeathMessages.DeathMessages.DeathMessages;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PAPI_expansion extends PlaceholderExpansion{
	private DeathMessages plugin;

	@Override
    public boolean canRegister(){
        return true;
    }

	@Override
    public boolean register(){
		if(!canRegister()){
            return false;
        }

        plugin = (DeathMessages) Bukkit.getPluginManager().getPlugin("DeathMessages");

        if(plugin == null){
            return false;
        }

        return super.register();
    }

	@Override
	public String getAuthor() {
		return plugin.getDescription().getAuthors().toString();
	}

	@Override
	public String getIdentifier() {
		return "DeathMessages";
	}

	@Override
    public String getVersion(){
        return "1.0.0";
    }

	@Override
    public String onRequest(OfflinePlayer player, String identifier){
		if(player == null){
            return "";
        }

		// %DeathMessages_last_death_x%
        if(identifier.equalsIgnoreCase("last_death_x")){

        }

        // %DeathMessages_last_death_y%
        if(identifier.equalsIgnoreCase("last_death_y")){

        }

        // %DeathMessages_last_death_z%
        if(identifier.equalsIgnoreCase("last_death_z")){

        }

        // %DeathMessages_last_death_entity_type%
        if(identifier.equalsIgnoreCase("last_death_entity_type")){

        }

        // %DeathMessages_last_death_entity_name%
        if(identifier.equalsIgnoreCase("last_death_entity_name")){

        }

        return null;
    }

	@Override
    public String onPlaceholderRequest(Player player, String identifier){
		if(player == null){
            return "";
        }

		// %OnePlayerSleep_sleeping_player_count%
        if(identifier.equalsIgnoreCase("sleeping_player_count")){

        }

        // %OnePlayerSleep_total_player_count%
        if(identifier.equalsIgnoreCase("total_player_count")){

        }

        return null;
    }
}
