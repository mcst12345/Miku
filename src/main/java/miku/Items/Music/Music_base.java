package miku.Items.Music;

import miku.Miku.Miku;
import miku.Network.NetworkHandler;
import miku.Network.Packet.PlayMusicPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Music_base extends Item {
    public String File;

    public int id;

    public Music_base() {
        this.setCreativeTab(Miku.MIKU_MUSIC_TAB);
        this.setMaxStackSize(1);
        this.canRepair = false;
        this.File = null;
    }

    @Override
    @Nonnull
    public ActionResult<ItemStack> onItemRightClick(@Nullable World world, EntityPlayer player, @Nonnull EnumHand hand) {
        if (!player.world.isRemote) {
            NetworkHandler.INSTANCE.sendMessageToPlayer(new PlayMusicPacket(id), (EntityPlayerMP) player);
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}
