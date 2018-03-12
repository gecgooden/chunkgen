package com.gecgooden.chunkgen.reference;

import com.gecgooden.chunkgen.util.ChunkPosition;
import org.apache.logging.log4j.Logger;

import java.text.DecimalFormat;
import java.util.LinkedList;

public class Reference {
    public static Integer x;
    public static Integer z;
    public static Integer depth;
    public static Integer width;
    public static Integer dimension;
    public static double numChunksPerTick;
    public static boolean pauseForPlayers;
    public static Integer maxChunksLoaded;

    public static final String MOD_ID = "chunkgen";
    public static final String VERSION = "1.3";
    public static final String GUI_FACTORY = "com.gecgooden.chunkgen.client.gui.GuiFactory";

    public static LinkedList<ChunkPosition> toGenerate = new LinkedList<>();
    public static int startingSize;
    public static int updateDelay;

    public static DecimalFormat decimalFormat;

    public static Logger logger;
    public static int skipChunks;
}
