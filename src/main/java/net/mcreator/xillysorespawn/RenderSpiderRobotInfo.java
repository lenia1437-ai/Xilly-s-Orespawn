package net.mcreator.xillysorespawn.entity.renderer;

/** Client-side leg solver state used by the original Spider Robot model. */
public final class RenderSpiderRobotInfo {
    public final float[] ydisplayangle = new float[8];
    public final float[] ywantedangle = new float[8];
    public final float[] ycurrentangle = new float[8];
    public final float[] yvelocity = new float[8];
    public final float[] ymid = new float[8];
    public final float[] yoff = new float[8];
    public final float[] yrange = new float[8];
    public final float[] uddisplayangle = new float[8];
    public final float[] udwantedangle = new float[8];
    public final float[] udcurrentangle = new float[8];
    public final float[] udvelocity = new float[8];
    public final double[] p1xangle = new double[8];
    public final double[] p2xangle = new double[8];
    public final double[] p3xangle = new double[8];
    public final float[] pxvelocity = new float[8];
    public final float[] foot_xpos = new float[8];
    public final float[] foot_ypos = new float[8];
    public final float[] foot_zpos = new float[8];
    public final float[] legoff = new float[8];
    public final float[] realposx = new float[8];
    public final float[] realposy = new float[8];
    public final float[] realposz = new float[8];
    public final int[] footup = new int[8];
    public final float[] uppoint = new float[8];
    public final int[] footingticker = new int[8];
    public final int[] pairedwith = new int[8];
    public int gpcounter;
}
