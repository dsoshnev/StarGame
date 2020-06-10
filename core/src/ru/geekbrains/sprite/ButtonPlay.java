package ru.geekbrains.sprite;

import ru.geekbrains.base.ButtonSprite;
import ru.geekbrains.screen.GameScreen;
import ru.geekbrains.utils.Rect;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ButtonPlay extends ButtonSprite {

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("ButtonPlay"), game);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setRight(worldBounds.getRight() - 0.01f);
        setTop(worldBounds.getTop() - 0.01f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}
