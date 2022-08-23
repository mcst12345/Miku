package miku.Miku.Proxy;

import miku.Config.MikuConfig;
import miku.Event.*;
import miku.Gui.MikuGuiHandler;
import miku.Miku.MikuLoader;
import miku.Network.NetworkHandler;
import miku.World.MikuWorld.MikuWorld;
import miku.World.OverWorldGenStructure;
import miku.World.OverWorldOreGen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.IOException;

public class CommonProxy {
    public CommonProxy() {
    }

    public void preInit(FMLPreInitializationEvent event) throws IOException {
        MikuConfig.init(event);
        GameRegistry.registerWorldGenerator(new OverWorldOreGen(), 3);
        GameRegistry.registerWorldGenerator(new OverWorldGenStructure(), 6);
        MikuWorld.initialization();
        MinecraftForge.EVENT_BUS.register(new MikuEntityEvent());
        MinecraftForge.EVENT_BUS.register(new BreakBlock());
        MinecraftForge.EVENT_BUS.register(new EntityDropEvent());
        MinecraftForge.EVENT_BUS.register(new MikuItemEvent());
        MinecraftForge.EVENT_BUS.register(new PlayerEvent());
        MinecraftForge.EVENT_BUS.register(new EntityEvent());
        MinecraftForge.EVENT_BUS.register(new WorldEvent());
        MinecraftForge.EVENT_BUS.register(new ToolTipEvent());
    }

    public void init(FMLInitializationEvent event) {
        MikuLoader.LoadRecipes();
        NetworkHandler.INSTANCE.name();
        MikuGuiHandler.INSTANCE.name();
    }
}
