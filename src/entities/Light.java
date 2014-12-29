package entities;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	
	private Vector3f position;
	private Vector3f colour;
	private float ambientLight;
	
	public Light(Vector3f position, Vector3f colour, float ambientLight) {
		this.position = position;
		this.colour = colour;
		this.ambientLight = ambientLight;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColour() {
		return colour;
	}

	public void setColour(Vector3f colour) {
		this.colour = colour;
	}

	public float getAmbientLight() {
		return ambientLight;
	}
	
	public void setAmbientLight(float ambientLight){
		this.ambientLight = ambientLight;
	}
	
	
	
}
