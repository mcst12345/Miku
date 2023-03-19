package miku.TileEntity.Machine;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityHopper;

public class MikuFurnaceTile extends MachineTileBase {
    public MikuFurnaceTile() {
        super(500);
    }

    @Override
    public void update() {
        super.update();
        if (power < 5) return;
        IInventory inventory = TileEntityHopper.getInventoryAtPosition(world, pos.getX(), pos.getY() - 1, pos.getZ());
        if (inventory != null) {
            System.out.println("found inventory");
            for (int index = 0; index < inventory.getSizeInventory(); ++index) {
                ItemStack itemstack = inventory.getStackInSlot(index);
                ItemStack result = FurnaceRecipes.instance().getSmeltingResult(itemstack);
                if (result != ItemStack.EMPTY && result.getItem() != null && power >= 5 && itemstack.getCount() != 0) {
                    inventory.setInventorySlotContents(index, new ItemStack(result.getItem(), itemstack.getCount()));
                    power -= 5;
                }
            }
        }
    }
}
