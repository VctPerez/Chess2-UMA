package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class AnimationActor extends Actor{
	private Animation<TextureRegion> animation;
	private Texture texture;
	private TextureRegion region;
	private TextureRegion[] frames;
	
	private float duracion = 0;
	
	/**
	 * Dados la duracion de la animacion, el path de la imagen con los frames y el numero de frames,
	 * crea un actor con la animacion contenida en la imagen 
	 * @param frameDuration
	 * @param texturePath
	 * @param framesNumber
	 */
	public AnimationActor(float frameDuration, String texturePath, int framesNumber) {
		texture = new Texture(texturePath);
		region = new TextureRegion(texture, texture.getWidth(), texture.getHeight());
		TextureRegion[][] temp = region.split(texture.getWidth()/framesNumber, texture.getHeight());
		frames = new TextureRegion[temp.length * temp[0].length];
		
		int index = 0;
		for(int i = 0; i<temp.length;i++) {
			for(int j=0; j<temp[0].length; j++) {
				frames[index++] = temp[i][j];
			}
		}
		animation = new Animation(frameDuration, frames);
	}

	public AnimationActor(float frameDuration, Texture texture, int framesNumber){
		this.texture = texture;
		region = new TextureRegion(texture, texture.getWidth(), texture.getHeight());
		TextureRegion[][] temp = region.split(texture.getWidth()/framesNumber, texture.getHeight());
		frames = new TextureRegion[temp.length * temp[0].length];

		int index = 0;
		for(int i = 0; i<temp.length;i++) {
			for(int j=0; j<temp[0].length; j++) {
				frames[index++] = temp[i][j];
			}
		}
		animation = new Animation(frameDuration, frames);
	}
	@Override
	public void draw(Batch batch, float parentAlpha) {
		this.toFront();

		duracion += Gdx.graphics.getDeltaTime();
		TextureRegion frame = animation.getKeyFrame(duracion);
		batch.draw(frame, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation(), true);
		if(animation.isAnimationFinished(duracion)) {
			remove();
			//texture.dispose();
		}
	}
	
	
}
