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
    private Batch batch;

    public GameScreen(final MusicRunner game) {
        this.game = game;
        this.batch = game.getBatch();
        playerBlock = new PlayerBlock(startX, startY);
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
