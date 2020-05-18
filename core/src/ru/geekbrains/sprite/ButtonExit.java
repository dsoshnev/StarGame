package ru.geekbrains.sprite;

import ru.geekbrains.base.ButtonSprite;
import ru.geekbrains.utils.Rect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ButtonExit extends ButtonSprite {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("ButtonExit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        //super.resize(worldBounds);
        setRight(worldBounds.getRight() - 0.01f);
        setTop(worldBounds.getTop() - 0.12f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
