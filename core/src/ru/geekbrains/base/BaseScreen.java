package ru.geekbrains.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.utils.MatrixUtils;
import ru.geekbrains.utils.Rect;

public class BaseScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    protected Rect screenBounds;
    protected Rect worldBounds;
    protected Rect glBounds;

    protected Matrix4 worldToGl;
    protected Matrix3 screenToWorld;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {
        System.out.println("worldBounds worldBounds.height = " + worldBounds.getHeight() + " worldBounds.with = " + worldBounds.getWidth());
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.printf("keyDown=%s%n", keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.printf("keyUp=%s%n", keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 touch = new Vector2();
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        return touchDown(touch, pointer, button);
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 touch = new Vector2();
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
        return touchUp(touch, pointer, button);
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
