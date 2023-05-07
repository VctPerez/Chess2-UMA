package multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;

import utils.Parser;
import utils.Render;

public class OnlinePlayer extends Thread{
	protected Socket playerConnection;
	protected String message = "";
	public boolean draftSent = false;
	public OnlinePlayer() {
		
	}
	
	public void sendMessage(String message) throws IOException {
        PrintWriter pw = new PrintWriter(playerConnection.getOutputStream());
        pw.println(message);
        pw.flush();
        
    }
    public void receiveMessage() throws IOException {
        InputStreamReader in = new InputStreamReader(playerConnection.getInputStream());
        BufferedReader buffer = new BufferedReader(in);
        message = buffer.readLine();
    }
    
    public void sendDraft(Collection<String> draftCollection) throws IOException {
    	ArrayList<String> draft = new ArrayList<>(draftCollection);
    	sendMessage(Parser.parseDraftToString(draft));
    	draftSent = true;
    }
    
    public void sendPromotion(String path) {
    	try {//meter este metodo en host y guest y que se llame sendPromotion
			String movement = Parser.parseMovementToString(Render.GameScreen.currentTile, Render.GameScreen.nextTile);
			movement += "-";
			movement += path;
			
			Render.player.sendMessage(movement);
			Render.GameScreen.PLAYER = !Render.GameScreen.PLAYER;
			Render.GameScreen.moved=false;
			
			String[] params = movement.split("-");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
    /**
     * Devuelve el mensaje recibido
     * @return
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
}
