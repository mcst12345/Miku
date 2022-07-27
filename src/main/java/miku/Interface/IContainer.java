package miku.Interface;

import net.minecraft.item.ItemStack;

public interface IContainer {
    boolean hasInventory(ItemStack stack);

    IMikuInfinityInventory getInventory(ItemStack stack);
}
