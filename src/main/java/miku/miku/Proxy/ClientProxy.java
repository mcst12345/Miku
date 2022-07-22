package miku.miku.Proxy;

import miku.Entity.Hatsune_Miku;
import miku.event.InputEvent;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;
import java.io.IOException;

import static miku.event.InputEvent.DESTROY_WORLD;
import static miku.event.InputEvent.ReloadConfig;

public class ClientProxy extends CommonProxy {
    public ClientProxy() {
    }

    public void preInit(FMLPreInitializationEvent event) throws IOException {
        super.preInit(event);
        ClientRegistry.registerKeyBinding(DESTROY_WORLD);
        ClientRegistry.registerKeyBinding(ReloadConfig);
        MinecraftForge.EVENT_BUS.register(new InputEvent());
        RenderingRegistry.registerEntityRenderingHandler((Class) Hatsune_Miku.class, renderManager -> {
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
}
