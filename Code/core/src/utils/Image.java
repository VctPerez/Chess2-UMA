package utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.List;

public class Image extends Actor{
    public Texture img;
    public Sprite sprt;
    private float transparencyConst;
    private boolean fade = false, fadeIn = false, fadeOut = false;
    private float contTime = 0;
    public float getTransparencyConst() {
        return transparencyConst;
    }

    public boolean isFade() {
        return fade;
    }

    public float getContTime() {
        return contTime;
    }

    public void setTransparencyConst(float transparencyConst) {
        this.transparencyConst = transparencyConst;
    }

    public void setFade(boolean fade) {
        this.fade = fade;
    }

    public void setContTime(float contTime) {
        this.contTime = contTime;
    }

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
    	sprt.setPosition(getX(),getY());
    	sprt.setSize(getWidth(), getHeight());
    	sprt.draw(batch);
    }
    
    @Override
    public void act(float delta) {
    	super.act(delta);
    }

    /**
     * Cambia el nivel de opacidad/transparencia de la imagen segun el parametro t.
     * @param t constante de transparencia
     */
    public void setTransparency(float t){
        sprt.setAlpha(t);
    }
    
    /**
     * invierte la imagen, x e y son booleanos que indican si invertir la imagen en ese eje o no
     * @param x
     * @param y
     */
    public void flip(Boolean x, Boolean y) {
    	sprt.flip(x, y);
    }

    /**
     *  Fader de imagen.
     * @param wait tiempo de espera cuando es totalmente opaca.
     * @param increase constante para incrementar la opacidad de la imagen, o disminuirla.
     * @return True = imagen transparente, False = imagen opaca.
     */
    public static boolean fader(float wait, float increase, List<Image> images){ // esto se puede hacer con el act y actions
        boolean end = false;

        for(Image img : images) {
            if (!img.isFade() && img.fadeIn) {
                img.setTransparencyConst(img.getTransparencyConst() + increase);
                if (img.getTransparencyConst() > 1) {
                    img.setTransparencyConst( 1);
                    img.setFade(true);
                }
                img.setTransparency(img.getTransparencyConst());
            } else if(img.fadeOut) {
                img.setContTime(img.getContTime() + 0.05f);
                if (img.getContTime() > wait) {
                    img.setTransparencyConst(img.getTransparencyConst() - increase);
                    if (img.getTransparencyConst() < 0) {
                        img.setTransparencyConst(0);
                        if (images.indexOf(img) == images.size() - 1) end = true;
                    }
                    img.setTransparency(img.getTransparencyConst());
                }
            }
        }
        return end;
    }

    /**
     * Cambia el tamaño de la imagen segun width, height.
     * @param width anchura.
     * @param height altura.
     */
//    public void setSize(float width, float height){
//    	super.setSize(width,height);
//        sprt.setSize(width, height);
//    }
    
    public void setImage(String path) {
    	sprt.setTexture(new Texture(path));
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

    public void setFadeIn(boolean fadeIn) {
        this.fadeIn = fadeIn;
    }

    public void setFadeOut(boolean fadeOut) {
        this.fadeOut = fadeOut;
    }

    public void dispose(){
        img.dispose();
    }


}

