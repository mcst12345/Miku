package miku.items.Recipes;

import miku.miku.Loader;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Delicious_Scallion {
    public Delicious_Scallion() {
        GameRegistry.addSmelting(Loader.SCALLION, new ItemStack(Loader.DeliciousScallion), 5);
    }
}
