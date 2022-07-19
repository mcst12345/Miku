package miku.event;

import miku.Network.NetworkHandler;
import miku.Network.Packet.MikuDestroyWorldPacket;
import miku.miku.MikuLoader;
import miku.utils.InventoryUtil;
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

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class InputEvent {
    @SideOnly(Side.CLIENT)
    public static final KeyBinding DESTROY_WORLD = new KeyBinding("key.miku.fk_world", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_X, "key.category.miku");
    @SideOnly(Side.CLIENT)
    public static final KeyBinding ReloadConfig = new KeyBinding("key.miku.reload_config", KeyConflictContext.IN_GAME, KeyModifier.ALT, Keyboard.KEY_O, "key.category.miku");

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyPressed(KeyInputEvent event) throws IOException {
        if (DESTROY_WORLD.isPressed()) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (!InventoryUtil.invHaveMiku(player)) return;
            NetworkHandler.INSTANCE.sendMessageToServer(new MikuDestroyWorldPacket(player.dimension));
        }
        if (ReloadConfig.isPressed()) {
            MikuLoader.LoadConfig();
        }
    }
}
