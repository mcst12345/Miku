package miku.Miku;

import miku.Blocks.BlockLoader;
import miku.Items.ItemLoader;
import miku.Miku.Proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;

@Mod(
        modid = Miku.MODID,
        name = Miku.NAME,
        version = Miku.VERSION,
        dependencies = "required:mikulib@[1.11)"
)
public class Miku {

    public static final String MODID = "miku";

    public static final String NAME = "Miku";
    public static final String VERSION = "1.1.7";

    public Miku() {
    }

    @SidedProxy(
            clientSide = "miku.Miku.Proxy.ClientProxy",
            serverSide = "miku.Miku.Proxy.CommonProxy"
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

    public static final CreativeTabs MIKU_TAB = new CreativeTabs("miku") {
        @Override
        @Nonnull
        public ItemStack createIcon() {
            return new ItemStack(ItemLoader.SCALLION);
        }
    };
    public static final CreativeTabs MIKU_MUSIC_TAB = new CreativeTabs("miku_music") {
        @Override
        @Nonnull
        public ItemStack createIcon() {
            return new ItemStack(ItemLoader.SCALLION);
        }
    };

    public static final CreativeTabs MIKU_MACHINE = new CreativeTabs("miku_machine") {
        @Override
        @Nonnull
        public ItemStack createIcon() {
            return new ItemStack(BlockLoader.MikuGenerator);
        }
    };


}
