package is.kvaldik.scene;

import java.util.Arrays;

public class VertexBuffer {
	private float[] vertices;
	private short[] indices;
	private int verticesIndex;
	private int indicesIndex;
	private int verticesSize;
	private int indicesSize;
	
	
	public VertexBuffer(int newSize) {
		this.verticesSize = newSize*newSize*newSize*180;
		this.indicesSize = newSize*newSize*newSize*36;
		this.vertices = new float[this.verticesSize];
		this.indices = new short[this.indicesSize];
		this.verticesIndex = 0;
		this.verticesIndex = 0;
	}
	
	public void addVertices(float[] newValues) {
		for (int i = 0; i < newValues.length; i++) {
			this.vertices[this.verticesIndex++] = newValues[i];
		}
	}
	
	public void addIndices(short[] newValues) {
		for (int i = 0; i < newValues.length; i++) {
			this.indices[this.indicesIndex++] = newValues[i];
		}
	}
	
	public void finalize() {
		float[] oldVertices = this.vertices;
		this.vertices = new float[this.verticesIndex];
		this.vertices = Arrays.copyOfRange(oldVertices, 0, verticesIndex);
		oldVertices = null;
		
		short[] oldIndices = this.indices;
		this.indices = new short[indicesIndex];
		this.indices = Arrays.copyOfRange(oldIndices, 0, indicesIndex);
		oldIndices = null;
	}
	
	public void clearAll() {
		this.vertices = new float[this.verticesSize];
		this.indices = new short[this.indicesSize];
		this.verticesIndex = 0;
		this.indicesIndex = 0;
	}
	
	/*
	 * Get and set
	 */
	
	public float[] getVertices() {
		return this.vertices;
	}
	
	public short[] getIndices() {
		return this.indices;
	}
	
	public int getIndicesCount() {
		return this.indicesIndex;
	}
	
	public int getVerticesCount() {
		return this.verticesIndex/5;
	}
}
