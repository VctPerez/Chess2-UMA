package elements;

import java.io.IOException;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import utils.Image;
import utils.Render;
import utils.Resources;


public class DrawBox extends Actor{
	private Image check = new Image(Resources.TICK_PATH);
	private Image cross =new Image(Resources.CROSS_PATH);
	
	public DrawBox() {
		check.setPosition(1050, 100);
		check.setSize(50, 50);
		check.addListener(new ClickListener() {
    		@Override
    		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
    			
    			try{
    				if(Render.hosting) {
	    				Render.host.sendMessage("ACEPTAR");
	    			}else {
	    				Render.guest.sendMessage("ACEPTAR");
	    			}
    			}catch(IOException e) {
    				e.printStackTrace();
    			}
    			Render.GameScreen.results.setDraw();
    			Render.GameScreen.showPopup = true;
    			Render.GameScreen.blackCheckMate = true;
    			return true;
    		}
    	});
		
		cross.setPosition(1100, 100);
		cross.setSize(50, 50);
		cross.addListener(new ClickListener() {
    		@Override
    		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
    			System.out.println("TOCADO");
    			try{
    				if(Render.hosting) {
	    				Render.host.sendMessage("RECHAZAR");
	    			}else {
	    				Render.guest.sendMessage("RECHAZAR");
	    			}
    				Render.GameScreen.draw.setTouchable(Touchable.enabled);
    			}catch(IOException e) {
    				e.printStackTrace();
    			}
    			cross.remove();
    			check.remove();
    			return true;
    		}
    	});
	}
	
	public Image getCheck() {
		return check;
	}

	public void setCheck(Image check) {
		this.check = check;
	}

	public Image getCross() {
		return cross;
	}

	public void setCross(Image cross) {
		this.cross = cross;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {	
		check.draw(batch, parentAlpha);
		cross.draw(batch, parentAlpha);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
	

}
