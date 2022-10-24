package miku.Mixin;

import miku.DamageSource.MikuDamage;
import miku.Interface.MixinInterface.IEvent;
import miku.Utils.Judgement;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// ((Event)(Object)this)
@Mixin(value = Event.class)
public class MixinEvent implements IEvent {
    protected boolean isCancelable = false;
    @Shadow(remap = false)
    private boolean isCanceled = false;

    @Inject(at = @At("HEAD"), method = "isCancelable", cancellable = true, remap = false)
    public void isCancelable(CallbackInfoReturnable<Boolean> cir) throws NoSuchFieldException, ClassNotFoundException {
        if (((Event) (Object) this) instanceof LivingDeathEvent) {
            LivingDeathEvent death = ((LivingDeathEvent) (Event) (Object) this);
            if (!Judgement.isMiku(death.getEntityLiving())) {
                if (death.getSource() instanceof MikuDamage || Judgement.isMiku(death.getSource().getTrueSource())) {
                    cir.setReturnValue(false);
                }
            }
        }
        if (((Event) (Object) this) instanceof LivingAttackEvent) {
            LivingAttackEvent Attack = ((LivingAttackEvent) (Event) (Object) this);
            if (!Judgement.isMiku(Attack.getEntityLiving())) {
                if (Attack.getSource() instanceof MikuDamage || Judgement.isMiku(Attack.getSource().getTrueSource())) {
                    cir.setReturnValue(false);
                }
            }
        }
        if (((Event) (Object) this) instanceof LivingDamageEvent) {
            LivingDamageEvent Damage = ((LivingDamageEvent) (Event) (Object) this);
            if (!Judgement.isMiku(Damage.getEntityLiving())) {
                if (Damage.getSource() instanceof MikuDamage || Judgement.isMiku(Damage.getSource().getTrueSource())) {
                    cir.setReturnValue(false);
                }
            }
        }
        if (((Event) (Object) this) instanceof LivingHurtEvent) {
            LivingHurtEvent Hurt = ((LivingHurtEvent) (Event) (Object) this);
            if (!Judgement.isMiku(Hurt.getEntityLiving())) {
                if (Hurt.getSource() instanceof MikuDamage || Judgement.isMiku(Hurt.getSource().getTrueSource())) {
                    cir.setReturnValue(false);
                }
            }
        }
        if (isCancelable) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "setCanceled", cancellable = true, remap = false)
    public void setCanceled(boolean cancel, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (((Event) (Object) this) instanceof LivingDeathEvent) {
            LivingDeathEvent death = ((LivingDeathEvent) (Event) (Object) this);
            if (!Judgement.isMiku(death.getEntityLiving())) {
                if (death.getSource() instanceof MikuDamage || Judgement.isMiku(death.getSource().getTrueSource())) {
                    isCanceled = false;
                    ci.cancel();
                }
            }
        }
        if (((Event) (Object) this) instanceof LivingAttackEvent) {
            LivingAttackEvent attack = ((LivingAttackEvent) (Event) (Object) this);
            if (!Judgement.isMiku(attack.getEntityLiving())) {
                if (attack.getSource() instanceof MikuDamage || Judgement.isMiku(attack.getSource().getTrueSource())) {
                    isCanceled = false;
                    ci.cancel();
                }
            }
        }
        if (((Event) (Object) this) instanceof LivingDamageEvent) {
            LivingDamageEvent damage = ((LivingDamageEvent) (Event) (Object) this);
            if (!Judgement.isMiku(damage.getEntityLiving())) {
                if (damage.getSource() instanceof MikuDamage || Judgement.isMiku(damage.getSource().getTrueSource())) {
                    isCanceled = false;
                    ci.cancel();
                }
            }
        }
        if (((Event) (Object) this) instanceof LivingHurtEvent) {
            LivingHurtEvent hurt = ((LivingHurtEvent) (Event) (Object) this);
            if (!Judgement.isMiku(hurt.getEntityLiving())) {
                if (hurt.getSource() instanceof MikuDamage || Judgement.isMiku(hurt.getSource().getTrueSource())) {
                    isCanceled = false;
                    ci.cancel();
                }
            }
        }
    }

    @Override
    public void Canceled() {
        isCanceled = true;
    }

    @Override
    public void CancelAble() {
        isCancelable = true;
    }
}
