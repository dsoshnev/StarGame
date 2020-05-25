package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.SpaceShip;
import ru.geekbrains.sprite.Stars;
import ru.geekbrains.utils.Rect;
import ru.geekbrains.utils.Rnd;

public class GameScreen extends BaseScreen {

    private TextureAtlas atlas;
    private SpaceShip spaceShip;
    private Background background;
    private Music music;
    private Stars stars;

    // Enemy
    private static final float ENEMY_TIME = 4f;
    private List<EnemyShip> enemyShips;
    private Pool<EnemyShip> enemyShipPool;
    private float passingTime;


    public GameScreen(Game game) {
        super(game);
        Gdx.graphics.setResizable(false);
        Gdx.graphics.setWindowedMode(480,640);
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("mainAtlas.tpack");
        spaceShip = new SpaceShip(atlas);
        background = new Background(new Texture("bg.png"));
        stars = new Stars(atlas, 300);
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/gamemusic.mp3"));
        music.setVolume(0.05f);
        music.play();

        this.enemyShips = new ArrayList<>();
        this.enemyShipPool = new Pool<EnemyShip>(5,10) {
            @Override
            protected EnemyShip newObject() {
                return new EnemyShip(atlas);
            }
        };
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        spaceShip.resize(worldBounds);
        stars.resize(worldBounds);
        for (EnemyShip enemyShip: enemyShips) {
            enemyShip.resize(worldBounds);
        }
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

        Iterator itr = enemyShips.iterator();
        while (itr.hasNext())
        {
            EnemyShip enemyShip = (EnemyShip) itr.next();
            enemyShip.update(delta);
            if(!enemyShip.isActive()) {
                itr.remove();
                enemyShipPool.free(enemyShip);
                System.out.printf("EnemyPool/EnemyActive:%s/%s%n", enemyShipPool.getFree(), enemyShips.size());
            }
        }

        //Draw
        batch.begin();
        background.draw(batch);
        stars.draw(batch);
        spaceShip.draw(batch);
        for (EnemyShip enemyShip: enemyShips) {
            enemyShip.draw(batch);
        }

        batch.end();
    }

    private void createEnemy() {
        float x0 = Rnd.nextFloat(-0.25f, 0.25f);
        EnemyShip enemyShip = enemyShipPool.obtain();
        enemyShip.setup(new Vector2(x0, 1f), new Vector2(x0, -0.01f), worldBounds);
        enemyShips.add(enemyShip);
        System.out.printf("EnemyPool/EnemyActive:%s/%s%n", enemyShipPool.getFree(), enemyShips.size());
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
