/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author User
 */

public class AdminHomePage implements ActionListener{
    protected String welcome_msg = "Welcome back, ";
    protected String log_out = "Log Out";
    protected String log_out_warning = "Are you sure you want to log out?";  
    protected JLabel uName_label;
    protected JPanel panel;
    protected JFrame frame;
    protected JButton logout_but;
    protected final int WINDOW_WIDTH = 600;
    protected final int WINDOW_HEIGHT = 600;
    protected JButton but1;//create
    protected JButton but2;//read
    protected JButton but3;//update
    protected JButton but4;//delete
    protected JButton but5;//generate
    protected JButton but6;//make announcements
    protected JButton but7;//apply discount for cars
    AdminHomePage(String uName) {
        uName_label = new JLabel(welcome_msg + uName + "!");
        uName_label.setBounds(100, 50, 400, 35);
        uName_label.setHorizontalAlignment(SwingConstants.CENTER);
        uName_label.setVerticalAlignment(SwingConstants.CENTER);
        uName_label.setFont(new Font(null, Font.ITALIC, 20));
        logout_but = new JButton();
        logout_but.setBounds(450, 500,100,50);
        logout_but.setFocusable(false);
        logout_but.setText(log_out);
        logout_but.addActionListener(this);
        but1 = new JButton("Create New Car");
        but1.setBounds(100, 100, 400,25);
        but1.addActionListener(this);
        but1.setFocusable(false);
        
        but2 = new JButton("Search for Car");
        but2.setBounds(100, 150, 400, 25);
        but2.setFocusable(false);
        but2.addActionListener(this);
        
        but3 = new JButton("Update Car Information");
        but3.setBounds(100, 200, 400, 25);
        but3.setFocusable(false);
        but3.addActionListener(this);
        
        but4 = new JButton("Delete Car(s)");
        but4.setBounds(100, 250, 400, 25);
        but4.setFocusable(false);
        but4.addActionListener(this);
        
        but5 = new JButton("Generate Analysed Report");
        but5.setBounds(100, 300, 400, 25);
        but5.setFocusable(false);
        but5.addActionListener(this);
        
        but6 = new JButton("Make Announcement");
        but6.setBounds(100, 350, 400, 25);
        but6.setFocusable(false);
        but6.addActionListener(this);
        
        but7 = new JButton("Apply Discount");
        but7.setBounds(100, 400, 400, 25);
        but7.setFocusable(false);
        but7.addActionListener(this);
        
        panel = new JPanel();
        panel.setBounds(0, 0, WINDOW_HEIGHT, WINDOW_WIDTH);
        panel.add(uName_label); panel.add(logout_but); panel.add(but1); panel.add(but2);panel.add(but3);panel.add(but4);panel.add(but5);panel.add(but6);panel.add(but7);
        panel.setVisible(true);
        panel.setLayout(null);
        
        frame = new JFrame();
        frame.setTitle("Admin Home Page");
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    @Override 
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == logout_but){
            int res = JOptionPane.showOptionDialog(null, log_out_warning, "Log out Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (res == JOptionPane.YES_OPTION){
                LoginPage login_page = new LoginPage();
                frame.dispose();    
            }
        } else if(e.getSource() == but1){
            CreateNewCarPage createPage = new CreateNewCarPage();
            
        } else if (e.getSource() == but2){
            ReadCarPage readPage = new ReadCarPage();
        } else if(e.getSource() == but3){
            UpdateCarPage updatePage = new UpdateCarPage();
        }else if (e.getSource() == but4) {
            DeleteCarPage deletePage = new DeleteCarPage();
        }else if (e.getSource() == but5){
            GenerateReportPage grPage = new GenerateReportPage();
        }else if(e.getSource() == but6){
            Announcement ann = new Announcement();
        } else if(e.getSource() == but7){
            ApplyDiscountPage disPage = new ApplyDiscountPage();
        }
    }
}



class CusHomePage extends AdminHomePage{ 
    private String uName;
    CusHomePage(String uName){
        super(uName);
        this.uName = uName;
        
        Component[] componentList = panel.getComponents();
        for (Component c: componentList){
            if (c.equals(but5) ){
                panel.remove(c);
            } else if (c.equals(but6)){
                panel.remove(c);
            } else if (c.equals(but7)){
                panel.remove(c);
            }
        }
        
        but1.setText("Book a Car");
        but2.setText("Check Booking History");
        but3.setText("Mailbox");
        but4.setText("Cancel Booking");
        frame.setTitle("Customer Home Page");
    }
    @Override 
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == logout_but){
            int res = JOptionPane.showOptionDialog(null, log_out_warning, "Log out Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (res == JOptionPane.YES_OPTION){
                LoginPage login_page = new LoginPage();
                frame.dispose();
                
            }
        } else if(e.getSource() == but1){
            BookingPage bookPage = new BookingPage(uName);
        } else if(e.getSource() == but2){
            CheckBookingHis cbhis = new CheckBookingHis(uName);
        } else if(e.getSource() == but3){
            Mailbox mailb = new Mailbox(uName);
        } else if(e.getSource() == but4){
            CancelBookingPage cbp = new CancelBookingPage(uName);
        }
    }
}
