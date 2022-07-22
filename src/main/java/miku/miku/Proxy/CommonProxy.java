package miku.miku.Proxy;

import miku.World.MikuWorld.MikuWorld;
import miku.World.OverWorldGen;
import miku.event.*;
import miku.miku.MikuLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.IOException;

public class CommonProxy {
    public CommonProxy() {
    }

    public void preInit(FMLPreInitializationEvent event) throws IOException {
        MikuLoader.LoadConfig();
        GameRegistry.registerWorldGenerator(new OverWorldGen(), 3);
        MikuWorld.initialization();
        MinecraftForge.EVENT_BUS.register(new MikuEntityEvent());
        MinecraftForge.EVENT_BUS.register(new BreakBlock());
        MinecraftForge.EVENT_BUS.register(new EntityDropEvent());
        MinecraftForge.EVENT_BUS.register(new MikuItemEvent());
        MinecraftForge.EVENT_BUS.register(new PlayerEvent());
        MinecraftForge.EVENT_BUS.register(new EntityEvent());
        MinecraftForge.EVENT_BUS.register(new WorldEvent());
    }

    public void init(FMLInitializationEvent event) {
        MikuLoader.LoadRecipes();
    }
}
