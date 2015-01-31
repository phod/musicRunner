package io.github.phod.musicRunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by phil on 1/02/15.
 */
public class MusicBlock {

    private int xPos;
    private int yPos;
    private Texture blockImage;
    private Rectangle blockCol;

    /**
     * TODO: Abstract Rectangle and Texture out of class to manager class.
     *
     *
     */
    public MusicBlock(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        blockImage = new Texture(Gdx.files.internal("ground1.png"));
        blockCol = new Rectangle();
        blockCol.x = this.xPos;
        blockCol.y = this.yPos;
        blockCol.width = 64;
        blockCol.height = 64;
    }

    public void draw(Batch batch) {
        batch.draw(blockImage, xPos, yPos);
    }

}
