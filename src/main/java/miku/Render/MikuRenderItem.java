package miku.Render;

import miku.Interface.MixinInterface.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MikuRenderItem extends RenderItem {
    public static MikuRenderItem instance;

    public MikuRenderItem(TextureManager textureManager, ModelManager modelMesher, ItemColors itemColors) {
        super(textureManager, modelMesher, itemColors);
    }

    public static void init() {
        Minecraft mc = Minecraft.getMinecraft();
        instance = new MikuRenderItem(mc.renderEngine, ((IMinecraft) mc).GetModelManager(), mc.getItemColors());
        ((IReloadableResourceManager) mc.getResourceManager()).registerReloadListener(instance);
    }

    public void renderItemOverlayIntoGUI(@Nonnull FontRenderer fr, ItemStack stack, int xPosition, int yPosition, @Nullable String text) {
        if (!stack.isEmpty() && text == null) {
            int count = stack.getCount();
            if (count != 1) {
                if (count > 1000000000) {
                    text = stack.getCount() / 1000000000 + "G";
                } else if (count > 1000000) {
                    text = stack.getCount() / 1000000 + "M";
                } else if (count > 1000) {
                    text = stack.getCount() / 1000 + "K";
                }
            }
        }
        super.renderItemOverlayIntoGUI(fr, stack, xPosition, yPosition, text);
    }
}
