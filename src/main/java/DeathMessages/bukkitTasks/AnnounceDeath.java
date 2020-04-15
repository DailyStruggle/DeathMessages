package DeathMessages.bukkitTasks;

import DeathMessages.DeathMessages.DeathMessages;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import DeathMessages.tools.Config;
import DeathMessages.types.Message;

public class AnnounceDeath extends BukkitRunnable {
    private DeathMessages plugin;
    private Config config;
    private Player player;

    public AnnounceDeath(DeathMessages plugin, Player player) {
        this.plugin = plugin;
        this.config = config = plugin.getPluginConfig();
        this.player = player;
    }
    @Override
    public void run() {
        Boolean doOtherWorld= config.config.getBoolean("doOtherWorlds");
        Boolean doOtherDim = config.config.getBoolean("doOtherDimensions");
        Boolean perPlayer = config.config.getBoolean("randomPerPlayer");
        Message resMsg = new Message( "", "",0.0);
        ConfigurationSection worlds = this.config.messages.getConfigurationSection("worlds");
        String worldName = this.player.getWorld().getName().replace("_nether","").replace("the_end","");
        if(!worlds.contains(worldName)) {
            worlds.set(worldName, "&a" + worldName);
        }
        worldName = worlds.getString(worldName);

    }
}
