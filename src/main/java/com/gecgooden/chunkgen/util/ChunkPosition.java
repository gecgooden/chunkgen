package com.gecgooden.chunkgen.util;

import net.minecraft.command.ICommandSender;

public class ChunkPosition {

	private final int x;
	private final int z;
	private final int dimensionID;
	private final ICommandSender iCommandSender;
	
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
	
	public int getZ() {
		return z;
	}
	
	public int getDimensionID() {
		return dimensionID;
	}
	
	public ICommandSender getICommandSender() {
		return iCommandSender;
	}
}
