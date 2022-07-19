package miku.Network;

import miku.Network.Packet.MikuDestroyWorldPacket;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public enum NetworkHandler {
    INSTANCE;

    private final SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel("miku");

    NetworkHandler() {
        int index = 0;
        this.channel.registerMessage(MikuDestroyWorldPacket.MessageHandler.class, MikuDestroyWorldPacket.class, index++, Side.CLIENT);
    }

    public void sendMessageToServer(IMessage msg) {
        channel.sendToServer(msg);
    }
}
