package engineTester;

import java.util.List;

import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import entities.Camera;
import entities.Entity;
import entities.EntityCreator;
import entities.Light;
import entities.Player;

public class MainGameLoop {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
			
		

		
		Light light = new Light(new Vector3f(300,2000,200), new Vector3f(1,1,1), 0.4f);
		
		TerrainTexture backTexture = new TerrainTexture(loader.loadTexture("grass"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		TerrainTexturePack texturePack = new TerrainTexturePack(backTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		
		Terrain terrain = new Terrain(0,-1,loader,texturePack,blendMap, "heightmap");
		//Terrain terrain2 = new Terrain(-1,-1,loader,texturePack,blendMap, "heightmap");

		//Entity list
		EntityCreator creator = new EntityCreator(loader, terrain);
				
		List<Entity> allEntities = creator.getAllEntities();
		
		
		MasterRenderer renderer = new MasterRenderer();
		
		ModelData playerData = OBJFileLoader.loadOBJ("person");
		RawModel playerModel = loader.loadToVAO(playerData.getVertices(), playerData.getTextureCoords(), playerData.getNormals(), playerData.getIndices());
		ModelTexture playerTexture = new ModelTexture(loader.loadTexture("playerTexture"));
		TexturedModel texuredPlayerModel = new TexturedModel(playerModel,playerTexture);
		
		Player player = new Player(texuredPlayerModel, new Vector3f(100, 0, -50),0, 0, 0, 1);
		
		Camera camera = new Camera(player);
		
		while(!Display.isCloseRequested()){
			//entity.increasePostion(0, 0, -0.05f);
			//entity.increaseRotation(0, 1, 0);
			
			camera.move();
			player.move(terrain);
			renderer.processEntity(player);
			
			renderer.processTerrain(terrain);
			//renderer.processTerrain(terrain2);
			

			for(Entity entity : allEntities){
			renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			
		}
		
		renderer.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();

	}

}
