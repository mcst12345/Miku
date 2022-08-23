package miku.Mixin;

import miku.Interface.MixinInterface.IEnderInventory;
import miku.Interface.MixinInterface.IInventoryBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = InventoryEnderChest.class)
public class MixinEnderInventory extends InventoryBasic implements IEnderInventory {

    public MixinEnderInventory(String title, boolean customName, int slotCount) {
        super(title, customName, slotCount);
    }

    @Override
    public void Clear() {
        for (int i = 0; i < getSizeInventory(); i++) {
            ((IInventoryBasic) this).GetInventory().set(i, ItemStack.EMPTY);
        }
    }
}
