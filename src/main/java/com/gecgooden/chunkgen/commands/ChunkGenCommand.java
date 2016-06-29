package com.gecgooden.chunkgen.commands;

import com.gecgooden.chunkgen.reference.Reference;
import com.gecgooden.chunkgen.util.Utilities;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ChunkGenCommand implements ICommand
{	
	private List aliases;
	public ChunkGenCommand()
	{
		this.aliases = new ArrayList();
		this.aliases.add("chunkgen");
	}

	@Override
	public String getCommandName()
	{
		return "chunkgen";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "chunkgen <x> <y> <height> <width> [dimension]";
	}

	@Override
	public List getCommandAliases()
	{
		return this.aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender icommandsender, String[] astring) throws CommandException {
		if(!icommandsender.canCommandSenderUseCommand(getRequiredPermissionLevel(), this.getCommandName()) && !server.isSinglePlayer()) {
			TextComponentTranslation chatTranslation = new TextComponentTranslation("commands.generic.permission", new Object[0]);
			server.addChatMessage(chatTranslation);
			icommandsender.addChatMessage(new TextComponentString(chatTranslation.getUnformattedComponentText()));
		} else {
			int playerX = 0;
			int playerY = 0;
			int playerZ = 0;
			if(!icommandsender.getName().equalsIgnoreCase("Rcon")) {
				EntityPlayer ep = server.worldServerForDimension(0).getPlayerEntityByName(icommandsender.getName());
				BlockPos blockPos = icommandsender.getPosition();
				playerX = blockPos.getX();
				playerY = blockPos.getY();
				playerZ = blockPos.getZ();
			}
			if(astring.length == 0 || astring[0].equalsIgnoreCase("help")) {
				TextComponentTranslation chatTranslation = new TextComponentTranslation(getCommandUsage(icommandsender), new Object[0]);
				server.addChatMessage(chatTranslation);
				icommandsender.addChatMessage(new TextComponentString(chatTranslation.getUnformattedComponentText()));
			}
			else if(astring[0].equalsIgnoreCase("stop")) {
				Reference.toGenerate.clear();
				TextComponentTranslation chatTranslation = new TextComponentTranslation("commands.stopped");
				server.addChatMessage(chatTranslation);
				icommandsender.addChatMessage(new TextComponentString(chatTranslation.getUnformattedComponentText()));
			} else {
				try {
					int x = 0;
					int z = 0;
					if(astring[0].equalsIgnoreCase("~")) {
						x = playerX/16;
					} else {
						x = Integer.parseInt(astring[0]);
					}
					if(astring[1].equalsIgnoreCase("~")) {
						z = playerZ/16;
					} else {
						z = Integer.parseInt(astring[1]);
					}
					int height = Integer.parseInt(astring[2]);
					int width = Integer.parseInt(astring[3]);
					int dimensionID = icommandsender.getEntityWorld().provider.getDimension();
					if(astring.length == 5) {
						dimensionID = Integer.parseInt(astring[4]);
					}
					Utilities.queueChunkGeneration(icommandsender, 0, x, z, height, width, dimensionID);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					TextComponentTranslation chatTranslation = new TextComponentTranslation("commands.numberFormatException");
					server.addChatMessage(chatTranslation);
					icommandsender.addChatMessage(new TextComponentString(chatTranslation.getUnformattedComponentText()));
				} catch (Exception e) {
					e.printStackTrace();
					TextComponentTranslation chatTranslation = new TextComponentTranslation("commands.failed");
					server.addChatMessage(chatTranslation);
					icommandsender.addChatMessage(new TextComponentString(chatTranslation.getUnformattedComponentText()));
				}
			}
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer minecraftServer, ICommandSender iCommandSender) {
		return true;
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings, @Nullable BlockPos blockPos) {
		return null;
	}

	/**
	 * Return the required permission level for this command.
	 */
	public int getRequiredPermissionLevel()
	{
		return 4;
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}
}