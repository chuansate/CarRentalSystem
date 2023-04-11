/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



/**
 *
 * @author User
 */
public class DisplayHist extends JFrame{
    private HistPanel panel;
    private int[] revenues;
    public DisplayHist(int[] revenues, String year){
        this.revenues = revenues;
        int max_rev = 0;
        for (int rev:revenues){
            if (rev > max_rev){
                max_rev = rev;
            }
        }
        setSize(600, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("Histogram of " + year);
        panel = new HistPanel(max_rev, this.revenues);
        add(panel);
    }
}

class HistPanel extends JPanel{
    private JLabel x_lb;
    private JLabel y_lb;
    private JLabel jan;
    private JLabel feb;
    private JLabel mar;
    private JLabel apr;
    private JLabel may;
    private JLabel jun;
    private JLabel jul;
    private JLabel aug;
    private JLabel sep;
    private JLabel oct;
    private JLabel nov;
    private JLabel dec;
    private int[] y_bars;
    private int max_revenue;
    HistPanel(int max_revenue, int[] revenues) {
        this.max_revenue = max_revenue;
        y_bars = new int[12];
        for (int i=0; i<12; i++){
            y_bars[i] = (revenues[i] * 400)/max_revenue;
        } 
        x_lb = new JLabel("Revenue(RM)");
        x_lb.setBounds(25, 25, 100, 25);
        y_lb = new JLabel("Month");
        y_lb.setBounds(520, 490, 50, 25);
        jan = new JLabel("Jan");
        jan.setBounds(80, 510, 30, 25);
        feb = new JLabel("Feb");
        feb.setBounds(110, 510, 30, 25);
        mar = new JLabel("Mar");
        mar.setBounds(140, 510, 30, 25);
        apr = new JLabel("Apr");
        apr.setBounds(170, 510, 30, 25);
        may = new JLabel("May");
        may.setBounds(200, 510, 30, 25);
        jun = new JLabel("Jun");
        jun.setBounds(230, 510, 30, 25);
        jul = new JLabel("Jul");
        jul.setBounds(260, 510, 30, 25);
        aug = new JLabel("Aug");
        aug.setBounds(290, 510, 30, 25);
        sep = new JLabel("Sep");
        sep.setBounds(320, 510, 30, 25);
        oct = new JLabel("Oct");
        oct.setBounds(350, 510, 30, 25);
        nov = new JLabel("Nov");
        nov.setBounds(380, 510, 30, 25);
        dec = new JLabel("Dec");
        dec.setBounds(410, 510, 30, 25);
        setLayout(null);
        add(x_lb); add(y_lb);add(jan); add(feb);
        add(feb); add(mar);add(apr); add(may);add(jun); add(jul);add(aug);add(sep);add(oct);add(nov);add(dec);
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.drawLine(50, 500, 500, 500);  //x-axis
        g.drawLine(50,50,50, 500); //y-axis
        int x_initial = 80;
        for (int y_bar:y_bars){
            g.drawRect(x_initial, 500-y_bar, 30, y_bar);
            x_initial = x_initial + 30;
        }
        int height_lb = 460;
        for (int i=0; i<10; i++){
            g.drawLine(40, height_lb, 60, height_lb);
            
            height_lb = height_lb - 40;
        }
        int y_axis_callibration = 0;
        height_lb = 500;
        for (int i=0; i<11; i++){
            g.drawString(Integer.toString(y_axis_callibration), 10, height_lb);
            height_lb = height_lb - 40;
            y_axis_callibration = y_axis_callibration + (max_revenue/10);
        } 
    }
}
