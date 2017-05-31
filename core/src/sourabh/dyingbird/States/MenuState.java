package sourabh.dyingbird.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import sourabh.dyingbird.AdHandler;
import sourabh.dyingbird.DyingBirdGame;

/**
 * Created by Brent on 6/25/2015.
 */
public class MenuState extends State{
    Texture background;
    Texture playBtn;
    AdHandler adHandler;


    public MenuState(GameStateManager gsm, AdHandler adHandler) {
        super(gsm);
        this.adHandler = adHandler;

        cam.setToOrtho(false, DyingBirdGame.WIDTH/2,DyingBirdGame.HEIGHT/2);

        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm,adHandler));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playBtn,cam.position.x - playBtn.getWidth()/2,cam.position.y);
        sb.end();

    }
}
