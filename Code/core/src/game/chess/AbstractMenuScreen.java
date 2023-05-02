package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import interaccionFichero.LectorLineas;
import utils.Render;
import utils.TextButton;

public abstract class AbstractMenuScreen extends AbstractScreen{

	protected Stage stage;
	protected Table table;
	
	protected TextButton[] textButton;
    protected Label title;
    
    protected LectorLineas configReader;
    
    protected int activatedTextButton;
    protected boolean animationStarted;
	
	@Override
	public void show() {
    	stage = new Stage(new FitViewport(1280, 720));
    	table = new Table();
    	
    	createTableElements();
        setupTable();
        addActors();
        
        addListeners();
        
        animationStarted = false;
        
        addEnterAnimation();
        
        Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render(float delta) {
        Render.clearScreen();

        Render.camera.update();
        Render.Batch.setProjectionMatrix(Render.camera.combined);
        
        //---------------
        
        stage.act();
        stage.draw();
        
        if(animationStarted && checkAnimationsFinished()) {
        	selectScreen(activatedTextButton);
        }
        
        //---------------
        
	}
	
    protected abstract void createTableElements();
    
    protected void setupTable() {
    	table.setFillParent(true);
    	table.left().pad(50);
    	table.defaults().left().space(40);
    	table.add(title);
    	table.row();
    	for (int i = 0 ; i < textButton.length ; i++) {
    		table.add(textButton[i]);
        	table.row();
    	}
    }
    
    protected void addActors() {
    	stage.addActor(Render.menuBG);
    	stage.addActor(table);
    }
	
    /**
     * Metodo que añade la animacion de salida a los elementos de la UI.
     * Cuando se pulsa un boton, van saliendo de la pantalla con un delay.
     * Una vez acaba la animación se realiza la accion del botón gracias a {@link selectScreen}.
     * @param distance Distancia en píxeles a desplazar
     * @param delay Retraso entre animaciones
     * @param time Tiempo que tarda en realizarse la accion
     */
    protected void addExitAnimation(float distance, float delay, float time) {
    	
    	title.addAction(Actions.fadeOut(0.25f));
    	
    	for(int i = 0; i < textButton.length ; i++) {
			textButton[i].addAction(Actions.delay(delay * i, Actions.moveBy(-distance, 0, time)));
			textButton[i].setDisabled(true);
    	}
    }
    
    /**
     * Método que añade la animación de entrada.
     * Se establece el alpha a 0 y luego se incrementa con un fadeIn
     */
    protected void addEnterAnimation() {
    	table.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f)));
    }
    
    /**
     * Dado el indice de un TextButton, realiza su accion correspondiente
     * @param button
     */
    protected abstract void selectScreen(int button);
    
    /**
     * Devuelve TRUE si las animaciones de salida han terminado. En caso contrario, FALSE
     */
    protected boolean checkAnimationsFinished() {
    	boolean finished = true;
    	int i = 0;
    	
    	while(i < textButton.length && finished) {
    		finished = !textButton[i].hasActions();
    		i++;
    	}
    	
    	return finished;
    }
    
    /**
     * Inputs de los botones con animacion de salida
     */
    protected void addListeners() {
        for(int i = 0 ; i < textButton.length ; i++) {
        	final int index = i;
        	textButton[i].addListener(new ClickListener() {
        		@Override
        		public void clicked(InputEvent event, float x, float y) {
        			super.clicked(event, x, y);
        			activatedTextButton = index;
        			addExitAnimation(800f,0.25f,0.5f);
        			animationStarted = true;
        		}
        	});
        }
	}

	@Override
	public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        
        Render.SCREEN_WIDTH = stage.getViewport().getScreenWidth();
        Render.SCREEN_HEIGHT = stage.getViewport().getScreenHeight();

	}

	@Override
	public void dispose() {
		super.dispose();
	    stage.dispose();
	}
	
}
