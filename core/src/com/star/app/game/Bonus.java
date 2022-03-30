package com.star.app.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.ScreenManager;

public class Bonus implements Poolable {
    private BonusType type;
    private boolean active;
    private GameController gc;
    private Vector2 position;
    private Vector2 velocity;
    private Circle hitArea;
    private Texture texture;

    private final float BASE_SIZE = 100.0f;
    private final float BASE_RADIUS = BASE_SIZE / 2;

    public enum BonusType {
        MONEY,
        MEDICINE,
        AMMUNITION
    }

    public Bonus(GameController gc) {
        this.gc = gc;
        this.active = false;
        this.position = new Vector2();
        this.velocity = new Vector2();
        this.hitArea = new Circle(0, 0, 0);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public BonusType getType() {
        return type;
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);

        if ((position.x < -200) || (position.y < -200)
                || (position.x > ScreenManager.SCREEN_WIDTH + 200)
                || (position.y > ScreenManager.SCREEN_HEIGHT + 200)) {
            this.active = false;
        }
        hitArea.setPosition(position);
    }

    public void activate(float x, float y, float vx, float vy, float scale) {
        position.set(x, y);
        velocity.set(vx, vy);
        active = true;
        hitArea.setPosition(x, y);
        hitArea.setRadius(BASE_RADIUS * scale * 0.9f);
        int rnd = MathUtils.random(1, 3);
        if (rnd==1){
            type = BonusType.AMMUNITION;
            texture = new Texture("ammunition.png");
        }
        if (rnd==2){
            type = BonusType.MEDICINE;
            texture = new Texture("medicine.png");
        }
        if (rnd==3){
            type = BonusType.MONEY;
            texture = new Texture("money.png");
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32,
                64, 64, 1, 1, 0, 0, 0, 64, 64,
                false, false);
    }

    public Circle getHitArea() {
        return hitArea;
    }

    public void deactivate() {
        active = false;
    }

}
