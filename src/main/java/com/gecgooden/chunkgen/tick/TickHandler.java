package com.gecgooden.chunkgen.tick;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

import com.gecgooden.chunkgen.reference.Reference;
import com.gecgooden.chunkgen.util.ChunkPosition;
import com.gecgooden.chunkgen.util.Utilities;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickHandler {

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		if(Reference.toGenerate != null && !Reference.toGenerate.isEmpty()) {
			for(int i = 0; i < Reference.numChunksPerTick; i++) {
				ChunkPosition cp = Reference.toGenerate.poll();
				if(cp != null) {
					Utilities.generateChunk(cp.getX(), cp.getZ(), cp.getDimensionID());
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
