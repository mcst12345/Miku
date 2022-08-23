package miku.Items.scallion;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class Axe extends ItemAxe {
    public Axe(ToolMaterial material) {
        super(material);
        //material.setRepairItem(new ItemStack(MikuLoader.SCALLION));
        this.maxStackSize = 1;
        this.setMaxDamage(50);
        this.canRepair = true;
        this.setTranslationKey("scallion_axe");
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
