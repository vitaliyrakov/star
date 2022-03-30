package com.star.app.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.star.app.screen.ScreenManager;
import com.star.app.screen.utils.Assets;

public class Hero {
    private StringBuilder sb;
    private GameController gc;
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private float angle;
    private float enginePower;
    private float fireTimer;
    private int score;
    private int scoreView;
    private int hp;
    private int hpMax;
    private Circle hitArea;
    private Weapon currentWeapon;
    private int money;

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public int getScore() {
        return score;
    }

    public int getHp() {
        return hp;
    }

    public int getScoreView() {
        return scoreView;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public float getAngle() {
        return angle;
    }

    public Hero(GameController gc) {
        this.gc = gc;
        this.texture = Assets.getInstance().getAtlas().findRegion("ship");
        this.position = new Vector2(640, 360);
        this.velocity = new Vector2(0, 0);
        this.angle = 0.0f;
        this.enginePower = 700.0f;
        this.hitArea = new Circle(0, 0, 0);
        this.hpMax = 100;
        this.hp = hpMax;
        this.sb = new StringBuilder();
        this.currentWeapon = new Weapon(gc, this,0.2f,1, 700, 100,
                new Vector3[]{
                        new Vector3(28, 0,0),
                        new Vector3(28, -90,-10),
                        new Vector3(28, 90,10),
                });
    }

    public Circle getHitArea() {
        return hitArea;
    }

    public void addScore(int amount) {
        score += amount;
    }

    public void delHP(int amount) {
        hp -= amount;
    }
    public void addMonew(int amount) {
        money += amount;
    }
    public void addHP(int amount) {
        hp += amount;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 32, position.y - 32, 32, 32,
                64, 64, 1, 1, angle);
    }

    public void update(float dt) {
        fireTimer += dt;
        if (scoreView < score) {
            scoreView += 1500 * dt;
            if (scoreView > score) {
                scoreView = score;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            tryToFire();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            angle += 180 * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            angle -= 180 * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.x += MathUtils.cosDeg(angle) * enginePower * dt;
            velocity.y += MathUtils.sinDeg(angle) * enginePower * dt;

            float bx = position.x + MathUtils.cosDeg(angle + 180) * 25;
            float by = position.y + MathUtils.sinDeg(angle + 180) * 25;

            for (int i = 0; i < 3; i++) {
                gc.getParticleController().setup(bx + MathUtils.random(-4, 4), by + MathUtils.random(-4, 4),
                        velocity.x * -0.1f + MathUtils.random(-20, 20), velocity.y * -0.1f + MathUtils.random(-20, 20),
                        0.4f,
                        1.2f, 0.2f,
                        1.0f, 0.5f, 0.0f, 1.0f,
                        1.0f, 1.0f, 1.0f, 0.0f);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.x += MathUtils.cosDeg(angle - 180) * enginePower / 2 * dt;
            velocity.y += MathUtils.sinDeg(angle - 180) * enginePower / 2 * dt;

            float bx = position.x + MathUtils.cosDeg(angle - 90) * 25;
            float by = position.y + MathUtils.sinDeg(angle - 90) * 25;

            for (int i = 0; i < 3; i++) {
                gc.getParticleController().setup(bx + MathUtils.random(-4, 4), by + MathUtils.random(-4, 4),
                        velocity.x * 0.1f + MathUtils.random(-20, 20), velocity.y * 0.1f + MathUtils.random(-20, 20),
                        0.2f,
                        1.2f, 0.2f,
                        1.0f, 0.5f, 0.0f, 1.0f,
                        1.0f, 1.0f, 1.0f, 0.0f);
            }

            bx = position.x + MathUtils.cosDeg(angle + 90) * 25;
            by = position.y + MathUtils.sinDeg(angle + 90) * 25;

            for (int i = 0; i < 3; i++) {
                gc.getParticleController().setup(bx + MathUtils.random(-4, 4), by + MathUtils.random(-4, 4),
                        velocity.x * 0.1f + MathUtils.random(-20, 20), velocity.y * 0.1f + MathUtils.random(-20, 20),
                        0.2f,
                        1.2f, 0.2f,
                        1.0f, 0.5f, 0.0f, 1.0f,
                        1.0f, 1.0f, 1.0f, 0.0f);
            }
        }

        position.mulAdd(velocity, dt);
        float stopKoef = 1.0f - dt;
        if (stopKoef < 0.0f) {
            stopKoef = 0.0f;
        }
        velocity.scl(stopKoef);

        checkBorders();
        hitArea.setPosition(position);
        hitArea.setRadius(20);
    }

    public void checkBorders() {
        if (position.x < 32) {
            position.x = 32f;
            velocity.x *= -0.5f;
        }
        if (position.x > ScreenManager.SCREEN_WIDTH - 32) {
            position.x = ScreenManager.SCREEN_WIDTH - 32f;
            velocity.x *= -0.5f;
        }
        if (position.y < 32) {
            position.y = 32f;
            velocity.y *= -0.5f;
        }
        if (position.y > ScreenManager.SCREEN_HEIGHT - 32) {
            position.y = ScreenManager.SCREEN_HEIGHT - 32f;
            velocity.y *= -0.5f;
        }
    }

    public void renderGUI(SpriteBatch batch, BitmapFont font) {
        sb.setLength(0);
        sb.append("SCORE: ").append(scoreView).append("\n");
        sb.append("HP: ").append(hp).append("/").append(hpMax).append("\n");
        sb.append("BULLETS: ").append(currentWeapon.getCurBullets()).append("/").append(currentWeapon.getMaxBullets()).append("\n");
        sb.append("$: ").append(money).append("\n");
        font.draw(batch, sb, 20, 700);
    }

    private void tryToFire() {
        if (fireTimer > 0.2) {
            fireTimer = 0.0f;
            currentWeapon.fire();
        }
    }

}
