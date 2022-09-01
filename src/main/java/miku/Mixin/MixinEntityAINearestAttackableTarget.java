package miku.Mixin;

import miku.Utils.InventoryUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityAINearestAttackableTarget.class)
public class MixinEntityAINearestAttackableTarget<T extends EntityLivingBase> {
    @Shadow
    protected T targetEntity;

    @Inject(at = @At("HEAD"), method = "shouldExecute", cancellable = true)
    public void shouldExecute(CallbackInfoReturnable<Boolean> cir) throws NoSuchFieldException, ClassNotFoundException {
        if (InventoryUtil.isMiku(targetEntity)) cir.setReturnValue(false);
    }
}
