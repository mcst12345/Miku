package miku.Mixin;

import com.chaoswither.entity.EntityChaosWither;
import miku.Interface.MixinInterface.IEntityChaosWither;
import org.spongepowered.asm.mixin.Mixin;

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
}
