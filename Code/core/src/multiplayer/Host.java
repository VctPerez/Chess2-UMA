package multiplayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Host {
    private Socket player2;
    private ServerSocket gameServer;
    private Player p2, p1;

    public Host(Player p1) throws IOException {
        gameServer = new ServerSocket(8000);
        this.p1 = p1;
    }

    public void waitConnection() throws IOException {
        gameServer.accept();
    }

    public boolean isP2connected() {
        return player2.isConnected();
    }

    public Player getPlayer2(){
        return p2;
    }
}
