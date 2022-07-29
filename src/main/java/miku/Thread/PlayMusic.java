package miku.Thread;

import miku.Exception.MusicFileNotFound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PlayMusic extends Thread {
    public static boolean isPlaying = false;
    private final String FileName;

    public PlayMusic(String name) {
        FileName = name;
        System.out.println("Creating " + FileName + "play thread");
    }

    @Override
    public void run() {
        System.out.println("playing music");
        AudioInputStream as;
        try {
            if (isPlaying) {
                System.out.println("[info]Already playing music");
                return;
            }
            isPlaying = true;
            File MusicFile = new File("audio/" + FileName + ".wav");
            if (!MusicFile.exists()) {
                throw new MusicFileNotFound();
            }
            as = AudioSystem.getAudioInputStream(MusicFile);
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
