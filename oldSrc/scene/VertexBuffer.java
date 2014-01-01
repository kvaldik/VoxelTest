package is.kvaldik.scene;

import java.util.Arrays;

public class VertexBuffer {
	private float[] vertexBufferArray;
	private int index;
	private int bufferSize;
	
	private short[] indices;
	private int index2;
	
	public VertexBuffer(int newSize) {
		this.vertexBufferArray = new float[newSize*newSize*newSize*180];
		this.indices = new short[newSize*newSize*newSize*36];
		this.index = 0;
		this.index2 = 0;
		this.bufferSize = newSize;
	}
	
	public void add2(short[] newValues) {
		for (int i = 0; i < newValues.length; i++) {
			this.indices[index2++] = newValues[i];
		}
	}
	
	public void add(float[] newValues) {
		for (int i = 0; i < newValues.length; i++) {
			this.vertexBufferArray[index++] = newValues[i];
		}
	}
	
	public void finalize() {
		float[] oldArray = this.vertexBufferArray;
		this.vertexBufferArray = new float[index];
		this.vertexBufferArray = Arrays.copyOfRange(oldArray, 0, index);
		oldArray = null;
	}
	
	public void clearAll() {
		this.vertexBufferArray = new float[this.bufferSize*this.bufferSize*this.bufferSize*180];
		index = 0;
		index2 = 0;
	}
	
	/*
	 * Get and set
	 */
	
	public float[] getVertexBufferArray() {
		return this.vertexBufferArray;
	}
	
	public short[] getBle() {
		return this.indices;
	}
	
	public short getFluff() {
		return (short)this.index2;
	}
	
	public int getVertexCount() {
		return this.index/5;
	}
}
