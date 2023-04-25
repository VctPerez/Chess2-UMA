package elements;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;

import utils.Image;
import utils.Resources;
/**
 * Fondo animado con desplazamiento en diagonal. La imagen de fondo se define en un archivo .PNG.
 * El efecto se consigue al generar 4 im�genes id�nticas y recolocandolas cuando salen de la pantalla
 */
public class Background extends Actor{
	
	private Image[] fondo;
	
	private float speed;
	private float speedX;
	private float speedY;
	
	private int pos3 = 1;
	private int pos2 = 0;

	public Background() {
		fondo = new Image[4];
		
		//Velocidades que permiten trayectoria del movimiento desde esquina superior izquierda
		//hasta esquina inferior derecha.
		
		speed = 2.5f;
		speedX = speed;
		speedY = -speed * (720f/1280f);
		
		//Iniciamos posX a 0 porque inicialmente el Viewport ocupa toda la pantalla
		
		for(int i  = 0; i < fondo.length ; i++) {
			fondo[i] = new Image(new Texture(Resources.MENU_BACKGROUND_PATH));
			fondo[i].setSize(1280, 720);
		}
		
		//POSCIONES INICIALES DE LAS IMAGENES
		fondo[0].setPosition(0,0);
		fondo[1].setPosition(-1280, 720);
		fondo[2].setPosition(-1280, 0);
		fondo[3].setPosition(0, 720);

		
	}
	
	public void setSize(float x,float y) {
		for(int i  = 0; i < fondo.length ; i++) {
			fondo[i] = new Image(new Texture(Resources.MENU_BACKGROUND_PATH));
			fondo[i].setSize(x, y);
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		updatePositions();
		
		for (int i = 0; i < fondo.length ; i++) {
			fondo[i].draw(batch, 0);
		}
	}
	/**
	 * Metodo que recalcula la posici�n de cada imagen en pantalla. En cada frame las mueve en una direccion X e Y.
	 * Si alguna imagen deja de verse en pantalla, se vuelve a posicion en las coordenadas adecuadas.
	 */
	public void updatePositions() {
		for(int i = 0; i < fondo.length ; i++) {
						
			if(fondo[i].getPosition().x >= -1280 && fondo[i].getPosition().x <= 1280 && 
					fondo[i].getPosition().y >= -720 && fondo[i].getPosition().y <= 720) {
				//Imagen visible en pantalla
				fondo[i].setPosition( fondo[i].getPosition().x + speedX, fondo[i].getPosition().y + speedY);
				
			}else {
				//Imagen no visible en pantalla
				switch (i) {
				case 0:
					//fondo[0] deja de ser visible, se recoloca en funcion de fondo[1]
					fondo[0].setPosition(fondo[1].getPosition().x - 1280, fondo[1].getPosition().y + 720);
					break;
				case 1:
					//fondo[1] deja de ser visible, se recoloca en funcion de fondo[0]
					fondo[1].setPosition(fondo[0].getPosition().x - 1280, fondo[0].getPosition().y + 720);
					break;
				case 2:
					//En cada iteraci�n del loop fondo[2] se tendra que colocar debajo de 0 o de 1
					fondo[2].setPosition(fondo[pos2].getPosition().x, fondo[pos2].getPosition().y - 720);
					//XOR para cambiar entre 0 y 1;
					pos2 ^= 1;
					break;
				case 3:
					//En cada iteraci�n del loop fondo[3] se tendra que colocar debajo de 0 o de 1
					fondo[3].setPosition(fondo[pos3].getPosition().x, fondo[pos3].getPosition().y + 720);
					pos3 ^= 1;
					break;
				}
			}
		}
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
		speedX = speed;
		speedY = -speed * ((float) 720/(float) 1280);
	}
	
	/**
	 * Metodo para actualizar la imagen de fondo
	 * @param path Ruta interna de la imagen
	 */
	public void setImage(String path) {
		for(int i = 0; i < fondo.length ; i++) {
			fondo[i].setImage(path);
		}
	}
	
}
