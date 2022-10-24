package miku.Mixin;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SimpleNetworkWrapper.class, remap = false)
public class MixinSimpleNetworWrapperClientSide {
    @Inject(at = @At("HEAD"), method = "sendToAll")
    public void sendToAll(IMessage message, CallbackInfo ci) {

    }
}
