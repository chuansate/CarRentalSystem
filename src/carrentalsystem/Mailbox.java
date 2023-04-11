/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;

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

/**
 *
 * @author User
 */
public class Mailbox extends JFrame{ 
    public Mailbox(String uName) {
        setTitle("Mailbox");
        setLayout(new BorderLayout());
        JPanel panel = createPanel(uName);
        add(BorderLayout.CENTER, new JScrollPane(panel));
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Get the total number of mails of a particular customer
    private static int getNumOfMails(String filePath, String uName) throws IOException{ 
        BufferedReader input = new BufferedReader(new FileReader(filePath));
        int num_mails = 0;
        String record;
        String[] attributes;//uName,car,brand,startingDate,rentalDay,mailDate,rentalFee
        
        input = new BufferedReader(new FileReader(filePath));
        while((record = input.readLine()) != null){
            attributes = record.split(",", 7);
            // if the username of the mail is "xxx", then this mail is intended for all customers. Hence it should be counted as well
            if (attributes[0].equals(uName) || attributes[0].equals("xxx")){
                num_mails++;
            }
        }
        return num_mails;
    }
    
    // Load all the mails into the window
    private static JPanel loadMails(String filePath, int num_mails, String uName) throws IOException{
        JPanel panel = new JPanel();
        // set the number of rows in GridLayout
        panel.setLayout(new GridLayout(num_mails, 1, 10, 10));
        BufferedReader input = new BufferedReader(new FileReader(filePath));
        String[][] mail_records;
        mail_records = new String[num_mails][7];
        
        int cur_mail_num = 0;
        String mail_content;
        String record;
        String[] attributes;
        while((record = input.readLine()) != null){
            attributes = record.split(",", 7);
            if (attributes[0].equals(uName)||attributes[0].equals("xxx")){
                for (int i = 0; i < 7; i++){
                    mail_records[cur_mail_num][i] = attributes[i];
                }
                cur_mail_num++;
            }
        }
        input.close();
        String startDate;
        //Displaying the mails onto window
        for (int rec=num_mails-1; rec>=0; rec--){ 
            if (mail_records[rec][0].equals("xxx")){//Displaying announcements
                String title = mail_records[rec][1].replace("`", ",");
                String msg = mail_records[rec][2].replace("`", ",");
                mail_content = "<html>" + title + "<br><br>" + msg + "<br><br>" + mail_records[rec][5] + "</html>";
                JLabel mail = new JLabel(mail_content);
                mail.setFont(new Font("Arial", Font.PLAIN, 20));
                Border mail_border = BorderFactory.createLineBorder(Color.yellow, 5);
                mail.setBorder(mail_border);
                panel.add(mail); 
            }else {//displaying booking confirmation letters
                startDate = mail_records[rec][3].substring(0, 11).concat(mail_records[rec][3].substring(20, 28));
                mail_content = "<html>Dear " + uName + ", <br>You have booked a " + mail_records[rec][1] + ", " + mail_records[rec][2] + " for " + mail_records[rec][4] + " days, starting at " + startDate + ".<br>Total rental fee = RM" + mail_records[rec][6] + ".<br><br>" + mail_records[rec][5] + "</html>";
                JLabel mail = new JLabel(mail_content);
                mail.setFont(new Font("Arial", Font.PLAIN, 20));
                Border mail_border = BorderFactory.createLineBorder(Color.yellow, 5);
                mail.setBorder(mail_border);
                panel.add(mail); 
            }
        }
        return panel;
    }
    
    public static JPanel createPanel(String uName) {
        JPanel panel = new JPanel();
        String filePath;
        filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("//data//Customer//Mails.txt");
        //BufferedReader input;
        String record;
        String[] attributes;//uName,car,brand,startingDate,rentalDay,mailDate,rentalFee
        int num_mails = 0;
        //counting the number of mails for a given customer
        try {
            num_mails = getNumOfMails(filePath, uName);  
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Failed to open, try again!", "Operation Failed", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        
        try {
            panel = loadMails(filePath, num_mails, uName);
        } catch (IOException ex){
            JOptionPane.showMessageDialog(null, "Failed to display mails. Try again!","Display Errors", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
        return panel;
    }
}
