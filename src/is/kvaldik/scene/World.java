package is.kvaldik.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;




public class World {
	private int worldSize;
	private int chunkSize;
	private float worldScale;
	private Chunk[][][] chunks;
	private VertexBuffer vertexBuffer;
	private Mesh[][][] meshes;
	private Texture texture;
	
	public World(int newSize, int newChunkSize, float newWorldScale) {
		this.worldSize = newSize;
		this.chunkSize = newChunkSize;
		this.worldScale = newWorldScale;
		this.chunks = new Chunk[newSize][newSize][newSize];
		this.vertexBuffer = new VertexBuffer(newChunkSize);
		this.meshes = new Mesh[newSize][newSize][newSize];
		
		FileHandle imageFileHandle = Gdx.files.internal("assets/textures/crate01.jpg"); 
        this.texture = new Texture(imageFileHandle);
        
        // Create the chunks
        for (int x = 0; x < this.worldSize; x++) {
			for (int y = 0; y < this.worldSize; y++) {
				for (int z = 0; z < this.worldSize; z++) {
					this.chunks[x][y][z] = new Chunk(newChunkSize);
				}
			}
        }
        
     // Testing map generation
        for (int x = 0; x < this.worldSize; x++) {
			for (int z = 0; z < this.worldSize; z++) {
				for (int x1 = 0; x1 < this.chunkSize; x1++) {
					for (int z1 = 0; z1 < this.chunkSize; z1++) {
						int xPos = x*16+x1;
						//int zPos = z*16+z1;

						for (int y = 0; y < this.worldSize; y++) {
							for (int y1 = 0; y1 < this.chunkSize; y1++) {
								int yPos = y*16+y1;
								if (yPos < (15+10*Math.cos(Math.toRadians(xPos*10))))
									this.chunks[x][y][z].setVoxel(1, x1, y1, z1);
							}
						}
			        }
				}
			}
        }
        
        // Create and build the meshes
        for (int x = 0; x < this.worldSize; x++) {
			for (int y = 0; y < this.worldSize; y++) {
				for (int z = 0; z < this.worldSize; z++) {
					this.chunks[x][y][z].buildVertexBuffer(this.vertexBuffer, this, x, y, z);
					this.meshes[x][y][z] = new Mesh(true, this.chunks[x][y][z].getVerticesCount(), this.vertexBuffer.getIndicesCount(), 
							new VertexAttribute(Usage.Position, 3, "a_position"),
			                new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
					this.meshes[x][y][z].setVertices(this.vertexBuffer.getVertices());
					this.meshes[x][y][z].setIndices(this.vertexBuffer.getIndices());
				}
			}
        }
	}
	
	public void drawWorld() {
	    Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
		for (int x = 0; x < this.worldSize; x++) {
			for (int y = 0; y < this.worldSize; y++) {
				for (int z = 0; z < this.worldSize; z++) {
				    texture.bind();
			    	this.meshes[x][y][z].render(GL11.GL_TRIANGLES);
				}
			}
		}
	}
	
	public boolean checkVoxel(int chunkX, int chunkY, int chunkZ, int x, int y, int z) {
		// Check if the chunk is out of bounds
		if (chunkX < 0 || chunkX >= this.worldSize ||
			chunkY < 0 || chunkY >= this.worldSize ||
			chunkZ < 0 || chunkZ >= this.worldSize) {
			return false;
		}
		// Check if the voxels are out of chunks, adjust the values and call checkVoxel again
		else if (x < 0)					{x = this.chunkSize-1; chunkX--; return this.checkVoxel(chunkX, chunkY, chunkZ, x, y, z);}
		else if (x >= this.chunkSize) 	{x = 0; chunkX++; return this.checkVoxel(chunkX, chunkY, chunkZ, x, y, z);}
		else if (y < 0) 				{y = this.chunkSize-1; chunkY--; return this.checkVoxel(chunkX, chunkY, chunkZ, x, y, z);}
		else if (y >= this.chunkSize) 	{y = 0; chunkY++; return this.checkVoxel(chunkX, chunkY, chunkZ, x, y, z);}
		else if (z < 0) 				{z = this.chunkSize-1; chunkZ--; return this.checkVoxel(chunkX, chunkY, chunkZ, x, y, z);}
		else if (z >= this.chunkSize) 	{z = 0; chunkZ++; return this.checkVoxel(chunkX, chunkY, chunkZ, x, y, z);}
		// The voxel is within bounds, check for it
		else
			return this.chunks[chunkX][chunkY][chunkZ].checkVoxel(x, y, z);
	}
	
	/*
	 * Get and set
	 */
	
	public float getWorldScale() {
		return this.worldScale;
	}
}