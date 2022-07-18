package miku.event;

import miku.miku.Loader;
import miku.utils.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

import static miku.utils.BlockUtil.FK_world;

@SideOnly(Side.CLIENT)
public class InputEvent {
    /*
     * 参数依次是 String description, IKeyConflictContext context, KeyModifier modifier, int key, String category。
     * - description 是快捷键名称的本地化键。
     * - context 是 Forge patch 进去的，用于决定快捷键在什么情况下和别的快捷键冲突。
     *   Forge 提供了三个实现，即 KeyConflictContext 这个 enum 下的三个值：UNIVERSAL、
     *   IN_GAME 和 GUI，分别代表“全局适用”，“只在实际游戏中，但没有打开 GUI 时适用”和“只在打
     *   开 GUI 时适用”。如此一来，如果有两个快捷键设定的按键是一样的，但其中一个 context 是 GUI，
     *   另一个是 IN_GAME，那么这两个快捷键就不会被看作是互相冲突的。
     * - modifier 用于提供非常基础的组合键支持，只有四种可能：CTRL、ALT、SHIFT 和 NONE。
     *   NONE 代表原版风格的没有组合键，其他三种都代表组合键支持。不支持三种及以上的组合键。
     *   对于 CTRL，Forge 能正确将其处理为 macOS 上的 Command 键。CTRL、ALT 和 SHIFT 都不
     *   对左右作区分。
     *   举例：这里我们指定了 MY_HOTKEY 的默认键位是 CTRL+K，在 macOS 上则是 CMD+K。
     * - key 参考 Keyboard 类下的常量字段们。
     * - category 原版按键设定中把按键们分成了若干类别，就是这个了。
     *   传入的字符串也充当该类别的本地化键。
     */
    @SideOnly(Side.CLIENT)
    public static final KeyBinding DESTROY_WORLD = new KeyBinding("key.miku.fk_world", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_X, "key.category.miku");
    public static final KeyBinding ReloadConfig = new KeyBinding("key.miku.reload_config", KeyConflictContext.IN_GAME, KeyModifier.ALT, Keyboard.KEY_C, "kay.category.miku");

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyPressed(KeyInputEvent event) throws IOException {
        if (DESTROY_WORLD.isPressed()) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (!InventoryUtil.invHaveMiku(player)) return;
            System.out.println("WARN:" + player.getName() + "is destroying the world");
            double x = player.posX;
            double y = player.posY;
            double z = player.posZ;
            BlockPos pos = new BlockPos(x, y, z);
            FK_world(pos, player);
        }
        if (ReloadConfig.isPressed()) {
            Loader.LoadConfig();
        }
    }
}
