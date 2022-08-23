package miku.Network.Packet;

import io.netty.buffer.ByteBuf;
import miku.Thread.PlayMusic;
import miku.Utils.Music_Util;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayMusicPacket implements IMessage {
    protected int Music_id;

    public PlayMusicPacket() {
    }

    public PlayMusicPacket(int Music_id) {
        this.Music_id = Music_id;
    }

    public int GetId() {
        return Music_id;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(Music_id);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        Music_id = buf.readInt();
    }

    public static class MessageHandler implements IMessageHandler<PlayMusicPacket, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PlayMusicPacket message, MessageContext ctx) {
            PlayMusic pm = new PlayMusic(Music_Util.GetMusicFromId(message.GetId()));
            pm.start();
            return null;
        }
    }
}
