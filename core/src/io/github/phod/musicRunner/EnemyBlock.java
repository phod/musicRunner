package io.github.phod.musicRunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by phil on 1/02/15.
 */
public class EnemyBlock {

    private int xPos;
    private int yPos;
    private Texture enemyImage;
    private Rectangle enemyCol;
    private final int MAX_HEIGHT;
    private final int MIN_HEIGHT;
    private int direction = 1;
    private final int moveSpeed;

    public EnemyBlock(int xPos, int yPos, int moveSpeed, int maxY, int minY) {
        this.xPos = xPos;
        this.yPos = yPos;
        enemyImage = new Texture(Gdx.files.internal("enemy1.png"));
        enemyCol = new Rectangle();
        enemyCol.x = this.xPos;
        enemyCol.y = this.yPos;
        enemyCol.width = 64;
        enemyCol.height = 64;
        MAX_HEIGHT = maxY;
        MIN_HEIGHT = minY;
        this.moveSpeed = moveSpeed;
    }

    public void draw(Batch batch) {
        batch.draw(enemyImage, xPos, yPos);
    }

    public Rectangle getEnemyCol() {
        return enemyCol;
    }

    public void changeHeight(float deltaTime) {
        this.yPos = Math.round(yPos + moveSpeed * deltaTime * direction);
        if (this.yPos > MAX_HEIGHT) {
            yPos = MAX_HEIGHT;
            direction *= -1;
        } else if (this.yPos < MIN_HEIGHT) {
            yPos = MIN_HEIGHT;
            direction *= -1;
        }
    }

    public void moveXPos(int xPos) {
        this.xPos -= xPos;
        enemyCol.x = this.xPos;
    }

    public int getXPos() {
        return xPos;
    }

}
