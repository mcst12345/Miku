package miku.Network.Packet;

import io.netty.buffer.ByteBuf;
import miku.lib.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;

public class RangeKillPacket implements IMessage {
    protected double x, y, z;
    protected int world;

    public RangeKillPacket() {
    }

    public RangeKillPacket(double x, double y, double z, int world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        world = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeInt(world);
    }

    public static class MessageHandler implements IMessageHandler<RangeKillPacket, IMessage> {
        @Override
        public IMessage onMessage(RangeKillPacket message, MessageContext ctx) {
            MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
            World world = server.getWorld(message.world);
            List<Entity> list = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(message.x - 100, message.y - 100, message.z - 100, message.x + 100, message.y + 100, message.z + 100));
            EntityUtil.Kill(list);
            return null;
        }
    }
}
