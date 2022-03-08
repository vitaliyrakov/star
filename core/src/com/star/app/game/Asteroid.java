package com.star.app.game;

import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.ScreenManager;

public class Asteroid implements Poolable {
    private GameController gc;
    private Vector2 position;
    private boolean active;
    private Vector2 velocity;

    public Asteroid() {
        this.position = new Vector2();
        this.velocity = new Vector2();
        this.active = false;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        if (position.x < -250) {
            position.x = ScreenManager.SCREEN_WIDTH + 400;
        }
        if (position.y > ScreenManager.SCREEN_HEIGHT + 400) {
            position.y = 0;
        }
    }

    public void activate(float x, float y, float vx, float vy) {
        position.set(x, y);
        velocity.set(vx, vy);
        active = true;
    }

}
