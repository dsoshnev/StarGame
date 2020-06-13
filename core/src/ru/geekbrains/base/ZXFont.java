package ru.geekbrains.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class ZXFont extends BitmapFont {

    public static final float TEXT_SIZE = 0.03f;
    public static final float TEXT_MARGIN = 0.03f;

    public ZXFont(String fontFile, String imageFile) {
        super(Gdx.files.internal(fontFile), Gdx.files.internal(imageFile), false, false);
        setColor(Color.WHITE);
    }

    public void setSize(float size) {
        getData().setScale(size * getScaleY() / getCapHeight());
    }

    public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, int halign) {
        return super.draw(batch, str, x, y,0f, halign, false);
    }
}
