package elements;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import utils.Image;
import utils.Render;
import utils.Resources;
/**
 * Fondo animado con desplazamiento en diagonal. La imagen de fondo se define en un archivo .PNG.
 * El efecto se consigue al generar 4 imágenes idénticas y recolocandolas cuando salen de la pantalla
 */
public class Background extends Actor{
	
	private Image[] fondo;
	private float speedX;
	private float speedY;
	
	float posX;
	float posY;
	
	private int pos3 = 1;
	private int pos2 = 0;

	public Background() {
		fondo = new Image[4];
		
		//Velocidades que permiten trayectoria del movimiento desde esquina superior izquierda
		//hasta esquina inferior derecha.
		speedX = 2.5f;
		speedY = -1.40625f;
		
		for(int i  = 0; i < fondo.length ; i++) {
			fondo[i] = new Image(new Texture(Resources.MENU_BACKGROUND_PATH));
			fondo[i].setSize(Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT);
		}
		
		//POSCIONES INICIALES DE LAS IMAGENES
		fondo[0].setPosition(0,0);
		fondo[1].setPosition(-Render.SCREEN_WIDTH, Render.SCREEN_HEIGHT);
		fondo[2].setPosition(-Render.SCREEN_WIDTH, 0);
		fondo[3].setPosition(0, Render.SCREEN_HEIGHT);

		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		updatePositions();
		
		for (int i = 0; i < fondo.length ; i++) {
			fondo[i].draw(batch, 0);
		}
	}
	/**
	 * Metodo que recalcula la posición de cada imagen en pantalla. En cada frame las mueve en una direccion X e Y.
	 * Si alguna imagen deja de verse en pantalla, se vuelve a posicion en las coordenadas adecuadas.
	 */
	public void updatePositions() {
		for(int i = 0; i < fondo.length ; i++) {
			
			//Coordenadas de la imagen
			posX = fondo[i].getPosition().x;
			posY = fondo[i].getPosition().y;
			
			
			if(posX >= -Render.SCREEN_WIDTH && posX <= Render.SCREEN_WIDTH && 
					posY >= -Render.SCREEN_HEIGHT && posY <= Render.SCREEN_HEIGHT) {
				//Imagen visible en pantalla
				fondo[i].setPosition(posX + speedX, posY + speedY);
				
			}else {
				//Imagen no visible en pantalla
				switch (i) {
				case 0:
					//fondo[0] deja de ser visible, se recoloca en funcion de fondo[1]
					fondo[0].setPosition(fondo[1].getPosition().x - Render.SCREEN_WIDTH, fondo[1].getPosition().y + Render.SCREEN_HEIGHT);
					break;
				case 1:
					//fondo[1] deja de ser visible, se recoloca en funcion de fondo[0]
					fondo[1].setPosition(fondo[0].getPosition().x - Render.SCREEN_WIDTH, fondo[0].getPosition().y + Render.SCREEN_HEIGHT);
					break;
				case 2:
					//En cada iteración del loop fondo[2] se tendra que colocar debajo de 0 o de 1
					fondo[2].setPosition(fondo[pos2].getPosition().x, fondo[pos2].getPosition().y - Render.SCREEN_HEIGHT);
					//XOR para cambiar entre 0 y 1;
					pos2 ^= 1;
					break;
				case 3:
					//En cada iteración del loop fondo[3] se tendra que colocar debajo de 0 o de 1
					fondo[3].setPosition(fondo[pos3].getPosition().x, fondo[pos3].getPosition().y + Render.SCREEN_HEIGHT);
					pos3 ^= 1;
					break;
				}
			}
		}
	}
	
	/**
	 * Método para actualizar la imagen de fondo
	 * @param path Ruta interna de la imagen
	 */
	public void setImage(String path) {
		for(int i = 0; i < fondo.length ; i++) {
			fondo[i].setImage(path);
		}
	}
	
}
