package miku.Render;

import miku.Entity.Machine.MikuGenerator;
import miku.Model.MikuGeneratorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RenderMikuGenerator extends Render<MikuGenerator> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("miku", "textures/machines/miku_generator.png");
    private final ModelBase model = new MikuGeneratorModel();

    public RenderMikuGenerator(RenderManager renderManager) {
        super(renderManager);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(@Nullable MikuGenerator entity) {
        return TEXTURE;
    }

    @Override
    public void doRender(@Nonnull MikuGenerator entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.model.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0f, entity);
        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
        this.model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0f);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
