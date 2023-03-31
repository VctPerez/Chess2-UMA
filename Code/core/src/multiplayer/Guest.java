package multiplayer;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Guest {
    Socket gameConnection;
    public Guest(String ip) throws IOException {
        gameConnection = new Socket(ip, 8000);
    }
}
