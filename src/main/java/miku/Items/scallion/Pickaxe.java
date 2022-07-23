package miku.Items.scallion;

import miku.Miku.MikuLoader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class Pickaxe extends ItemPickaxe {
    protected Pickaxe(ToolMaterial material) {
        super(material);
        material.setRepairItem(new ItemStack(MikuLoader.SCALLION));
        this.attackDamage = 3.0F;
        this.maxStackSize = 1;
        this.setTranslationKey("scallion_pickaxe");
    }

    @Override
    public boolean canHarvestBlock(@Nullable IBlockState blockIn) {
        return true;
    }

    @Override
    public float getDestroySpeed(@Nullable ItemStack stack, @Nullable IBlockState state) {
        return 8.0F;
    }
}
