package multiplayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Host extends Thread{
    private Socket player2;
    private ServerSocket gameServer;
    private Player p2, p1;

    public Host(Player p1) throws IOException {
        gameServer = new ServerSocket(8000);
        this.p1 = p1;
    }

    @Override
    public void run() {
        try {
            System.out.println("inicio de la hebra2");
            waitConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void waitConnection() throws IOException {
        gameServer.accept();
    }

    public void kickPlayer2() throws IOException {
        player2.close();
    }

    public boolean isP2connected() {
        if(player2 != null)return player2.isConnected();
        else return false;
    }

    public Player getPlayer2(){
        return p2;
    }
}
