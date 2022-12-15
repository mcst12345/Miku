package miku.TileEntity.Machine;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.math.BlockPos;

public class MikuFurnaceTile extends MachineTileBase {
    public MikuFurnaceTile() {
        super(500);
    }

    @Override
    public void update() {
        for (int x = pos.getX() - 5; x <= pos.getX() + 5; x++) {
            for (int y = pos.getY() - 5; y <= pos.getY() + 5; y++) {
                for (int z = pos.getZ() - 5; z <= pos.getZ() + 5; z++) {
                    TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
                    if (entity instanceof MikuPowerStationTile || entity instanceof MikuGeneratorTile) {
                        MachineTileBase miku = (MachineTileBase) entity;
                        if (power < MaxPower && miku.power > 0) {
                            miku.power--;
                            power++;
                        }
                    }
                }
            }
        }
        if (power < 5) return;
        IInventory inventory = TileEntityHopper.getInventoryAtPosition(world, pos.getX(), pos.getY() - 1, pos.getZ());
        if (inventory != null) {
            System.out.println("found inventory");
            for (int index = 0; index < inventory.getSizeInventory(); ++index) {
                System.out.println("index:" + index);
                ItemStack itemstack = inventory.getStackInSlot(index);
                ItemStack result = FurnaceRecipes.instance().getSmeltingResult(itemstack);
                if (result != ItemStack.EMPTY && result.getItem() != null && power >= 5 && itemstack.getCount() != 0) {
                    inventory.setInventorySlotContents(index, new ItemStack(result.getItem(), itemstack.getCount()));
                    System.out.println("Set" + index + "to" + result.getItem() + ":" + itemstack.getCount());
                    power -= 5;
                }
            }
        }
    }
}
