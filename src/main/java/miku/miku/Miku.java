package miku.miku;

import miku.Entity.Hatsune_Miku;
import miku.World.MikuWorld.MikuWorld;
import miku.World.OverWorldGen;
import miku.event.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static miku.event.InputEvent.DESTROY_WORLD;

@Mod(
        modid = Miku.MODID,
        name = Miku.NAME,
        version = Miku.VERSION
)
public class Miku {


    public static final String MODID = "miku";
    public static final String NAME = "Miku";
    public static final String VERSION = "1.0.1-pre4";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new OverWorldGen(), 3);
        MikuWorld.initialization();

        MinecraftForge.EVENT_BUS.register(new MikuEntityEvent());
        ClientRegistry.registerKeyBinding(DESTROY_WORLD);
        MinecraftForge.EVENT_BUS.register(new BreakBlock());
        MinecraftForge.EVENT_BUS.register(new EntityDropEvent());
        MinecraftForge.EVENT_BUS.register(new InputEvent());
        MinecraftForge.EVENT_BUS.register(new MikuItemEvent());
        MinecraftForge.EVENT_BUS.register(new PlayerEvent());
        MinecraftForge.EVENT_BUS.register(new EntityEvent());
        MinecraftForge.EVENT_BUS.register(new WorldEvent());

        RenderingRegistry.registerEntityRenderingHandler((Class) Hatsune_Miku.class, renderManager -> {
            final RenderBiped customRender = new RenderBiped(renderManager, new ModelBiped(), 0.5f) {
                protected ResourceLocation getEntityTexture(final Entity entity) {
                    return new ResourceLocation("miku:textures/entities/miku.png");
                }
            };
            customRender.addLayer(new LayerBipedArmor(customRender) {
                protected void initArmor() {
                    this.modelLeggings = (ModelBiped) new ModelBiped(0.5f);
                    this.modelArmor = (ModelBiped) new ModelBiped(1.0f);
                }
            });
            return customRender;
        });
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        Loader.LoadRecipes();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    public static final CreativeTabs MIKU_TAB = new CreativeTabs("miku") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Loader.SCALLION);
        }
    };
    public static final CreativeTabs MIKU_MUSIC_TAB = new CreativeTabs("miku_music") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(Loader.SCALLION);
        }
    };
}
