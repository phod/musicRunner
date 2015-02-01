package io.github.phod.musicRunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;


public class GameScreen implements Screen {

    private final MusicRunner game;
    private PlayerBlock playerBlock;
    private final int startX = 64;
    private final int startY = 64;
    private final int blockY = 0;
    private Batch batch;
    private Random random;

    private MusicBlock[] groundBlocks;
    private Rectangle playerCol;
    private int frontBlock; //MusicBlock in groundBlocks closest to x=-64
    private int endBlock; //MusicBlock in groundBlocks furthest from x=-64

    private final int MAX_SPEED = 400;
    private final int MIN_SPEED = 100;
    private final int SPEED_CHANGE = 15;
    private int currPlayerSpeed;

    private Array<EnemyBlock> enemyBlocks;
    private long lastSpawnTime;
    private long nextSpawnTime;
    private final int ENEMY_X_SPAWN = 764;
    private final int ENEMY_MAX_HEIGHT = 480 - 64;
    private final int ENEMY_MIN_HEIGHT = 64;
    private int enemiesSpawned;
    private final int ENEMY_SPEED_MULT = 10;
    private final int MAX_ENEMY_MULT = 40;

    public GameScreen(final MusicRunner game) {
        this.game = game;
        this.batch = this.game.getBatch();
        playerBlock = new PlayerBlock(startX, startY);
        playerCol = playerBlock.getPlayerCol();
        groundBlocks = new MusicBlock[864/64 + 1];
        int count = 0;
        for (int i = 0; i < 864; i += 64){
            groundBlocks[count] = new MusicBlock(i, blockY);
            count++;
        }
        frontBlock = 0;
        endBlock = groundBlocks.length - 1;
        currPlayerSpeed = playerBlock.getSpeed();

        random = new Random();
        enemyBlocks = new Array<EnemyBlock>();
        lastSpawnTime = TimeUtils.nanoTime();
        nextSpawnTime = calcEnemySpawn();
        enemiesSpawned = 0;
    }

    public long calcEnemySpawn() {
        return lastSpawnTime + (random.nextInt(5) + 1) * 1000000000l;
    }

    @Override
    public void show() {

    }

    public void getInput() {
        currPlayerSpeed = playerBlock.getSpeed();
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (currPlayerSpeed < MAX_SPEED) {
                playerBlock.setSpeed((currPlayerSpeed + SPEED_CHANGE));
            }
            if (currPlayerSpeed > MAX_SPEED) {
                playerBlock.setSpeed((MAX_SPEED));
            }

        } else if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (currPlayerSpeed > MIN_SPEED) {
                playerBlock.setSpeed((currPlayerSpeed - SPEED_CHANGE));
            }
            if (currPlayerSpeed < MIN_SPEED) {
                playerBlock.setSpeed((MIN_SPEED));
            }
        }
    }

    public void spawnEnemy() {
        if (enemiesSpawned < MAX_ENEMY_MULT) {
            enemiesSpawned++;
        }
        int yPos = random.nextInt(ENEMY_MAX_HEIGHT - ENEMY_MIN_HEIGHT)
                + ENEMY_MIN_HEIGHT; //Spawn enemy between min and max height
        int moveSpeed = random.nextInt(210) + 100 + enemiesSpawned*ENEMY_SPEED_MULT;
        enemyBlocks.add(new EnemyBlock(ENEMY_X_SPAWN, yPos, moveSpeed,
                ENEMY_MAX_HEIGHT, ENEMY_MIN_HEIGHT));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        playerBlock.draw(batch);
        for(MusicBlock mB: groundBlocks) {
            mB.draw(batch);
        }
        for(EnemyBlock enemy: enemyBlocks) {
            enemy.draw(batch);
        }
        batch.end();

        //Check if the player is on top of a block and play a note, if so.
        for(MusicBlock mB: groundBlocks) {
            Rectangle currCol = mB.getBlockCol();
            if (currCol.overlaps(this.playerCol)) {
                mB.playNote();
            }
        }

        //Move all the blocks along to the left.
        for(MusicBlock mB: groundBlocks) {
            mB.moveXPos(Math.round(playerBlock.getSpeed() * Gdx.graphics.getDeltaTime()));
        }

        //Check for enemy/player collision
        //Else check if enemies off the screen and move enemies along to the left.
        for (int i = 0; i < enemyBlocks.size; i++) {
            if (enemyBlocks.get(i).getEnemyCol().overlaps(this.playerCol)) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            } else if (enemyBlocks.get(i).getXPos() < -64) {
                enemyBlocks.removeIndex(i);
                i--;
            } else {
                enemyBlocks.get(i).moveXPos(Math.round(playerBlock.getSpeed() *
                        Gdx.graphics.getDeltaTime()));
            }
        }

        //Check if block is off the screen, if so move to other end of screen
        if (groundBlocks[frontBlock].getXPos() < -64) {
            groundBlocks[frontBlock].setXPos(
                    groundBlocks[endBlock].getXPos() + 64, true);
            frontBlock = (frontBlock + 1) % groundBlocks.length;
            endBlock = (endBlock + 1) % groundBlocks.length;
        }

        getInput();

        if(TimeUtils.nanoTime() > nextSpawnTime) {
            spawnEnemy();
            lastSpawnTime = nextSpawnTime;
            nextSpawnTime = calcEnemySpawn();
        }

        for(EnemyBlock enemy: enemyBlocks) {
            enemy.changeHeight(Gdx.graphics.getDeltaTime());
        }

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
