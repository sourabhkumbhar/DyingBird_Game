package sourabh.dyingbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import sourabh.dyingbird.States.GameStateManager;
import sourabh.dyingbird.States.MenuState;

public class DyingBirdGame extends ApplicationAdapter {
	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;
	public static final float SCALE = 0.5f;
	public static final String TITLE = "FlappyBird";
	public static int GAMEOVER_COUNT = 0;

	private SpriteBatch spriteBatch;
	private GameStateManager gameStateManager;
	AdHandler handler;


	public DyingBirdGame(AdHandler adHandler) {

		this.handler = adHandler;
	}


	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		gameStateManager = new GameStateManager();
		gameStateManager.push(new MenuState(gameStateManager,handler));

		Gdx.gl.glClearColor(1, 0, 0, 1);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(spriteBatch);
	}

}
