package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.ZXFont;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.ButtonExit;
import ru.geekbrains.sprite.ButtonPlay;
import ru.geekbrains.utils.Rect;

public class MenuScreen extends BaseScreen {

    private static final String TEXT_LINE1 = "*** SHIELDS UP ***";
    private static final String TEXT_LINE2 = "Press Space or 0 to Fire";
    private static final String TEXT_LINE3 = "Press S to Shield Up";
    private static final String TEXT_LINE4 = "Press 2 to Move Left";
    private static final String TEXT_LINE5 = "Press 4 to Move Right";
    private static final String TEXT_LINE6 = "COPYRIGHT BY DMITRY SOSHNEV";

    private Background background;

    private ButtonPlay buttonPlay;
    private ButtonExit buttonExit;
    private ZXFont font;

    public MenuScreen(StarGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        // Background
        background = new Background(new Texture("stars.jpg"));
        background.setActive(true);

        // Buttons
        buttonPlay = new ButtonPlay(game.getMenuAtlas(), game);
        buttonExit = new ButtonExit(game.getMenuAtlas(), game);
        buttonPlay.setActive(true);
        buttonExit.setActive(true);

        // Font
        font = new ZXFont("fonts/ZXSpectrum.fnt", "fonts/ZXSpectrum.png");
        font.setSize(ZXFont.TEXT_SIZE);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        buttonPlay.resize(worldBounds);
        buttonExit.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        printInfo();
        buttonPlay.draw(batch);
        buttonExit.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        buttonPlay.touchDown(touch, pointer, button);
        buttonExit.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        buttonPlay.touchUp(touch, pointer, button);
        buttonExit.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }

    void printInfo() {
        //float  mx = font.getLineHeight() / 2;
        float  my = font.getCapHeight() + ZXFont.TEXT_MARGIN;
        //TextureRegion t = gameAtlas.findRegion("enemy1");
        //TextureRegion t2 = gameAtlas.findRegion("enemy2");
        //batch.draw(t, (-mx * TEXT_LINE1.length()) - ZXFont.TEXT_MARGIN * 12, worldBounds.getTop() - my * 2, 0.1f, 0.1f * t.getRegionHeight()/t.getRegionWidth());
        //batch.draw(t2, (mx * TEXT_LINE1.length()) + ZXFont.TEXT_MARGIN, worldBounds.getTop() - my * 2, 0.1f, 0.1f * t2.getRegionHeight()/t2.getRegionWidth());

        font.draw(batch, TEXT_LINE1, 0,worldBounds.getTop() - my, Align.center);
        font.draw(batch, TEXT_LINE2, font.getSpaceXadvance() * -10,worldBounds.getTop() - my * 4, Align.left);
        font.draw(batch, TEXT_LINE3, font.getSpaceXadvance() * -10,worldBounds.getTop() - my * 5, Align.left);
        font.draw(batch, TEXT_LINE4, font.getSpaceXadvance() * -10,worldBounds.getTop() - my * 6, Align.left);
        font.draw(batch, TEXT_LINE5, font.getSpaceXadvance() * -10,worldBounds.getTop() - my * 7, Align.left);
        font.draw(batch, TEXT_LINE6, 0,worldBounds.getTop() - my * 10, Align.center);
    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
        super.dispose();
    }
}
