package com.star.app.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.star.app.game.helpers.ObjectPool;
import com.star.app.screen.ScreenManager;

public class AsteroidController extends ObjectPool<Asteroid> {
    private Texture asteroidTexture;

    @Override
    protected Asteroid newObject() {
        return new Asteroid();
    }

    public AsteroidController() {
        this.asteroidTexture = new Texture("asteroid.png");
        setup(ScreenManager.SCREEN_WIDTH,0,-100,100);
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < activeList.size(); i++) {
            Asteroid b = activeList.get(i);
            batch.draw(asteroidTexture, b.getPosition().x - 16, b.getPosition().y - 16);
        }
    }

    public void setup(float x, float y, float vx, float vy){
        getActiveElement().activate(x, y, vx, vy);
    }

    public void update(float dt) {
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
        }
        checkPool();
    }
}
