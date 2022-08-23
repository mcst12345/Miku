package miku.Blocks.MikuPower;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class MikuPowerBlockBase extends Block {
    protected final long MaxPower;

    public MikuPowerBlockBase(long maxPower) {
        super(Material.BARRIER);
        MaxPower = maxPower;
    }

    public boolean getTickRandomly() {
        needsRandomTick = true;
        return true;
    }

    @Override
    @Deprecated
    public boolean canEntitySpawn(@Nullable IBlockState state, @Nullable Entity entityIn) {
        return false;
    }

    @Override
    public int tickRate(@Nullable World worldIn) {
        return 100;
    }
}
