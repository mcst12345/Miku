package miku.Miku.Proxy;

import miku.Miku.MikuLoader;
import miku.Network.NetworkHandler;
import miku.World.MikuWorld.MikuWorld;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.IOException;

public class CommonProxy {
    public CommonProxy() {
    }

    public void preInit(FMLPreInitializationEvent event) throws IOException {
        MikuLoader.RegisterWorldGen();
        MikuLoader.RegisterEvent();
        MikuLoader.RegisterTile();
        MikuWorld.initialization();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void init(FMLInitializationEvent event) {
        MikuLoader.LoadRecipes();
        NetworkHandler.INSTANCE.name();
    }
}
