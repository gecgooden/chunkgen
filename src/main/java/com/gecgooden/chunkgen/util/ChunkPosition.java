package com.gecgooden.chunkgen.util;

import net.minecraft.command.ICommandSender;

public class ChunkPosition {

	private int x;
	private int z;
	private int dimensionID;
	private ICommandSender iCommandSender;
	
	public ChunkPosition(int x, int z, int dimensionID, ICommandSender icommandsender) {
		super();
		this.x = x;
		this.z = z;
		this.dimensionID = dimensionID;
		iCommandSender = icommandsender;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
	public int getDimensionID() {
		return dimensionID;
	}
	
	public void setDimensionID(int dimensionID) {
		this.dimensionID = dimensionID;
	}

	public ICommandSender getICommandSender() {
		return iCommandSender;
	}

	public void setICommandSender(ICommandSender iCommandSender) {
		this.iCommandSender = iCommandSender;
	}
	
}
