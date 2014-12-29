package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.util.vector.Vector3f;

import renderEngine.Loader;
import terrains.Terrain;
import textures.ModelTexture;

public class EntityCreator {
	
	private Loader loader;
	
	private List<Entity> allEntities = new ArrayList<Entity>();
	private Random random = new Random();
	private Terrain terrain;
	

	public EntityCreator(Loader loader, Terrain terrain) {
		this.loader = loader;
		this.terrain = terrain;
		createRandomEntities("grassModel", "grassTexture", 100, 1, 100, 0.1f, true, true);
		createRandomEntities("fern", "fern", 60, 1, 100, 0.1f, true, true);
		createRandomEntities("dragon", "blank", 15, 1, 5, 1, false, false);
		createRandomEntities("bunny", "white", 10, 1, 10, 0.3f, false, false);
		createRandomEntities("tree", "tree", 100, 10, 1000, 0.3f, false, false);

	}
	
	
	
	public List<Entity> getAllEntities() {
		return allEntities;
	}
	
	private void createRandomEntities(String model_name, String texture_name, int numbers, float scale, float shineDamper, float reflectivity, boolean tranparent, boolean fakeLighting){
		
		ModelData data = OBJFileLoader.loadOBJ(model_name);
		data.setUseFakeLighting(fakeLighting);
		RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
		ModelTexture texture = new ModelTexture(loader.loadTexture(texture_name));
		TexturedModel texuredModel = new TexturedModel(model,texture);
		
		texture.setShineDamper(shineDamper);
		texture.setReflectivity(reflectivity);
		texture.setTransparent(tranparent);
		//texture.setUseFakeLighting(fakeLighting);
		
		//float y = 0;
		for (int i = 0; i < numbers; i++){
			float x = random.nextFloat()*1500 - 750;
			//float y = random.nextFloat()*100 - 50;
			float z = random.nextFloat()*(-700)-50;
			float y = terrain.getHeightOfTerrain(x, z);
			float ry = random.nextFloat()*360;
			allEntities.add(new Entity(texuredModel, new Vector3f(x,y,z),0,ry,0,scale));
		}
	}

	
}
