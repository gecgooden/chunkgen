package com.gecgooden.chunkgen.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.gecgooden.chunkgen.reference.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Property;

public class ConfigurationHandler {
	
	public static Configuration configuration;
		
	public static void init(File configFile) {
		if(configuration == null) { 
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	@SubscribeEvent
	public void onConfigurationChangedEvent(OnConfigChangedEvent event) {
		if(event.modID.equalsIgnoreCase("chunkgen")) {
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		Reference.x = configuration.get(Configuration.CATEGORY_GENERAL, "x", 0, "X starting value").getInt();
		Reference.z = configuration.get(Configuration.CATEGORY_GENERAL, "z", 0, "Z starting value").getInt();
		Reference.height = configuration.get(Configuration.CATEGORY_GENERAL, "height", 0, "Height starting value").getInt();
		Reference.width = configuration.get(Configuration.CATEGORY_GENERAL, "width", 0, "Width starting value").getInt();
		Reference.pauseForPlayers = configuration.get(Configuration.CATEGORY_GENERAL, "pauseForPlayers", true, "Pause chunk generation when players are logged on").getBoolean();
		Reference.maxChunksLoaded = configuration.get(Configuration.CATEGORY_GENERAL, "maxChunksLoaded", 3000, "Pause chunk generation if more chunks than this are in memory").getInt();
		Reference.numChunksPerTick = configuration.get(Configuration.CATEGORY_GENERAL, "numChunksPerTick", 1.0, "Number of chunks loaded per tick").getDouble();
		Reference.updateDelay = configuration.get(Configuration.CATEGORY_GENERAL, "updateDelay", 40, "Number of chunks inbetween percentage updates").getInt();
		Reference.skipChunks = getSkipChunks().getInt();
		
		if(configuration.hasChanged()) {
			configuration.save();
		}
	}

	public static void UpdateSkipChunks() {
		getSkipChunks().set(Reference.skipChunks);

		configuration.save();
	}

	private static Property getSkipChunks() {
		return configuration.get(Configuration.CATEGORY_GENERAL, "skipChunks", 0, "Skip a number of chunks at start of generation. Set automatically.");
	}
}
