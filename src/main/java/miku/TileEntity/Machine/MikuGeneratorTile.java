package miku.TileEntity.Machine;

import miku.Items.Scallion;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityHopper;

public class MikuGeneratorTile extends MachineTileBase {
    public MikuGeneratorTile() {
        super(1000);
    }

    @Override
    public void update() {
        if (power >= MaxPower) return;
        IInventory inventory = TileEntityHopper.getInventoryAtPosition(world, pos.getX(), pos.getY() + 1, pos.getZ());
        if (inventory != null) {
            for (int index = 0; index < inventory.getSizeInventory(); ++index) {
                ItemStack itemstack = inventory.getStackInSlot(index);
                if (itemstack.getItem() instanceof Scallion) {
                    if (itemstack.getCount() > 0) {
                        power++;
                        itemstack.shrink(1);
                    }
                }
            }
        }
    }
}
