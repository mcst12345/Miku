package miku.Interface.MixinInterface;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IInventoryBasic {
    NonNullList<ItemStack> GetInventory();
}
