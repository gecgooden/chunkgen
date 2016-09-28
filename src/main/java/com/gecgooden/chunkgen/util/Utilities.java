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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;


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

    private static boolean chunkPopulated(ChunkProviderServer cps, int x, int z, int dimensionID) {
        Chunk chunk = cps.provideChunk(x, z);

        if (chunk.isTerrainPopulated()) {
            return true;
        } else {
            Reference.logger.info("Chunk at " + x + " " + z + " Dim: " + dimensionID + " not populated");
            return false;
        }
    }

    private static boolean chunkPrepared(ChunkProviderServer cps, int x, int z, int dimensionID) {
        if (chunksExist(x, z, dimensionID) && chunkPopulated(cps, x, z, dimensionID)) {
            return true;
        } else {
            return false;
        }
    }

    public static void generateChunk(MinecraftServer server, int x, int z, int dimensionID) {
		ChunkProviderServer cps = server.worldServerForDimension(dimensionID).getChunkProvider();
		if(!chunkPrepared(cps, x, z, dimensionID)) {
			cps.loadChunk(x, z);

			cps.loadChunk(x, z+1);
			cps.loadChunk(x+1, z);
			cps.loadChunk(x+1, z+1);

			Reference.logger.info(String.format("Loaded Chunk at %s, %s DIM%s ", x, z, DimensionManager.getProviderType(dimensionID) != null ? DimensionManager.getProviderType(dimensionID).getName() : dimensionID));
		}
	}

	private static class Region{
        int x;
        int z;
        Region(int x, int z){
            this.x = x;
            this.z = z;
        }
    }

	private static void queueAllRegions(int xmaxreg, int xminreg, int zmaxreg, int zminreg, BiConsumer<Integer, Integer> addChunk){
        List<Region> list = new ArrayList<>((xmaxreg-xminreg)*(zmaxreg-zminreg)*2);
        for (int xreg = xminreg; xreg < xmaxreg; xreg++) {
            for (int zreg = zminreg; zreg < zmaxreg; zreg++) {
                list.add(new Region(xreg, zreg));
            }
        }
        list.stream().sorted(Comparator.comparingInt((r) -> (int)(Math.abs(r.x)+Math.abs(r.z)))).forEachOrdered(
                r -> queueRegion(r.x, r.z, addChunk)
        );

    }

	private static void queueRegion(int regx, int regz, BiConsumer<Integer, Integer> addChunk){
        final int xoffset = 512*regx;
        final int zoffset = 512*regz;
        final int sqx = xoffset/128;
        final int sqz = zoffset/128;
        for (int xcounter = -64; xcounter < 64; xcounter++) {
            for (int zcounter = -64; zcounter < 64; zcounter++) {
                queueSquare(sqx+xcounter, sqz+zcounter, addChunk);
            }
        }
    }

    private static void queueSquare(int sqx, int sqz, BiConsumer<Integer, Integer> addChunk){
        final int xoffset = 4*sqx;
        final int zoffset = 4*sqz;
        for (int xcounter = 0; xcounter < 4; xcounter++) {
            for (int zcounter = 0; zcounter < 4; zcounter++) {
                addChunk.accept(xcounter+xoffset, zcounter+zoffset);
            }
        }
    }

	public static int queueChunkGeneration(ICommandSender icommandsender, int skipChunks, int x, int z, int radius, int dimensionID, boolean logToChat) {
        final int xmax = x+radius;
        final int xmin = x-radius;
        final int zmax = z+radius;
        final int zmin = z-radius;
        //now actually using origin point
        final Vec3d zerovec = new Vec3d(x, 0, z);
        BiConsumer<Integer, Integer> addChunk = (xpos,zpos) -> {
            //guard for range
            if (zerovec.distanceTo(new Vec3d(xpos, 0, zpos)) <= radius) {
                return;
            }
            Reference.toGenerate.add(new ChunkPosition(xpos, zpos, dimensionID, icommandsender, logToChat));
        };
        return queueChunkGeneration(xmax, xmin, zmax, zmin, addChunk);
	}

    public static int queueChunkGeneration(ICommandSender icommandsender, int skipChunks, int x, int z, int xSize, int zSize, int dimensionID, boolean logToChat) {
        final int xmax = x+xSize;
        final int xmin = x-xSize;
        final int zmax = z+zSize;
        final int zmin = z-zSize;
        BiConsumer<Integer, Integer> addChunk = (xpos,zpos) -> {
            //guard for range
            if (xpos > xmax || xpos < xmin
                    || zpos > zmax || xpos < zmin) {
                return;
            }
            Reference.toGenerate.add(new ChunkPosition(xpos, zpos, dimensionID, icommandsender, logToChat));
        };
        return queueChunkGeneration(xmax, xmin, zmax, zmin, addChunk);
    }

    private static int queueChunkGeneration(int xmax, int xmin, int zmax, int zmin, BiConsumer<Integer, Integer> addChunk){
        final int xmaxreg = xmax/512 + 1;
        final int xminreg = xmin/512 - 1;
        final int zmaxreg = zmax/512 + 1;
        final int zminreg = zmin/512 - 1;
        queueAllRegions(xmaxreg, xminreg, zmaxreg, zminreg, addChunk);
        //todo enable skipping again
        return Reference.startingSize = Reference.toGenerate.size();
    }
}
