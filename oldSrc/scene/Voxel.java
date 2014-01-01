package is.kvaldik.scene;

public class Voxel {
	private byte type;
	
	public Voxel() {
		this.type = (byte)(Math.random()+0.5);
		//this.type = 1;
	}
	
	public boolean isActive() {
		return (type != 0);
	}
	
	/*
	 * Get and set
	 */
	
	public void setType(byte newType) {
		this.type = newType;
	}
	
	public byte getType() {
		return this.type;
	}
}
