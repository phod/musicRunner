package io.github.phod.musicRunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;


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
        Random random = new Random();
        int groundNo = random.nextInt(3) + 1;
        String groundName = "ground" + groundNo + ".png";

        blockImage = new Texture(Gdx.files.internal(groundName));
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

    public void setXPos(int xPos, boolean reset) {
        this.xPos = xPos;
        blockCol.x = xPos;
        if (reset) {
            notePlayed = false;
        }
    }

    public void moveXPos(int xPos) {
        this.xPos -= xPos;
        blockCol.x = this.xPos;
    }

    public int getXPos() {
        return this.xPos;
    }
}
