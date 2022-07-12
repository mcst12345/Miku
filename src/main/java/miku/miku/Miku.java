package miku.miku;

import miku.Entity.Hatsune_Miku;
import miku.World.MikuWorld.MikuWorldGen;
import miku.World.MikuWorld.MikuWorldProvider;
import miku.event.*;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
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

    public static int dimID = 393939;
    public static DimensionType MikuWorld;

    public static final String MODID = "miku";
    public static final String NAME = "Miku";
    public static final String VERSION = "1.0.1-pre4";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new MikuWorldGen(), 3);
        // 第一个参数代表此维度的内部名称。
        // 第二个参数是村庄等数据保存时会用到的“当前维度的专有后缀名”。
        // 第三个参数是维度 ID。维度 ID 是基于 Minecraft 1.12.2 的 Forge Mod 开发中
        // 仍然需要手动指定数字 ID 的游戏内容，也因此几乎所有的 Modder 都会允许通过配置文件
        // 修改维度 ID。
        // 第四个参数是该维度使用的 WorldProvider 的类。要求这个类有零参构造器。
        // 第五个参数代表“是否保持该维度的 spawn 区块一直加载”。
        MikuWorld = DimensionType.register("miku_world", "new_miku_world", dimID, MikuWorldProvider.class, false);
        // 在拿到自己的维度的 myDimType 后，需要再向 Forge 告知这一新的 DimensionType 的存在，
        // 否则某些 Forge 加入的功能会无法在你的新维度中工作。
        // 第一个参数仍然是维度 ID，和上面保持一致即可。
        DimensionManager.registerDimension(dimID, MikuWorld);

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
