package DeathMessages.types;

import org.bukkit.entity.Entity;

public class DamageData {
    public Long time = Long.valueOf(0);

    public Entity otherGuy;

    public DamageData(Entity otherGuy){
        this.otherGuy = otherGuy;

        this.time = System.currentTimeMillis();
    }
}
