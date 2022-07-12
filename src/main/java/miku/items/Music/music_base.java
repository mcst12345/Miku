package miku.items.Music;

import miku.Thread.PlayMusic;
import miku.miku.Miku;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        PlayMusic pm = new PlayMusic(File);
        pm.start();
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}
