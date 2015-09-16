package com.gecgooden.chunkgen.handlers;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import com.gecgooden.chunkgen.reference.Reference;
import com.gecgooden.chunkgen.util.ChunkPosition;
import com.gecgooden.chunkgen.util.Utilities;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.world.World;

public class TickHandler {

	private double chunkQueue = 0;
	private int chunksGenerated = 0;

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		// Note that this only works on dedicated servers.
		final World world = MinecraftServer.getServer().getEntityWorld();
		if (Reference.pauseForPlayers && world.playerEntities.size() > 0) return;

		if(Reference.toGenerate != null && !Reference.toGenerate.isEmpty()) {
			chunkQueue += Reference.numChunksPerTick;
			while (chunkQueue > 1) {
				chunkQueue--;
				chunksGenerated++;
				ChunkPosition cp = Reference.toGenerate.poll();
				if(cp != null) {
					Utilities.generateChunk(cp.getX(), cp.getZ(), cp.getDimensionID());
					if(chunksGenerated % Reference.updateDelay == 0) {
						float completedPercentage = 1 - (float)Reference.toGenerate.size()/(float)Reference.startingSize;
						Reference.logger.info("percentage: " + completedPercentage);
						ChatComponentTranslation chatTranslation = new ChatComponentTranslation("");
						MinecraftServer.getServer().addChatMessage(chatTranslation);

						cp.getICommandSender().addChatMessage(new ChatComponentText("Chunkgen: " + (int)(completedPercentage * 100) + "% completed"));

						ConfigurationHandler.UpdateSkipChunks();
					}
					if(Reference.toGenerate.peek() == null) {
						ChatComponentTranslation chatTranslation = new ChatComponentTranslation("commands.successful");
						MinecraftServer.getServer().addChatMessage(chatTranslation);
						cp.getICommandSender().addChatMessage(new ChatComponentText(chatTranslation.getUnformattedTextForChat()));
					}
				}
				Reference.skipChunks++;
			}
		}
	}

}
