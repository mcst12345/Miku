package miku.Mixin;

import com.chaoswither.entity.EntityChaosWither;
import com.google.common.collect.Lists;
import miku.Entity.Hatsune_Miku;
import miku.Items.Miku.MikuItem;
import miku.Utils.InventoryUtil;
import miku.Utils.Killer;
import net.mcreator.cthulhu.MCreatorAzathoth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Mixin(World.class)
public abstract class MixinWorld {

    @Final
    @Shadow
    protected final List<Entity> unloadedEntityList = Lists.newArrayList();

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

    @Inject(at = @At("HEAD"), method = "onEntityRemoved", cancellable = true)
    public void onEntityRemoved(Entity entityIn, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            if (entityIn instanceof Hatsune_Miku) {
                ((Hatsune_Miku) entityIn).Protect();
            }
            if (entityIn instanceof EntityPlayer) {
                MikuItem.Protect(entityIn);
            }
            System.out.println("Successfully fucked MC");
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "removeEntity", cancellable = true)
    public void removeEntity(Entity entityIn, CallbackInfo ci) {
        if (InventoryUtil.isMiku(entityIn)) {
            if (entityIn instanceof Hatsune_Miku) {
                ((Hatsune_Miku) entityIn).Protect();
            }
            if (entityIn instanceof EntityPlayer) {
                MikuItem.Protect(entityIn);
            }
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
            if (entityIn instanceof EntityChaosWither && Killer.NoMoreChaosWither()) {
                System.out.println("Successfully fucked MC & Chaoswither");
                cir.cancel();
            }
        }
        if (Loader.isModLoaded("cthulhu")) {
            if (entityIn instanceof MCreatorAzathoth.EntityCustom && Killer.NoMoreAzathoth()) {
                System.out.println("Successfully fucked MC & Cthulhu");
                cir.cancel();
            }
        }
    }

    /**
     * @author mcst12345
     * @reason Protect some entities
     */
    @Overwrite
    public void unloadEntities(Collection<Entity> entityCollection) {
        List<Entity> fucked = new ArrayList();
        for (Entity en : entityCollection) {
            if (!InventoryUtil.isMiku(en)) fucked.add(en);
        }
        unloadedEntityList.addAll(fucked);
    }
}
