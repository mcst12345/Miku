package miku.Mixin;

import com.chaoswither.chaoswither;
import com.chaoswither.event.ChaosUpdateEvent;
import com.chaoswither.event.DetermineEvent;
import com.chaoswither.event.PlayerUpdateEvent;
import miku.Config.MikuConfig;
import miku.Utils.Killer;
import miku.Utils.SafeKill;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.chaoswither.event.ChaosUpdateEvent.WitherPlayerList;

@Mixin(value = PlayerUpdateEvent.class, remap = false)
public class MixinChaosPlayerUpdateEvent {
    @Inject(at = @At("HEAD"), method = "onLivingSetAttackTarget", cancellable = true)
    public void onLivingSetAttackTarget(LivingSetAttackTargetEvent event, CallbackInfo ci) {
        if (SafeKill.GetIsKillingChaosWither() || Killer.isKilling() || (Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither)) {
            ChaosUpdateEvent.WITHERLIVE = false;
            WitherPlayerList.clear();
            chaoswither.happymode = false;
            DetermineEvent.WITHERLIVE = false;
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "onLivingAttack1", cancellable = true)
    public void onLivingAttack1(AttackEntityEvent event, CallbackInfo ci) {
        if (SafeKill.GetIsKillingChaosWither() || Killer.isKilling() || (Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither)) {
            ChaosUpdateEvent.WITHERLIVE = false;
            WitherPlayerList.clear();
            chaoswither.happymode = false;
            DetermineEvent.WITHERLIVE = false;
            ci.cancel();
        }
    }
}
