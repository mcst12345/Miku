package miku.Interface.MixinInterface;

import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModContainer;

import java.util.Map;

public interface ILoader {
    Map<String, ModContainer> namedMods();

    LoadController modController();
}
