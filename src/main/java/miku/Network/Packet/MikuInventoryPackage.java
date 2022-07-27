package miku.Network.Packet;

import io.netty.buffer.ByteBuf;
import miku.Gui.Container.MikuInventoryContainer;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MikuInventoryPackage implements IMessage {
    private boolean next;

    public MikuInventoryPackage() {
    }

    public MikuInventoryPackage(boolean next) {
        this.next = next;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        next = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(next);
    }

    public boolean isNext() {
        return next;
    }

    public static class MessageHandler implements IMessageHandler<MikuInventoryPackage, IMessage> {

        @Override
        public IMessage onMessage(MikuInventoryPackage message, MessageContext ctx) {
            Container container = ctx.getServerHandler().player.openContainer;
            if (container instanceof MikuInventoryContainer) {
                if (message.isNext()) {
                    ((MikuInventoryContainer) container).nextPage();
                } else {
                    ((MikuInventoryContainer) container).prePage();
                }
            }
            return null;
        }

    }
}
