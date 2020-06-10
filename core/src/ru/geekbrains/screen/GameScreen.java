package ru.geekbrains.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;
import java.util.Random;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.group.SpriteFactory;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.SpaceShip;
import ru.geekbrains.group.Stars;
import ru.geekbrains.utils.Rect;
import ru.geekbrains.utils.Rnd;

public class GameScreen extends BaseScreen {

    public enum GameState {
        GAME_MAIN,
        GAME_OVER
    }

    private GameState gameState;

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
        atlas = new TextureAtlas("GameAtlas.atlas");

        // Game
        gameState = GameState.GAME_MAIN;

        // Bullets
        bullets = new SpriteFactory(SpriteFactory.SpriteType.BULLET);

        // Spaceship
        spaceShip = new SpaceShip(atlas, bullets);

        // Stars and Background
        background = new Background(new Texture("bg.png"));
        stars = new Stars(atlas, 100);

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
        bullets.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta) {
        stars.update(delta);

        if(gameState == GameState.GAME_MAIN) {
            spaceShip.update(delta);

            passingTime += delta;
            if (passingTime >= ENEMY_TIME) {
                createEnemy();
                passingTime = 0;
            }

            enemyShips.update(delta);

            checkCollision();

            bullets.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        stars.draw(batch);
        spaceShip.draw(batch);
        enemyShips.draw(batch);
        bullets.draw(batch);
        batch.end();
    }

    private void createEnemy() {
        float x0 = Rnd.nextFloat(-0.25f, 0.25f);
        //float x0 = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        int enemyType = new Random().nextInt(3);
        EnemyShip enemyShip = (EnemyShip) enemyShips.obtain();
        Vector2 pos0 = new Vector2(x0, 1f);
        enemyShip.setup(atlas, bullets, pos0, worldBounds, enemyType);
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

    private void checkCollision() {
        if(gameState == GameState.GAME_MAIN) {
            List<Sprite> activeEnemyShips = enemyShips.getActiveSprites();
            List<Sprite> activeBullets = bullets.getActiveSprites();
            for (Sprite activeEnemyShip : activeEnemyShips) {
                for (Sprite activeBullet : activeBullets) {
                    if (activeBullet.isActive() && activeBullet.getOwner() != activeEnemyShip) {
                        if (activeEnemyShip.collided(activeBullet)) {
                            activeEnemyShip.destroy();
                        }
                    }
                }
            }
            for (Sprite activeBullet : activeBullets) {
                if (activeBullet.isActive() && activeBullet.getOwner() != spaceShip) {
                    if (spaceShip.collided(activeBullet)) {
                        gameState = GameState.GAME_OVER;
                    }
                }
            }
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        atlas.dispose();
        music.dispose();
        spaceShip.dispose();
        enemyShips.dispose();
        super.dispose();
    }
}
