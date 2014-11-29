package com.gecgooden.chunkgen.reference;

import java.util.Queue;

import org.apache.logging.log4j.Logger;

import com.gecgooden.chunkgen.util.ChunkPosition;

public class Reference {
	public static Integer x; 
	public static Integer z;
	public static Integer height;
	public static Integer width;
	public static Integer numChunksPerTick;
	
	public static final String MOD_ID = "chunkgen";
    public static final String VERSION = "1.7.10-1.1";
    public static final String GUI_FACTORY = "com.gecgooden.chunkgen.client.gui.GuiFactory";
    
    public static Queue<ChunkPosition> toGenerate;
    
    public static Logger logger;
}
