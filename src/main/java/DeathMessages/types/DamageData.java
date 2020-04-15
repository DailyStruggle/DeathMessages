package DeathMessages.types;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageData {
    public Double x;
    public Double y;
    public Double z;

    public EntityDamageEvent cause;

    public Long time = Long.valueOf(0);

    public Entity attacker;
    public Entity attackee;

    public DamageData(){};


    public DamageData(Entity attacker, Entity attackee){
        this.attackee = attackee;
        this.attacker = attacker;

        this.cause = attackee.getLastDamageCause();
        this.time = System.currentTimeMillis();
    }
}
