package miku.Mixin;

import miku.MixinInterface.IEntityLivingBase;
import miku.Utils.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity implements IEntityLivingBase {

    @Final
    @Shadow
    private static final DataParameter<Float> HEALTH = EntityDataManager.createKey(EntityLivingBase.class, DataSerializers.FLOAT);

    @Shadow
    protected int recentlyHit;

    @Shadow
    public abstract float getMaxHealth();

    public MixinEntityLivingBase(World worldIn) {
        super(worldIn);
    }

    @Override
    public void ZeroHealth() {
        System.out.println("Successfully fucked MC");
        this.recentlyHit = 60;
        this.dataManager.set(HEALTH, MathHelper.clamp(0, 0.0F, 0));
    }

    @Inject(at = @At("HEAD"), method = "setHealth", cancellable = true)
    public void setHealth(float health, CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityLivingBase) (Object) this)) {
            if (this.getMaxHealth() > 0.0F)
                this.dataManager.set(HEALTH, MathHelper.clamp(this.getMaxHealth(), this.getMaxHealth(), this.getMaxHealth()));
            else {
                this.dataManager.set(HEALTH, MathHelper.clamp(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE));
            }
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "damageEntity", cancellable = true)
    protected void damageEntity(DamageSource damageSrc, float damageAmount, CallbackInfo ci) {
        if (InventoryUtil.isMiku((EntityLivingBase) (Object) this)) {
            System.out.println("Successfully fucked MC");
            ci.cancel();
        }
    }

}
