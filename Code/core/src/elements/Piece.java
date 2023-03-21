package elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Piece extends Actor{
	private Sprite sprite;
	//Soy david
	
	public Piece() {
		sprite = new Sprite(new Texture("Piece2.png"));
<<<<<<< HEAD
		System.out.println("B");
=======
		System.out.println("A");
>>>>>>> c4a22f3a9fd1fc276ecaf9addced783adf805c99
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		sprite.setPosition(getX(), getY());
		sprite.setSize(getWidth(), getHeight());
		sprite.setScale(getScaleX());
		sprite.draw(batch);
	}

}
