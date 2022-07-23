package miku.DamageSource;

import net.minecraft.util.DamageSource;

public class MikuDamage {
    public static final DamageSource MikuDamage = new DamageSource("miku").setDamageBypassesArmor().setMagicDamage().setDamageIsAbsolute().setDamageAllowedInCreativeMode();
}
