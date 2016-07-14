package com.gecgooden.chunkgen.util;

import net.minecraft.command.ICommandSender;

public class ChunkPosition {

	private int x;
	private int z;
	private int dimensionID;
	private ICommandSender iCommandSender;
	private boolean logToChat;

	public ChunkPosition(int x, int z, int dimensionID, ICommandSender icommandsender, boolean logToChat) {
		this.x = x;
		this.z = z;
		this.dimensionID = dimensionID;
		this.iCommandSender = icommandsender;
		this.logToChat = logToChat;
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

	public boolean logToChat() {
		return logToChat;
	}

	public void setLogToChat(boolean logToChat) {
		this.logToChat = logToChat;
	}

}
