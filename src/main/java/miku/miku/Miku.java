package miku.miku;

import miku.miku.Proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;

@Mod(
        modid = Miku.MODID,
        name = Miku.NAME,
        version = Miku.VERSION
)
public class Miku {


    public static final String MODID = "miku";
    public static final String NAME = "Miku";
    public static final String VERSION = "1.0.1-pre10";

    public Miku() {
    }

    @SidedProxy(
            clientSide = "miku.miku.Proxy.ClientProxy",
            serverSide = "miku.miku.Proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @Mod.Instance
    public static Miku INSTANCE;

    public Logger log;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        proxy.preInit(event);
        this.log = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
    }

    public static final CreativeTabs MIKU_TAB = new CreativeTabs("miku") {
        @Override
        @Nonnull
        public ItemStack createIcon() {
            return new ItemStack(MikuLoader.SCALLION);
        }
    };
    public static final CreativeTabs MIKU_MUSIC_TAB = new CreativeTabs("miku_music") {
        @Override
        @Nonnull
        public ItemStack createIcon() {
            return new ItemStack(MikuLoader.SCALLION);
        }
    };
}
