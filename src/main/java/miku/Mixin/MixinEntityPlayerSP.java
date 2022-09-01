package miku.Mixin;

import miku.Utils.InventoryUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Inject(at = @At("HEAD"), method = "damageEntity", cancellable = true)
    protected void damageEntity(DamageSource damageSrc, float damageAmount, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (InventoryUtil.isMiku((EntityPlayerSP) (Object) this)) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "attackEntityFrom", cancellable = true)
    public void attackEntityFrom(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) throws NoSuchFieldException, ClassNotFoundException {
        if (InventoryUtil.isMiku((EntityPlayerSP) (Object) this)) {
            cir.setReturnValue(false);
        }
    }
}
