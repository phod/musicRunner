package io.github.phod.musicRunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;

public class GameScreen implements Screen {

    private final MusicRunner game;
    private PlayerBlock playerBlock;
    private final int startX = 40;
    private final int startY = 40;
    private final int blockY = 0;
    private Batch batch;
    private MusicBlock[] groundBlocks;

    public GameScreen(final MusicRunner game) {
        this.game = game;
        this.batch = game.getBatch();
        playerBlock = new PlayerBlock(startX, startY);
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
