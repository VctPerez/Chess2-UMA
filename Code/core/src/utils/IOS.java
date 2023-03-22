package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * Esta clase se encarga de la entrada por teclado, y del ratón.
 */
public class IOS implements InputProcessor {
    public int mouseX, mouseY; // posiciones "x" e "y" del mouse.

    public boolean justPressed(int keycode){
        return Gdx.input.isKeyJustPressed(keycode);
    }
    @Override
    public boolean keyDown(int keycode) {
        return Gdx.input.isKeyPressed(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return !Gdx.input.isKeyPressed(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Este metodo es lanzado cada vez que se hace click en pantalla.
     * @return True | False.
     */
    public boolean isClicked(){
        return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseX = screenX;
        if(Gdx.graphics.isFullscreen())mouseY = Gdx.graphics.getHeight() - screenY;
        else mouseY = Render.SCREEN_HEIGHT - screenY;
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
