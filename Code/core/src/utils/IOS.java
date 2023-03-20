package utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class IOS implements InputProcessor {

    public int mouseX, mouseY;

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

    public boolean isClicked(){
        return Gdx.input.isTouched();
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
        else mouseY = Render.PANTALLA_ALTURA - screenY;
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
