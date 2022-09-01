package miku.Miku;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MikuCombatTracker extends CombatTracker {
    public MikuCombatTracker(EntityLivingBase fighterIn) {
        super(fighterIn);
    }

    @Override
    public void trackDamage(@Nullable DamageSource damageSrc, float healthIn, float damageAmount) {
    }

    @Override
    @Nonnull
    public ITextComponent getDeathMessage() {
        return new TextComponentTranslation("info.dead");
    }

    @Override
    @Nullable
    public EntityLivingBase getBestAttacker() {
        return null;
    }
}
