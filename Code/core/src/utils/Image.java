package utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Image {
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
     * Dibuja la imagen
     */
    public void draw(){
        sprt.draw(Render.Batch);
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
     * @return True = imagen transparente, False = imagen opaca
     */
    public boolean fader(){
        boolean end = false;


        if(!fade){
            transparencyConst+=0.005f;
            if (transparencyConst > 1) {
                transparencyConst = 1;
                fade = true;
            }
        }
        else{
            contTime +=0.05f;
            float tWait = 5;
            if(contTime > tWait){
                transparencyConst -= 0.005f;
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
}

