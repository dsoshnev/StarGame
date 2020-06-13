package ru.geekbrains.sprite;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.ButtonSprite;
import ru.geekbrains.utils.Rect;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ButtonPlay extends ButtonSprite {

    public ButtonPlay(TextureAtlas atlas, StarGame game) {
        super(atlas.findRegion("ButtonPlay"), game);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setLeft(worldBounds.getLeft() + 0.1f);
        setBottom(worldBounds.getBottom() + 0.1f);
    }

    @Override
    public void action() {
        game.setScreen(game.getGameScreen());
    }
}
