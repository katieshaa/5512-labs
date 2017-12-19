package com.nvisary.dop;

import com.nvisary.util.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.TimerTask;


public class AlarmTask extends TimerTask {
    private ObjectOutputStream out;
    public AlarmTask(ObjectOutputStream objectOutputStream) {
        out = objectOutputStream;
    }

    @Override
    public void run() {
        try {
            out.writeObject(new Message("Wake Up!!", "System"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
