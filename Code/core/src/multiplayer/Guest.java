package multiplayer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Guest {
    Socket gameConnection;
    Player player2, player1;
    public Guest(String name,String ip) throws IOException{
        player2 = new Player(name);
        gameConnection = new Socket(ip, 8000);
    }
    public void receivePlayer1() throws IOException {
        InputStreamReader in = new InputStreamReader(gameConnection.getInputStream());
        BufferedReader buffer = new BufferedReader(in);
        player1 = new Player(buffer.readLine());
        in.close();
        buffer.close();
    }
    public void sendPlayer2() throws IOException {
        PrintWriter pw = new PrintWriter(gameConnection.getOutputStream());
        pw.println(player1.getName());
        pw.flush();
        pw.close();
    }
}
