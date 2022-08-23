package miku.Thread;

import miku.Utils.Killer;

public class RemoveFromAntiEntityList extends Thread {
    Class c;

    public RemoveFromAntiEntityList(Class c) {
        this.c = c;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Killer.AntiEntityClass.remove(c);
    }
}
