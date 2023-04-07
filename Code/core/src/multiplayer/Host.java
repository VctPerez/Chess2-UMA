package multiplayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Host extends Thread {
    private Socket player2;
    private ServerSocket gameServer;
    private Player p2, p1;

    public Host(Player p1) throws IOException {
        gameServer = new ServerSocket(8000);
        gameServer.setSoTimeout(10000);
        this.p1 = p1;
    }

    @Override
    public void run() {
        try {
            System.out.println("inicio de la hebra2");
            waitConnection();
        } catch (Exception e) {
            System.err.println(e.getMessage());;
        }
    }

    public void stopFind() throws IOException {
        gameServer.close();
    }

    public void waitConnection() throws IOException {
        gameServer.accept();
    }

    public void kickPlayer2() throws IOException {
        player2.close();
    }

    public boolean isP2connected() {
        if(player2 != null) return player2.isConnected();
        else return false;
    }

    public void sendPlayer1() throws IOException {
        PrintWriter pw = new PrintWriter(player2.getOutputStream());
        pw.println(p1.getName());
        pw.flush();
        pw.close();
    }
    public void receivePlayer2() throws IOException {
        InputStreamReader in = new InputStreamReader(player2.getInputStream());
        BufferedReader buffer = new BufferedReader(in);
        p2 = new Player(buffer.readLine());
        in.close();
        buffer.close();
    }

    public Player getPlayer2(){
        return p2;
    }
}
