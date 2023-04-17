package utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Image extends Actor {
    public Texture img;
    public Sprite sprt;
    private float transparencyConst;
    private boolean fade = false;
    private float contTime = 0;

    /**
     * Constructor de una imagen
     * @param path String de la ruta de la imagen.
     */
    public Image(String path){
        img = new Texture(path);
        sprt = new Sprite(img);
    }

    /**
     * Constructor de una imagen
     * @param img Textura a utilizar
     */
    public Image(Texture img){
        this.img = img;
        sprt = new Sprite(img);
    }

    /**
     * Dibuja la imagen
     */
    @Override
    public void draw(Batch batch, float parentAlpha){
        sprt.draw(batch);
    }

    /**
     * Cambia el nivel de opacidad/transparencia de la imagen segun el parametro t.
     * @param t constante de transparencia
     */
    public void setTransparency(float t){
        sprt.setAlpha(t);
    }

    /**
     *  Fader de imagen.
     * @param wait tiempo de espera cuando es totalmente opaca.
     * @param increase constante para incrementar la opacidad de la imagen, o disminuirla.
     * @return True = imagen transparente, False = imagen opaca.
     */
    public boolean fader(float wait, float increase){ // esto se puede hacer con el act y actions
        boolean end = false;


        if(!fade){
            transparencyConst+=increase;
            if (transparencyConst > 1) {
                transparencyConst = 1;
                fade = true;
            }
        }
        else{
            contTime +=0.05f;
            if(contTime > wait){
                transparencyConst -= increase;
                if(transparencyConst < 0){
                    transparencyConst = 0;
                    end = true;
                }
            }
        }
        setTransparency(transparencyConst);
        return end;
    }

    /**
     * Cambia el tamaño de la imagen segun width, height.
     * @param width anchura.
     * @param height altura.
     */
    public void setSize(float width, float height){
        sprt.setSize(width, height);
    }

    /**
     * Cambia la posicion de la imagen (siendo la posición la esquina inferior izquierda).
     * @param x coordenada x.
     * @param y coordenada y.
     */
    public void setPosition(float x, float y){
        sprt.setPosition(x,y);
    }

    public Vector2 getPosition(){
        return new Vector2(sprt.getX(), sprt.getY());
    }

    public Vector2 getDimensions(){
        return new Vector2(sprt.getWidth(),sprt.getHeight());
    }
    
    public void setScale(float x) {
    	sprt.setScale(x);
    }
    
    public void setColor(float r, float g, float b, float alpha) {
    	sprt.setColor(r, g, b, alpha);
    }

    public void dispose(){
        img.dispose();
    }
}

