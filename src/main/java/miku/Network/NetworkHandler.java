package miku.Network;

import miku.Network.Packet.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public enum NetworkHandler {
    INSTANCE;

    private final SimpleNetworkWrapper channel = NetworkRegistry.INSTANCE.newSimpleChannel("miku");

    NetworkHandler() {
        int index = 0;
        this.channel.registerMessage(MikuDestroyWorldPacket.MessageHandler.class, MikuDestroyWorldPacket.class, index++, Side.SERVER);
        this.channel.registerMessage(MikuKillNoSizeEntity.MessageHandler.class, MikuKillNoSizeEntity.class, index++, Side.SERVER);
        this.channel.registerMessage(OpenGuiPacket.MessageHandler.class, OpenGuiPacket.class, index++, Side.SERVER);
        this.channel.registerMessage(MikuInventoryPackage.MessageHandler.class, MikuInventoryPackage.class, index++, Side.SERVER);
        this.channel.registerMessage(PlayMusicPacket.MessageHandler.class, PlayMusicPacket.class, index++, Side.CLIENT);
        this.channel.registerMessage(MikuInventorySlotChangePacket.MessageHandler.class, MikuInventorySlotChangePacket.class, index++, Side.CLIENT);
        this.channel.registerMessage(MikuInventorySlotInitPacket.MessageHandler.class, MikuInventorySlotInitPacket.class, index++, Side.CLIENT);
        this.channel.registerMessage(ExitGamePacket.MessageHandler.class, ExitGamePacket.class, index++, Side.CLIENT);
    }

    public void sendMessageToServer(IMessage msg) {
        channel.sendToServer(msg);
    }

    public void sendMessageToPlayer(IMessage msg, EntityPlayerMP player) {
        channel.sendTo(msg, player);
    }
}
