package miku.Miku.Proxy;

import miku.Entity.Hatsune_Miku;
import miku.Event.ToolTipEvent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

public class ClientProxy extends CommonProxy {

    public void preInit(FMLPreInitializationEvent event) throws IOException {
        super.preInit(event);
        MinecraftForge.EVENT_BUS.register(new ToolTipEvent());
        File MusicFolder = new File("audio");
        if (!MusicFolder.isDirectory()) {
            System.out.println("WARNING:Cannot find music pack! Please download the music pack. You can find the link on the modrinth page.");
        }
        RenderingRegistry.registerEntityRenderingHandler((Class<? extends Entity>) Hatsune_Miku.class, renderManager -> {
            final RenderBiped customRender = new RenderBiped(renderManager, new ModelBiped(), 0.5f) {
                protected ResourceLocation getEntityTexture(@Nonnull final Entity entity) {
                    return new ResourceLocation("miku:textures/entities/miku.png");
                }
            };
            customRender.addLayer(new LayerBipedArmor(customRender) {
                protected void initArmor() {
                    this.modelLeggings = new ModelBiped(0.5f);
                    this.modelArmor = new ModelBiped(1.0f);
                }
            });
            return customRender;
        });
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }
}
