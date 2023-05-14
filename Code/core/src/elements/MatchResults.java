package elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import interaccionFichero.LineReader;
import interaccionFichero.LineWriter;
import utils.*;

import java.io.IOException;
import java.text.DecimalFormat;

public class MatchResults extends Actor{
	
	
	LineReader configReader;
	LineReader inGameReader;
	LineReader ProfileReader;
	LineWriter ProfileWriter;
	private Table table;
	private Label matchres;
	private TextButton goMenu;
	private Stage stage;
	private Image fondo;
	
	private String p1,p2; //p1 son las blancas y p2 las negras

	public MatchResults(Stage stage ,String p1, String p2) {
		this(stage);
		setPlayers(p1,p2);
	}

	public MatchResults(Stage stage) {
		this.stage=stage;
		table = new Table();
		table.setBounds(350, 100, 550, 550);
		
		configReader = new LineReader("files/config.txt"); //Lector del txt configuracion para sacar el idioma
		inGameReader = new LineReader("files/lang/" + configReader.readLine(Settings.language) + "Draft-Game.txt");
		ProfileWriter = new LineWriter("files/Datos.txt");
		ProfileReader = new LineReader("files/Datos.txt");

		Texture texture = new Texture(Resources.RESULTS_BACKGROUND_PATH);
		TextureRegionDrawable drawable = new TextureRegionDrawable(texture);
		table.setBackground(drawable);
		
		fondo = new Image(Resources.BLACK_OPACITY_PATH);
		fondo.setSize(Render.SCREEN_WIDTH,Render.SCREEN_HEIGHT);
		fondo.setTransparency(0.5f);
		
    	createTableElements();
	    setupTable();
    	addActors();

		String p1 = "",p2 = "";
		switch (configReader.readLine(Settings.language)){
			case "eng/":
				p1 = "White";
				p2 = "Black";
				break;
			case "esp/":
				p1 = "Blanco";
				p2 = "Negro";
				break;
		}
		setPlayers(p1,p2);
	}

	private void setPlayers(String player1, String  player2) {
		p1 = player1;
		p2 = player2;
	}


	private void addActors() {
		stage.addActor(fondo);
		stage.addActor(table);
	}

	private void setupTable() {
	    table.add(matchres).center();
	    table.row();
	    table.add(goMenu).center().padTop(50);
	}

	private void createTableElements() {
		matchres = new Label("-", Render.skin, "TitleStyle");
		matchres.setFontScale(0.35f, 0.35f);
    	goMenu = new TextButton("Volver al Menú Principal","SingleClickStyle");
    	goMenu.getLabel().setFontScale(0.35f,0.35f);
	}

	public void setWinner(Boolean equipo) {
		String winner = p1;
		if (!equipo) winner = p2;
		matchres.setText(winner + " " + inGameReader.readLine(6)); //WINS, completa WINNER Wins
		if(!Render.music.equals(Resources.WIN_MUSIC))
    	{
    		Render.bgMusic.stop();
    		Render.setMusic(Resources.WIN_MUSIC);
    		Render.playBgMusic(false);
    	}
	}
	
	public void setWinnerSurrender(Boolean equipo) {
		String winner = equipo?p1:p2,loser = equipo?p2:p1;
		matchres.setText(winner + " " + inGameReader.readLine(6) + "\n" + //Frase completa WINNER Wins LOSER Forfeited
				loser + " " + inGameReader.readLine(7)); //Forfeited
		if(!Render.music.equals(Resources.WIN_MUSIC))
    	{
    		Render.bgMusic.stop();
    		Render.setMusic(Resources.WIN_MUSIC);
    		Render.playBgMusic(false);
    	}
	}
	
	public void setWinnerKingKilled(Boolean equipo) {
		String winner = equipo?p1:p2,loser = equipo?p2:p1;
		matchres.setText(winner + " " + inGameReader.readLine(6) + "\n" + //WINNER Wins LOSER Murdered their king
				loser + " " + inGameReader.readLine(6)); //Murdered their king
		if(!Render.music.equals(Resources.WIN_MUSIC))
    	{
    		Render.bgMusic.stop();
    		Render.setMusic(Resources.WIN_MUSIC);
    		Render.playBgMusic(false);
    	}
	}
	
	public void setWinner() {
		matchres.setText("HAS GANADO");
		//Posible inserción de música de victoria
		if(!Render.music.equals(Resources.WIN_MUSIC))
    	{
    		Render.bgMusic.stop();
    		Render.setMusic(Resources.WIN_MUSIC);
    		Render.playBgMusic(false);
    	}
	}
	
	public void setLooser() {
		matchres.setText("HAS PERDIDO");
		//Posible inserción de música de derrota
		if(!Render.music.equals(Resources.WIN_MUSIC))
    	{
    		Render.bgMusic.stop();
    		Render.setMusic(Resources.WIN_MUSIC);
    		Render.playBgMusic(false);
    	}
	}

	public void setDraw() {
		matchres.setText(inGameReader.readLine(5)); //Draw
		
		//Actualizar empates
		drawUpdate();
		updatePlays();
		
		//Posible inserción de Música de Empate
	}
	public void Show() {
		fondo.toFront();
		table.toFront();
		fondo.setVisible(true);
		table.setVisible(true);
	}
	
	public void Hide() {
		fondo.setVisible(false);
		table.setVisible(false);
	}
	
	public void render() {
		if(goMenu.isPressed()) {
			if(Render.hosting){
				if(Render.DraftController == 3) {
					try{Render.host.stopHosting();}catch (IOException e){e.printStackTrace();}
					Render.host = null;
				}
			}else{
				if(Render.DraftController == 3) {
					try{Render.guest.disconnect();}catch (IOException e){e.printStackTrace();}
					Render.guest = null;
				}
			}
			//Render.player1Draft = new ArrayList<>();
			//Render.player2Draft = new ArrayList<>();
			Render.app.setScreen(Render.MAINSCREEN);
			Render.hosting=true;
		}
	}
	
	public String WinnerTraduction(Boolean equipo) {
		if(equipo) {
			return "BLANCO";
		}else {
			return "NEGRO";
		}
	}

	public void updateWinner(boolean b,boolean equipo) {
			int v;
			if(b == equipo) {
				v = Integer.parseInt(ProfileReader.readLine(2));
				v++;
				System.out.println("Escribo " + v);
				ProfileWriter.escribirLinea(2, "" + v);
			}else {
				v = Integer.parseInt(ProfileReader.readLine(3)); 
				v++;
				System.out.println("Escribo " + v);
				ProfileWriter.escribirLinea(3, "" + v);
			}
			updatePlays();
	}

	private void updatePlays() {
		int v,total;
		float res;
		total =  Integer.parseInt(ProfileReader.readLine(5)); 
		total++;
		//System.out.println("Escribo " + total);
		ProfileWriter.escribirLinea(5,"" + total);
		v = Integer.parseInt(ProfileReader.readLine(2));
		res=(float)v/total*100;
		DecimalFormat df = new DecimalFormat("0.00");
		String formatted = df.format(res);
		ProfileWriter.escribirLinea(6, formatted + "%");

	}
	
	private void drawUpdate() {
		int v = Integer.parseInt(ProfileReader.readLine(4));
		v++;
		System.out.println("Escribo " + v);
		ProfileWriter.escribirLinea(4,"" + v);
	}

	
}
