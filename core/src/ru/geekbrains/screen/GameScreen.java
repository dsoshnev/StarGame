package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.group.SpriteFactory;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.SpaceShip;
import ru.geekbrains.group.Stars;
import ru.geekbrains.utils.Rect;
import ru.geekbrains.utils.Rnd;

public class GameScreen extends BaseScreen {

    private TextureAtlas atlas;
    private SpaceShip spaceShip;
    private Background background;
    private Music music;

    //Stars
    private Stars stars;

    //Bullets
    private SpriteFactory bullets;

    // Enemy Ships
    private static final float ENEMY_TIME = 4f;
    private SpriteFactory enemyShips;


    private float passingTime;

    public GameScreen(Game game) {
        super(game);
        //Gdx.graphics.setResizable(false);
        Gdx.graphics.setWindowedMode(480,640);
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("mainAtlas.tpack");

        // Bullets
        bullets = new SpriteFactory(SpriteFactory.SpriteType.BULLET);

        // Spaceship
        spaceShip = new SpaceShip(atlas, bullets);

        // Stars
        background = new Background(new Texture("bg.png"));
        stars = new Stars(atlas, 300);

        // Music
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/gamemusic.mp3"));
        music.setVolume(0.05f);
        music.play();

        // EnemyShips
        enemyShips = new SpriteFactory(SpriteFactory.SpriteType.ENEMY_SHIP);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        spaceShip.resize(worldBounds);
        stars.resize(worldBounds);
        enemyShips.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        //Update
        stars.update(delta);
        spaceShip.update(delta);

        passingTime +=delta;
        if (passingTime >= ENEMY_TIME) {
            createEnemy();
            passingTime = 0;
        }

        enemyShips.update(delta);

        //Draw
        batch.begin();
        background.draw(batch);
        stars.draw(batch);
        spaceShip.draw(batch);
        enemyShips.draw(batch);
        batch.end();
    }

    private void createEnemy() {
        float x0 = Rnd.nextFloat(-0.25f, 0.25f);
        int type = new Random().nextInt(3);
        EnemyShip enemyShip = (EnemyShip) enemyShips.obtain();
        enemyShip.setup(atlas, bullets, new Vector2(x0, 1f), worldBounds, type);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        spaceShip.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        spaceShip.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        spaceShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        spaceShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public void dispose() {
        background.dispose();
        atlas.dispose();
        music.dispose();
        super.dispose();
    }
}
