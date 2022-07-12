package miku.Thread;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

@SideOnly(Side.CLIENT)
public class PlayMusic extends Thread {
    public static boolean isPlaying = false;
    private final String FileName;

    public PlayMusic(String name) {
        FileName = name;
        System.out.println("Creating " + FileName + "play thread");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void run() {
        System.out.println("playing music");
        AudioInputStream as;
        try {
            if (isPlaying) {
                System.out.println("[info]Already playing music");
                return;
            }
            isPlaying = true;
            as = AudioSystem.getAudioInputStream(new File("audio/" + FileName + ".wav"));//音频文件在项目根目录的img文件夹下
            AudioFormat format = as.getFormat();
            SourceDataLine sdl;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(format);
            sdl.start();
            int nBytesRead = 0;
            byte[] abData = new byte[512];
            while (nBytesRead != -1) {
                nBytesRead = as.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    sdl.write(abData, 0, nBytesRead);
            }
            //关闭SourceDataLine
            sdl.drain();
            sdl.close();
            isPlaying = false;
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
