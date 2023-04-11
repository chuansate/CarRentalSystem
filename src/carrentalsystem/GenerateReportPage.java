/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
/**
 *
 * @author User
 */
public class GenerateReportPage implements ActionListener {
    private JFrame frame;
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
    private JLabel title;
    private JLabel year_lb;
    private JTextField year_tf;
    private JButton generate_but;
    private JLabel report_title;
    private DisplayHist hist;
    private JLabel total_rev;
    private JLabel total_num_book;
    private JLabel modal_month;
    private JLabel avg_rev;
    private JPanel panel;
    GenerateReportPage() {
        title = new JLabel("Select a year to generate the corresponding report:");
        title.setBounds(50, 50, 300, 25);
        year_tf = new JTextField();
        year_tf.setBounds(350, 50, 100, 25);
        
        generate_but = new JButton("Generate");
        generate_but.setBounds(480, 50, 100, 25);
        generate_but.addActionListener(this);
        
        report_title = new JLabel();
        report_title.setBounds(250, 90, 400, 35);
        report_title.setHorizontalAlignment(SwingConstants.CENTER);
        report_title.setVerticalAlignment(SwingConstants.CENTER); 
        total_rev = new JLabel();
        total_num_book = new JLabel();
        total_rev.setBounds(250, 140, 400, 25);
        total_rev.setHorizontalAlignment(SwingConstants.CENTER);
        total_rev.setVerticalAlignment(SwingConstants.CENTER); 
        total_num_book.setBounds(250, 180, 400, 25);
        total_num_book.setHorizontalAlignment(SwingConstants.CENTER);
        total_num_book.setVerticalAlignment(SwingConstants.CENTER); 
        modal_month = new JLabel();
        modal_month.setBounds(250, 220, 400, 25);
        modal_month.setHorizontalAlignment(SwingConstants.CENTER);
        modal_month.setVerticalAlignment(SwingConstants.CENTER); 
        avg_rev = new JLabel();
        avg_rev.setBounds(250, 260, 400, 25);
        avg_rev.setHorizontalAlignment(SwingConstants.CENTER);
        avg_rev.setVerticalAlignment(SwingConstants.CENTER); 
        panel = new JPanel();
        panel.setBounds(0, 0, 900, 900);
        panel.setLayout(null);
        panel.setVisible(true);
        panel.add(title);panel.add(year_tf);panel.add(generate_but);panel.add(report_title);panel.add(total_rev);panel.add(total_num_book);panel.add(modal_month);
        panel.add(avg_rev);
        frame = new JFrame();
        frame.setTitle("Analysed Report");
        frame.add(panel);
       
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900,900);
        frame.setLayout(null);
        frame.setVisible(true);
        
        
    }
    
    // Display the statistics about revenues in specific year, then instantiate `DisplayHist` to create histogram to visualize the data.
    private void getStatistics(String year) throws IOException{
        int[] revenues;
        revenues = new int[12];
        Arrays.fill(revenues, 0);
        int[] num_bookings;
        num_bookings = new int[12];
        Arrays.fill(num_bookings, 0);
        BufferedReader input;
        String[] attributes;
        String record;
        String filePath;
        filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("//data//Admin//Bookings.txt");
        input = new BufferedReader(new FileReader(filePath));
        if (input == null){
            throw new IOException();
        }
        boolean found = false;
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov" ,"Dec"};
        while ((record = input.readLine()) != null){
            attributes = record.split(",", 9);
            if (attributes[6].substring(24, 28).equals(year)){
                for (int i=0; i<months.length; i++){
                    if (attributes[6].substring(4, 7).equals(months[i])){
                        revenues[i] = revenues[i] + Integer.parseInt(attributes[5]);
                        num_bookings[i]++;
                        break;
                    }
                    
                }
                found = true;
            }
        }
        input.close();
        
        if (!found){
            JOptionPane.showMessageDialog(null, "No annual report for " + year + "!", "Record not found!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        report_title.setText("Annual Report " + year);
        report_title.setFont(new Font("Arial", Font.ITALIC, 20));
        Border report_border = BorderFactory.createLineBorder(Color.blue, 5);
        report_title.setBorder(report_border);
        System.out.print("revenues = ");
        for (int elem: revenues){
            System.out.print(elem + ", ");
        }
        
        System.out.print("num_bookings = ");
        for (int elem: num_bookings){
            System.out.print(elem + ", ");
        }
        System.out.println("");
        
        hist = new DisplayHist(revenues, year);
        
        //DISPLAY TOTAL REVENUE, TOTAL NUMBER OF BOOKINGS
        // MODAL CLASS BASED ON REVENUE, MODAL CLASS BASED ON NUM OF BOOKING
        // AVERAGE REVENUE PER MONTH
        int sum_rev = 0;
        for (int rev:revenues){
            sum_rev = sum_rev + rev;
        }
        //System.out.println("sum_rev = " + sum_rev);
        
        total_rev.setText("Total Revenue = RM " + sum_rev);
        int sum_book = 0;
        for (int num: num_bookings){
            sum_book = sum_book + num;
        }
        //System.out.println("sum_book = " + sum_book);
        total_num_book.setText("Total No. of Booking = " + sum_book);
        int modal_index = 0;
        int highest_freq = 0;
        for (int i=0; i<12; i++){
            if (num_bookings[i] > highest_freq){
                modal_index = i;
                highest_freq = num_bookings[i];
            }
        }
        modal_month.setText("Most number of bookings was at " + months[modal_index] + " = " + highest_freq);
        
        avg_rev.setText("Average Revenue(rounded off) = RM " + sum_rev/12);
    }
    
    
    
    
    @Override 
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == generate_but){
            String year = year_tf.getText();
            int year_int;
            try {
                year_int = Integer.parseInt(year);
                if (year_int < 2022){
                    JOptionPane.showMessageDialog(null, "Cannot generate annual report for year before 2022!", "Invalid year", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                getStatistics(year);
               
                
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Invalid format for year!", "Invalid format", JOptionPane.INFORMATION_MESSAGE);
            }catch(IOException ex){
                JOptionPane.showMessageDialog(null, "Errors occured when generating report, try again!", "Errors", JOptionPane.INFORMATION_MESSAGE);
            }   
        }
    }
}
