package objConverter;

public class ModelData {

	private float[] vertices;
	private float[] textureCoords;
	private float[] normals;
	private int[] indices;
	private float furthestPoint;
	private boolean useFakeLighting = false;

	public ModelData(float[] vertices, float[] textureCoords, float[] normals, int[] indices,
			float furthestPoint) {
		this.vertices = vertices;
		this.textureCoords = textureCoords;
		this.normals = normals;
		this.indices = indices;
		this.furthestPoint = furthestPoint;
	}
	
	public void setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
	}


	public float[] getVertices() {
		return vertices;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public float[] getNormals() {
		if(useFakeLighting){
			//create fake normals
			float[] fakeNormals = new float[normals.length];
			for(int i = 0;i < normals.length;i++){
				if(i%3 == 1){
					fakeNormals[i] = 1.0f;
					
				}else{
					fakeNormals[i] = 0.0f;
				}
			}
			return fakeNormals;
		}else{
			return normals;
		}
	}

	public int[] getIndices() {
		return indices;
	}

	public float getFurthestPoint() {
		return furthestPoint;
	}

}
