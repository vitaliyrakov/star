package com.star.app.game;

import com.badlogic.gdx.math.MathUtils;
import com.star.app.screen.ScreenManager;

public class GameController {
    private Background background;
    private BulletController bulletController;
    private AsteroidController asteroidController;
    private BonusController bonusController;
    private ParticleController particleController;
    private Hero hero;

    public ParticleController getParticleController() {
        return particleController;
    }

    public BulletController getBulletController() {
        return bulletController;
    }

    public AsteroidController getAsteroidController() {
        return asteroidController;
    }

    public BonusController getBonusController() {
        return bonusController;
    }

    public Background getBackground() {
        return background;
    }

    public Hero getHero() {
        return hero;
    }

    public GameController() {
        this.background = new Background(this);
        this.bulletController = new BulletController(this);
        this.asteroidController = new AsteroidController(this);
        this.bonusController = new BonusController(this);
        this.hero = new Hero(this);
        this.particleController = new ParticleController();

        for (int i = 0; i < 3; i++) {
            asteroidController.setup(MathUtils.random(0, ScreenManager.SCREEN_WIDTH),
                    MathUtils.random(0, ScreenManager.SCREEN_HEIGHT),
                    MathUtils.random(-150, 150), MathUtils.random(-150, 150), 1.0f);
        }
    }

    public void update(float dt) {
        background.update(dt);
        bulletController.update(dt);
        asteroidController.update(dt);
        bonusController.update(dt);
        particleController.update(dt);
        hero.update(dt);
        checkCollisions();
    }

    public void checkCollisions() {
        for (int i = 0; i < bulletController.getActiveList().size(); i++) {
            Bullet b = bulletController.getActiveList().get(i);
            for (int j = 0; j < asteroidController.getActiveList().size(); j++) {
                Asteroid a = asteroidController.getActiveList().get(j);
                if (a.getHitArea().contains(b.getPosition())) {
                    particleController.setup(b.getPosition().x + MathUtils.random(-4, 4), b.getPosition().y + MathUtils.random(-4, 4),
                            b.getVelocity().x * -0.3f + MathUtils.random(-30, 30), b.getVelocity().y * -0.3f + MathUtils.random(-30, 30),
                            0.2f,
                            2.5f, 1.2f,
                            1.0f, 1.0f, 1.0f, 1.0f,
                            0.0f, 0.1f, 1.0f, 0.0f);

                    b.deactivate();
                    if (a.takeDamage(1)) {
                        hero.addScore(a.getHpMax() * 100);
                    }
                    break;
                }
            }
        }

        for (int j = 0; j < asteroidController.getActiveList().size(); j++) {
            Asteroid a = asteroidController.getActiveList().get(j);
            if (a.getHitArea().overlaps(hero.getHitArea())) {
                hero.addScore(a.getHpMax() * 100);
                hero.delHP(a.getHpMax());
                a.deactivate();
                break;
            }
        }

        for (int j = 0; j < bonusController.getActiveList().size(); j++) {
            Bonus b = bonusController.getActiveList().get(j);
            if (b.getHitArea().overlaps(hero.getHitArea())) {
                if (b.getType().equals(Bonus.BonusType.MEDICINE))
                    hero.addHP(10);
                if (b.getType().equals(Bonus.BonusType.AMMUNITION))
                    hero.getCurrentWeapon().setCurBullets(hero.getCurrentWeapon().getCurBullets()+100);
                if (b.getType().equals(Bonus.BonusType.MONEY))
                    hero.addMonew(100);

                b.deactivate();
                break;
            }
        }

    }
}
