package com.gecgooden.chunkgen.util;

import com.gecgooden.chunkgen.reference.Reference;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.RegionFileCache;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.DimensionManager;

import java.security.Provider;


public class Utilities {

    private static boolean chunksExist(MinecraftServer server, int x, int z, int dimensionID) {
        WorldServer world = DimensionManager.getWorld(dimensionID);
        ChunkProviderServer chunkProvider = server.getWorld(dimensionID).getChunkProvider();
        return chunkProvider.chunkExists(x, z) || RegionFileCache.createOrLoadRegionFile(world.getChunkSaveLocation(), x, z).chunkExists(x & 0x1F, z & 0x1F);
    }

    public static boolean generateChunk(MinecraftServer server, int x, int z, int dimensionID) {
        ChunkProviderServer chunkProvider = server.getWorld(dimensionID).getChunkProvider();
        if (chunkProvider.chunkExists(x, z)) {
            return false;
        }
        if (chunkProvider.getLoadedChunkCount() > Reference.maxChunksLoaded) {
            chunkProvider.saveChunks(true);
            chunkProvider.queueUnloadAll();
        }
        chunkProvider.provideChunk(x, z);
        return true;
    }

    public static int queueChunkGeneration(ICommandSender icommandsender, int skipChunks, int x, int z, int radius, int dimensionID, boolean logToChat) {
        final int xmax = x + radius;
        final int xmin = x - radius;
        final int zmax = z + radius;
        final int zmin = z - radius;
        //now actually using origin point
        final Vec3d zerovec = new Vec3d(x, 0, z);
        for (int xPos = xmin; xPos < xmax; xPos++) {
            for (int zPos = zmin; zPos < zmax; zPos++) {
                if (zerovec.distanceTo(new Vec3d(xPos, 0, zPos)) > radius) {
                    continue;
                }
                if (!chunksExist(icommandsender.getServer(), xPos, zPos, dimensionID)) {
                    Reference.toGenerate.add(new ChunkPosition(xPos, zPos, dimensionID, icommandsender, logToChat));
                }
            }
        }
        Reference.toGenerate.sort(ChunkPosition.byAngleComparator(new ChunkPosition(x, z, dimensionID, icommandsender, logToChat)));
        return Reference.startingSize = Reference.toGenerate.size();
    }

    public static int queueChunkGeneration(ICommandSender icommandsender, int skipChunks, int x, int z, int xSize, int zSize, int dimensionID, boolean logToChat) {
        final int xmax = x + xSize;
        final int xmin = x - xSize;
        final int zmax = z + zSize;
        final int zmin = z - zSize;
        for (int xPos = xmin; xPos < xmax; xPos++) {
            for (int zPos = zmin; zPos < zmax; zPos++) {
                if (!chunksExist(icommandsender.getServer(), xPos, zPos, dimensionID)) {
                    Reference.toGenerate.add(new ChunkPosition(xPos, zPos, dimensionID, icommandsender, logToChat));
                }
            }
        }
        Reference.toGenerate.sort(ChunkPosition.byAngleComparator(new ChunkPosition(x, z, dimensionID, icommandsender, logToChat)));
        return Reference.startingSize = Reference.toGenerate.size();
    }
}
