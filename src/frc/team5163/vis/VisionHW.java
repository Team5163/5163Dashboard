/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.team5163.vis;

import frc.team5163.vis.provider.VisionProvider;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import frc.team5163.Dashboard;
import static frc.team5163.Dashboard.NETWORK_TABLE_SERVER_IP;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rish Shadra <rshadra@gmail.com>
 */
public class VisionHW extends VisionProvider {

    private final double[] defaultArray = {-1.0};
    private NetworkTable contours;
    private NetworkTable outputTable;

    private double targetX;
    private double targetY;

    public VisionHW() {
        NetworkTable.setClientMode();
        NetworkTable.setIPAddress(NETWORK_TABLE_SERVER_IP);
        contours = NetworkTable.getTable("GRIP/contours");
        outputTable = NetworkTable.getTable("targets");
    }

    @Override
    public void process() {
        
        //System.out.println("H/W VISION EXEC");

        double[] contourCenterXValues = contours.getNumberArray("centerX", defaultArray);
        double[] contourCenterYValues = contours.getNumberArray("centerY", defaultArray);
        double[] contourHeights = contours.getNumberArray("height", defaultArray);
        double[] contourWidths = contours.getNumberArray("width", defaultArray);

        double targetRatio = 0.6;
        double leastRatioDiff = Double.MAX_VALUE;
        double closestRatio = -1;

        double targetWidth;
        double targetHeight;

        int bestContour = -1;

        for (int i = 0; i < contourHeights.length; i++) {

            double ratio = (contourHeights[i] / contourWidths[i]);
            double ratiodiff = Math.abs(ratio - targetRatio);

            //System.out.println("Contour " + i + " has a H/W ratio of " + ratio);
            if (ratiodiff < leastRatioDiff) {
                leastRatioDiff = ratiodiff;
                closestRatio = ratio;
                bestContour = i;
            }

        }

        if (closestRatio < 0.4 || closestRatio > 0.8) {
            bestContour = -1;
            //System.out.println("NO SUITABLE TARGET");
        }

        if (bestContour > -1) {
            targetX = contourCenterXValues[bestContour];
            targetY = contourCenterYValues[bestContour];
            targetWidth = contourWidths[bestContour];
            targetHeight = contourHeights[bestContour];
        } else {
            targetX = -1;
            targetY = -1;
            targetWidth = -1;
            targetHeight = -1;
        }

        outputTable.putNumber("targetX", targetX);
        outputTable.putNumber("targetY", targetY);
        outputTable.putNumber("targetWidth", targetWidth);
        outputTable.putNumber("targetHeight", targetHeight);

        updateWindow();

        //System.out.println("Best contour: " + bestContour);
        //System.out.println("---");
    }

    public void updateWindow() {
        Dashboard.getTargetInfoWindow().update(true, (int) targetX, (int) targetY, 0);
    }

}
