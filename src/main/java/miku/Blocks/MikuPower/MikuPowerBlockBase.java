package miku.Blocks.MikuPower;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;

import javax.annotation.Nullable;

public abstract class MikuPowerBlockBase extends Block {
    protected final long MaxPower;

    public MikuPowerBlockBase(long maxPower) {
        super(Material.BARRIER);
        MaxPower = maxPower;
    }

    @Override
    @Deprecated
    public boolean canEntitySpawn(@Nullable IBlockState state, @Nullable Entity entityIn) {
        return false;
    }
}
