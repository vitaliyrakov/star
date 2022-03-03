package com.star.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Asteroid {
    private Texture texture;
    private Vector2 position;
    private Vector2 lastDisplacement;
    private float angle;

    public Asteroid() {
        this.texture = new Texture("asteroid.png");
        this.position = new Vector2(640, 360);
        this.lastDisplacement = new Vector2(0, 0);
        this.angle = 0.0f;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32,
                256, 256, 1, 1, angle, 0, 0, 256, 256,
                false, false);
    }

    public void update(float dt) {
        position.x += MathUtils.cosDeg(angle + 180) * 200f * dt;
        position.y += MathUtils.sinDeg(angle + 180) * 200f * dt;
        lastDisplacement.set(MathUtils.cosDeg(angle + 180) * 200f * dt, MathUtils.sinDeg(angle + 180) * 200f * dt);
        checkBorders(dt);
    }

    public void checkBorders(float dt) {
        if (position.x < -250) {
            position.x = ScreenManager.SCREEN_WIDTH + 400;
            angle -= 180 * dt;
        }
        if (position.y > ScreenManager.SCREEN_HEIGHT + 250) {
            position.y = 0;
        }
    }
}
