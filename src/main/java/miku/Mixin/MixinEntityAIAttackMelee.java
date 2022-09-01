package miku.Mixin;

import miku.Utils.InventoryUtil;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityAIAttackMelee.class)
public class MixinEntityAIAttackMelee {
    @Shadow
    protected EntityCreature attacker;

    @Inject(at = @At("HEAD"), method = "shouldExecute", cancellable = true)
    public void shouldExecute(CallbackInfoReturnable<Boolean> cir) throws NoSuchFieldException, ClassNotFoundException {
        EntityLivingBase entitylivingbase = attacker.getAttackTarget();
        if (InventoryUtil.isMiku(entitylivingbase)) cir.setReturnValue(false);
    }
}
