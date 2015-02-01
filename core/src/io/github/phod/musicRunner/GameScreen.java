package io.github.phod.musicRunner;

import com.badlogic.gdx.Gdx;
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

    public GameScreen(final MusicRunner game) {
        this.game = game;
        this.batch = this.game.getBatch();
        playerBlock = new PlayerBlock(startX, startY);
        playerCol = playerBlock.getPlayerCol();
        groundBlocks = new MusicBlock[800/64 + 1];
        int count = 0;
        for (int i = 0; i < 800; i += 64){
            groundBlocks[count] = new MusicBlock(i, blockY);
            count++;
        }
    }

    @Override
    public void show() {

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

        for(MusicBlock mB: groundBlocks) {
            Rectangle currCol = mB.getBlockCol();
            if (currCol.overlaps(this.playerCol)) {
                mB.playNote();
            }
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
