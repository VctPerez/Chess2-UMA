package elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import interaccionFichero.LineWriter;
import interaccionFichero.LineReader;
import utils.Image;
import utils.Render;
import utils.Resources;
import utils.Settings;
import utils.TextButton;

import java.io.IOException;
import java.util.ArrayList;

public class MatchResults extends Actor{
	
	
	LineReader ProfileReader, configReader;	
	LineWriter ProfileWriter;
	private Table table;
	private Label matchres;
	private TextButton goMenu;
	private Stage stage;
	private Image fondo;
	
	
	
	public MatchResults(Stage stage) {
		this.stage=stage;
		table = new Table();
		table.setBounds(350, 100, 550, 550);
		
		configReader = new LineReader("files/config.txt"); //Lector del txt configuracion para sacar el idioma
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
		equipo=!equipo;
		String winner = WinnerTraduction(equipo);
		matchres.setText("HA GANADO EL " + winner);
	}
	
	public void setWinnerSurrender(Boolean equipo) {
		//equipo=!equipo;
		String winner = WinnerTraduction(equipo);
		matchres.setText("HA GANADO EL " + winner+" \n    POR RENDICION");
	}
	
	public void setWinnerKingKilled(String Winner) {
		matchres.setText("HA GANADO EL " + Winner+" \n    POR ASESINATO");
	}
	
	public void setWinner() {
		matchres.setText("HAS GANADO");
		//Posible inserción de música de victoria
	}
	
	public void setLooser() {
		matchres.setText("HAS PERDIDO");
		//Posible inserción de música de derrota
	}

	public void setDraw() {
		matchres.setText("Empate");
		
		//Actualizar empates
		int v = ProfileReader.leerINTLinea(4);
		v++;
		System.out.println("Escribo " + v);
		ProfileWriter.escribirLineaINT(4, v);
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
		}
	}
	
	public String WinnerTraduction(Boolean equipo) {
		if(equipo) {
			return "BLANCO";
		}else {
			return "NEGRO";
		}
	}

	public void updateLocalWinner(boolean b,boolean equipo) {
			int v;
			equipo=!equipo;
			if(b && equipo) {
				v = ProfileReader.leerINTLinea(2);
				v++;
				System.out.println("Escribo " + v);
				ProfileWriter.escribirLineaINT(2, v);
			}else {
				v = ProfileReader.leerINTLinea(3);
				v++;
				System.out.println("Escribo " + v);
				ProfileWriter.escribirLineaINT(3, v);
			}
			updatePlays();
	}
	
	public void updateOnlineWinner(boolean b, boolean equipo) {
		
	}

	private void updatePlays() {
		int v,total;
		float res;
		total = ProfileReader.leerINTLinea(5);
		total++;
		System.out.println("Escribo " + total);
		ProfileWriter.escribirLineaINT(5, total);
		
		v = ProfileReader.leerINTLinea(2);
		res= (float)v/total*100;
		ProfileWriter.escribirLinea(6, res + "%");
		
		
	}
	
}
