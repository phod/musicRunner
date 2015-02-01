package io.github.phod.musicRunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;


public class GameScreen implements Screen {

    private final MusicRunner game;
    private PlayerBlock playerBlock;
    private final int startX = 64;
    private final int startY = 64;
    private final int blockY = 0;
    private Batch batch;
    private MusicBlock[] groundBlocks;
    private Rectangle playerCol;
    private int frontBlock; //MusicBlock in groundBlocks closest to x=-64
    private int endBlock; //MusicBlock in groundBlocks furthest from x=-64
    private final int MAX_SPEED = 400;
    private final int MIN_SPEED = 100;
    private final int SPEED_CHANGE = 15;
    private int currPlayerSpeed;

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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        playerBlock.draw(batch);
        for(MusicBlock mB: groundBlocks) {
            mB.draw(batch);
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

        //Check if block is off the screen, if so move to other end of screen
        if (groundBlocks[frontBlock].getXPos() < -64) {
            groundBlocks[frontBlock].setXPos(
                    groundBlocks[endBlock].getXPos() + 64, true);
            frontBlock = (frontBlock + 1) % groundBlocks.length;
            endBlock = (endBlock + 1) % groundBlocks.length;
        }

        getInput();

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
