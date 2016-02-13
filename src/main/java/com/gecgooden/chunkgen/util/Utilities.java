package com.gecgooden.chunkgen.util;

import com.gecgooden.chunkgen.reference.Reference;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.RegionFileCache;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Utilities {

	public static void generateChunks(int x, int z, int width, int height, int dimensionID) {
		ChunkProviderServer cps = MinecraftServer.getServer().worldServerForDimension(dimensionID).theChunkProviderServer;

		List<Chunk> chunks = new ArrayList<Chunk>(width*height);
		for(int i = (x - width/2); i < (x + width/2); i++) {
			for(int j = (z - height/2); j < (z + height/2); j++) {
				generateChunk(i, j, dimensionID);
			}
		}
		for(Chunk c : chunks) {
			cps.unloadChunksIfNotNearSpawn(c.xPosition, c.zPosition);
		}
	}

	private static boolean chunksExist(int x, int z, int dimensionID) {
		WorldServer world = DimensionManager.getWorld(dimensionID);
		
		return RegionFileCache.createOrLoadRegionFile(world.getChunkSaveLocation(), x, z).chunkExists(x & 0x1F, z & 0x1F);

		
	}
	
	public static void generateChunk(int x, int z, int dimensionID) {
		ChunkProviderServer cps = MinecraftServer.getServer().worldServerForDimension(dimensionID).theChunkProviderServer;
		if (!chunksExist(x, z, dimensionID)) {
			cps.loadChunk(x, z);

			cps.loadChunk(x, z + 1);
			cps.loadChunk(x + 1, z);
			cps.loadChunk(x + 1, z + 1);

			Reference.logger.info("Loaded Chunk at " + x + " " + z + " " + dimensionID);
		}
	}

	public static void queueChunkGeneration(ICommandSender icommandsender, int skipChunks, int x0, int z0, int height, int width, int dimensionID) {
		int x = 0, z = 0, dx = 0, dz = -1;
		int t = Math.max(height, width);
		int maxI = t * t;

		if (Reference.toGenerate == null) {
			Reference.toGenerate = new LinkedList<ChunkPosition>();
		}

		for (int i = 0; i < maxI; i++) {
			if ((-width / 2 <= x) && (x <= width / 2) && (-height / 2 <= z) && (z <= height / 2)) {
				if (skipChunks > 0) {
					skipChunks--;
				} else {
					Reference.toGenerate.add(new ChunkPosition(x + x0, z + z0, dimensionID, icommandsender));
				}
			}

			if ((x == z) || ((x < 0) && (x == -z)) || ((x > 0) && (x == 1 - z))) {
				t = dx;
				dx = -dz;
				dz = t;
			}
			x += dx;
			z += dz;
		}

		Reference.startingSize = Reference.toGenerate.size();
	}
}
