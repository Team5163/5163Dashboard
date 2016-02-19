/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.team5163.vis;

import frc.team5163.Dashboard;
import frc.team5163.net.NetTableInterface;
import frc.team5163.vis.provider.VisionProvider;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rish Shadra <rshadra@gmail.com>
 */
public class VisionDisabled extends VisionProvider {

    @Override
    public void process() {
        
        NetTableInterface net = new NetTableInterface();

        net.putNumber("targets", "targetX", -1);
        net.putNumber("targets", "targetY", -1);
        net.putNumber("targets", "targetWidth", -1);
        net.putNumber("targets", "targetHeight", -1);

        updateWindow(-1, -1);

    }

    public void updateWindow(int x, int y) {
        Dashboard.getTargetInfoWindow().update(true, (int) x, (int) y, 0);
    }
}
