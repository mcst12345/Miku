package miku.Network;

import miku.Network.Packet.MikuDestroyWorldPacket;
import miku.Network.Packet.MikuInventoryPackage;
import miku.Network.Packet.MikuKillNoSizeEntity;
import miku.Network.Packet.OpenGuiPacket;
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
        this.channel.registerMessage(MikuKillNoSizeEntity.MessageHandler.class, MikuKillNoSizeEntity.class, index++, Side.SERVER);
        this.channel.registerMessage(OpenGuiPacket.MessageHandler.class, OpenGuiPacket.class, index++, Side.SERVER);
        this.channel.registerMessage(MikuInventoryPackage.MessageHandler.class, MikuInventoryPackage.class, index++, Side.SERVER);
        //this.channel.registerMessage(PlayMusicPacket.);
    }

    public void sendMessageToServer(IMessage msg) {
        channel.sendToServer(msg);
    }
}
