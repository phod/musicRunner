package io.github.phod.musicRunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
    private Sound blockSound;
    private boolean notePlayed;

    /**
     * TODO: Abstract Texture out of class to a manager class.
     *
     *
     */
    public MusicBlock(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        blockImage = new Texture(Gdx.files.internal("ground1.png"));
        blockCol = new Rectangle();
        blockCol.x = this.xPos;
        blockCol.y = this.yPos+64; //collider is ABOVE the ground to
                                   //trigger music.
        blockCol.width = 64;
        blockCol.height = 64;
        blockSound = Gdx.audio.newSound(Gdx.files.internal("placeHolder.wav"));
        notePlayed = false;
    }

    public void draw(Batch batch) {
        batch.draw(blockImage, xPos, yPos);
    }

    public void playNote() {
        if (!notePlayed) { //Only play once, prevents multiple play calls.
            blockSound.play();
            notePlayed = true;
        }
    }

    public Rectangle getBlockCol() {
        return blockCol;
    }

}
