package com.gecgooden.chunkgen.util;

import java.util.ArrayList;
import java.util.List;

import com.gecgooden.chunkgen.reference.Reference;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;

public class Utilities {

	public static void generateChunks(int x, int z, int width, int height, int dimensionID) {

		ChunkProviderServer cps = MinecraftServer.getServer().worldServerForDimension(dimensionID).theChunkProviderServer;

		List<Chunk> chunks = new ArrayList<Chunk>(width*height);
		for(int i = (x - width/2); i < (x + width/2); i++) {
			for(int j = (z - height/2); j < (z + height/2); j++) {
				if(!cps.chunkExists(i, j)) {
					chunks.add(cps.loadChunk(i, j)); 
					cps.saveChunks(true, null);
				}
				Reference.logger.info("Loaded Chunk at " + i + " " + j + " " + dimensionID);
			}
		}
		cps.unloadAllChunks();
	}

	public static void generateChunk(int x, int z, int dimensionID) {
		ChunkProviderServer cps = MinecraftServer.getServer().worldServerForDimension(dimensionID).theChunkProviderServer;
		if(!cps.chunkExists(x, z)) {
			cps.loadChunk(x, z);
			cps.unloadAllChunks();
			Reference.logger.info("Loaded Chunk at " + x + " " + z + " " + dimensionID);
		}
	}
}
