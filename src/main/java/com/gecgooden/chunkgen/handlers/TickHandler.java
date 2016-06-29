package com.gecgooden.chunkgen.handlers;

import com.gecgooden.chunkgen.reference.Reference;
import com.gecgooden.chunkgen.util.ChunkPosition;
import com.gecgooden.chunkgen.util.Utilities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickHandler {

	private double chunkQueue = 0;
	private int chunksGenerated = 0;

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {

		final MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		final World world = server.getEntityWorld();
		if (Reference.pauseForPlayers && world.playerEntities.size() > 0) return;

		if(Reference.toGenerate != null && !Reference.toGenerate.isEmpty()) {
			chunkQueue += Reference.numChunksPerTick;
			while (chunkQueue > 1) {
				chunkQueue--;
				chunksGenerated++;
				ChunkPosition cp = Reference.toGenerate.poll();
				if(cp != null) {
					Utilities.generateChunk(server, cp.getX(), cp.getZ(), cp.getDimensionID());
					if(chunksGenerated % Reference.updateDelay == 0) {
						float completedPercentage = 1 - (float)Reference.toGenerate.size()/(float)Reference.startingSize;
						Reference.logger.info("percentage: " + completedPercentage);
						TextComponentString chatTranslation = new TextComponentString("");
						server.addChatMessage(chatTranslation);

						cp.getICommandSender().addChatMessage(new TextComponentString("Chunkgen: " + (int)(completedPercentage * 100) + "% completed"));

						ConfigurationHandler.UpdateSkipChunks();
					}
					if(Reference.toGenerate.peek() == null) {
						TextComponentTranslation chatTranslation = new TextComponentTranslation("commands.successful");
						server.addChatMessage(chatTranslation);
						cp.getICommandSender().addChatMessage(new TextComponentString(chatTranslation.getUnformattedComponentText()));
					}
				}
				Reference.skipChunks++;
			}
		}
	}

}
