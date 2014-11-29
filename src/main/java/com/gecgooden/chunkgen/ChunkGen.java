package com.gecgooden.chunkgen;

import java.util.Map;

import com.gecgooden.chunkgen.commands.ChunkGenCommand;
import com.gecgooden.chunkgen.handlers.ConfigurationHandler;
import com.gecgooden.chunkgen.reference.Reference;
import com.gecgooden.chunkgen.tick.TickHandler;
import com.gecgooden.chunkgen.util.Utilities;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, guiFactory=Reference.GUI_FACTORY)
public class ChunkGen
{
    /**
     * Makes the mod server side only
     * @param map
     * @param side
     * @return Always true.
     */
    @NetworkCheckHandler
    public boolean networkCheckHandler(Map<String, String> map, Side side) { 
    	return true;
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigurationHandler());
		FMLCommonHandler.instance().bus().register(new TickHandler());
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
      event.registerServerCommand(new ChunkGenCommand());
      if(Reference.x != null && Reference.z != null && Reference.height != null && Reference.width != null && Reference.height > 0 && Reference.width > 0) {
    	  Utilities.generateChunks(Reference.x, Reference.z, Reference.width, Reference.height, 0);
      }
    }
}
