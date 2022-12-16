package miku.TileEntity.Machine;

import miku.Items.Scallion;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityHopper;

public class MikuGeneratorTile extends MikuPowerTileBase {
    public MikuGeneratorTile() {
        super(1000);
    }

    @Override
    public void update() {
        if (power >= MaxPower) return;
        for (int x = pos.getX() - 10; x <= pos.getX() + 10; x++) {
            for (int y = pos.getY() - 10; y <= pos.getY() + 10; y++) {
                for (int z = pos.getZ() - 10; z <= pos.getZ() + 10; z++) {
                    IInventory inventory = TileEntityHopper.getInventoryAtPosition(world, x, y, z);
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
        }
    }
}
