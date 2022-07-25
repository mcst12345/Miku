package miku.Items.Music;

import miku.Miku.Miku;
import miku.Utils.Music_Box_Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class Miku_Music_Box extends Item {
    public Miku_Music_Box() {
        this.setCreativeTab(Miku.MIKU_MUSIC_TAB);
        this.setTranslationKey("miku_music_box");
        this.setMaxStackSize(1);
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull EnumHand hand) {
        if (world.isRemote) return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));
        Music_Box_Util.Get_Music_Box_Reward();
        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, Music_Box_Util.item);
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}
