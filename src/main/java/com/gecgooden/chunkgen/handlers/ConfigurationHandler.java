package com.gecgooden.chunkgen.handlers;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.gecgooden.chunkgen.reference.Reference;

import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

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
		Reference.numChunksPerTick = configuration.get(Configuration.CATEGORY_GENERAL, "numChunksPerTick", 1.0, "Number of chunks loaded per tick").getDouble();
		Reference.tickDelay = configuration.get(Configuration.CATEGORY_GENERAL, "tickDelay", 40, "Number of ticks inbetween percentage updates").getInt();
		
		if(configuration.hasChanged()) {
			configuration.save();
		}
	}
}
