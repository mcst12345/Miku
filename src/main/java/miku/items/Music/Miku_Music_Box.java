package miku.items.Music;

import miku.miku.Miku;
import miku.utils.Music_Box_Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class Miku_Music_Box extends Item {
    public Miku_Music_Box() {
        this.setCreativeTab(Miku.MIKU_MUSIC_TAB);
        this.setTranslationKey("miku_music_box");
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        Music_Box_Util.Get_Music_Box_Reward();
        player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, Music_Box_Util.item);
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}
