package entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;

public class Camera {
	
	private static final float MIN_ZOOM_LEVEL = 20;
	private static final float MAX_ZOOM_LEVEL = 300;
	private static final float HEIGHT_OFFSET = 12;
	private static final float NEUTRAL_PITCH = 15;
	
	private static final float MIN_PITCH = 0;
	private static final float MAX_PITCH = 75;
	
	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(100,35,50);
	private float pitch = NEUTRAL_PITCH;
	private float yaw;
	private float roll;
	private float previousPlayerRotY;
	
	private Player player;
	
	public Camera(Player player) {
		this.player = player;
		
	}
	
	public void move(){
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horDistance, float verDistance){
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verDistance + HEIGHT_OFFSET;
		
		this.yaw = (180 - theta);

		
	}
	
	private float calculateHorizontalDistance(){
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance(){
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	private void calculateZoom(){
		float zoomLevel = Mouse.getDWheel() * 0.1F;
		this.distanceFromPlayer -= zoomLevel;
		//clamp distance
		this.distanceFromPlayer = Math.max(MIN_ZOOM_LEVEL, Math.min(MAX_ZOOM_LEVEL, this.distanceFromPlayer));	
	}
	
	private void calculatePitch(){
		if(Mouse.isButtonDown(2)){
			float pitchChange = Mouse.getDY() * 0.1f;
			this.pitch -= pitchChange;
			this.pitch = Math.max(MIN_PITCH, Math.min(MAX_PITCH, pitch));
		}else if(Math.abs(pitch-NEUTRAL_PITCH) > 5 && player.getCurrentSpeed() > 0.5f){
			this.pitch -= Math.copySign(Math.abs(pitch-NEUTRAL_PITCH)* 1.2f * DisplayManager.getFrameTimeSeconds(), (pitch-NEUTRAL_PITCH));
		}
	}
	
	private void calculateAngleAroundPlayer(){
		if(previousPlayerRotY != player.getRotY()){
			this.angleAroundPlayer += previousPlayerRotY - player.getRotY();
		}
		
		
		if(Mouse.isButtonDown(2)){
			float angleChange = Mouse.getDX() * 0.3f;
			this.angleAroundPlayer -= angleChange;
			
		}else if(Math.abs(angleAroundPlayer) > 0.05f && player.getCurrentSpeed() > 0.5f){
			//return to normal a-a-p if player is moving, make sure to rotate shortest distance
			float angleAroundPlayerOpposite = (Math.abs(angleAroundPlayer) % 180) - 180;
			float AAPReturnSpeed = 2;
			float deltaAAP;
			if(Math.abs(angleAroundPlayer)>180){
				deltaAAP = Math.copySign(angleAroundPlayerOpposite * AAPReturnSpeed * DisplayManager.getFrameTimeSeconds(), angleAroundPlayerOpposite);
			}else{
				deltaAAP = Math.copySign(angleAroundPlayer * AAPReturnSpeed * DisplayManager.getFrameTimeSeconds(), angleAroundPlayer);
			}
			
			this.angleAroundPlayer -= deltaAAP;
		}
		this.angleAroundPlayer = this.angleAroundPlayer % 360;
		previousPlayerRotY = player.getRotY();
	}
	

}
