package miku.TileEntity.Machine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

import javax.annotation.Nonnull;

public abstract class MachineTileBase extends TileEntity implements ITickable {
    public final int MaxPower;
    public int power = 0;

    public MachineTileBase(int maxPower) {
        MaxPower = maxPower;
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound compound) {
        super.readFromNBT(compound);
        power = compound.getInteger("power");
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("power", power);
        return compound;
    }
}
