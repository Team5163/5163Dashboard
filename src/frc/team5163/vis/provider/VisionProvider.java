/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frc.team5163.vis.provider;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rish Shadra <rshadra@gmail.com>
 */
public abstract class VisionProvider {
    
    public void exec() {
        preExec();
        process();
        postExec();
    }
    
    public void preExec() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException ex) {
            Logger.getLogger(VisionProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void postExec() {
        
    }
    
    public abstract void process();
}
