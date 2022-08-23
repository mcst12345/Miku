package miku.DamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;

import javax.annotation.Nullable;

public class MikuDamage extends EntityDamageSource {
    public MikuDamage(@Nullable Entity damageSourceEntityIn) {
        super("miku", damageSourceEntityIn);
        this.setDamageBypassesArmor()
                .setMagicDamage()
                .setDamageIsAbsolute()
                .setDamageAllowedInCreativeMode();
    }
}
