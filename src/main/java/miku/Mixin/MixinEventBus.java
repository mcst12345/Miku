package miku.Mixin;

import miku.Entity.Hatsune_Miku;
import miku.Items.Miku.MikuItem;
import miku.Utils.Killer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EventBus.class, remap = false)
public class MixinEventBus {
    @Inject(at = @At("HEAD"), method = "post", cancellable = true, remap = false)
    public void post(Event event, CallbackInfoReturnable<Boolean> cir) {
        if (MikuItem.isTimeStop() || Killer.isKilling()) cir.setReturnValue(false);
    }

    @Inject(at = @At("TAIL"), method = "post")
    public void POST(Event event, CallbackInfoReturnable<Boolean> cir) throws NoSuchFieldException, ClassNotFoundException {
        if (event instanceof TickEvent.ServerTickEvent) {
            for (Entity en : MikuItem.GetMikuList()) {
                if (en instanceof EntityPlayer) {
                    MikuItem.Protect(en);
                }
                if (en instanceof Hatsune_Miku) ((Hatsune_Miku) en).Protect();
            }
        }
    }
}
