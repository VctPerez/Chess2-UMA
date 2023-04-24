package game.chess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.*;

import elements.Background;
import elements.Board;
import elements.DropDownMenu;
import elements.Graveyard;
import elements.Piece;
import elements.Tile;
import elements.Timer;
import elements.MatchResults;
import elements.pieces.*;
import interaccionFichero.LectorLineas;
import utils.AnimationActor;
import utils.Parser;
import utils.Render;
import utils.Resources;
import utils.Settings;
import utils.TextButton;

import java.util.ArrayList;

public class AnimationTestScreen extends GameScreen {
	

	private Animation<TextureRegion> animation;
	private Texture testTexture;
	private TextureRegion testRegion;
	private TextureRegion[] testFrames;
	
	private float duracion = 0;
	
	private int pos = 0;
	
	private AnimationActor testActor;
	
	@Override
	public void show() {
		super.show();
		testActor = new AnimationActor(0.13f, "sword-swing.png", 4);
		testActor.rotateBy(90);
		testActor.setPosition(300, 300);
		testActor.setSize(84*3, 84);
		
		//stage.addActor(testActor);
	}
	
	
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
//		
//		duracion += delta;
//		TextureRegion frame = animation.getKeyFrame(duracion, true);
//		Render.Batch.begin();
//		Render.Batch.draw(frame, 600, 300);
//		Render.Batch.end();
	}
}

