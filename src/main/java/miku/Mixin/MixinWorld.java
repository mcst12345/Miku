package miku.Mixin;

import miku.Entity.Hatsune_Miku;
import miku.utils.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(World.class)
public abstract class MixinWorld {
    @Shadow
    public abstract void removeEntity(Entity entityIn);

    @Inject(at = @At("HEAD"), method = "setEntityState")
    public void setEntityState(Entity entityIn, byte state, CallbackInfo ci) {
        if (entityIn instanceof Hatsune_Miku) {
            if (state == (byte) 3) {
                entityIn.isDead = false;
                entityIn = null;
                return;
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "unloadEntities")
    public void unloadEntities(Collection<Entity> entityCollection, CallbackInfo ci) {
        for (Entity en : entityCollection) {
            if (InventoryUtil.isMiku(en)) {
                return;
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "onEntityRemoved")
    public void onEntityRemoved(Entity entityIn, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            entityIn.isDead = false;
            entityIn = null;
            return;
        }
    }

    @Inject(at = @At("HEAD"), method = "removeEntity")
    public void removeEntity(Entity entityIn, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            entityIn.isDead = false;
            entityIn = null;
            return;
        }
    }

    @Inject(at = @At("HEAD"), method = "removeEntityDangerously")
    public void removeEntityDangerously(Entity entityIn, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            entityIn.isDead = false;
            entityIn = null;
            return;
        }
    }
}
