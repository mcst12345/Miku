package miku.Mixin;

import miku.Interface.MixinInterface.ITileEntityHopper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = TileEntityHopper.class)
public class MixinTileEntityHopper implements ITileEntityHopper {
    @Shadow
    private NonNullList<ItemStack> inventory;

    @Override
    public NonNullList<ItemStack> getInventory() {
        return inventory;
    }
}
