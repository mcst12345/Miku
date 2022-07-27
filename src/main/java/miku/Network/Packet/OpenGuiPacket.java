package miku.Network.Packet;

import io.netty.buffer.ByteBuf;
import miku.Items.Miku.MikuItem;
import miku.Miku.Miku;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;

public class OpenGuiPacket implements IMessage {
    private int id;

    public OpenGuiPacket() {
    }

    public OpenGuiPacket(int id) {
        this.id = id;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
    }

    public int getId() {
        return id;
    }

    public static class MessageHandler implements IMessageHandler<OpenGuiPacket, IMessage> {

        @Override
        public IMessage onMessage(OpenGuiPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            if (!Objects.requireNonNull(player.getServer()).isCallingFromMinecraftThread()) {
                player.getServer().addScheduledTask(() -> {
                    this.onMessage(message, ctx);
                });
            } else {
                ItemStack stack = player.getHeldItemMainhand();
                boolean mainhand = true;
                if (stack.isEmpty() || !(stack.getItem() instanceof MikuItem)) {
                    stack = ctx.getServerHandler().player.getHeldItemOffhand();
                    mainhand = false;
                }
                if (!stack.isEmpty() && stack.getItem() instanceof MikuItem && ((MikuItem) stack.getItem()).hasInventory(stack)) {
                    player.openGui(Miku.INSTANCE, message.getId(), player.world, mainhand ? player.inventory.currentItem : -1, mainhand ? 0 : 1, 0);
                }
            }
            return null;
        }
    }
}
