package com.gecgooden.chunkgen.handlers;

import java.text.DecimalFormat;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import com.gecgooden.chunkgen.reference.Reference;
import com.gecgooden.chunkgen.util.ChunkPosition;
import com.gecgooden.chunkgen.util.Utilities;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickHandler {

	private int tickCounter = 0;
	
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		if(Reference.toGenerate != null && !Reference.toGenerate.isEmpty()) {
			tickCounter++;
			for(int i = 0; i < Reference.numChunksPerTick; i++) {
				ChunkPosition cp = Reference.toGenerate.poll();
				if(cp != null) {
					Utilities.generateChunk(null, cp.getX(), cp.getZ(), cp.getDimensionID());
					float completedPercentage = 1 - (float)Reference.toGenerate.size()/(float)Reference.startingSize;
					if(tickCounter == Reference.tickDelay) {
						Reference.logger.info("percentage: " + completedPercentage);
						tickCounter = 0;
						ChatComponentTranslation chatTranslation = new ChatComponentTranslation("");
						MinecraftServer.getServer().addChatMessage(chatTranslation);
						
						cp.getICommandSender().addChatMessage(new ChatComponentText("Chunkgen: " + (int)(completedPercentage * 100) + "% completed"));
					}
					if(Reference.toGenerate.peek() == null) {
						ChatComponentTranslation chatTranslation = new ChatComponentTranslation("commands.successful");
						MinecraftServer.getServer().addChatMessage(chatTranslation);
						cp.getICommandSender().addChatMessage(new ChatComponentText(chatTranslation.getUnformattedTextForChat()));
					}
				}
			}
		}
	}

}
