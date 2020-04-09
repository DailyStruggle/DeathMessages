package tools;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;

import DeathMessages.DeathMessages;
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
		return "OnePlayerSleep";
	}

	@Override
    public String getVersion(){
        return "1.3.5";
    }
	
	@Override
    public String onRequest(OfflinePlayer player, String identifier){
		if(player == null){
            return "";
        }
        
		// %OnePlayerSleep_sleeping_player_count%
        if(identifier.equals("sleeping_player_count")){
            
        }
        
        // %OnePlayerSleep_total_player_count%
        if(identifier.equals("total_player_count")){
            
        }
        
        return null;
    }
	
	@Override
    public String onPlaceholderRequest(Player player, String identifier){
		if(player == null){
            return "";
        }
        
		// %OnePlayerSleep_sleeping_player_count%
        if(identifier.equals("sleeping_player_count")){
            
        }
        
        // %OnePlayerSleep_total_player_count%
        if(identifier.equals("total_player_count")){
            
        }
        
        return null;
    }
}
