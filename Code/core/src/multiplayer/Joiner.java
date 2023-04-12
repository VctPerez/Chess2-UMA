package multiplayer;

import com.badlogic.gdx.Gdx;
import utils.MyTextListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Joiner extends Thread{
    private String ip, pName;
    public Joiner(){
        ip = null;
        pName = null;
    }

    public String getIp() {
        return ip;
    }

    public String getPName() {
        return pName;
    }

    @Override
    public void run() {
        MyTextListener listener = new MyTextListener();
        Gdx.input.getTextInput(listener, "User + code", "User: ","user + code");
        String[] info = listener.getWritten().split(" ");
        pName = info[0];
        ip = info[1];
    }
}
