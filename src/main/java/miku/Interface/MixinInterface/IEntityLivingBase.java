package miku.Interface.MixinInterface;

import net.minecraft.potion.PotionEffect;

public interface IEntityLivingBase {
    void ZeroHealth();

    void TrueOnDeath();

    void TrueAttackEntityFrom();

    void TrueDamageEntity();

    void ZeroAbsorptionAmount();

    void ZeroMaxHealth();

    void TrueClearActivePotions();

    void TrueAddPotionEffect(PotionEffect potioneffect);

    void ZeroAiMoveSpeed();

    void ZeroMovementSpeed();

    void NullLastAttackedEntity();
}
