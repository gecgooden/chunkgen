package com.gecgooden.chunkgen.util;

import net.minecraft.command.ICommandSender;

import java.util.Comparator;

public class ChunkPosition {

    private int x;
    private int z;
    private int dimensionID;
    private ICommandSender iCommandSender;
    private boolean logToChat;

    public ChunkPosition(int x, int z, int dimensionID, ICommandSender icommandsender, boolean logToChat) {
        this.x = x;
        this.z = z;
        this.dimensionID = dimensionID;
        this.iCommandSender = icommandsender;
        this.logToChat = logToChat;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getDimensionID() {
        return dimensionID;
    }

    public void setDimensionID(int dimensionID) {
        this.dimensionID = dimensionID;
    }

    public ICommandSender getICommandSender() {
        return iCommandSender;
    }

    public void setICommandSender(ICommandSender iCommandSender) {
        this.iCommandSender = iCommandSender;
    }

    public boolean logToChat() {
        return logToChat;
    }

    public void setLogToChat(boolean logToChat) {
        this.logToChat = logToChat;
    }


    /**
     * Creates a comparator that compares points by the angle that the line
     * between the given center and the point has to the x-axis.
     *
     * @param center The center
     * @return The comparator
     */
    public static Comparator<ChunkPosition> byAngleComparator(ChunkPosition center) {
        final int centerX = center.getX();
        final int centerY = center.getZ();
        return new Comparator<ChunkPosition>() {
            @Override
            public int compare(ChunkPosition p0, ChunkPosition p1) {
                float angle0 = angleToX(centerX, centerY, p0.getX(), p0.getZ());
                float angle1 = angleToX(centerX, centerY, p1.getX(), p1.getZ());
                return Float.compare(angle0, angle1);
            }
        };
    }

    /**
     * Computes the angle, in radians, that the line from (x0,y0) to (x1,y1)
     * has to the x axis
     *
     * @param x0 The x-coordinate of the start point of the line
     * @param y0 The y-coordinate of the start point of the line
     * @param x1 The x-coordinate of the end point of the line
     * @param y1 The y-coordinate of the end point of the line
     * @return The angle, in radians, that the line has to the x-axis
     */
    private static float angleToX(float x0, float y0, float x1, float y1) {
        float dx = x1 - x0;
        float dy = y1 - y0;
        return (float) Math.atan2(dy, dx);
    }

    @Override
    public String toString() {
        return "ChunkPosition{" +
                "x:" + x +
                ", z:" + z +
                ", dimensionID:" + dimensionID +
                '}';
    }
}
