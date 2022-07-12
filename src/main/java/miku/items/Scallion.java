package miku.items;

import miku.blocks.Portal.MikuPortal;
import miku.miku.Loader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static miku.miku.Miku.MIKU_TAB;

public class Scallion extends ItemFood {
    public Scallion() {
        super(1, 0.1f, false);
        this
                .setCreativeTab(MIKU_TAB)
                .setTranslationKey("miku.scallion")
                .setMaxStackSize(64);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        pos = pos.offset(facing);
        ((MikuPortal) Loader.MikuPortal).trySpawnPortal(worldIn, pos);
        return EnumActionResult.SUCCESS;
    }
}
