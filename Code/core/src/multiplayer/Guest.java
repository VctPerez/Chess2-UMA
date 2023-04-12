package multiplayer;

import utils.Render;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Guest extends Thread{
    private String ipDest;
    Socket gameConnection;
    Player player2, player1;
    private boolean finished = false;

    public Guest(){
    }

    @Override
    public void run() {
        try {
            JFrame frame = new JFrame();
            Object result = JOptionPane.showInputDialog(frame, "Introduce tu nombre (espacio) la ip:");
            String[] options = result.toString().split(" ");
            player2 = new Player(options[0]);
            gameConnection = new Socket(options[1], 8000);
            System.out.println("conectado -> " + gameConnection.isConnected());
            sendPlayer2();
            receivePlayer1();
            finished = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean getStatus(){
        return finished;
    }
    public void receivePlayer1() throws IOException {
        System.out.println("Recibiendo player 1");
        InputStreamReader in = new InputStreamReader(gameConnection.getInputStream());
        BufferedReader buffer = new BufferedReader(in);
        player1 = new Player(buffer.readLine());
        System.out.println("player1 recibido");
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void sendPlayer2() throws IOException {
        System.out.println("Enviando player 2");
        PrintWriter pw = new PrintWriter(gameConnection.getOutputStream());
        pw.println(player2.getName());
        pw.flush();
        System.out.println("player2 enviado");
    }
}
