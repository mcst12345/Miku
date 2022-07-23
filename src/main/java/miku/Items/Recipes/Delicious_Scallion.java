package miku.Items.Recipes;

import miku.Miku.MikuLoader;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Delicious_Scallion {
    public Delicious_Scallion() {
        GameRegistry.addSmelting(MikuLoader.SCALLION, new ItemStack(MikuLoader.DeliciousScallion), 5);
    }
}
