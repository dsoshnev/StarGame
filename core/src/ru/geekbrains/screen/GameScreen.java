package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;
import java.util.Random;

import ru.geekbrains.StarGame;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.base.ZXFont;
import ru.geekbrains.group.SpriteFactory;
import ru.geekbrains.sprite.EnemyShip;
import ru.geekbrains.sprite.Explosion;
import ru.geekbrains.sprite.SpaceShip;
import ru.geekbrains.group.Stars;
import ru.geekbrains.utils.Rect;
import ru.geekbrains.utils.Rnd;

public class GameScreen extends BaseScreen {

    private static final String TEXT_SCORE = "SCORE:";
    private static final String TEXT_HP = "HP:";
    private static final String TEXT_RD = "RED ALARM";
    private static final String TEXT_GAME_OVER = "GAME OVER";
    private static final float GAME_OVER_INTERVAL = 5f;

    public enum GameState {
        GAME_MAIN,
        GAME_OVER
    }

    private GameState gameState;

    public TextureAtlas atlas;

    private SpaceShip spaceShip;


    //Stars
    private Stars stars;

    //Bullets
    private SpriteFactory bullets;

    //Enemies
    private static final float ENEMY_INTERVAL = 4f;
    private SpriteFactory enemyShips;

    //Explosion
    private Explosion explosion;
    private Sound explosionSound;

    //Font
    ZXFont font;
    StringBuffer sbScore;
    StringBuffer sbHP;
    int score;

    private float passingTime;
    private float passingTime1;

    public GameScreen(StarGame game) {
        super(game);
        //Gdx.graphics.setResizable(false);
        //Gdx.graphics.setWindowedMode(480,640);
    }

    @Override
    public void show() {
        super.show();
        atlas = game.getGameAtlas();

        // Game
        gameState = GameState.GAME_MAIN;

        // Bullets
        bullets = new SpriteFactory(SpriteFactory.SpriteType.BULLET);

        // Spaceship
        spaceShip = new SpaceShip(atlas, bullets);
        spaceShip.setActive(true);

        // Stars and Background
        stars = new Stars(atlas, 100);

        // Enemies
        enemyShips = new SpriteFactory(SpriteFactory.SpriteType.ENEMY_SHIP);

        // Explosion
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosion = new Explosion(atlas, explosionSound);


        //Font
        font = new ZXFont("fonts/ZXSpectrum.fnt", "fonts/ZXSpectrum.png");
        font.setSize(ZXFont.TEXT_SIZE * 0.75f);
        sbScore = new StringBuffer(TEXT_SCORE);
        sbHP = new StringBuffer(TEXT_HP);
        score = 0;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        stars.resize(worldBounds);
        bullets.resize(worldBounds);
        spaceShip.resize(worldBounds);
        enemyShips.resize(worldBounds);
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
            createEnemy(delta);
            enemyShips.update(delta);
            checkCollision();
            bullets.update(delta);
        } else {
            explosion.update(delta);
            gameOver(delta);
        }
    }

    private void draw() {
        batch.begin();
        stars.draw(batch);
        bullets.draw(batch);
        spaceShip.draw(batch);
        enemyShips.draw(batch);
        explosion.draw(batch);
        printInfo();
        batch.end();
    }

    private void createEnemy(float delta) {
        passingTime += delta;
        if (passingTime >= ENEMY_INTERVAL) {
            //float x0 = Rnd.nextFloat(-0.25f, 0.25f);
            float x0 = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
            int enemyType = new Random().nextInt(3);
            EnemyShip enemyShip = (EnemyShip) enemyShips.obtain();
            Vector2 pos0 = new Vector2(x0, 1f);
            enemyShip.setup(atlas, bullets, pos0, worldBounds, enemyType);
            passingTime = 0;
        }
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
                            activeEnemyShip.damage(activeBullet.getDamage());
                            score += activeEnemyShip.getDamage();
                            activeBullet.destroy();
                        }
                    }
                }

                if (spaceShip.collided(activeEnemyShip)) {
                    spaceShip.damage(activeEnemyShip.getDamage());
                    score += activeEnemyShip.getDamage();
                    activeEnemyShip.destroy();
                }
            }

            for (Sprite activeBullet : activeBullets) {
                if (activeBullet.isActive() && activeBullet.getOwner() != spaceShip) {
                    if (spaceShip.collided(activeBullet)) {
                        if(!spaceShip.isShieldUp()) {
                            spaceShip.damage(activeBullet.getDamage());
                        }
                        activeBullet.destroy();
                    }
                }
            }
            if(spaceShip.getHitPoint() <= 0) {
                gameState = GameState.GAME_OVER;
                explosion.setup(0.1f, spaceShip.pos);
            }
        }
    }

    void printInfo() {
        sbScore.setLength(TEXT_SCORE.length());
        sbHP.setLength(TEXT_HP.length());
        font.setSize(ZXFont.TEXT_SIZE * 0.75f);

        if(spaceShip.isShieldUp()) {
            font.setColor(Color.RED);
            font.draw(batch, TEXT_RD, worldBounds.getLeft() + font.getSpaceXadvance() * 16, worldBounds.getTop(), Align.left);
        } else {
            font.setColor(Color.WHITE);
        }
        font.draw(batch, sbScore.append(score), worldBounds.getLeft() + font.getSpaceXadvance() * 0.1f,worldBounds.getTop(), Align.left);
        font.draw(batch, sbHP.append(spaceShip.getHitPoint()), worldBounds.getLeft() + font.getSpaceXadvance() * 9,worldBounds.getTop(), Align.left);

        if(gameState == GameState.GAME_OVER) {
            font.setSize(ZXFont.TEXT_SIZE * 2f);
            font.draw(batch, TEXT_GAME_OVER, 0,0, Align.center);
        }
    }

    void gameOver(float delta) {
        passingTime1 += delta;
        if (passingTime1 >= GAME_OVER_INTERVAL) {
            game.setScreen(game.getMenuScreen());
            passingTime1 = 0;
        }
    }


    @Override
    public void dispose() {
        atlas.dispose();
        spaceShip.dispose();
        enemyShips.dispose();
        explosion.dispose();
        explosionSound.dispose();
        font.dispose();
        super.dispose();
    }
}
