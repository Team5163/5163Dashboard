/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.team5163.net;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import static frc.team5163.Dashboard.NETWORK_TABLE_SERVER_IP;

/**
 *
 * @author Rish Shadra <rshadra@gmail.com>
 */
public class NetTableInterface {
    
    private final double[] DEFAULT_TABLE = {-1.0};
    
    public NetTableInterface() {
        NetworkTable.setClientMode();
        NetworkTable.setIPAddress(NETWORK_TABLE_SERVER_IP);
    }
    
    public double getNumber(String table, String key) {
        NetworkTable n = NetworkTable.getTable(table);
        return n.getNumber(key, -2.0);
    }
    
    public void putNumber(String table, String key, double d) {
        NetworkTable n = NetworkTable.getTable(table);
        n.putNumber(key, d);
    }
    
    public double[] getNumberArray(String table, String key) {
        NetworkTable n = NetworkTable.getTable(table);
        return n.getNumberArray(key, DEFAULT_TABLE);
    }
}
