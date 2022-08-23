package miku.Mixin;

import miku.Interface.MixinInterface.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Minecraft.class)
public class MixinMinecraft implements IMinecraft {
    @Shadow
    private ModelManager modelManager;


    @Override
    public ModelManager GetModelManager() {
        return modelManager;
    }
}
