package com.star.app.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.star.app.game.Background;
import com.star.app.game.Hero;
import com.star.app.screen.utils.Assets;


public class GameOverScreen extends AbstractScreen {
    private BitmapFont font72;
    private BitmapFont font48;
    private BitmapFont font24;
    private Background background;
    private Hero defeatedHero;
    private StringBuilder sb;
    private Sound gameOver;

    public void setDefeatedHero(Hero defeatedHero) {
        this.defeatedHero = defeatedHero;
    }

    public GameOverScreen(SpriteBatch batch) {
        super(batch);
    }

    @Override
    public void show() {
        this.background = new Background(null);
        this.font72 = Assets.getInstance().getAssetManager().get("fonts/font72.ttf");
        this.font48 = Assets.getInstance().getAssetManager().get("fonts/font48.ttf");
        this.font24 = Assets.getInstance().getAssetManager().get("fonts/font24.ttf");
        this.sb = new StringBuilder();
        this.gameOver = Assets.getInstance().getAssetManager().get("audio/gameover.mp3");
    }

    public void update(float dt) {
        background.update(dt);
        if (Gdx.input.justTouched()) {
            ScreenManager.getInstance().changeScreen(ScreenManager.ScreenType.MENU);
        }
        gameOver.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        ScreenUtils.clear(0.0f, 0.0f, 0.2f, 1);
        batch.begin();
        background.render(batch);
        font72.draw(batch, "Game over", 0, 600, ScreenManager.SCREEN_WIDTH, Align.center, false);
        sb.setLength(0);
        sb.append("SCORE: ").append(defeatedHero.getScore()).append("\n");
        sb.append("MONEY: ").append(defeatedHero.getMoney()).append("\n");
        font48.draw(batch, sb, 0, 400,ScreenManager.SCREEN_WIDTH, Align.center, false);
        font24.draw(batch, "Tap screen to return to main menu", 0, 40,
                ScreenManager.SCREEN_WIDTH, Align.center, false);
        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
