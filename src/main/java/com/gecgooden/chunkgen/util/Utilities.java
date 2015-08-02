package com.gecgooden.chunkgen.util;

import java.util.ArrayList;
import java.util.List;

import com.gecgooden.chunkgen.reference.Reference;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraft.world.chunk.storage.RegionFileCache;
import net.minecraftforge.common.DimensionManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;




public class Utilities {

	public static void generateChunks(int x, int z, int width, int height, int dimensionID) {

		ChunkProviderServer cps = MinecraftServer.getServer().worldServerForDimension(dimensionID).theChunkProviderServer;

		List<Chunk> chunks = new ArrayList<Chunk>(width*height);
		for(int i = (x - width/2); i < (x + width/2); i++) {
			for(int j = (z - height/2); j < (z + height/2); j++) {
				generateChunk(null, i, j, dimensionID);
			}
		}
		for(Chunk c : chunks) {
			cps.unloadChunksIfNotNearSpawn(c.xPosition, c.zPosition);
		}
	}

	private static boolean chunksExist(ChunkProviderServer cps, int x, int z, int dimensionID) {
		WorldServer world = null;
	
		world = DimensionManager.getWorld(dimensionID);
		
		return RegionFileCache.createOrLoadRegionFile(world.getChunkSaveLocation(), x, z).chunkExists(x & 0x1F, z & 0x1F);

		
	}
	
	public static void generateChunk(ChunkProviderServer cps, int x, int z, int dimensionID) {
		if(cps == null) {
			cps = MinecraftServer.getServer().worldServerForDimension(dimensionID).theChunkProviderServer;
		}
		if(!chunksExist(cps, x, z, dimensionID)) {
			cps.loadChunk(x, z);

			cps.loadChunk(x, z+1);
			cps.loadChunk(x+1, z);
			cps.loadChunk(x+1, z+1);
			
//			cps.unloadChunksIfNotNearSpawn(x, z);
//			
//			cps.unloadChunksIfNotNearSpawn(x, z+1);
//			cps.unloadChunksIfNotNearSpawn(x, z-1);
//			cps.unloadChunksIfNotNearSpawn(x+1, z);
//			cps.unloadChunksIfNotNearSpawn(x-1, z);
			

			Reference.logger.info("Loaded Chunk at " + x + " " + z + " " + dimensionID);
		}
	}
}
