package miku.Interface.MixinInterface;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nullable;

public interface IEntityLivingBase {
    void ZeroHealth();

    void TrueOnDeath(@Nullable EntityPlayer killer);

    void TrueAttackEntityFrom(@Nullable EntityPlayer killer);

    void TrueDamageEntity(@Nullable EntityPlayer killer);

    void ZeroAbsorptionAmount();

    void ZeroMaxHealth();

    void TrueClearActivePotions();

    void TrueAddPotionEffect(PotionEffect potioneffect);

    void ZeroAiMoveSpeed();

    void ZeroMovementSpeed();

    void NullLastAttackedEntity();
}
