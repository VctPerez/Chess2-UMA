package multiplayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Host {
    private static Socket player2;
    private static ServerSocket gameServer;

    private static boolean p2connected = false;
    private static Player p2;

    public static void createMatch() throws IOException {
        gameServer = new ServerSocket(8000);
        player2 = gameServer.accept();
        InputStreamReader in = new InputStreamReader(player2.getInputStream());
        BufferedReader buffer = new BufferedReader(in);

        String nameP2 = buffer.readLine();
        p2 = new Player(nameP2);

        p2connected = true;
    }

    public boolean isP2connected() {
        return p2connected;
    }

    public Player getPlayer2(){
        return p2;
    }
}
