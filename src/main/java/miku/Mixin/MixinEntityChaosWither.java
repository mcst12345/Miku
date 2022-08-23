package miku.Mixin;

import com.chaoswither.entity.EntityChaosWither;
import miku.Interface.MixinInterface.IEntityChaosWither;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityChaosWither.class, remap = false)
public class MixinEntityChaosWither implements IEntityChaosWither {
    protected boolean isMikuDead = false;

    @Override
    public void SetMikuDead() {
        isMikuDead = true;
    }

    @Override
    public boolean IsMikuDead() {
        return isMikuDead;
    }

    @Inject(at = @At("HEAD"), method = "func_70636_d", remap = false, cancellable = true)
    public void func_70636_d(CallbackInfo ci) {
        if (isMikuDead) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "func_70619_bc", remap = false, cancellable = true)
    protected void func_70619_bc(CallbackInfo ci) {
        if (isMikuDead) ci.cancel();
    }
}
