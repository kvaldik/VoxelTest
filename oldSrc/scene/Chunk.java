package is.kvaldik.scene;


public class Chunk {
	private int chunkSize;
	private int vertexCount;
	private Voxel[][][] voxels;
	
	public Chunk(int newSize) {
		this.chunkSize = newSize;
		this.voxels = new Voxel[this.chunkSize][this.chunkSize][this.chunkSize];
		
		// Create the voxels
		for (int x = 0; x < this.chunkSize; x++) {
			for (int y = 0; y < this.chunkSize; y++) {
				for (int z = 0; z < this.chunkSize; z++) {
					this.voxels[x][y][z] = new Voxel();
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
		this.vertexCount = vertexBuffer.getVertexCount();
	}
	
	public boolean checkVoxel(int x, int y, int z) {
		return this.voxels[x][y][z].isActive();
	}
	
	public boolean isEmpty() {
		return this.vertexCount == 0;
	}

	public void addVoxel(VertexBuffer vertexBuffer, World world, int chunkX, int chunkY, int chunkZ, int x, int y, int z) {
		// Testing 2
		// Front
		/*if (!world.checkVoxel(chunkX, chunkY, chunkZ, x, y, z-1))
		{
			short i = (short) vertexBuffer.getVertexCount();
			vertexBuffer.add2(new short[] {i,i,(short) (i+1),(short) (i+2), (short) (i+3), (short) (i+3)});
			vertexBuffer.add(new float[] {
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					//0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					//-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
			});
		}
		// Back
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x, y, z+1))
		{
			short i = (short) vertexBuffer.getVertexCount();
			vertexBuffer.add2(new short[] {i,i,(short) (i+1),(short) (i+2), (short) (i+3), (short) (i+3)});
			vertexBuffer.add(new float[] {
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					//-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					//0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
			});
		}
		// Right
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x+1, y, z))
		{
			short i = (short) vertexBuffer.getVertexCount();
			vertexBuffer.add2(new short[] {i,i,(short) (i+1),(short) (i+2), (short) (i+3), (short) (i+3)});
			vertexBuffer.add(new float[] {
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					//0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					//0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
			});
		}
		// Left
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x-1, y, z))
		{
			short i = (short) vertexBuffer.getVertexCount();
			vertexBuffer.add2(new short[] {i,i,(short) (i+1),(short) (i+2), (short) (i+3), (short) (i+3)});
			vertexBuffer.add(new float[] {				
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					//-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					//-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
			});
		}
		// Top
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x, y+1, z))
		{
			short i = (short) vertexBuffer.getVertexCount();
			vertexBuffer.add2(new short[] {i,i,(short) (i+1),(short) (i+2), (short) (i+3), (short) (i+3)});
			vertexBuffer.add(new float[] {
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					//0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					//-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
			});
		}
		// Bottom
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x, y-1, z))
		{
			short i = (short) vertexBuffer.getVertexCount();
			vertexBuffer.add2(new short[] {i,i,(short) (i+1),(short) (i+2), (short) (i+3), (short) (i+3)});
			vertexBuffer.add(new float[] {
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					//0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					//-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f
			});
		}*/
			
					
					
					
		
		// Testing
		/*vertexBuffer.add(new float[] {
				-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
				0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
				-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
				0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
				-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
				0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
				-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
				0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
		});*/
		
		
		// Front
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x, y, z-1))
			vertexBuffer.add(new float[] {
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					//0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					//-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
			});
		// Back
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x, y, z+1))
			vertexBuffer.add(new float[] {
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					//-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					//0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
			});
		// Right
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x+1, y, z))
			vertexBuffer.add(new float[] {
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					//0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					//0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
			});
		// Left
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x-1, y, z))
			vertexBuffer.add(new float[] {
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					//-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					//-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
			});
		// Top
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x, y+1, z))
			vertexBuffer.add(new float[] {
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					//0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					//-0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
					0.5f+this.chunkSize*chunkX+x, 0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
			});
		// Bottom
		if (!world.checkVoxel(chunkX, chunkY, chunkZ, x, y-1, z))
			vertexBuffer.add(new float[] {
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 0.0f, 0.0f,
					-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					//0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					//-0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 0.0f, 1.0f,
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, -0.5f+this.chunkSize*chunkZ+z, 1.0f, 0.0f,
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f,
					0.5f+this.chunkSize*chunkX+x, -0.5f+this.chunkSize*chunkY+y, 0.5f+this.chunkSize*chunkZ+z, 1.0f, 1.0f
			});
	
	}
	
	/*
	 * Get and set
	 */
	
	public int getVertexCount() {
		return this.vertexCount;
	}
}
