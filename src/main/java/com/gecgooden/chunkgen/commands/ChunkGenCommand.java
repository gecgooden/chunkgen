package com.gecgooden.chunkgen.commands;

import java.util.List;

import com.gecgooden.chunkgen.reference.Reference;
import com.gecgooden.chunkgen.util.Utilities;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class ChunkGenCommand extends CommandBase
{	
	public ChunkGenCommand()
	{

	}

	@Override
	public String getCommandName()
	{
		return "chunkgen";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "commands.chunkgen.usage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender icommandsender, String[] astring) throws CommandException {
		if(astring.length == 0 || astring[0].equalsIgnoreCase("help")) {
			notifyCommandListener(icommandsender, this, "commands.chunkgen.usage");
		} else {
			switch (astring[0]) {
			case "zone":
				if(astring.length == 1){
					notifyCommandListener(icommandsender, this, "commands.chunkgen.usage.zone");
				} else {
					int x = (int) parseCoordinate(icommandsender.getPosition().getX() >> 4, astring[1], false).getResult();
					int z = (int) parseCoordinate(icommandsender.getPosition().getZ() >> 4, astring[2], false).getResult();
					int height = parseInt(astring[3]);
					int width = parseInt(astring[4]);
					int dimensionID = icommandsender.getEntityWorld().provider.getDimension();
					boolean logToChat = false;
					if(astring.length > 5 && !"~".equals(astring[5])) {
						dimensionID = parseInt(astring[5]);
					}
					if(astring.length > 6){
						logToChat = parseBoolean(astring[6]);
					}
					notifyCommandListener(icommandsender, this, "commands.chunkgen.enqueued", Utilities.queueChunkGeneration(icommandsender, 0, x, z, height, width, dimensionID, logToChat));
				}
				break;
			case "radius":
				if(astring.length == 1){
					notifyCommandListener(icommandsender, this, "commands.chunkgen.usage.radius");
				} else {
					int x = (int) parseCoordinate(icommandsender.getPosition().getX() >> 4, astring[1], false).getResult();
					int z = (int) parseCoordinate(icommandsender.getPosition().getZ() >> 4, astring[2], false).getResult();
					int radius = parseInt(astring[3]);
					int dimensionID = icommandsender.getEntityWorld().provider.getDimension();
					boolean logToChat = false;
					if(astring.length > 4 && !"~".equals(astring[4])) {
						dimensionID = parseInt(astring[4]);
					}
					if(astring.length > 5){
						logToChat = parseBoolean(astring[5]);
					}
					notifyCommandListener(icommandsender, this, "commands.chunkgen.enqueued", Utilities.queueChunkGeneration(icommandsender, 0, x, z, radius, dimensionID, logToChat));
				}
				break;
			case "stop":
				Reference.toGenerate.clear();
				break;
			default:
				getCommandUsage(icommandsender);
				break;
			}
		}
	}

	@Override
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args,BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, "zone", "radius", "stop") : null;
	}

}
