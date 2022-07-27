package miku.Interface;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IMikuInfinityInventory extends IInventory {
    NonNullList<ItemStack> getPage(int index);
}
