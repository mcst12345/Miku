package miku.Mixin;

import com.chaoswither.chaoswither;
import com.chaoswither.event.ChaosAttackEvent;
import com.chaoswither.event.DetermineEvent;
import miku.Config.MikuConfig;
import miku.Utils.Judgement;
import miku.Utils.Killer;
import miku.Utils.SafeKill;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChaosAttackEvent.class, remap = false)
public class MixinChaosAttackEvent {
    @Inject(at = @At("HEAD"), method = "onInternect", cancellable = true)
    public void onInternect(PlayerInteractEvent event, CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        if (Judgement.isMiku(event.getEntityPlayer())) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "onWorldTickEvent", cancellable = true)
    public void onWorldTickEvent(TickEvent.WorldTickEvent event, CallbackInfo ci) {
        if (SafeKill.GetIsKillingChaosWither() || Killer.isKilling() || (Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither)) {
            chaoswither.happymode = false;
            DetermineEvent.WITHERLIVE = false;
            DetermineEvent.WitherPlayerList.clear();
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "onLivingUpdate", cancellable = true)
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event, CallbackInfo ci) {
        if (SafeKill.GetIsKillingChaosWither() || Killer.isKilling() || (Killer.NoMoreChaosWither() && MikuConfig.FuckChaosWither)) {
            chaoswither.happymode = false;
            DetermineEvent.WITHERLIVE = false;
            DetermineEvent.WitherPlayerList.clear();
            ci.cancel();
        }
    }
}
