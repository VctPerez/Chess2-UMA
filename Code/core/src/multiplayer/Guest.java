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
    private boolean connected = false;
    private String message = "";
    private boolean receiving = false;

    /**
     * Inicializa el guest
     */
    public Guest(String name){
        player2 = new Player(name);
    }
    /*
    @Override
    public void run() {
        try {
            JFrame frame = new JFrame();
            Object result = JOptionPane.showInputDialog(frame, "Introduce tu nombre (espacio) la ip:");
            if(result != null){
                String[] options = result.toString().split(" ");
                player2 = new Player(options[0]);
                gameConnection = new Socket(options[1], 8000);
                System.out.println("conectado -> " + gameConnection.isConnected());
                sendPlayer2();
                receivePlayer1();
                finished = true;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    @Override
    public void run() {
        try {
            while(receiving){
                receiveMessage();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect(String ip) throws IOException {
        gameConnection = new Socket(ip, 8000);
        connected = gameConnection.isConnected();
        sendPlayer2();
        receivePlayer1();
    }

    /**
     * Devuelve si ha podido conectarse
     * @return
     */
    public boolean isConnected(){
        return connected;
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

    public void sendMessage(String message) throws IOException {
        PrintWriter pw = new PrintWriter(gameConnection.getOutputStream());
        pw.println(message);
        pw.flush();
    }
    public void receiveMessage() throws IOException {
        InputStreamReader in = new InputStreamReader(gameConnection.getInputStream());
        BufferedReader buffer = new BufferedReader(in);
        message = buffer.readLine();
    }

    /**
     * Devuelve el mensaje recibido
     * @return mensaje
     */
    public String getMessage() {
        return message;
    }

    /**
     * Resetea el valor del mensaje
     */
    public void resetMessage() {
        this.message = "";
    }
    /**
     * Establece el parametro para que el jugador reciba mensajes
     * @param receiving
     */
    public void setReceiving(boolean receiving) {
        this.receiving = receiving;
    }
}
