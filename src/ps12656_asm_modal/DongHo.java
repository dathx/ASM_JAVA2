/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps12656_asm_modal;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

/**
 *
 * @author DMX
 */
public class DongHo extends Thread{
    JLabel lb;

    public DongHo(JLabel lb) {
        this.lb = lb;
    }

    @Override
    public void run() {
        super.run();
               while (true) {
            try {
                Date a = new Date();
                SimpleDateFormat fm = new SimpleDateFormat();
                fm.applyPattern("hh:mm aa");
                String time = fm.format(a);
                lb.setText(time);
                Thread.sleep(6000);
            } catch (Exception e) {
                break;
            }
        }
    }
    
}
