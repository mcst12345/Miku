package miku.Event;

import miku.Network.NetworkHandler;
import miku.Network.Packet.MikuDestroyWorldPacket;
import miku.Utils.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class InputEvent {
    @SideOnly(Side.CLIENT)
    public static final KeyBinding DESTROY_WORLD = new KeyBinding("key.miku.fk_world", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_X, "key.category.miku");

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyPressed(KeyInputEvent event) {
        if (DESTROY_WORLD.isPressed()) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (!InventoryUtil.isMiku(player)) return;
            NetworkHandler.INSTANCE.sendMessageToServer(new MikuDestroyWorldPacket(player.dimension));
        }
    }
}
