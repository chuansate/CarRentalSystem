/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author User
 */
public class PaymentPage implements ActionListener{
    private String regNum;
    private String uName;
    private String brand;
    private String model;
    private String rentalDay;
    private String rentalFeePerDay;
    private String totalFee;
    private String startDate;
    private JLabel title;
    private JPanel panel;
    private JFrame frame;
    private JButton pay_but;
    private JLabel paymentDetails;
    private JLabel paymentDetails2;
    private JLabel cardnum_lb;
    private JLabel expiry_lb;
    private JLabel cvv_lb;
    private JTextField cardnum_tf;
    private JTextField expiry_tf;
    private JTextField cvv_tf;
    //Write RegNum, userName, brand, model, rentalDay, rentalFee, StartingDate, transactionDate  into Bookings.txt
    PaymentPage(String regNum, String uName, String brand, String model, String rentalDay, String rentalFeePerDay, String startDate) {
        this.regNum = regNum;
        this.uName = uName;
        this.brand = brand;
        this.model = model;
        this.rentalDay = rentalDay;
        this.rentalFeePerDay = rentalFeePerDay;
        this.totalFee = Integer.toString(Integer.parseInt(rentalDay) * Integer.parseInt(rentalFeePerDay));
        this.startDate = startDate;
        title = new JLabel("Payment Page");
        title.setBounds(0, 50, 600, 35);
        title.setFont(new Font(null, Font.ROMAN_BASELINE, 25));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        paymentDetails = new JLabel("Dear beloved customer " + uName + ", you have booked " + brand + ", " + model + " for " + rentalDay + " day(s)." );
        paymentDetails.setBounds(0, 100, 600, 25);
        paymentDetails.setFont(new Font(null, Font.ROMAN_BASELINE, 15));
        paymentDetails.setHorizontalAlignment(SwingConstants.CENTER);
        paymentDetails.setVerticalAlignment(SwingConstants.CENTER);
        paymentDetails2 = new JLabel("Total fee = RM " + this.totalFee);
        paymentDetails2.setBounds(0, 130, 600, 25);
        paymentDetails2.setFont(new Font(null, Font.ROMAN_BASELINE, 15));
        paymentDetails2.setHorizontalAlignment(SwingConstants.CENTER);
        paymentDetails2.setVerticalAlignment(SwingConstants.CENTER);
        
        cardnum_lb = new JLabel("Card Number");
        cardnum_lb.setBounds(150, 140, 150, 25);
        cvv_lb = new JLabel("CVV");
        cvv_lb.setBounds(370, 140, 50, 25);
        cardnum_tf = new JTextField();
        cardnum_tf.setBounds(150,170, 150, 25);
        cvv_tf = new JTextField();
        cvv_tf.setBounds(370, 170, 50, 25);
        expiry_lb = new JLabel("Expiry Date(MM/YY)");
        expiry_lb.setBounds(150,200,200, 25);
        expiry_tf = new JTextField();
        expiry_tf.setBounds(150,230,50, 25);
        pay_but = new JButton("Pay");
        pay_but.setBounds(320, 260,100, 25);
        pay_but.addActionListener(this);
        panel = new JPanel();
        panel.add(title);panel.add(pay_but);panel.add(paymentDetails);panel.add(cardnum_tf);
        panel.add(cvv_tf);panel.add(cardnum_lb);panel.add(expiry_lb);panel.add(expiry_tf);
        panel.add(cvv_lb);panel.add(paymentDetails2);panel.setBounds(0, 0, 600,600); panel.add(title);
        panel.setLayout(null);
        frame = new JFrame();
        frame.add(panel);
        frame.setTitle("Payment Page");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    private boolean expiryDateIsInvalid(String expiryDate){
        if (expiryDate.length() != 5){
            return true;
        }
        
        if (expiryDate.charAt(2) != '/'){
            return true;
        }
       
        for (int i=0; i<expiryDate.length(); i++){
            if (i == 2){
                
            } else if (expiryDate.charAt(i) < '0' || expiryDate.charAt(i) > '9') {
                System.out.println("Char at index " + i + " is not a digit!");
                return true;
            } 
        }
        if (Integer.parseInt(expiryDate.substring(0, 2)) < 1 || Integer.parseInt(expiryDate.substring(0, 2)) > 12){
            return true;
        }
        
        if (Integer.parseInt(expiryDate.substring(3, 5)) < 22) {
            return true;
        }
        
        return false;
    }
    
    // validate if all inputs are valid for the credit card/debit card.
    // returns TRUE if all the given inputs have passed all the validations; otherwise returns FALSE
    private boolean validateInputs(String cardNum, String CVV, String expiryDate){
        boolean validated = false; //indicating whether the inputs have passed all validations
        if (cardNum.length() != 16){
            JOptionPane.showMessageDialog(null, "Card number must be of 16 digits only!", "Invalid format", JOptionPane.INFORMATION_MESSAGE);
            return validated;
        }
        //ensure cardNum is made up of DIGITS only
        for (int i = 0; i<cardNum.length(); i++){
            if (cardNum.charAt(i) < '0' || cardNum.charAt(i) > '9'){
                JOptionPane.showMessageDialog(null, "Card number must contain digits only!", "Invalid format", JOptionPane.INFORMATION_MESSAGE);
                return validated;
            }
        }
        if (CVV.length() != 3){
            JOptionPane.showMessageDialog(null, "CVV must be of 3 digits only", "Invalid format", JOptionPane.INFORMATION_MESSAGE);
            return validated;
        }
        for (int i = 0; i<CVV.length(); i++){
            if (CVV.charAt(i) < '0' || CVV.charAt(i) > '9'){
                JOptionPane.showMessageDialog(null, "CVV must contain digits only!", "Invalid format", JOptionPane.INFORMATION_MESSAGE);
                return validated;
            }
        }
        
        if (expiryDateIsInvalid(expiryDate)){
            JOptionPane.showMessageDialog(null, "Invalid format for expiry date!", "Invalid format", JOptionPane.INFORMATION_MESSAGE);
            return validated;
        }
                
        validated = true;
        return validated;
    }
    
    // After payment is made, open Cars.txt to update the quantity of car(subtract by 1) 
    private void updateQuantityOfCar() throws IOException{
        String filePath = new File("").getAbsolutePath(); 
        String filePath_cars = filePath.concat("\\data\\Admin\\Cars.txt");
        String filePath_temp = filePath.concat("\\data\\Admin\\temp.txt");
        BufferedReader input_car = new BufferedReader(new FileReader(filePath_cars));
        BufferedWriter output_temp = new BufferedWriter(new FileWriter(filePath_temp));
        String record;
        String[] attributes;
        // open Cars.txt to read the every records. If any one of the records matches the regNum, subtract its quantity by 1 
        while((record = input_car.readLine()) != null){
            attributes = record.split(",",5);
            if (!attributes[0].equals(regNum)){
                output_temp.write(record + "\n");
            }else {
                attributes[3] = String.valueOf(Integer.parseInt(attributes[3]) - 1);
                output_temp.write(attributes[0] + "," + attributes[1] + "," + attributes[2] + "," + attributes[3] + "," + attributes[4] + "\n");
            }            
        }  
        output_temp.close();
        input_car.close();
        
        // Delete the original Cars.txt, which its quantity is not updated
        File file_to_be_deleted = new File(filePath_cars);
        file_to_be_deleted.delete();
        // rename the created temp.txt to  Cars.txt
        File file_to_be_renamed = new File(filePath_temp);
        File renamed_file = new File(filePath_cars);
        file_to_be_renamed.renameTo(renamed_file);
    }
    
    // After payment is made, write the booking history into Bookings.txt
    private void writeBookingHistory(Date date) throws IOException{
        String filePath = new File("").getAbsolutePath(); 
        String filePath_bookings = filePath.concat("\\data\\Admin\\Bookings.txt");
        FileWriter output_booking = new FileWriter(filePath_bookings, true);
        output_booking.write(regNum + "," + uName + "," + brand + "," + model + "," + rentalDay + "," + totalFee + "," + startDate + "," + date + ",XCancelled" + "\n");
        output_booking.close();
    }
    
    // After payment is made, system will send confirmation to customers. The confirmation will be stored at Mails.txt
    private void sendConfirmation(Date date) throws IOException{
        String filePath = new File("").getAbsolutePath(); 
        String filePath_mails = filePath.concat("\\data\\Customer\\Mails.txt");
        //Sending the confirmation letter after payment, saved at Mails.txt
        FileWriter output_mails = new FileWriter(filePath_mails, true);
        output_mails.write(uName + "," + brand + "," + model + "," + startDate + "," + rentalDay + "," + date + "," + totalFee + "\n");
        output_mails.close();  
    }
    
    @Override 
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == pay_but){
            String cardNum = cardnum_tf.getText();
            String CVV = cvv_tf.getText();
            String expiryDate = expiry_tf.getText();
            
            boolean update_successful = true; // indicate whether each operation(updateQauntityOfCar, writeBookingHistory, sendConfirmation) on files is successful. 
            //If any one of them has failed, the following operations should not be continued.
            
            // if all inputs about credit/debit card are correct, then update quantity, write booking history, and send confirmation to customer
            if (validateInputs(cardNum, CVV, expiryDate)){
                try {
                    updateQuantityOfCar();
                }catch(IOException ex){
                    update_successful = false;
                }
                // get the current time
                Date date = new Date(System.currentTimeMillis());
                if (update_successful){
                    try {
                        writeBookingHistory(date);
                    }catch (IOException ex){
                        update_successful = false;
                    }
                }
                
                if (update_successful){
                    try {
                        sendConfirmation(date);
                    } catch(IOException ex){
                        update_successful = false;
                    }
                }
                
                if (!update_successful){
                    JOptionPane.showMessageDialog(null, "Failed to do the payment. Please try again!", "Errors occur", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Payment successful!", "Payment", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                }               
            }      
        }
    }  
}
