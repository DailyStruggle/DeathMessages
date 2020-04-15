package DeathMessages.events;

import DeathMessages.DeathMessages.DeathMessages;
import DeathMessages.types.DamageData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class onEntityDamage implements Listener {
    private DeathMessages plugin;

    public onEntityDamage(DeathMessages plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Boolean isPlayer = event.getEntityType() == EntityType.PLAYER;
        Boolean fromPlayer = event.getEntity().getLastDamageCause().getEntityType() == EntityType.PLAYER;

        if(!isPlayer && !fromPlayer) return;

        if( isPlayer && ((Player) event.getEntity()).hasPermission("deathMsg.ignore") ) return;
        if( fromPlayer && ((Player) event.getEntity().getLastDamageCause().getEntity()).hasPermission("deathMsg.ignore") ) return;


        if(isPlayer && event.getEntity().getLastDamageCause().getEntity() == null) return;

        if(event.getFinalDamage() > 0) {
            if(isPlayer) this.plugin.setLastEntityDamageData((Player)event.getEntity(),new DamageData(event.getEntity().getLastDamageCause().getEntity(), event.getEntity()));
            if(fromPlayer) this.plugin.setLastEntityDamageData((Player)event.getEntity().getLastDamageCause().getEntity(),new DamageData(event.getEntity().getLastDamageCause().getEntity(), event.getEntity()));
        }
    }
}
