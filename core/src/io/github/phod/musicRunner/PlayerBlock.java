package io.github.phod.musicRunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by phil on 1/02/15.
 */
public class PlayerBlock {

    private int xPos;
    private int yPos;
    private Texture playerImage;
    private Rectangle playerCol;

    public PlayerBlock(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        playerImage = new Texture(Gdx.files.internal("player.png"));
        playerCol = new Rectangle();
        playerCol.x = this.xPos;
        playerCol.y = this.yPos;
        playerCol.width = 64;
        playerCol.height = 64;
    }

    public void draw(Batch batch) {
        batch.draw(playerImage, xPos, yPos);
    }

}
