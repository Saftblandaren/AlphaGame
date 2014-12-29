package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity{
	
	//private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -100;
	//private static final float JUMP_POWER = 40;
	
	private float RUN_SPEED = 20;
	private float JUMP_POWER = 40;
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float currentUpwardSpeed = 0;
	
	private boolean isInAir = false;

	public Player(TexturedModel model, Vector3f position, float rotX,
			float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	
	
	public float getCurrentSpeed() {
		return currentSpeed;
	}



	public void move(Terrain terrain){
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		this.currentUpwardSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		float dy = currentUpwardSpeed * DisplayManager.getFrameTimeSeconds();
		super.increasePostion(dx, dy, dz);
		
		//check that player is not below terrain
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		if(super.getPosition().y < terrainHeight){
			super.getPosition().y = terrainHeight;
			this.currentUpwardSpeed = 0;
			this.isInAir = false;
		}
	}
	
	private void jump(){
		if(!isInAir){
			this.currentUpwardSpeed = JUMP_POWER;
			this.isInAir = true;
		}
		
	}
	
	private void checkInputs(){
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			RUN_SPEED = 100;
			JUMP_POWER = 100;
		}else{
			RUN_SPEED = 20;
			JUMP_POWER = 40;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
			this.currentSpeed = RUN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
		}else{
			this.currentSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
			this.currentTurnSpeed = -TURN_SPEED;
		}else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
			this.currentTurnSpeed = TURN_SPEED;
		}else{
			this.currentTurnSpeed = 0;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			jump();
		}
	}
}
