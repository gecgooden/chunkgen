package com.gecgooden.chunkgen;

import com.gecgooden.chunkgen.commands.ChunkGenCommand;
import com.gecgooden.chunkgen.handlers.ConfigurationHandler;
import com.gecgooden.chunkgen.handlers.TickHandler;
import com.gecgooden.chunkgen.reference.Reference;
import com.gecgooden.chunkgen.util.Utilities;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;

import java.text.DecimalFormat;
import java.util.Map;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, guiFactory=Reference.GUI_FACTORY, acceptableRemoteVersions = "*")
public class ChunkGen
{

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Reference.logger = LogManager.getLogger(Reference.MOD_ID);

		Reference.decimalFormat = new DecimalFormat();
		Reference.decimalFormat.setMaximumFractionDigits(2);
		
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
		MinecraftForge.EVENT_BUS.register(new TickHandler());
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new ChunkGenCommand());
		if(Reference.x != null && Reference.z != null && Reference.height != null && Reference.width != null && Reference.height > 0 && Reference.width > 0) {
			Utilities.queueChunkGeneration(event.getServer(), Reference.skipChunks, Reference.x, Reference.z, Reference.height, Reference.width, 0, false);
		}
	}
}
