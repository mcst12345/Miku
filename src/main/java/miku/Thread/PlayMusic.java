package miku.Thread;

import miku.lib.util.AudioUtil;

public class PlayMusic extends Thread {
    private final String FileName;

    public PlayMusic(String name) {
        FileName = name;
        System.out.println("Creating " + FileName + "play thread");
    }

    @Override
    public void run() {
        AudioUtil.PlayMusic("audio/" + FileName + ".wav");
    }
}
