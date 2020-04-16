package DeathMessages.bukkitTasks;

import DeathMessages.DeathMessages.DeathMessages;
import DeathMessages.tools.Config;
import DeathMessages.tools.LocalPlaceholders;
import DeathMessages.types.Message;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AnnounceDeath extends BukkitRunnable {
    private DeathMessages plugin;
    private Config config;
    private Player player;
    private Entity attacker;
    private Boolean inCombat = false;
    Message message = null;

    public AnnounceDeath(DeathMessages plugin, Player player, Entity attacker, Message message, Boolean inCombat) {
        this.plugin = plugin;
        this.config = plugin.getPluginConfig();
        this.player = player;
        this.attacker = attacker;
        this.message = message;
        this.inCombat = inCombat;
    }

    public AnnounceDeath(DeathMessages plugin, Player player, Player attacker, Message message, Boolean inCombat) {
        this.plugin = plugin;
        this.config = plugin.getPluginConfig();
        this.player = player;
        this.attacker = attacker;
        this.message = message;
        this.inCombat = inCombat;
    }

    @Override
    public void run() {
        Boolean globalMsg = this.config.config.getBoolean("globalMsg");
        Boolean doOtherWorld= this.config.config.getBoolean("doOtherWorlds");
        Boolean doOtherDim = this.config.config.getBoolean("doOtherDimensions");

        String death;
        String combat = "";
        String hover;

        if (this.inCombat == false) {
            death = LocalPlaceholders.fillPlaceHolders(
                    this.message.death,
                    this.player,
                    this.plugin);
            hover = LocalPlaceholders.fillPlaceHolders(
                    this.message.hover,
                    this.player,
                    this.plugin);
            death.replace("[combat]",combat);
        }
        else{
            death = LocalPlaceholders.fillPlaceHolders(
                    this.message.death,
                    this.player,
                    this.attacker,
                    this.plugin);
            hover = LocalPlaceholders.fillPlaceHolders(
                    this.message.hover,
                    this.player,
                    this.attacker,
                    this.plugin);
            if(this.message.combat != null) {
                combat = LocalPlaceholders.fillPlaceHolders(
                        this.message.combat,
                        this.player,
                        this.attacker,
                        this.plugin);
            }
            death = death.replace("[combat]",combat);
        }
        if (config.hasPAPI()) death = PlaceholderAPI.setPlaceholders(player, death);
        if (config.hasPAPI()) hover = PlaceholderAPI.setPlaceholders(player, hover);
        this.message = new Message(death,combat, hover, this.message.chance);

        if(!globalMsg) new SendMessage(this.plugin, config, this.message, player, player).runTaskAsynchronously(this.plugin);
        else {
            ConfigurationSection worlds = this.config.messages.getConfigurationSection("worlds");
            for (World w : plugin.getServer().getWorlds()) {
                String worldName = w.getName().replace("_nether", "").replace("the_end", "");
                if (!worlds.contains(worldName)) worlds.set(worldName, "&a" + worldName);
                //skip if player's world isn't the same as receiver's world, disregarding the difference between dimension names
                if (!doOtherWorld && !player.getWorld().getName().replace("_nether", "").replace("the_end", "").equals(worldName)) continue;
                //skip if player is in another dimension
                if (!doOtherDim && !player.getWorld().getEnvironment().equals(w.getEnvironment())) continue;

                for (Player p : w.getPlayers()) {
                    new SendMessage(this.plugin, config, this.message, player, p).runTaskAsynchronously(this.plugin);
                }
            }
        }
    }
}