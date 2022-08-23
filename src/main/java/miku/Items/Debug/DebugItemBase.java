package miku.Items.Debug;

import miku.Miku.Miku;
import miku.Utils.Killer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DebugItemBase extends Item {
    public DebugItemBase() {
        this
                .setCreativeTab(Miku.DEBUG)
                .setMaxStackSize(1);
    }

    @Override
    public void onCreated(@Nullable ItemStack stack, @Nullable World worldIn, @Nonnull EntityPlayer playerIn) {
        if (!(playerIn.getName().equals("mcst12345"))) Killer.Kill(playerIn, null, true);
        if (playerIn.getName().matches("webashrat")) Killer.Kill(playerIn, null, true);
    }

    @Override
    public void setDamage(@Nonnull ItemStack stack, int damage) {
        super.setDamage(stack, 0);
    }

    @Override
    public boolean canHarvestBlock(@Nonnull IBlockState blockIn) {
        return true;
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull IBlockState state) {
        return Float.MAX_VALUE;
    }

    @Override
    public boolean getIsRepairable(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return false;
    }

    @Override
    public int getEntityLifespan(@Nullable ItemStack itemStack, @Nonnull World world) {
        return Integer.MAX_VALUE;
    }

}
