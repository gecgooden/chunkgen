package com.gecgooden.chunkgen.commands;

import java.util.List;

import com.gecgooden.chunkgen.reference.Reference;
import com.gecgooden.chunkgen.util.Utilities;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class ChunkGenCommand extends CommandBase {

    public static final String SUB_CMD_HELP = "help";
    public static final String SUB_CMD_ZONE = "zone";
    public static final String SUB_CMD_RADIUS = "radius";
    public static final String SUB_CMD_STOP = "stop";

    public ChunkGenCommand() {

    }

    @Override
    public String getName() {
        return "chunkgen";
    }

    @Override
    public String getUsage(ICommandSender icommandsender) {
        return "commands.chunkgen.usage";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender icommandsender, String[] astring) throws CommandException {
        if (astring.length == 0 || astring[0].equalsIgnoreCase(SUB_CMD_HELP)) {
            notifyCommandListener(icommandsender, this, "commands.chunkgen.usage");
        } else {
            switch (astring[0]) {
                case SUB_CMD_ZONE:
                    if (astring.length == 1) {
                        notifyCommandListener(icommandsender, this, "commands.chunkgen.usage.zone");
                    } else {
                        int x = (int) parseCoordinate(icommandsender.getPosition().getX() >> 4, astring[1], false).getResult();
                        int z = (int) parseCoordinate(icommandsender.getPosition().getZ() >> 4, astring[2], false).getResult();
                        int depth = parseInt(astring[3]);
                        int width = parseInt(astring[4]);
                        int dimensionID = icommandsender.getEntityWorld().provider.getDimension();
                        boolean logToChat = false;
                        if (astring.length > 5 && !"~".equals(astring[5])) {
                            dimensionID = parseInt(astring[5]);
                        }
                        if (astring.length > 6) {
                            logToChat = parseBoolean(astring[6]);
                        }
                        notifyCommandListener(icommandsender, this, "commands.chunkgen.enqueued", Utilities.queueChunkGeneration(icommandsender, 0, x, z, width,depth, dimensionID, logToChat));
                    }
                    break;
                case SUB_CMD_RADIUS:
                    if (astring.length == 1) {
                        notifyCommandListener(icommandsender, this, "commands.chunkgen.usage.radius");
                    } else {
                        int x = (int) parseCoordinate(icommandsender.getPosition().getX() >> 4, astring[1], false).getResult();
                        int z = (int) parseCoordinate(icommandsender.getPosition().getZ() >> 4, astring[2], false).getResult();
                        int radius = parseInt(astring[3]);
                        int dimensionID = icommandsender.getEntityWorld().provider.getDimension();
                        boolean logToChat = false;
                        if (astring.length > 4 && !"~".equals(astring[4])) {
                            dimensionID = parseInt(astring[4]);
                        }
                        if (astring.length > 5) {
                            logToChat = parseBoolean(astring[5]);
                        }
                        notifyCommandListener(icommandsender, this, "commands.chunkgen.enqueued", Utilities.queueChunkGeneration(icommandsender, 0, x, z, radius, dimensionID, logToChat));
                    }
                    break;
                case SUB_CMD_STOP:
                    Reference.toGenerate.clear();
                    icommandsender.sendMessage(new TextComponentTranslation("commands.chunkgen.stopped"));
                    break;
            }
        }
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
        switch (args.length) {
            case 1:
                return getListOfStringsMatchingLastWord(args, SUB_CMD_HELP, SUB_CMD_ZONE, SUB_CMD_RADIUS, SUB_CMD_STOP);
            case 2:
                switch (args[0]) {
                    case SUB_CMD_ZONE:
                    case SUB_CMD_RADIUS:
                        return getListOfStringsMatchingLastWord(args, Reference.x + "", "~", "<x>");
                }
            case 3:
                switch (args[0]) {
                    case SUB_CMD_ZONE:
                    case SUB_CMD_RADIUS:
                        return getListOfStringsMatchingLastWord(args, Reference.z + "", "~", "<z>");
                }
            case 4:
                switch (args[0]) {
                    case SUB_CMD_ZONE:
                        return getListOfStringsMatchingLastWord(args, Reference.width + "", "<xSize>");
                    case SUB_CMD_RADIUS:
                        return getListOfStringsMatchingLastWord(args, Reference.startingSize + "", "<radius>");
                }
            case 5:
                switch (args[0]) {
                    case SUB_CMD_ZONE:
                        return getListOfStringsMatchingLastWord(args, Reference.depth + "", "<zSize>");
                    case SUB_CMD_RADIUS:
                        return getListOfStringsMatchingLastWord(args, "0", "~", "[dimension]");
                }
            case 6:
                switch (args[0]) {
                    case SUB_CMD_ZONE:
                        return getListOfStringsMatchingLastWord(args, sender.getEntityWorld().provider.getDimension() + "", "[dimension]");
                    case SUB_CMD_RADIUS:
                        return getListOfStringsMatchingLastWord(args, true + "", false + "");
                }
            case 7:
                switch (args[0]) {
                    case SUB_CMD_ZONE:
                        return getListOfStringsMatchingLastWord(args, true + "", false + "");
                }
        }
        return null;
    }

}