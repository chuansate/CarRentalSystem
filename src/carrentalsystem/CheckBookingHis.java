/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;

/**
 *
 * @author User
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

public class CheckBookingHis extends JFrame{
    public CheckBookingHis(String uName) {
        setTitle(uName + "'s Booking History");
        setLayout(new BorderLayout());
        JPanel panel = createPanel(uName);
        add(BorderLayout.CENTER, new JScrollPane(panel));
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Get the total number of bookings of a particular customer
    private static int getNumOfBookings(String filePath, String uName) throws IOException{ 
        BufferedReader input = new BufferedReader(new FileReader(filePath));
        int num_bookings = 0;
        String record;
        String[] data;
        while((record = input.readLine()) != null){
            data = record.split(",", 9);
            if (data[1].equals(uName)){
                num_bookings++;
            }
        }
        input.close();
        return num_bookings;
    }
    
    // Load all the booking histories into the window
    private static JPanel loadBookingHistories(String filePath, int num_bookings, String uName) throws IOException{
        JPanel panel = new JPanel();
        // set the number of rows in GridLayout
        panel.setLayout(new GridLayout(num_bookings, 1, 10, 10));
        String[][] book_rec;
        book_rec = new String[num_bookings][9];
        int cur_book_num = 0;
        String bookHis_content;
        String record;
        String[] data;
        BufferedReader input = new BufferedReader(new FileReader(filePath));
        while((record = input.readLine()) != null){
            data = record.split(",",9);  
            if (data[1].equals(uName)){
                for (int i = 0; i < 9; i++){
                    book_rec[cur_book_num][i] = data[i];
                }
                cur_book_num++;
            }
        }
        input.close();
        //0 RegNum, 1 username, 2 brand, 3 model, 4 days, 5 fees, 6 rental starting date, 7 booking date, 8 status
        for (int rec=num_bookings-1; rec>=0; rec--){
            if (book_rec[rec][8].equals("CANCELLED")){
                bookHis_content = "<html><font color='red'>(CANCELLED)</font>You placed a booking on " + book_rec[rec][7]+ ". <br><br>&nbsp&nbsp&nbsp&nbsp Car Booked: " + book_rec[rec][2] + ", " + book_rec[rec][3] + ". <br>&nbsp&nbsp&nbsp&nbsp Rental starting date: " + book_rec[rec][6] + ". <br>&nbsp&nbsp&nbsp&nbsp Days Booked: " + book_rec[rec][4] + ". <br>&nbsp&nbsp&nbsp&nbsp Total Rental Fees: " + book_rec[rec][5] + ". <br></html>";
                JLabel mail = new JLabel(bookHis_content);
                mail.setFont(new Font("Arial", Font.PLAIN, 20));
                Border mail_border = BorderFactory.createLineBorder(new Color(86, 185, 231), 5);
                mail.setBorder(mail_border);
                panel.add(mail); 
            } else {
                bookHis_content = "<html>You placed a booking on " + book_rec[rec][7]+ ". <br><br>&nbsp&nbsp&nbsp&nbsp Car Booked: " + book_rec[rec][2] + ", " + book_rec[rec][3] + ". <br>&nbsp&nbsp&nbsp&nbsp Rental starting date: " + book_rec[rec][6] + ". <br>&nbsp&nbsp&nbsp&nbsp Days Booked: " + book_rec[rec][4] + ". <br>&nbsp&nbsp&nbsp&nbsp Total Rental Fees: " + book_rec[rec][5] + ". <br></html>";
                JLabel mail = new JLabel(bookHis_content);
                mail.setFont(new Font("Arial", Font.PLAIN, 20));
                Border mail_border = BorderFactory.createLineBorder(new Color(86, 185, 231), 5);
                mail.setBorder(mail_border);
                panel.add(mail); 
            }
            
        }
        
        return panel;
    }
    
    public static JPanel createPanel(String uName) {
        JPanel panel = new JPanel();
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("\\data\\Admin\\Bookings.txt");
        int num_bookings = 0; // get the number of bookings that the customer has
        try {
            num_bookings = getNumOfBookings(filePath, uName);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Failed to open, try again!", "Operation Failed", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        
        try {
            panel = loadBookingHistories(filePath, num_bookings, uName);
        } catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Failed to display booking history. Try again!","Display Errors", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        return panel;
    }
    
}

