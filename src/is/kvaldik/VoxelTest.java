package is.kvaldik;

import is.kvaldik.scene.World;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;

/******************************************************************************
 * GameClient.java
 * 
 * This is the main class that contains all the parts (classes) of the game.
 * It starts of running the connecting part and then switches to the main game
 * part. A short review of all the parts:
 * 
 * Connecting		Is the stage where the game asks for a nick anme and
 * 					connects to the server
 * Game				Is the second/final stage of the game
 * Player			Represents the player, his movements and his actions
 * World			Is the map the player is in
 * OtherPlayers		Represents all the other players in the game, their
 * 					nickname, position and so fourth. It also contains this
 * 					players ammo and health
 * HUD				Is the Heads Up Display and draws information about the
 * 					game status, also draws the score board
 * TcpClient		Is a separate thread that communicates with the server
 * 
 * There is only one instance of each of these classes in the game.
 *****************************************************************************/


public class VoxelTest implements ApplicationListener {
	private Player player;
	private World world;
	

	@Override
	public void create() {
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.6f, 1.0f);
		
		this.player = new Player();
		this.world = new World(8,16, 5.0f);
	}
	
	private void update() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		this.player.update(deltaTime);
		
	}

	private void display() {
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		this.player.updateCamera();
		
		Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		Gdx.gl11.glLoadIdentity();
		Gdx.glu.gluPerspective(Gdx.gl11, 60.0f, (float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight(), 1.0f, 1100.0f);
		Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		
		this.world.drawWorld();
	}

	@Override
	public void render() {
		this.update();
		this.display();
	}

	@Override public void resize(int width, int height) {}
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void dispose() {
		System.out.printf("Exiting the game! \n");
	}
}