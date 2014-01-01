package is.kvaldik;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.FloatBuffer;

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
	// Parts of the game client
	//private Connecting connecting;		// Initial state of the game
	private Player player;				// Represents the current player
	//private World world;				// The world/map of the game
	
	// Rendering variables
	private FloatBuffer vertexBuffer;
	/*float [] vertexBuffer ={-0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.0f,
							-0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f,
							0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
							0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f,};
							
							/*0.5f, -0.5f, -0.5f, Color.toFloatBits(0, 255, 0, 255),
							0.5f, 0.5f, -0.5f, Color.toFloatBits(0, 255, 0, 255),
							0.5f, -0.5f, 0.5f, Color.toFloatBits(0, 255, 0, 255),
							0.5f, 0.5f, 0.5f, Color.toFloatBits(0, 255, 0, 255),
							
							0.5f, -0.5f, 0.5f, Color.toFloatBits(0, 0, 255, 255),
							0.5f, 0.5f, 0.5f, Color.toFloatBits(0, 0, 255, 255),
							-0.5f, -0.5f, 0.5f, Color.toFloatBits(0, 0, 255, 255),
							-0.5f, 0.5f, 0.5f, Color.toFloatBits(0, 0, 255, 255),
							
							-0.5f, -0.5f, 0.5f, Color.toFloatBits(255, 0, 0, 255),
							-0.5f, 0.5f, 0.5f, Color.toFloatBits(0, 255, 0, 255),
							-0.5f, -0.5f, -0.5f, Color.toFloatBits(0, 0, 255, 255),
							-0.5f, 0.5f, -0.5f, Color.toFloatBits(255, 0, 0, 255),
							
							-0.5f, 0.5f, -0.5f, Color.toFloatBits(255, 0, 0, 255),
							-0.5f, 0.5f, 0.5f, Color.toFloatBits(255, 0, 0, 255),
							0.5f, 0.5f, -0.5f, Color.toFloatBits(255, 0, 0, 255),
							0.5f, 0.5f, 0.5f, Color.toFloatBits(255, 0, 0, 255),
							
							-0.5f, -0.5f, -0.5f, Color.toFloatBits(255, 0, 0, 255),
							-0.5f, -0.5f, 0.5f, Color.toFloatBits(255, 0, 0, 255),
							0.5f, -0.5f, -0.5f, Color.toFloatBits(255, 0, 0, 255),
							0.5f, -0.5f, 0.5f, Color.toFloatBits(255, 0, 0, 255)};*/
	private Mesh mesh;
	private Texture texture;
	

	@Override
	public void create() {
		this.vertexBuffer = BufferUtils.newFloatBuffer(72);
		this.vertexBuffer.put(new float[]    {-0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.0f,
				-0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f,
				0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f,
				0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f,});
		this.vertexBuffer.rewind();
		float[] ble = this.vertexBuffer.array();
		
		//Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		//Gdx.gl11.glEnable(GL11.GL_LIGHT1);
		//Gdx.gl11.glEnable(GL11.GL_LIGHT0);
		Gdx.gl11.glEnable(GL11.GL_DEPTH_TEST);
		//Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
		
		Gdx.gl11.glClearColor(0.0f, 0.0f, 0.6f, 1.0f);
	
		//Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		//Gdx.gl11.glLoadIdentity();
		//Gdx.glu.gluPerspective(Gdx.gl11, 90, 1.333333f, 1.0f, 10.0f);
	
		//Gdx.gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	
		//vertexBuffer = new float[72];
		//Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		//this.world = new World(10);
		//this.tcpClient = new TcpClient("89.17.129.52", 5050, this.world, this.otherPlayers, this.hud);
		//this.connecting = new Connecting(this.tcpClient);
		this.player = new Player();
		//this.world = new World(10);
		int i = 0;/*
		this.vertexBuffer[i++] = -0.5f;
		this.vertexBuffer[i++] = -0.5f;
		this.vertexBuffer[i++] = -0.5f;
		this.vertexBuffer[i++] = 1.0f;
		this.vertexBuffer[i++] = 0.0f;
		this.vertexBuffer[i++] = 0.0f;
		this.vertexBuffer[i++] = 1.0f;
		
		this.vertexBuffer[i++] = -0.5f;
		this.vertexBuffer[i++] = 0.5f;
		this.vertexBuffer[i++] = -0.5f;
		this.vertexBuffer[i++] = 1.0f;
		this.vertexBuffer[i++] = 0.0f;
		this.vertexBuffer[i++] = 0.0f;
		this.vertexBuffer[i++] = 1.0f;
		
		this.vertexBuffer[i++] = 0.5f;
		this.vertexBuffer[i++] = -0.5f;
		this.vertexBuffer[i++] = -0.5f;
		this.vertexBuffer[i++] = 1.0f;
		this.vertexBuffer[i++] = 0.0f;
		this.vertexBuffer[i++] = 0.0f;
		this.vertexBuffer[i++] = 1.0f;
		
		this.vertexBuffer[i++] = 0.5f;
		this.vertexBuffer[i++] = 0.5f;
		this.vertexBuffer[i++] = -0.5f;
		this.vertexBuffer[i++] = 0.0f;
		this.vertexBuffer[i++] = 0.0f;// 26
		this.vertexBuffer[i++] = 0.0f;
		this.vertexBuffer[i++] = 1.0f;*/
		//this.vertexBuffer[26] = 1.0f;
		//this.vertexBuffer[26] = 0.0f;// 26
		
		// Voxel
		this.player.setInputProcessor();
		//this.world.loadMap(3);
		//System.out.printf("Number of blocks: %d \n", this.world.numberOfBlocks());
		
		mesh = new Mesh(true, 72, 0, 
				new VertexAttribute(Usage.Position, 3, "a_position"),
				new VertexAttribute(Usage.Color, 4, "a_color"),
                new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
		mesh.setVertices(ble);
		//this.vertexBuffer[25] = 1.0f; // Grænn
		//this.vertexBuffer[26] = 0.0f; // Blár
		//mesh.setVertices(this.vertexBuffer.array());
		
		
		FileHandle imageFileHandle = Gdx.files.internal("assets/textures/crate01.jpg"); 
        this.texture = new Texture(imageFileHandle);
		
		
	}
	
	// Update is a part of the second state of the game and represents all changes in the game
	private void update() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		// Update of the player returns true if the player moved, if so, the position is sent to the server
		this.player.update(deltaTime);
	}

	private void display() {
		// Set up the game
		Gdx.gl11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		this.player.updateCamera();
		
		// Configure light
		//Gdx.gl11.glLightfv(GL11.GL_LIGHT1, GL11.GL_AMBIENT, new float[] {2.0f,	2.0f, 2.0f, 5.0f}, 0);

		// Set diffuse material.
		//Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_DIFFUSE, new float[] {1.0f, 1.0f, 1.0f, 1.0f}, 0);
		//Gdx.gl11.glMaterialfv(GL11.GL_FRONT, GL11.GL_SPECULAR, new float[] {0.2f, .2f, .2f, 1.0f}, 0);
		//Gdx.gl11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 100);

		//Gdx.gl11.glEnable(GL11.GL_LIGHTING);
		//Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, this.vertexBuffer);

		//Gdx.gl11.glMatrixMode(GL11.GL_PROJECTION);
		//Gdx.gl11.glLoadIdentity();
		//Gdx.glu.gluPerspective(Gdx.gl11, 60.0f, (float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight(), 1.0f, 90.0f);
		
		//Gdx.gl11.glMatrixMode(GL11.GL_MODELVIEW);
		
		//this.drawBox();
	    Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
	    texture.bind();
		mesh.render(GL11.GL_TRIANGLE_STRIP);
		
		
		// Draw the world
		//this.world.drawWorld();
	}

	@Override
	public void render() {
		this.update();
		this.display();
	}
	
	private void drawBox() {
		Gdx.gl11.glNormal3f(0.0f, 0.0f, -1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		Gdx.gl11.glNormal3f(1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 4, 4);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, 1.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 8, 4);
		Gdx.gl11.glNormal3f(-1.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 12, 4);
		Gdx.gl11.glNormal3f(0.0f, 1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
		Gdx.gl11.glNormal3f(0.0f, -1.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 20, 4);
	}

	@Override public void resize(int width, int height) {}
	@Override public void pause() {}
	@Override public void resume() {}
	@Override public void dispose() {
		System.out.printf("Exiting the game! \n");
		//this.world.saveMap(3);
		//this.tcpClient.dispose();
	}
}