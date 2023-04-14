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

    /**
     * Inicializa el guest
     */
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

    /**
     * Devuelve si ha podido conectarse
     * @return
     */
    public boolean getStatus(){
        return finished;
    }

    /**
     * Recibe el nombre del jugador 1
     * @throws IOException
     */
    public void receivePlayer1() throws IOException {
        System.out.println("Recibiendo player 1");
        InputStreamReader in = new InputStreamReader(gameConnection.getInputStream());
        BufferedReader buffer = new BufferedReader(in);
        player1 = new Player(buffer.readLine());
        System.out.println("player1 recibido");
    }

    /**
     * Devuelve al jugador 1
     * @return Jugador 1
     */
    public Player getPlayer1() {
        return player1;
    }
    /**
     * Devuelve al jugador 2
     * @return Jugador 2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Envia el nombre del jugador 2
     * @throws IOException
     */
    public void sendPlayer2() throws IOException {
        PrintWriter pw = new PrintWriter(gameConnection.getOutputStream());
        pw.println(player2.getName());
        pw.flush();
    }
}
