package miku.Mixin;

import miku.Interface.MixinInterface.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.block.model.ModelManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Minecraft.class)
public class MixinMinecraft implements IMinecraft {
    @Shadow
    private ModelManager modelManager;


    @Override
    public ModelManager GetModelManager() {
        return modelManager;
    }

    @Inject(at = @At("HEAD"), method = "displayGuiScreen", cancellable = true)
    public void displayGuiScreen(GuiScreen guiScreenIn, CallbackInfo ci) {
        if (guiScreenIn instanceof GuiGameOver) {
            guiScreenIn.onGuiClosed();
            ci.cancel();
        }
        if (guiScreenIn != null) {
            if (guiScreenIn.toString() != null) {
                if (guiScreenIn.toString().toLowerCase().matches("(.*)dead(.*)") || guiScreenIn.toString().toLowerCase().matches("(.*)gameover(.*)")) {
                    ci.cancel();
                }
            }
        }
    }
}
