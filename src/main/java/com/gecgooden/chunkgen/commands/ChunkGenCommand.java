package com.gecgooden.chunkgen.commands;

import com.gecgooden.chunkgen.reference.Reference;
import com.gecgooden.chunkgen.util.Utilities;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;

import java.util.Collections;
import java.util.List;

public class ChunkGenCommand implements ICommand
{	

	@Override
	public String getCommandName()
	{
		return "chunkgen";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "chunkgen <x> <z> <height> <width> [dimension]";
	}

	@Override
	public List getCommandAliases()
	{
		return Collections.singletonList("chunkgen");
	}

	/**
	 * Return the required permission level for this command.
	 */
	public int getRequiredPermissionLevel()
	{
		return 4;
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] options)
	{
		if(!icommandsender.canCommandSenderUseCommand(getRequiredPermissionLevel(), this.getCommandName()) && !MinecraftServer.getServer().isSinglePlayer()) {
			ChatComponentTranslation chatTranslation = new ChatComponentTranslation("commands.generic.permission");
			MinecraftServer.getServer().addChatMessage(chatTranslation);
			icommandsender.addChatMessage(new ChatComponentText(chatTranslation.getUnformattedTextForChat()));
		} else {
			if(options.length == 0 || options[0].equalsIgnoreCase("help")) {
				ChatComponentTranslation chatTranslation = new ChatComponentTranslation(getCommandUsage(icommandsender));
				MinecraftServer.getServer().addChatMessage(chatTranslation);
				icommandsender.addChatMessage(new ChatComponentText(chatTranslation.getUnformattedTextForChat()));
			}
			else if(options[0].equalsIgnoreCase("stop")) {
				Reference.toGenerate.clear();
				ChatComponentTranslation chatTranslation = new ChatComponentTranslation("commands.stopped");
				MinecraftServer.getServer().addChatMessage(chatTranslation);
				icommandsender.addChatMessage(new ChatComponentText(chatTranslation.getUnformattedTextForChat()));
			} else {
				try {
					int playerX = 0;
					int playerZ = 0;
					if(!icommandsender.getCommandSenderName().equalsIgnoreCase("Rcon")) {
						ChunkCoordinates cc = icommandsender.getPlayerCoordinates();
						playerX = cc.posX;
						playerZ = cc.posZ;
					}

					int x;
					int z;
					if(options[0].equalsIgnoreCase("~")) {
						x = playerX/16;
					} else {
						x = Integer.parseInt(options[0]);
					}
					if(options[1].equalsIgnoreCase("~")) {
						z = playerZ/16;
					} else {
						z = Integer.parseInt(options[1]);
					}
					int height = Integer.parseInt(options[2]);
					int width = Integer.parseInt(options[3]);
					int dimensionID;
					if(options.length == 5) {
						dimensionID = Integer.parseInt(options[4]);
					} else {
						dimensionID = icommandsender.getEntityWorld().provider.dimensionId;
					}

					WorldServer worldServer = MinecraftServer.getServer().worldServerForDimension(dimensionID);
					if(worldServer == null) {
						ChatComponentTranslation chatTranslation = new ChatComponentTranslation("commands.invalidDimension",  dimensionID);
						MinecraftServer.getServer().addChatMessage(chatTranslation);
						icommandsender.addChatMessage(new ChatComponentText(chatTranslation.getUnformattedTextForChat()));
					} else {
						Utilities.queueChunkGeneration(icommandsender, 0, x, z, height, width, dimensionID);
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
					ChatComponentTranslation chatTranslation = new ChatComponentTranslation("commands.numberFormatException");
					MinecraftServer.getServer().addChatMessage(chatTranslation);
					icommandsender.addChatMessage(new ChatComponentText(chatTranslation.getUnformattedTextForChat()));
				} catch (Exception e) {
					e.printStackTrace();
					ChatComponentTranslation chatTranslation = new ChatComponentTranslation("commands.failed");
					MinecraftServer.getServer().addChatMessage(chatTranslation);
					icommandsender.addChatMessage(new ChatComponentText(chatTranslation.getUnformattedTextForChat()));
				}
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
	{	
		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender icommandsender,
			String[] astring)
	{
		return null;
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}

	@Override
	public int compareTo(Object o)
	{
		return 0;
	}
}