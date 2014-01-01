package is.kvaldik.scene;


public class Chunk {
	private int chunkSize;
	private int verticesCount;
	private Voxel[][][] voxels;
	
	
	public Chunk(int newSize) {
		this.chunkSize = newSize;
		this.voxels = new Voxel[this.chunkSize][this.chunkSize][this.chunkSize];
		
		// Create the voxels
		for (int x = 0; x < this.chunkSize; x++) {
			for (int y = 0; y < this.chunkSize; y++) {
				for (int z = 0; z < this.chunkSize; z++) {
					this.voxels[x][y][z] = new Voxel();
					if (Math.sqrt((float) (x-this.chunkSize/2)*(x-this.chunkSize/2) + (y-this.chunkSize/2)*(y-this.chunkSize/2) + (z-this.chunkSize/2)*(z-this.chunkSize/2)) <= this.chunkSize/2)
					{
						//this.voxels[x][y][z].setType(1);
					}
				}
			}
		}
	}
	
	public void buildVertexBuffer(VertexBuffer vertexBuffer, World world, int chunkX, int chunkY, int chunkZ) {
		vertexBuffer.clearAll();
		for (int x = 0; x < this.chunkSize; x++) {
			for (int y = 0; y < this.chunkSize; y++) {
				for (int z = 0; z < this.chunkSize; z++) {
					if (this.voxels[x][y][z].isActive())
						this.addVoxel(vertexBuffer, world, chunkX, chunkY, chunkZ, x, y, z);
				}
			}
		}
		vertexBuffer.finalize();
		this.verticesCount = vertexBuffer.getVerticesCount();
	}
	
	public boolean checkVoxel(int x, int y, int z) {
		return this.voxels[x][y][z].isActive();
	}
	
	public boolean isEmpty() {
		return this.verticesCount == 0;
	}

	public void addVoxel(VertexBuffer vertexBuffer, World world, int chunkX, int chunkY, int chunkZ, int x, int y, int z) {
		float worldScale = world.getWorldScale();
		int verticesIndex;
		// North
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x, y, z-1))
		{
			verticesIndex = (short) vertexBuffer.getVerticesCount();
			vertexBuffer.addIndices(new short[] {(short) verticesIndex, (short) (verticesIndex+1), (short) (verticesIndex+2), (short) (verticesIndex+1), (short) (verticesIndex+2), (short) (verticesIndex+3)});
			vertexBuffer.addVertices(new float[] {
					(-0.5f+this.chunkSize*chunkX+x)*worldScale, (-0.5f+this.chunkSize*chunkY+y)*worldScale, (-0.5f+this.chunkSize*chunkZ+z)*worldScale, 0.0f, 0.0f,
					(-0.5f+this.chunkSize*chunkX+x)*worldScale, (0.5f+this.chunkSize*chunkY+y)*worldScale, (-0.5f+this.chunkSize*chunkZ+z)*worldScale, 0.0f, 1.0f,
					(0.5f+this.chunkSize*chunkX+x)*worldScale, (-0.5f+this.chunkSize*chunkY+y)*worldScale, (-0.5f+this.chunkSize*chunkZ+z)*worldScale, 1.0f, 0.0f,
					(0.5f+this.chunkSize*chunkX+x)*worldScale, (0.5f+this.chunkSize*chunkY+y)*worldScale, (-0.5f+this.chunkSize*chunkZ+z)*worldScale, 1.0f, 1.0f,
			});
		}
		// South
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x, y, z+1))
		{
			verticesIndex = (short) vertexBuffer.getVerticesCount();
			vertexBuffer.addIndices(new short[] {(short) verticesIndex, (short) (verticesIndex+1), (short) (verticesIndex+2), (short) (verticesIndex+1), (short) (verticesIndex+2), (short) (verticesIndex+3)});
			vertexBuffer.addVertices(new float[] {
					(0.5f+this.chunkSize*chunkX+x)*worldScale, (-0.5f+this.chunkSize*chunkY+y)*worldScale, (0.5f+this.chunkSize*chunkZ+z)*worldScale, 0.0f, 0.0f,
					(0.5f+this.chunkSize*chunkX+x)*worldScale, (0.5f+this.chunkSize*chunkY+y)*worldScale, (0.5f+this.chunkSize*chunkZ+z)*worldScale, 0.0f, 1.0f,
					(-0.5f+this.chunkSize*chunkX+x)*worldScale, (-0.5f+this.chunkSize*chunkY+y)*worldScale, (0.5f+this.chunkSize*chunkZ+z)*worldScale, 1.0f, 0.0f,
					(-0.5f+this.chunkSize*chunkX+x)*worldScale, (0.5f+this.chunkSize*chunkY+y)*worldScale, (0.5f+this.chunkSize*chunkZ+z)*worldScale, 1.0f, 1.0f,
			});
		}
		// Right
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x+1, y, z))
		{
			verticesIndex = (short) vertexBuffer.getVerticesCount();
			vertexBuffer.addIndices(new short[] {(short) verticesIndex, (short) (verticesIndex+1), (short) (verticesIndex+2), (short) (verticesIndex+1), (short) (verticesIndex+2), (short) (verticesIndex+3)});
			vertexBuffer.addVertices(new float[] {
					(0.5f+this.chunkSize*chunkX+x)*worldScale, (-0.5f+this.chunkSize*chunkY+y)*worldScale, (-0.5f+this.chunkSize*chunkZ+z)*worldScale, 0.0f, 0.0f,
					(0.5f+this.chunkSize*chunkX+x)*worldScale, (0.5f+this.chunkSize*chunkY+y)*worldScale, (-0.5f+this.chunkSize*chunkZ+z)*worldScale, 0.0f, 1.0f,
					(0.5f+this.chunkSize*chunkX+x)*worldScale, (-0.5f+this.chunkSize*chunkY+y)*worldScale, (0.5f+this.chunkSize*chunkZ+z)*worldScale, 1.0f, 0.0f,
					(0.5f+this.chunkSize*chunkX+x)*worldScale, (0.5f+this.chunkSize*chunkY+y)*worldScale, (0.5f+this.chunkSize*chunkZ+z)*worldScale, 1.0f, 1.0f,
			});
		}
		// West
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x-1, y, z))
		{
			verticesIndex = (short) vertexBuffer.getVerticesCount();
			vertexBuffer.addIndices(new short[] {(short) verticesIndex, (short) (verticesIndex+1), (short) (verticesIndex+2), (short) (verticesIndex+1), (short) (verticesIndex+2), (short) (verticesIndex+3)});
			vertexBuffer.addVertices(new float[] {
					(-0.5f+this.chunkSize*chunkX+x)*worldScale, (-0.5f+this.chunkSize*chunkY+y)*worldScale, (0.5f+this.chunkSize*chunkZ+z)*worldScale, 0.0f, 0.0f,
					(-0.5f+this.chunkSize*chunkX+x)*worldScale, (0.5f+this.chunkSize*chunkY+y)*worldScale, (0.5f+this.chunkSize*chunkZ+z)*worldScale, 0.0f, 1.0f,
					(-0.5f+this.chunkSize*chunkX+x)*worldScale, (-0.5f+this.chunkSize*chunkY+y)*worldScale, (-0.5f+this.chunkSize*chunkZ+z)*worldScale, 1.0f, 0.0f,
					(-0.5f+this.chunkSize*chunkX+x)*worldScale, (0.5f+this.chunkSize*chunkY+y)*worldScale, (-0.5f+this.chunkSize*chunkZ+z)*worldScale, 1.0f, 1.0f,
			});
		}
		// Top
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x, y+1, z))
		{
			verticesIndex = (short) vertexBuffer.getVerticesCount();
			vertexBuffer.addIndices(new short[] {(short) verticesIndex, (short) (verticesIndex+1), (short) (verticesIndex+2), (short) (verticesIndex+1), (short) (verticesIndex+2), (short) (verticesIndex+3)});
			vertexBuffer.addVertices(new float[] {
					(-0.5f+this.chunkSize*chunkX+x)*worldScale, (0.5f+this.chunkSize*chunkY+y)*worldScale, (-0.5f+this.chunkSize*chunkZ+z)*worldScale, 0.0f, 0.0f,
					(-0.5f+this.chunkSize*chunkX+x)*worldScale, (0.5f+this.chunkSize*chunkY+y)*worldScale, (0.5f+this.chunkSize*chunkZ+z)*worldScale, 0.0f, 1.0f,
					(0.5f+this.chunkSize*chunkX+x)*worldScale, (0.5f+this.chunkSize*chunkY+y)*worldScale, (-0.5f+this.chunkSize*chunkZ+z)*worldScale, 1.0f, 0.0f,
					(0.5f+this.chunkSize*chunkX+x)*worldScale, (0.5f+this.chunkSize*chunkY+y)*worldScale, (0.5f+this.chunkSize*chunkZ+z)*worldScale, 1.0f, 1.0f,
			});
		}
		// Bottom
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x, y-1, z))
		{
			verticesIndex = (short) vertexBuffer.getVerticesCount();
			vertexBuffer.addIndices(new short[] {(short) verticesIndex, (short) (verticesIndex+1), (short) (verticesIndex+2), (short) (verticesIndex+1), (short) (verticesIndex+2), (short) (verticesIndex+3)});
			vertexBuffer.addVertices(new float[] {
					(-0.5f+this.chunkSize*chunkX+x)*worldScale, (-0.5f+this.chunkSize*chunkY+y)*worldScale, (-0.5f+this.chunkSize*chunkZ+z)*worldScale, 0.0f, 0.0f,
					(-0.5f+this.chunkSize*chunkX+x)*worldScale, (-0.5f+this.chunkSize*chunkY+y)*worldScale, (0.5f+this.chunkSize*chunkZ+z)*worldScale, 0.0f, 1.0f,
					(0.5f+this.chunkSize*chunkX+x)*worldScale, (-0.5f+this.chunkSize*chunkY+y)*worldScale, (-0.5f+this.chunkSize*chunkZ+z)*worldScale, 1.0f, 0.0f,
					(0.5f+this.chunkSize*chunkX+x)*worldScale, (-0.5f+this.chunkSize*chunkY+y)*worldScale, (0.5f+this.chunkSize*chunkZ+z)*worldScale, 1.0f, 1.0f,
			});
		}
	}
	
	/*
	 * Get and set
	 */
	
	public int getVerticesCount() {
		return this.verticesCount;
	}
	
	public void setVoxel(int newType, int x, int y, int z) {
		this.voxels[x][y][z].setType(newType);
	}
}
