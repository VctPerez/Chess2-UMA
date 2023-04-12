package multiplayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Host extends Thread {
    private Socket player2;
    private boolean accepting;
    private ServerSocket gameServer;
    private Player p2, p1;

    public Host(Player p1) throws IOException {
        accepting = false;
        gameServer = new ServerSocket(8000);
        gameServer.setSoTimeout(120000);
        this.p1 = p1;
    }

    @Override
    public void run() {
        try {
            System.out.println("Server abierto");
            waitConnection();
        } catch (Exception e) {
            System.err.println("Server closed - " + e.getClass().getName());
            e.printStackTrace();
        }
    }

    public void stopFind() throws IOException {
        gameServer.close();
    }

    public void waitConnection() throws IOException {
        accepting = true;
        player2 = gameServer.accept();
        System.out.println("jugador2 aceptado -> " + player2.isConnected());
    }

    public void kickPlayer2() throws IOException {
        player2.close();
    }

    public boolean isP2connected() {
        if(player2 != null) return player2.isConnected();
        else return false;
    }
    public boolean isServerOpen(){
        return accepting;
    }

    public void sendPlayer1() throws IOException {
        System.out.println("Enviando player 1");
        PrintWriter pw = new PrintWriter(player2.getOutputStream());
        pw.println(p1.getName());
        pw.flush();
        System.out.println("player1 enviado");
    }
    public void receivePlayer2() throws IOException {
        System.out.println("Recibiendo player 2");
        InputStreamReader in = new InputStreamReader(player2.getInputStream());
        BufferedReader buffer = new BufferedReader(in);
        p2 = new Player(buffer.readLine());
        System.out.println("player2 recibido");
    }

    public Player getPlayer2(){
        return p2;
    }
}
