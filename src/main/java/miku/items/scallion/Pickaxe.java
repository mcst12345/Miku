package miku.items.scallion;

import miku.miku.Loader;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class Pickaxe extends ItemPickaxe {
    protected Pickaxe(ToolMaterial material) {
        super(material);
        material.setRepairItem(new ItemStack(Loader.SCALLION));
        this.attackDamage = 3.0F;
        this.maxStackSize = 1;
        this.setTranslationKey("");
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn) {
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return 8.0F;
    }
}
