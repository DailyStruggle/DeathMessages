package DeathMessages.bukkitTasks;

import DeathMessages.DeathMessages.DeathMessages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveLastEntry extends BukkitRunnable {
    private DeathMessages plugin;
    private Player player;

    public RemoveLastEntry(DeathMessages plugin, Player player){
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public void run() {

    }
}
