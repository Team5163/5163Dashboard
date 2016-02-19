/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.team5163;

import frc.team5163.net.WebcamViewer;
import frc.team5163.gui.DriveParamsWindow;
import frc.team5163.gui.CameraWindow;
import java.io.IOException;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import frc.team5163.gui.TargetInfoWindow;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rish Shadra <rshadra@gmail.com>
 */
public class Dashboard implements Runnable {

    public static final String NETWORK_TABLE_SERVER_IP = "127.0.0.1";
    // Possible values:
    // 127.0.0.1
    // 10.51.63.106
    
    private static final CameraWindow c = new CameraWindow();
    private static final DriveParamsWindow d = new DriveParamsWindow();
    private static final TargetInfoWindow t = new TargetInfoWindow();

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //d.setVisible(true);
        c.setVisible(true);
        t.setVisible(true);
        WebcamViewer v = new WebcamViewer();
        v.init();
        
        NetworkTable.setClientMode();
        NetworkTable.setIPAddress(NETWORK_TABLE_SERVER_IP);
        
        //Thread t = new Thread(new Dashboard());
        //t.start();
        
    }

    public static CameraWindow getCameraWindow() {
        return c;
    }
    
    public static DriveParamsWindow getDriveParamsWindow() {
        return d;
    }
    
    public static TargetInfoWindow getTargetInfoWindow() {
        return t;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            NetworkTable.getTable("driveparams").putNumber("accelrate", Double.parseDouble(d.accelRate.getText()));
            NetworkTable.getTable("driveparams").putNumber("decayrate", Double.parseDouble(d.decayRate.getText()));
            NetworkTable.getTable("driveparams").putNumber("brakedecay", Double.parseDouble(d.brakeRate.getText()));
        }
    }

}
