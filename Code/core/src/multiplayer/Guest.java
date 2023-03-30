package multiplayer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Guest {
    public void connectToGame(String ip) throws IOException {
        Socket gameConnection = new Socket(ip, 8000);

        //TODO enviar mi nombre de jugador.
        /*Scanner in = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(gameConnection.getOutputStream());
        pw.println(in.nextLine());
        pw.flush();*/
    }
}
