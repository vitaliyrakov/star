package com.star.app.game;

public class GameController {
    private Background background;
    private BulletController bulletController;
    private AsteroidController asteroidController;
    private Hero hero;

    public BulletController getBulletController() {
        return bulletController;
    }

    public AsteroidController getAsteroidController() {
        return asteroidController;
    }

    public Background getBackground() {
        return background;
    }

    public Hero getHero() {
        return hero;
    }

    public GameController() {
        this.background = new Background(this);
        this.bulletController = new BulletController();
        this.asteroidController = new AsteroidController();
        this.hero = new Hero(this);
    }

    public void update(float dt) {
        background.update(dt);
        bulletController.update(dt);
        asteroidController.update(dt);
        hero.update(dt);
        checkCollisions();
    }

    public void checkCollisions() {
        for (int i = 0; i < bulletController.getActiveList().size(); i++) {
            Bullet b = bulletController.getActiveList().get(i);

            if (hero.getPosition().dst(b.getPosition()) < 32.0f) {
                //b.deactivate();
            }
        }

    }
}
