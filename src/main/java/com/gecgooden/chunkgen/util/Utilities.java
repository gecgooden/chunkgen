package com.gecgooden.chunkgen.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.gecgooden.chunkgen.reference.Reference;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.RegionFileCache;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.DimensionManager;


public class Utilities {

	public static void generateChunks(MinecraftServer server, int x, int z, int width, int height, int dimensionID) {

		ChunkProviderServer cps = server.worldServerForDimension(dimensionID).getChunkProvider();

		List<Chunk> chunks = new ArrayList<Chunk>(width*height);
		for(int i = (x - width/2); i < (x + width/2); i++) {
			for(int j = (z - height/2); j < (z + height/2); j++) {
				generateChunk(server, i, j, dimensionID);
			}
		}
		for(Chunk c : chunks) {
			cps.unloadAllChunks();
		}
	}

	private static boolean chunksExist(int x, int z, int dimensionID) {
		WorldServer world = DimensionManager.getWorld(dimensionID);
		return world.getChunkProvider().chunkExists(x, z) || RegionFileCache.createOrLoadRegionFile(world.getChunkSaveLocation(), x, z).chunkExists(x & 0x1F, z & 0x1F);
	}

	public static void generateChunk(MinecraftServer server, int x, int z, int dimensionID) {
		ChunkProviderServer cps = server.worldServerForDimension(dimensionID).getChunkProvider();
		if(!chunksExist(x, z, dimensionID)) {
			cps.loadChunk(x, z);

			cps.loadChunk(x, z+1);
			cps.loadChunk(x+1, z);
			cps.loadChunk(x+1, z+1);

			Reference.logger.info(String.format("Loaded Chunk at %s, %s (%s) ", x, z, DimensionManager.getProviderType(dimensionID) != null ? DimensionManager.getProviderType(dimensionID).getName() : dimensionID));
		}
	}

	public static int queueChunkGeneration(ICommandSender icommandsender, int skipChunks, int x, int z, int xSize, int zSize, int dimensionID, boolean logToChat) {
		for(int cx = 0; cx < xSize; cx++){
			for(int cz = 0; cz < zSize; cz++){
				if (skipChunks > 0) {
					skipChunks--;
				} else {
					Reference.toGenerate.add(new ChunkPosition(x + cx, z + cz, dimensionID, icommandsender, logToChat));
				}
			}
		}
		return Reference.startingSize = Reference.toGenerate.size();
	}

	public static int queueChunkGeneration(ICommandSender icommandsender, int skipChunks, int x, int z, int radius, int dimensionID, boolean logToChat) {
		for(int cx = -radius; cx < radius + 1; cx++){
			for(int cz = -radius; cz < radius + 1; cz++){
				if(new Vec3d(0, 0, 0).distanceTo(new Vec3d(cx, 0, cz)) <= radius){
					if (skipChunks > 0) {
						skipChunks--;
					} else {
						Reference.toGenerate.add(new ChunkPosition(x + cx, z + cz, dimensionID, icommandsender, logToChat));
					}
				}
			}
		}
		return Reference.startingSize = Reference.toGenerate.size();
	}
}
