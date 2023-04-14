package multiplayer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Host extends Thread {
    private Socket player2;
    private boolean accepting;
    private ServerSocket gameServer;
    private Player p2, p1;

    /**
     *
     * @param p1 Jugador Host
     * @throws IOException Si no es capaz de crear el servidor
     */
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

    /**
     * Cierra el servidor
     * @throws IOException
     */
    public void stopFind() throws IOException {
        gameServer.close();
    }

    /**
     * Espera la conexion del jugador 2
     * @throws IOException
     */
    public void waitConnection() throws IOException {
        accepting = true;
        player2 = gameServer.accept();
        //System.out.println("jugador2 aceptado -> " + player2.isConnected());
    }

    /**
     * Expulsa al jugador 2
     * @throws IOException
     */
    public void kickPlayer2() throws IOException {
        player2.close();
    }

    /**
     * Comprueba si el jugador 2 sigue conectado
     * @return
     */
    public boolean isP2connected() {
        if(player2 != null) return player2.isConnected();
        else return false;
    }

    /**
     * Comprueba si el servidor esta abierto
     * @return
     */
    public boolean isServerOpen(){
        return accepting;
    }

    /**
     * Envia el nombre del host al jugador 2
     * @throws IOException
     */
    public void sendPlayer1() throws IOException {
        PrintWriter pw = new PrintWriter(player2.getOutputStream());
        pw.println(p1.getName());
        pw.flush();
    }

    /**
     * Recibe el nombre del jugador 2
     * @throws IOException
     */
    public void receivePlayer2() throws IOException {
        InputStreamReader in = new InputStreamReader(player2.getInputStream());
        BufferedReader buffer = new BufferedReader(in);
        p2 = new Player(buffer.readLine());
    }

    /**
     * Devuelve el jugador 2 como Player
     * @return Jugador 2
     */
    public Player getPlayer2(){
        return p2;
    }
}
