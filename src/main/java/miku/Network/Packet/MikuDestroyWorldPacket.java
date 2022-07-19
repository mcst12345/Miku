package miku.Network.Packet;

import io.netty.buffer.ByteBuf;
import miku.Thread.WorldDestroyThread;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MikuDestroyWorldPacket implements IMessage {
    protected int world;

    public MikuDestroyWorldPacket() {
    }

    public MikuDestroyWorldPacket(int world) {
        this.world = world;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        world = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(world);
    }

    public int getWorldID() {
        return world;
    }

    public static class MessageHandler implements IMessageHandler<MikuDestroyWorldPacket, IMessage> {

        @Override
        @SideOnly(Side.SERVER)
        public IMessage onMessage(MikuDestroyWorldPacket message, MessageContext ctx) {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            WorldDestroyThread thread = new WorldDestroyThread(server.getWorld(message.getWorldID()));
            thread.start();
            return null;
        }

    }
}
