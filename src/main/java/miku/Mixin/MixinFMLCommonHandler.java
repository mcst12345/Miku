package miku.Mixin;

import miku.Entity.Hatsune_Miku;
import miku.Items.Miku.MikuItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FMLCommonHandler.class, remap = false)
public class MixinFMLCommonHandler {
    @Inject(at = @At("TAIL"), method = "onPostServerTick", remap = false)
    public void onPostServerTick(CallbackInfo ci) throws NoSuchFieldException, ClassNotFoundException {
        for (Entity en : MikuItem.GetMikuList()) {
            if (en instanceof EntityPlayer) MikuItem.Protect(en);
            if (en instanceof Hatsune_Miku) ((Hatsune_Miku) en).Protect();
        }
    }
}
