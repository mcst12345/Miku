package miku.Network.Packet;

import io.netty.buffer.ByteBuf;
import miku.lib.util.EntityUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Objects;

public class MikuKillNoSizeEntity implements IMessage {
    public MikuKillNoSizeEntity() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class MessageHandler implements IMessageHandler<MikuKillNoSizeEntity, IMessage> {

        @Override
        public IMessage onMessage(MikuKillNoSizeEntity message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            if (!Objects.requireNonNull(player.getServer()).isCallingFromMinecraftThread()) {
                player.getServer().addScheduledTask(() -> this.onMessage(message, ctx));
            } else {
                EntityUtil.KillNoSizeEntity(player);
            }
            return null;
        }

    }
}
