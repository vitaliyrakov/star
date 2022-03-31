package com.star.app.screen;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.star.app.game.Background;
import com.star.app.screen.utils.Assets;

public class GameOverScreen extends AbstractScreen {
    private BitmapFont font72;
    private Background background;

    public GameOverScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    public void show() {
        this.background = new Background(null);
        this.font72 = Assets.getInstance().getAssetManager().get("fonts/font72.ttf");
    }

    public void update(float dt) {
        background.update(dt);
    }

    @Override
    public void render(float delta) {
        update(delta);
        ScreenUtils.clear(0.0f, 0.0f, 0.2f, 1);
        batch.begin();
        background.render(batch);
        font72.draw(batch, "Game over", 0, 600, ScreenManager.SCREEN_WIDTH, Align.center, false);
        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
