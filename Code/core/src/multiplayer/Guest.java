package multiplayer;

import utils.Render;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Guest extends OnlinePlayer{
    Player player2, player1;
    private boolean connected = false;
    private boolean receiving = false;

    /**
     * Inicializa el guest
     */
    public Guest(String name){
        player2 = new Player(name);
    }

    @Override
    public void run() {
        try {
            while(receiving){
                receiveMessage();
            }
        } catch (IOException e) {
                System.err.println("Player1 disconnected");
        }
    }

    public void connect(String ip) throws IOException {
        playerConnection = new Socket(ip, 8000);
        connected = playerConnection.isConnected();
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
        InputStreamReader in = new InputStreamReader(playerConnection.getInputStream());
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
        PrintWriter pw = new PrintWriter(playerConnection.getOutputStream());
        pw.println(player2.getName());
        pw.flush();
    }
    /**
     * Establece el parametro para que el jugador reciba mensajes
     * @param receiving
     */
    public void setReceiving(boolean receiving) {
        this.receiving = receiving;
    }
    public void disconnect() throws IOException {
        setReceiving(false);
        playerConnection.close();
    }
}
