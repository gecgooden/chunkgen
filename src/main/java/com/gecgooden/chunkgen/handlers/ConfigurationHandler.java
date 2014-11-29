package com.gecgooden.chunkgen.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.gecgooden.chunkgen.reference.Reference;

import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
		Reference.numChunksPerTick= configuration.get(Configuration.CATEGORY_GENERAL, "numChunksPerTick", 1, "Number of chunks loaded per tick").getInt();
		
		if(configuration.hasChanged()) {
			configuration.save();
		}
	}
}
