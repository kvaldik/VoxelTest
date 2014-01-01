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
	private Chunk[][][] chunks;
	private VertexBuffer vertexBuffer;
	private Mesh[][][] meshes;
	private Texture texture;
	
	public World(int newSize, int newChunkSize) {
		this.worldSize = newSize;
		this.chunkSize = newChunkSize;
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
        
        // Create and build the meshes
        for (int x = 0; x < this.worldSize; x++) {
			for (int y = 0; y < this.worldSize; y++) {
				for (int z = 0; z < this.worldSize; z++) {
					this.chunks[x][y][z].buildVertexBuffer(this.vertexBuffer, this, x, y, z);
					if (!this.chunks[x][y][z].isEmpty()) {
						this.meshes[x][y][z] = new Mesh(true, newChunkSize*newChunkSize*newChunkSize*18, 0, 
								new VertexAttribute(Usage.Position, 3, "a_position"),
				                new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoords"));
						this.meshes[x][y][z].setVertices(this.vertexBuffer.getVertexBufferArray());
						//this.meshes[x][y][z].setIndices(new short[] {2,3,0,1,4,5,1,5,3,7,5,7,4,6,7,6,3,2,6,0,4});
						//this.meshes[x][y][z].setIndices(new short[] {0,0,1,2,3,3,4,4,5,6,7,7});
						//this.meshes[x][y][z].setIndices(this.vertexBuffer.getBle());
					}
					else
						this.chunks[x][y][z] = null;
				}
			}
        }
	}
	
	public void drawWorld() {
	    Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
		for (int x = 0; x < this.worldSize; x++) {
			for (int y = 0; y < this.worldSize; y++) {
				for (int z = 0; z < this.worldSize; z++) {
				    if (this.chunks[x][y][z] != null) {
					    texture.bind();
				    	this.meshes[x][y][z].render(GL11.GL_TRIANGLE_STRIP);
				    }
				}
			}
		}
	}
	
	public boolean checkVoxel(int chunkX, int chunkY, int chunkZ, int x, int y, int z) {
		// Check if the chunk is out of bounds
		if (chunkX < 0 | chunkX >= this.worldSize |
			chunkY < 0 | chunkY >= this.worldSize |
			chunkZ < 0 | chunkZ >= this.worldSize) {
			return false;
		}
		// Check if the voxels are out of chunks, adjust the values and call checkVoxel again
		else if (x < 0) {x = this.chunkSize; chunkX--; return this.checkVoxel(chunkX, chunkY, chunkZ, x, y, z);}
		else if (x >= this.chunkSize) {x = 0; chunkX++; return this.checkVoxel(chunkX, chunkY, chunkZ, x, y, z);}
		else if (y < 0) {y = this.chunkSize; chunkY--; return this.checkVoxel(chunkX, chunkY, chunkZ, x, y, z);}
		else if (y >= this.chunkSize) {y = 0; chunkY++; return this.checkVoxel(chunkX, chunkY, chunkZ, x, y, z);}
		else if (z < 0) {z = this.chunkSize; chunkZ--; return this.checkVoxel(chunkX, chunkY, chunkZ, x, y, z);}
		else if (z >= this.chunkSize) {z = 0; chunkZ++; return this.checkVoxel(chunkX, chunkY, chunkZ, x, y, z);}
		// The voxel is within bounds, check for it
		else
			return this.chunks[chunkX][chunkY][chunkZ].checkVoxel(x, y, z);
	}
}