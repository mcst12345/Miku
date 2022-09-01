package miku.Mixin;

import miku.Interface.MixinInterface.ILoader;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.common.ModContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

import static net.minecraftforge.fml.common.Loader.instance;

@Mixin(value = Loader.class)
public class MixinLoader implements ILoader {
    @Shadow(remap = false)
    private Map<String, ModContainer> namedMods;

    @Shadow(remap = false)
    private LoadController modController;

    /**
     * @author mcst12345
     * @reason No
     */
    @Overwrite(remap = false)
    public static boolean isModLoaded(String modname) {
        if (modname.matches("miku")) return false;
        return ((ILoader) instance()).namedMods().containsKey(modname) && ((ILoader) instance()).modController().getModState(((ILoader) instance()).namedMods().get(modname)) != LoaderState.ModState.DISABLED;
    }

    @Override
    public Map<String, ModContainer> namedMods() {
        return namedMods;
    }

    @Override
    public LoadController modController() {
        return modController;
    }
}
