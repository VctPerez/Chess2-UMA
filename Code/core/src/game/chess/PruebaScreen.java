package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import utils.Render;
import utils.Resources;
import utils.TextButton;
import utils.TextField;

/**
 * Clase para probar ligeramente elementos de la UI, no borrar plis
 */
public class PruebaScreen extends AbstractScreen{

    TextField cuadroTexto;
    TextButton texto;
    Stage scene;

	@Override
	public void show() {
		if(Render.hosting) Render.host.resetMessage();
		else Render.guest.resetMessage();
		
        cuadroTexto = new TextField("prueba");
        cuadroTexto.setPosition(200, 200);
        
        texto = new TextButton("hola victor");
        texto.setPosition(100, 100);
//        texto.getStyle().font.getData().setScale(1.5f);
        
        //Por algun motivo solo deja escalar el tamaño correctamenete con setBounds
        cuadroTexto.setBounds(500, 200, 300, 100);
        
        scene = new Stage(new FitViewport(1280, 720));
        scene.addActor(cuadroTexto);
        scene.addActor(texto);
        
        Gdx.input.setInputProcessor(scene);
	}

	@Override
	public void render(float delta) {
        Render.clearScreen();
        
        scene.act();
        scene.draw();
        
//        if(texto.isChecked()) {
//        	System.out.println("hola");
//        }
        
        if(texto.isPressed()) {
        	Render.app.setScreen(Render.MAINSCREEN);
        }
      
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		super.resize(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		super.resume();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		super.hide();
	}

	@Override
	public void dispose() {
		scene.dispose();
	}

}
