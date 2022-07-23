package miku.Items.Music;

import miku.Miku.Miku;
import miku.Thread.PlayMusic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class music_base extends Item {
    public String File;

    public music_base() {
        this.setCreativeTab(Miku.MIKU_MUSIC_TAB);
        this.setMaxStackSize(1);
        this.canRepair = false;
        this.File = null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nullable World world, EntityPlayer player, @Nonnull EnumHand hand) {
        if (!player.world.isRemote) {
            PlayMusic pm = new PlayMusic(File);
            pm.start();
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}
