package miku.Render;

import miku.Entity.Hatsune_Miku;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderMiku extends RenderLiving<Hatsune_Miku> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("miku", "textures/entities/miku.png");

    private final ModelPlayer miku;

    public RenderMiku(RenderManager rendermanager, ModelBase modelbase, float shadowsize) {
        super(rendermanager, modelbase, shadowsize);
        this.miku = new ModelPlayer(1.0F, true);
    }

    @Override
    public void doRender(Hatsune_Miku entity, double x, double y, double z, float entityYaw, float partialTicks) {
        mainModel = miku;
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(Hatsune_Miku entity) {
        return TEXTURE;
    }
}