/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;

import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;



/**
 *
 * @author User
 */
public class BookingPage implements ActionListener{
    // Book a car thru its Reg Num. This will check if the given inputs are valid.
    // availability of the desired car will be checked as well.
    private String uName;
    private JPanel panel;
    private JFrame frame;
    private JScrollPane car_records;
    private CarTable carTable;
    private JTable table;
    private final int WINDOW_WIDTH = 900;
    private final int WINDOW_HEIGHT = 900;
    private String[][] available_cars;
    private JLabel title;
    private JLabel book_label;
    private JLabel book_label2;
    private JTextField regNum_tf;
    private JButton book_but;
    private JLabel rentalDay_lb;
    private JTextField rentalDay_tf;
    private JLabel startDate_lb;
    private JDateChooser jDateChooser1;
    private String brand;
    private String model;
    private String rentalFeePerDay;
    BookingPage(String uName) {
        this.uName = uName;
        panel = new JPanel();
        frame = new JFrame();
        carTable = new CarTable(); 
        // Column Names
        String[] columnNames = { "Reg No.", "Brand", "Model", "Quantity", "RentalFee/Day"};
        table = new JTable(carTable.getCarRecords(), columnNames);
        table.setBounds(0, 0, 600, 900);
        table.setDefaultEditor(Object.class, null);
        car_records = new JScrollPane(table);
        car_records.setBounds(0, 0, 600, 850);
        
        title = new JLabel("Booking Page");
        title.setBounds(600, 50, 300, 30);
        title.setFont(new Font(null, Font.ROMAN_BASELINE, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        
        book_label = new JLabel("Book a car based on its Reg No.");
        book_label.setBounds(600, 90, 300, 25);
        book_label2 = new JLabel("Reg No.:");
        book_label2.setBounds(650, 130, 50, 25);
        book_label.setHorizontalAlignment(SwingConstants.CENTER);
        book_label.setVerticalAlignment(SwingConstants.CENTER);
        regNum_tf = new JTextField();
        regNum_tf.setBounds(700, 130, 150, 25);
        rentalDay_lb = new JLabel("Rental day(Max 30):");
        rentalDay_lb.setBounds(650, 160,150, 25);
        rentalDay_tf = new JTextField();
        rentalDay_tf.setBounds(800, 160, 50, 25);
        book_but = new JButton("Book");
        book_but.setBounds(750,250,100,25);
        
        startDate_lb = new JLabel("Starting Date:");
        startDate_lb.setBounds(650, 190, 100, 25);
        jDateChooser1 = new JDateChooser();
        jDateChooser1.setBounds(750, 190, 100, 25);
        
        book_but.addActionListener(this);
        panel.add(startDate_lb);panel.add(jDateChooser1);panel.add(rentalDay_lb);
        panel.add(rentalDay_tf);panel.add(book_but);panel.add(book_label2);
        panel.add(regNum_tf);panel.add(book_label); panel.add(car_records);
        panel.setBounds(0, 0, WINDOW_WIDTH,WINDOW_HEIGHT); panel.setLayout(null);panel.add(title);
        frame.add(panel);
        frame.setTitle("Booking Page");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setLayout(null);
        frame.setVisible(true);
        
    }
    
    // validate if all inputs are valid, and also checks if car is available for booking
    // returns TRUE if all the given inputs have passed all the validations; otherwise returns FALSE
    private boolean validateInputs(String regNum, String rentalDay){
        boolean validated = false;// indicating whether all the given inputs have passed all the validations
        //Ensure all text fields are given inputs
        if (jDateChooser1.getDate() == null){
            JOptionPane.showMessageDialog(null, "Please input a starting date.", "Insufficient input.", JOptionPane.INFORMATION_MESSAGE);
            return validated;
        }
        if(regNum.equals("")){
            JOptionPane.showMessageDialog(null, "Please enter Reg No.", "Insufficient input.", JOptionPane.INFORMATION_MESSAGE);
            return validated;
        }
        if (rentalDay.equals("")){
            JOptionPane.showMessageDialog(null, "Please enter rental day.", "Insufficient input.", JOptionPane.INFORMATION_MESSAGE);
            return validated;
        }
        
        //validate inputs
        //see if the Reg Num exists in the carTable
        int rec_index = 0;//to store the index of the car to be booked
        boolean found = false;
        for (int i=0; i<carTable.getNumRec(); i++){
            if (carTable.getCarRecords()[i][0].equals(regNum)){
                rec_index = i;
                brand = carTable.getCarRecords()[i][1];
                model = carTable.getCarRecords()[i][2];
                rentalFeePerDay = carTable.getCarRecords()[i][4];
                found = true;
                break;
            }
        }
        
        // IF the regNum is not found, raise warnings
        if (!found){
            JOptionPane.showMessageDialog(null, "Reg No. does not exist.", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
            return validated;
        }else{ // if the quantity of car is 0, this means it is not available for booking
            if (Integer.parseInt(carTable.getCarRecords()[rec_index][3]) == 0){
                JOptionPane.showMessageDialog(null, "The car is not available for booking.", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return validated;
            } 
        }
        
        // validate the rental day. max duration is 30 days, min duration is 1 day
        int rentalDay_num;
        try {
            rentalDay_num = Integer.parseInt(rentalDay);
            if (rentalDay_num < 1 || rentalDay_num > 30){
                JOptionPane.showMessageDialog(null, "Rental day is restricted between 1 until 30 days.", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return validated;
            }
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Invalid value for rental day!", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
            return validated;
        }
        
        // so far all the given inputs have passed all the validations, hence it is assigned a TRUE
        validated = true;
        return validated;
    }
    
    @Override 
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == book_but){
            String regNum = regNum_tf.getText();
            String rentalDay = rentalDay_tf.getText();
            
            // if all given inputs have passed all validations, dispose the BookingPage window and redirect to PaymentPage window
            if (validateInputs(regNum, rentalDay)){
                frame.dispose();
                PaymentPage payPage = new PaymentPage(regNum, uName, brand, model, rentalDay, rentalFeePerDay,jDateChooser1.getDate().toString());  
            }
            
        }
    }
}
