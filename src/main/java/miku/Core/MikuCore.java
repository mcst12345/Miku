package miku.Core;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.Name("miku")
public class MikuCore implements IFMLLoadingPlugin {
    public MikuCore() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.miku.json");
    }
    public static boolean debug;

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{"miku.Core.ClassTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        debug = !(Boolean) data.get("runtimeDeobfuscationEnabled");
        try {
            ClassLoader appClassLoader = Launch.class.getClassLoader();
            MethodUtils.invokeMethod(appClassLoader, true, "addURL", this.getClass().getProtectionDomain().getCodeSource().getLocation());
            MethodUtils.invokeStaticMethod(appClassLoader.loadClass(this.getClass().getName()), "initMixin");
        } catch (Exception ignored) {
        }
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
