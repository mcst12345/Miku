package miku.Items;

import miku.Blocks.Portal.MikuPortal;
import miku.Miku.MikuLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

import static miku.Miku.Miku.MIKU_TAB;

public class Scallion extends ItemFood {
    public Scallion() {
        super(1, 0.1f, false);
        this
                .setCreativeTab(MIKU_TAB)
                .setTranslationKey("miku.scallion")
                .setMaxStackSize(64);
    }

    @Override
    @Nonnull
    public EnumActionResult onItemUse(@Nonnull EntityPlayer player, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        pos = pos.offset(facing);
        if (!player.world.isRemote) ((MikuPortal) MikuLoader.MikuPortal).trySpawnPortal(worldIn, pos);
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
