package miku.Items.Miku;

import miku.lib.api.iEntityPlayer;
import miku.lib.item.SpecialItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static miku.Miku.Miku.MIKU_TAB;

public class MikuItem extends SpecialItem {

    public MikuItem() {
        this
                .setCreativeTab(MIKU_TAB)
                .setTranslationKey("miku.miku_item")
                .setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        tooltip.add("§b§o一根能毁灭世界的葱");
        tooltip.add("§bHatsuneMiku is the best singer!");
        tooltip.add("§fBy mcst12345");
    }

    @Override
    public int getEntityLifespan(@Nullable ItemStack itemStack, @Nonnull World world) {
        return Integer.MAX_VALUE;
    }
    @Override
    public void onUpdate(@Nonnull ItemStack stack, @Nonnull World world, @Nonnull Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof EntityPlayer) {
            ((iEntityPlayer) entity).setMiku();
        }
        super.onUpdate(stack, world, entity, itemSlot, isSelected);
    }

    @Override
    public void onCreated(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityPlayer playerIn) {
        ((iEntityPlayer) playerIn).setMiku();
    }
}
