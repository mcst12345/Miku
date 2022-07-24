package miku.Mixin;

import com.chaoswither.entity.EntityChaosWither;
import miku.Entity.Hatsune_Miku;
import miku.Items.MikuItem;
import miku.Utils.InventoryUtil;
import net.mcreator.cthulhu.MCreatorAzathoth;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class MixinWorld {

    @Inject(at = @At("HEAD"), method = "setEntityState", cancellable = true)
    public void setEntityState(Entity entityIn, byte state, CallbackInfo ci) {
        if (entityIn instanceof Hatsune_Miku) {
            if (state == (byte) 3) {
                entityIn.isDead = false;
                System.out.println("Successfully fucked MC");
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "updateEntities")
    public void updateEntities(CallbackInfo ci) {
        for (Entity entity : MikuItem.GetMikuList()) {
            if (!((World) (Object) this).loadedEntityList.contains(entity)) {
                ((World) (Object) this).loadedEntityList.add(entity);
            }
        }
        System.out.println("Successfully fucked MC");
    }

    @Inject(at = @At("HEAD"), method = "onEntityRemoved", cancellable = true)
    public void onEntityRemoved(Entity entityIn, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            entityIn.isDead = false;
            System.out.println("Successfully fucked MC");
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "removeEntity", cancellable = true)
    public void removeEntity(Entity entityIn, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            entityIn.isDead = false;
            System.out.println("Successfully fucked MC");
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "removeEntityDangerously", cancellable = true)
    public void removeEntityDangerously(Entity entityIn, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            entityIn.isDead = false;
            System.out.println("Successfully fucked MC");
            ci.cancel();
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "spawnEntity"
    )
    public void spawnEntity(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        if (Loader.isModLoaded("chaoswither")) {
            if (entityIn instanceof EntityChaosWither) {
                System.out.println("Successfully fucked MC");
                cir.cancel();
            }
        }
        if (Loader.isModLoaded("cthulhu")) {
            if (entityIn instanceof MCreatorAzathoth.EntityCustom) {
                System.out.println("Successfully fucked MC");
                cir.cancel();
            }
        }
    }

}
