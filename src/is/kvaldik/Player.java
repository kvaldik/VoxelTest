package is.kvaldik;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

/******************************************************************************
 * Player.java
 * 
 * This is the input processor of the game. It represents the current player
 * and the moves and actions he can take.
 *****************************************************************************/

public class Player implements InputProcessor {
	
	// Mouse movements
	private int mouseX;				// Position of the mouse
	private int mouseY;				// Position of the mouse
	private int mouseXm;			// Mouse movement
	private int mouseYm;			// Mouse movement
	private boolean mouseUpdate;	// Has the mouse moved
	private boolean mouseMovement;	// Is mouse movement enabled
	
	// A Camera that represents the player
	private PerspectiveCamera camera;
	
	
	public Player() {
		this.mouseX = Gdx.input.getX();
		this.mouseY = Gdx.input.getY();
		this.mouseMovement = true;
		Gdx.input.setCursorCatched(this.mouseMovement);
		
		this.camera = new PerspectiveCamera(60.0f, 1280/50, 720/50);
		Gdx.input.setInputProcessor(this);
		//this.camera.translate(6.0f + (float)(Math.random() * (mapLength - 6.0f)), mapLength, 6.0f + (float)(Math.random() * (mapLength - 6.0f)));
		//this.camera.lookAt(6.0f + (float)(Math.random() * (mapLength - 6.0f)), mapLength, 6.0f + (float)(Math.random() * (mapLength - 6.0f)));
		this.camera.translate(0,0,0);
		this.camera.lookAt(0,0,1);
	}
	
	public void update(float deltaTime) {
		Vector3 ble = new Vector3(this.camera.up.x, this.camera.up.y, this.camera.up.z);
		ble.crs(this.camera.direction);
		
		// Mouse movement
		if (mouseMovement) {
			int movedX = this.mouseX - this.mouseXm;
			int movedY = this.mouseY - this.mouseYm;
			if (this.mouseUpdate) {
				if(movedY < 0) 
					this.camera.rotate(-10*movedY*deltaTime, ble.x, ble.y, ble.z);
				if(movedY > 0) 
					this.camera.rotate(-10*movedY*deltaTime, ble.x, ble.y, ble.z);
				if(movedX > 0)
					this.camera.rotate(10*movedX*deltaTime, 0, 1, 0);
				if(movedX < 0)
					this.camera.rotate(10*movedX*deltaTime, 0, 1, 0);
				this.mouseUpdate = false;
			}
			
			Gdx.input.setCursorPosition(500, 500);
			this.mouseX = Gdx.input.getX();
			this.mouseY = Gdx.input.getY();
			this.mouseXm = 0;
			this.mouseYm = 0;
		}
		
		// Move the player
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			this.move(20*this.camera.direction.x*deltaTime, 20*this.camera.direction.z*deltaTime);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			this.move(-10*this.camera.direction.x*deltaTime, -20*this.camera.direction.z*deltaTime);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			this.strafe(10*ble.x*deltaTime, 20*ble.z*deltaTime);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			this.strafe(-10*ble.x*deltaTime, -20*ble.z*deltaTime);
		}
		
		// Rotate the camera manually
		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			this.camera.rotate(90*deltaTime, ble.x, ble.y, ble.z);
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			this.camera.rotate(-90*deltaTime, ble.x, ble.y, ble.z);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			this.camera.rotate(90*deltaTime, 0, 1, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			this.camera.rotate(-90*deltaTime, 0, 1, 0);
		
		
		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			this.moveY(20.0f*deltaTime);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
			this.moveY(-20.0f*deltaTime);
		}
	}
	
	private void move(float moveX, float moveZ) {
		moveX(moveX);
		moveZ(moveZ);
	}
	
	private void strafe(float strafeX, float strafeZ) {
		moveX(strafeX);
		moveZ(strafeZ);
	}
	
	private void moveX(float moveX) {
		this.camera.translate(moveX, 0, 0);
	}
	
	private void moveY(float moveY) {
		this.camera.translate(0, moveY, 0);
	}
	
	private void moveZ(float moveZ) {
		this.camera.translate(0, 0, moveZ);
	}
	
	public void updateCamera() {
		this.camera.update();
		this.camera.apply(Gdx.gl11);
	}
	
	@Override
	public boolean keyUp(int arg0) {
		// Exit the game
		if (Input.Keys.ESCAPE == arg0) 
			Gdx.app.exit();
		// Turn the mouse on and off
		if (Input.Keys.P == arg0) {
			this.mouseMovement = !this.mouseMovement;
			Gdx.input.setCursorCatched(this.mouseMovement);
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// Store this mouse movement and use it when calculating next frame
		this.mouseXm = arg0;
		this.mouseYm = arg1;
		this.mouseUpdate = true;
		return false;
	}

	@Override
	public boolean keyDown(int arg0) {return false;}

	@Override
	public boolean keyTyped(char arg0) {return false;}

	@Override
	public boolean scrolled(int arg0) {return false;}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {return false;}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {return false;}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {return false;}
	
	
	/*
	 * Get and set
	 */
	public float getPosX() {
		return camera.position.x;
	}
	
	public float getPosY() {
		return camera.position.y;
	}
	
	public float getPosZ() {
		return camera.position.z;
	}
	
	public float getDirX() {
		return camera.direction.x;
	}
	
	public float getDirY() {
		return camera.direction.y;
	}
	
	public float getDirZ() {
		return camera.direction.z;
	}
}
