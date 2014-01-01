package is.kvaldik.scene;

public class Voxel {
	private byte type;
	
	public Voxel() {
		//this.type = (byte)(Math.random()+0.5);
		//this.type = 1;
		this.type = 0;
	}
	
	public boolean isActive() {
		return (type != 0);
	}
	
	/*
	 * Get and set
	 */
	
	public void setType(int newType) {
		this.type = (byte) newType;
	}
	
	public byte getType() {
		return this.type;
	}
}
