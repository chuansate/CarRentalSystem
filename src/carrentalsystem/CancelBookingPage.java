/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

/**
 *
 * @author User
 */
public class CancelBookingPage extends JFrame implements ActionListener{
    private static JButton[] buttons;
    private String uName;
    private static String[][] book_rec;
    CancelBookingPage(String uName){
        setTitle(uName + "'s Booking History");
        this.uName = uName;
        setLayout(new BorderLayout());
        JPanel panel = createPanel(uName);
        
        
        for (JButton but:buttons){
            but.addActionListener(this);
        }
        add(BorderLayout.CENTER, new JScrollPane(panel));
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Open Bookings.txt and cancel a selected booking
    private void cancelBooking(int count_cancelled_booking) throws IOException {
        //Change the status(last field) of the booking record in Bookings.txt, from 'XCancelled' to 'CANCELLED'
        String filePath = new File("").getAbsolutePath(); 
        String filePath_book = filePath.concat("\\data\\Admin\\Bookings.txt");
        BufferedReader input_book = new BufferedReader(new FileReader(filePath_book));
        
        int count_bookings = 0;
        String record;
        String[] attributes;
        String[][] booking_recs;
        int cur_count_cancelled_booking = 0;
        while((record = input_book.readLine()) != null){
            count_bookings++;// count the number of bookings to initialize the 2D array
            
        }  
        input_book.close();
        
        // Load all the booking recs into the 2D array `booking_recs`
        booking_recs = new String[count_bookings][9];
        int i=0;
        input_book = new BufferedReader(new FileReader(filePath_book));
        while((record = input_book.readLine()) != null){
            if (record.isEmpty()){//becoz readLine() will read nothing after last record, and then causing ERR
                break;
            }
            
            attributes = record.split(",", 9);
            for (int j=0; j<9; j++){
                booking_recs[i][j] = attributes[j];
            }
            i++;
        }
        input_book.close();
        BufferedWriter output_book = new BufferedWriter(new FileWriter(filePath_book));
        // Loop thru the 2D array and write all of them into Bookings.txt, change the status of the cancelled booking to 'CANCELLED' 
        for (int row = 0; row < count_bookings; row++){
            if (cur_count_cancelled_booking == count_cancelled_booking && booking_recs[row][1].equals(uName)){
                output_book.write(booking_recs[row][0] + "," + booking_recs[row][1] + "," + booking_recs[row][2] + "," + booking_recs[row][3] + "," + booking_recs[row][4] + "," + booking_recs[row][5] + "," 
                        + booking_recs[row][6] + "," + booking_recs[row][7] + "," + "CANCELLED" + "\n");
            }else {
                output_book.write(booking_recs[row][0] + "," + booking_recs[row][1] + "," + booking_recs[row][2] + "," + booking_recs[row][3] + "," + booking_recs[row][4] + "," + booking_recs[row][5] + "," 
                        + booking_recs[row][6] + "," + booking_recs[row][7] + "," + booking_recs[row][8] + "\n");
            }
            
            //// only valid booking can be counted, those cancelled bookings cannot be counted.
            if (booking_recs[row][1].equals(uName) && booking_recs[row][8].equals("XCancelled")){
                cur_count_cancelled_booking++;
            }
            System.out.println("row = " + row);
        }
        output_book.close();
    }
    
    public static JPanel createPanel(String uName) {
        JPanel panel = new JPanel();
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("\\data\\Admin\\Bookings.txt");
        BufferedReader input;
        String record;
        String[] data;
        int nrec = 0;
        try {
            input = new BufferedReader(new FileReader(filePath));
            while((record = input.readLine()) != null){
                data = record.split(",", 9);
                if (data[1].equals(uName) && !data[8].equals("CANCELLED")){//if the booking was cancelled before, it shudnt be displayed on the window
                    nrec++;
                }
            }    
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Failed to open, try again!", "Operation Failed", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        panel.setLayout(new GridLayout(nrec, 1, 10, 10));
        buttons = new JButton[nrec]; 
        int buts_index = nrec - 1;
        JPanel rec_panel;
        book_rec = new String[nrec][9];
        int cur_book_num = 0;
        String his_content;
        try{
            input= new BufferedReader(new FileReader(filePath));

            while((record = input.readLine()) != null){
                data = record.split(",", 9);  
                if (data[1].equals(uName) && !data[8].equals("CANCELLED")){//if the booking was cancelled before, it shudnt be displayed on the window
                    for (int i = 0; i < 9; i++){
                        book_rec[cur_book_num][i] = data[i];
                    }
                    cur_book_num++;
                }
            }
            input.close();
            //0serial num, 1username, 2brand, 3model, 4days, 5fees, 6rental starting date, 7booking date
            for (int rec=nrec-1; rec>=0; rec--){
                rec_panel = null;
                rec_panel = new JPanel();
                rec_panel.setBounds(0, 0, 800, 180);
                his_content = "<html>You placed a booking on " + book_rec[rec][7]+ ". <br><br>&nbsp&nbsp&nbsp&nbsp Car Booked: " + book_rec[rec][2] + ", " + book_rec[rec][3] + ". <br>&nbsp&nbsp&nbsp&nbsp Rental starting date: " + book_rec[rec][6] + ". <br>&nbsp&nbsp&nbsp&nbsp Days Booked: " + book_rec[rec][4] + ". <br>&nbsp&nbsp&nbsp&nbsp Total Rental Fees: " + book_rec[rec][5] + ". <br></html>";
                JLabel mail = new JLabel(his_content);
                mail.setBounds(0, 0, 700, 300);
                mail.setFont(new Font("Arial", Font.PLAIN, 20));
                Border mail_border = BorderFactory.createLineBorder(new Color(86, 185, 231), 5);
                rec_panel.add(mail);
                JButton cancel_but = new JButton("Cancel Booking");
                cancel_but.setBackground(Color.red);
                cancel_but.setForeground(Color.WHITE);
                cancel_but.setOpaque(true);
                cancel_but.setBorderPainted(false);
                cancel_but.setBounds(700, 0, 100, 180);
                cancel_but.setFocusable(false);
                rec_panel.add(cancel_but);
                rec_panel.setBorder(mail_border);
                panel.add(rec_panel);
                buttons[buts_index] = cancel_but;
                buts_index--;
            } 
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Failed to display booking history. Try again!","Display Errors", JOptionPane.INFORMATION_MESSAGE);
        }
        return panel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        for (int i=0; i<buttons.length; i++){
            if (e.getSource() == buttons[i]){
                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy"); // to convert string to Date object 
                String rentalStartingDate = book_rec[i][6];
                try {
                    Date rStart_date = formatter.parse(rentalStartingDate);
                    Date curr_date = new Date(System.currentTimeMillis());
                    
                    //Checks if the car rental is in progress OR ended. If TRUE, then raise warning that such booking cannot be cancelled.
                    if (curr_date.compareTo(rStart_date) == 1){
                        JOptionPane.showMessageDialog(null, "You cannot cancel such bookings due to car rental had finished or car rental is in progress.", "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                } catch (ParseException ex) {
                    System.out.println("Unable to parse!");                
                }
                
                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel the booking?", "Cancelling Booking", JOptionPane.YES_NO_CANCEL_OPTION);   
                if (choice == 0){
                    try {
                        cancelBooking(i);
                        dispose();
                        JOptionPane.showMessageDialog(null, "Booking has been cancelled successfully!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex){
                        JOptionPane.showMessageDialog(null, "Failed to cancel booking, please try again.", "Warning", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    
                }
                break;
            }
        }
    }
}
