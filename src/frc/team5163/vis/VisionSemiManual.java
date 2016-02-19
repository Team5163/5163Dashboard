/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.team5163.vis;

import frc.team5163.vis.provider.VisionProvider;
import frc.team5163.Dashboard;
import frc.team5163.img.ImgOverlay;
import frc.team5163.net.NetTableInterface;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rish Shadra <rshadra@gmail.com>
 */
public class VisionSemiManual extends VisionProvider {

    private int customInitX, customInitY;
    private final int customValidRange = 50;

    public VisionSemiManual(int x, int y) {
        super();
        customInitX = x;
        customInitY = y;
        ImgOverlay.setCustom(x, y);
    }

    @Override
    public void exec() {

        try {
            Thread.sleep(150);
        } catch (InterruptedException ex) {
            Logger.getLogger(VisionHW.class.getName()).log(Level.SEVERE, null, ex);
        }

        //System.out.println("MANUAL VISION EXEC");

        NetTableInterface net = new NetTableInterface();

        double[] ContourXValues = net.getNumberArray("GRIP/contours", "centerX");
        double[] ContourYValues = net.getNumberArray("GRIP/contours", "centerY");
        double[] ContourHeights = net.getNumberArray("GRIP/contours", "height");
        double[] ContourWidths = net.getNumberArray("GRIP/contours", "width");

        int targetX = -1, targetY = -1, targetWidth = -1, targetHeight = -1;

        int closestCustom = 0;
        int minDistance = Integer.MAX_VALUE;

        for (int i = 0; i < ContourXValues.length; i++) {
            int distance = (int) Math.sqrt((int) Math.pow(ContourXValues[i] - customInitX, 2) + (int) Math.pow(ContourYValues[i] - customInitY, 2));
            if (distance < minDistance) {
                //System.out.println("Contour " + i + " has distance " + distance);
                minDistance = distance;
                closestCustom = i;
            }
        }

        if (minDistance < customValidRange || 1 == 1) {

            customInitX = (int) ContourXValues[closestCustom];
            customInitY = (int) ContourYValues[closestCustom];
            
            ImgOverlay.setCustom(customInitX, customInitY);
            
            try {
                targetX = (int) ContourXValues[closestCustom];
                targetY = (int) ContourYValues[closestCustom];
                targetWidth = (int) ContourWidths[closestCustom];
                targetHeight = (int) ContourHeights[closestCustom];
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Custom target not found.");
                ImgOverlay.clearCustom();
                Dashboard.getTargetInfoWindow().setHW();
            }

        } else {
            System.out.println("Custom target too far. Reverting to H/W. Distance: " + minDistance);
            ImgOverlay.clearCustom();
            Dashboard.getTargetInfoWindow().setHW();
        }

        net.putNumber("targets", "targetX", targetX);
        net.putNumber("targets", "targetY", targetY);
        net.putNumber("targets", "targetWidth", targetWidth);
        net.putNumber("targets", "targetHeight", targetHeight);
        
        updateWindow(targetX, targetY);

    }
    
    public void updateWindow(int x, int y) {
        Dashboard.getTargetInfoWindow().update(true, (int) x, (int) y, 0);
    }

}
