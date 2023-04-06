package multiplayer;

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
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        try {
            String[] info = buffer.readLine().split(" ");
            pName = info[0];
            ip = info[1];
            buffer.close();
        } catch (IOException e) {
            throw new RuntimeException("No se ha leido nada");
        }
    }
}
