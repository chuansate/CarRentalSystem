/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author User
 */
public class Announcement implements ActionListener{
    private JFrame frame;
    private JButton send_but;
    private JLabel title_lb;
    private JLabel msg_lb;
    private JTextField title_tf;
    private JTextArea msg_tf;
    Announcement() {
        send_but = new JButton("Send");
        send_but.setBounds(750, 700, 100, 25);
        send_but.addActionListener(this);
        send_but.setFocusable(false);
        title_lb = new JLabel("Title: ");
        title_lb.setBounds(100, 50, 200, 25);
        title_lb.setFont(new Font(null, Font.ITALIC, 20));
        title_tf = new JTextField();
        title_tf.setBounds(250, 50, 500, 25);
        msg_lb = new JLabel("Message: ");
        msg_lb.setBounds(100, 100, 200, 25);
        msg_lb.setFont(new Font(null, Font.ITALIC, 20));
        msg_tf = new JTextArea();
        msg_tf.setBounds(250, 100, 500, 500);
       
        frame = new JFrame();
        frame.add(send_but);frame.add(title_lb);frame.add(title_tf);frame.add(msg_lb);frame.add(msg_tf);
        frame.setTitle("Announcement Page");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 800);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    private void writeMail(String title, String msg) throws IOException{
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("\\data\\Customer\\Mails.txt");
        FileWriter output = new FileWriter(filePath, true);
        if (output == null){
            throw new IOException();
        }
        Date date = new Date(System.currentTimeMillis());
        // Since announcement is intended for all customers, hence the username is set to "xxx"
        // no need to be scared of someone is registering acc using the uName "xxx" becoz implemented validations limit the length 
        output.write("xxx," + title + "," + msg + ",-,-," + date + ",-\n");
        output.close();
    }
    
    @Override 
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == send_but){
            //Write records into Mails.txt, the uName must be 'xxx', 'xxx' refers to every user
            String title = title_tf.getText();
            String msg = msg_tf.getText();
            title = title.replace(",", "`");//to avoid the comma being treated as delimiter
            msg = msg.replace("\n", "<br>");
            msg = msg.replace(",", "`"); //to avoid the comma being treated as delimiter
            try {
                writeMail(title, msg);
                JOptionPane.showMessageDialog(null, "Sent announcement successfully!","Announcement Successful", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }catch(IOException ex){
                JOptionPane.showMessageDialog(null, "Failed to send announcement. Try again!","Sending Failure", JOptionPane.INFORMATION_MESSAGE);
               
            }
        }
    }
}
