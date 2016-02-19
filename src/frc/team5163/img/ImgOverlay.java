/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.team5163.img;

import frc.team5163.net.NetTableInterface;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/**
 *
 * @author Rish Shadra <rshadra@gmail.com>
 */
public class ImgOverlay {

    private static int customInitX, customInitY;

    private static boolean custom = false;
    private static BufferedImage latestFrame;

    public static BufferedImage paintOverlay(BufferedImage im) {

        NetTableInterface net = new NetTableInterface();

        boolean lock = net.getNumber("targets", "targetX") > 0;
        BufferedImage copy = cloneBufferedImage(im);
        Graphics2D image = copy.createGraphics();
        Stroke orig = image.getStroke();
        image.setStroke(new BasicStroke(3));

        double[] ContourXValues = net.getNumberArray("GRIP/contours", "centerX");
        double[] ContourYValues = net.getNumberArray("GRIP/contours", "centerY");

        for (int i = 0; i < ContourXValues.length; i++) {
            image.drawOval((int) ContourXValues[i], (int) ContourYValues[i], 10, 10);
        }

        if (lock) {
            image.setColor(Color.green);
            image.drawOval((int) net.getNumber("targets", "targetX"), (int) net.getNumber("targets", "targetY"), 10, 10);
        }

        image.setStroke(orig);

        if (custom) {
            image.setColor(Color.yellow);
            image.drawLine(customInitX - 5, customInitY - 5, customInitX + 5, customInitY + 5);
            image.drawLine(customInitX - 5, customInitY + 5, customInitX + 5, customInitY - 5);
        }

        image.setColor(Color.red);

        image.drawLine(320, 235, 320, 245);
        image.drawLine(315, 240, 325, 240);

        image.dispose();

        return copy;
    }

    public static BufferedImage repaintOverlay() {
        return paintOverlay(latestFrame);
    }

    public static void setLatestRawFrame(BufferedImage i) {
        latestFrame = cloneBufferedImage(i);
    }

    public static void setCustom(int x, int y) {
        custom = true;
        customInitX = x;
        customInitY = y;
    }

    public static void clearCustom() {
        custom = false;
    }

    static BufferedImage cloneBufferedImage(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

}
