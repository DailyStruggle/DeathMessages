package DeathMessages.events;

import DeathMessages.DeathMessages.DeathMessages;
import DeathMessages.bukkitTasks.OnDeathChecks;
import DeathMessages.types.DamageData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class onEntityDamage implements Listener {
    private DeathMessages plugin;

    public onEntityDamage(DeathMessages plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Boolean isPlayer = event.getEntityType() == EntityType.PLAYER;
        Boolean fromPlayer = event.getDamager().getType() == EntityType.PLAYER;

        if(!isPlayer && !fromPlayer) return;

        if( isPlayer && ((Player) event.getEntity()).hasPermission("deathMsg.ignore") ) return;
        if( fromPlayer && ((Player) event.getDamager()).hasPermission("deathMsg.ignore") ) return;

        if(isPlayer) {
            Player p = ((Player)event.getEntity());
            DamageData d = new DamageData(event.getDamager());
            this.plugin.setLastEntityDamageData(p,d);
        }
        if(fromPlayer) {
            Player p = ((Player)event.getDamager());
            DamageData d = new DamageData(event.getEntity());
            this.plugin.setLastEntityDamageData(p,d);
        }

        if(     isPlayer &&
                (((Player) event.getEntity()).getHealth() - event.getFinalDamage()) < 0 ){
            new OnDeathChecks(this.plugin, (Player)event.getEntity(), event.getDamager(), event.getCause()).runTaskAsynchronously(this.plugin);
        }
    }
}
