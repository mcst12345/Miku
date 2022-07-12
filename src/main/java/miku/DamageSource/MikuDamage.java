package miku.DamageSource;

import net.minecraft.util.DamageSource;

public class MikuDamage {
    public static DamageSource MikuDamage = new DamageSource("miku").setDamageBypassesArmor().setMagicDamage().setFireDamage().setDamageIsAbsolute().setDamageAllowedInCreativeMode().setExplosion();
}
