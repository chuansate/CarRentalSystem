/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 *
 * @author User
 */
public class LoginPage implements ActionListener {
    private String nextPage;
    private JLabel titleLabel;
    private JFrame frame;
    private JLabel msg1;
    private String[] choices;
    private JComboBox<String> drop_down;
    private JButton login_but;
    private String user_type;
    LoginPage() {
        int WINDOW_WIDTH = 900;
        int WINDOW_HEIGHT = 450;
        titleLabel = new JLabel();
        titleLabel.setBounds(250, 100, 365, 35);
        titleLabel.setFont(new Font(null, Font.PLAIN, 25));
        titleLabel.setText("Welcome to Car Rental System!");
        frame = new JFrame();
        frame.add(titleLabel);
        
        msg1 = new JLabel();
        msg1.setBounds(250, 200, 365, 25);
        msg1.setFont(new Font(null, Font.PLAIN, 15));
        msg1.setText("Select type of user to login:");
        frame.add(msg1);
        
        String[] choices = {"Admin", "Customer"};
        drop_down = new JComboBox<>(choices);
        drop_down.setBounds(450, 200, 100, 25);
        drop_down.setVisible(true);
        drop_down.addActionListener(this);
        frame.add(drop_down);
        user_type = choices[0];
        login_but = new JButton("Proceed");
        login_but.setBounds(400, 300, 100, 25);
        login_but.setFocusable(false);
        login_but.addActionListener(this);
        frame.add(login_but);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public String getNextPage(){
        return this.nextPage;
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == drop_down){
            user_type = (String)drop_down.getSelectedItem();
        } else if (e.getSource() == login_but){
            if(user_type.equals("Admin")){
                frame.dispose();
                this.nextPage = "Admin";
                AdminLoginPage adminLoginPage = new AdminLoginPage();
                
            } else {
                frame.dispose();
                this.nextPage = "Cus";
                CusLoginPage cusLoginPage = new CusLoginPage();
            }
        }
    }
}

class AdminLoginPage implements ActionListener{
    protected int WINDOW_WIDTH;
    protected int WINDOW_HEIGHT;
    protected JButton return_but;
    protected JFrame frame;
    protected JLabel titleLabel;
    protected JLabel uname_label;
    protected JLabel pwd_label;
    protected JTextField uname_tf;
    protected JPasswordField pwd_tf;
    protected JButton login_but;
    protected JLabel invalid_msg;
    protected JPanel panel;
    protected JToggleButton showPwd_but;
    protected String found_userName;
    AdminLoginPage() {
        WINDOW_WIDTH = 900;
        WINDOW_HEIGHT = 900;
        frame = new JFrame();
        titleLabel = new JLabel("Admin Login Page");
        uname_label = new JLabel("Username:");
        pwd_label = new JLabel("Password:");
        uname_tf = new JTextField();
        pwd_tf = new JPasswordField();
        login_but = new JButton("Login");
        invalid_msg = new JLabel();
        panel = new JPanel();
        showPwd_but = new JToggleButton("Show Password");
        panel.setBounds(50, 50, WINDOW_WIDTH-50, WINDOW_HEIGHT-50);
        panel.setLayout(null);
        String imgPath = new File("").getAbsolutePath(); 
        imgPath = imgPath.concat("//Icons//Return.png");
        return_but = new JButton(new ImageIcon(imgPath));
        return_but.setBounds(50, 50, 50, 50);
        return_but.addActionListener(this);
        panel.add(return_but);
        
        titleLabel.setBounds(300, 150, 300, 35);
        titleLabel.setFont(new Font(null, Font.PLAIN, 25));
        panel.add(titleLabel);
        
        uname_label.setBounds(200, 250, 100, 25);
        panel.add(uname_label);
        
        pwd_label.setBounds(200, 350, 100, 25);
        panel.add(pwd_label);
        
        uname_tf.setBounds(300, 250, 250, 25);
        panel.add(uname_tf);
        
        pwd_tf.setBounds(300, 350, 250, 25);
        panel.add(pwd_tf);
        
        login_but.setBounds(400, 500,100, 25);
        login_but.setFocusable(false);
        login_but.addActionListener(this);
        panel.add(login_but);
        
        invalid_msg.setBounds(200, 400, 350, 25);
        invalid_msg.setForeground(Color.RED);
        invalid_msg.setFont(new Font(null, Font.ITALIC, 15));
        panel.add(invalid_msg);
        
        ItemListener itemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent){
                int state = itemEvent.getStateChange();
                if (state == ItemEvent.SELECTED){
                    pwd_tf.setEchoChar((char)0);
                    showPwd_but.setText("Hide Password");
                }else {
                    pwd_tf.setEchoChar('*');
                    showPwd_but.setText("Show Password");
                }
            }
        };
        showPwd_but.setBounds(550, 350, 135, 25);
        showPwd_but.addItemListener(itemListener);
        showPwd_but.setFocusable(false);
        panel.add(showPwd_but);
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    //validate if credentials exist
    protected boolean credentialsExist(String uName, String pwd, String userType) throws IOException{
        BufferedReader input = null; //if FileReader fails to read a file, it returns null as well
        String filePath;
        filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("\\data\\Credentials\\").concat(userType).concat(".txt");
        input = new BufferedReader(new FileReader(filePath));
        //validate if credentials exist in Admin.txt
        String record;
        String[] attributes;
        boolean found = false;
        while((record = input.readLine()) != null){
            attributes = record.split(",",-1);
        
            if (uName.equals(attributes[0]) && pwd.equals(attributes[1])){
                found = true;
                found_userName = attributes[0];
                break;
            }
        }
        input.close();
        return found;
    }
    
    //write login records into LoginRecords.txt
    protected void writeLoginRecord(String userType) throws IOException{
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("\\data\\Admin\\LoginRecords.txt");
        Date date = new Date(System.currentTimeMillis());
        FileWriter writer = new FileWriter(filePath, true);
        writer.write(found_userName + "," + userType + "," + date + "\n");
        writer.close();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == return_but){
            frame.dispose();
            LoginPage login_page = new LoginPage();
        } else if (e.getSource() == login_but){
            //get all the credentials from uname_tf and pwd_tf
            String uName = uname_tf.getText(); //if user doesnt type anything in tf, then it is empty string("").
            String pwd = String.valueOf(pwd_tf.getPassword());
            
            try {
                // if credentials exist, write the login record into LoginRecords.txt
                if (credentialsExist(uName, pwd, "Admin")){    
                    try {
                        writeLoginRecord("ADMIN");       
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Failed to login, try again!", "Login Failed", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    AdminHomePage adminHomePage = new AdminHomePage(found_userName);
                    frame.dispose();
                }else {//when credentials do not exist
                    invalid_msg.setText("Invalid credentials. Please try again!");
                }
                
            }catch(IOException ex){
                JOptionPane.showMessageDialog(null, "Failed to validate credentials. Try again!", "Validation Failure", JOptionPane.INFORMATION_MESSAGE);
            }
            
        }
    }
}

class CusLoginPage extends AdminLoginPage implements ActionListener{
    JButton createNewAcc_but = new JButton("Create New Account");
    CusLoginPage() {
        titleLabel.setText("Customer Login Page");
        createNewAcc_but.setBounds(225, 500, 150, 25);
        createNewAcc_but.setFocusable(false);
        createNewAcc_but.addActionListener(this);
        panel.add(createNewAcc_but);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == return_but){
            frame.dispose();
            LoginPage login_page = new LoginPage();
        } else if (e.getSource() == login_but){
            //get all the credentials from uname_tf and pwd_tf
            String uName = uname_tf.getText(); //if user doesnt type anything in tf, then it is empty string("").
            String pwd = String.valueOf(pwd_tf.getPassword());
            
            try {
                // if credentials exist, write the login record into LoginRecords.txt
                if (credentialsExist(uName, pwd, "Customer")){    
                    try {
                        writeLoginRecord("CUS");       
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Failed to login, try again!", "Login Failed", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    CusHomePage cusHomePage = new CusHomePage(found_userName);
                    frame.dispose();
                }else {//when credentials do not exist
                    invalid_msg.setText("Invalid credentials. Please try again!");
                }
                
            }catch(IOException ex){
                JOptionPane.showMessageDialog(null, "Failed to validate credentials. Try again!", "Validation Failure", JOptionPane.INFORMATION_MESSAGE);
            }   
        } else if (e.getSource() == createNewAcc_but){
            frame.dispose();
            RegisterPage register_pg = new RegisterPage();
        }
    }
}

class RegisterPage extends AdminLoginPage{
    private JLabel phone_label = new JLabel("Phone Number:");
    private JTextField phone_tf = new JTextField();
    private JButton create_but = new JButton("Create");
    private JLabel duplicate_uName_label = new JLabel();
    private JLabel pwd_format_label = new JLabel();
    private JLabel pwd_format_warning = new JLabel();
    private JLabel uName_format_label = new JLabel();
    private JLabel uName_format_warning = new JLabel();
    private JLabel phone_format_warning = new JLabel();
    private JOptionPane msg_window;
    RegisterPage() {
        titleLabel.setText("Register Account");
        phone_label.setBounds(200, 450, 100, 25);
        phone_tf.setBounds(300, 450, 250, 25);
        Component[] componentList = panel.getComponents();
        for (Component c: componentList){
            if (c.equals(login_but) ){
                panel.remove(c);
                break;
            }
        }
        create_but.setBounds(400, 500,100, 25);
        create_but.setFocusable(false);
        create_but.addActionListener(this);
        duplicate_uName_label.setBounds(200, 280, 300, 25);        
        pwd_format_label.setBounds(200, 385, 550,25);
        pwd_format_label.setText("(Password must consist of 8-12 characters: at least 1 alphabet, 1 number, and 1 symbol)");
        pwd_format_warning.setBounds(200,420, 200, 25);
        uName_format_label.setBounds(200, 285, 300,25);
        uName_format_label.setText("(Username must consist of 8-14 chars)");
        uName_format_warning.setBounds(200 ,315,300,25);
        phone_format_warning.setBounds(200, 480, 300, 25);
        phone_format_warning.setForeground(Color.RED);
        phone_format_warning.setFont(new Font(null, Font.ITALIC, 15));
        panel.add(uName_format_warning);panel.add(uName_format_label);panel.add(create_but);
        panel.add(phone_label);panel.add(phone_tf);panel.add(duplicate_uName_label);
        panel.add(pwd_format_label);panel.add(pwd_format_warning);panel.add(phone_format_warning);
        frame.add(panel);
    }
    
    //A method returns TRUE if the uName is following the format and has not been used by others
    //otherwise returns FALSE
    private boolean validate_uName(String uName){
        //length of uName must be between 8 chars and 14 chars
        if (uName.length() < 8 || uName.length() > 14 ){
            uName_format_warning.setText("Invalid username format!");
            uName_format_warning.setForeground(Color.RED);
            uName_format_warning.setFont(new Font(null, Font.ITALIC, 15));
            return false;
        } 
      
        BufferedReader input;//if FileReader fails to read a file, it returns null as well
        String filePath;
        try {
            filePath = new File("").getAbsolutePath();
            filePath = filePath.concat("\\data\\Credentials\\Customer.txt");
            input = new BufferedReader(new FileReader(filePath));
            //validate if username exist in Customer.txt
            String record;
            String[] attributes;
            while((record = input.readLine()) != null){//`record` will be empty str after reading last record, have a look at "CarTable.java > CarTable()" to handle this problem
                attributes = record.split(",",4);
                if (uName.equals(attributes[0])){
                    //display warning msg if userName is duplicated
                    duplicate_uName_label.setText("Username has been used by others.");
                    duplicate_uName_label.setForeground(Color.RED);
                    duplicate_uName_label.setFont(new Font(null, Font.ITALIC, 15));
                    return false;
                }
            }
            input.close();
        } catch(IOException ex){
            System.out.println("Failed to check if username existed.");
            return false;
        } 
        
        return true;        
    }
    
    // returns TRUE if pwd is 8-12 chars: at least 1 alphabet, 1 number, and 1 symbol
    // otherwise FALSE
    private boolean validate_pwd(String pwd){
        if (pwd.length() < 8 || pwd.length() > 12){
            pwd_format_warning.setText("Invalid password format!");
            pwd_format_warning.setForeground(Color.RED);
            pwd_format_warning.setFont(new Font(null, Font.ITALIC, 15));
            
            return false;
        }
        
        int count_alpha = 0;
        int count_num = 0;
        int count_symbol = 0;
        for (int i=0; i<pwd.length(); i++){
            if ((pwd.charAt(i) >= 'a' && pwd.charAt(i) <= 'z') || (pwd.charAt(i) >= 'A' && pwd.charAt(i) <= 'Z')){
                count_alpha++;
            } else if(pwd.charAt(i) >= 48 && pwd.charAt(i) <= 57 ){
                count_num++;
            } else if(pwd.charAt(i) >= 33 && pwd.charAt(i) <= 126) {
                count_symbol++;
            }
        }
        if (count_alpha == 0 || count_num == 0 || count_symbol == 0){
            pwd_format_warning.setText("Invalid password format!");
            pwd_format_warning.setForeground(Color.RED);
            pwd_format_warning.setFont(new Font(null, Font.ITALIC, 15));
            return false;        
        }
        
        return true;
    }
    
    //return TRUE if the phoneNum is valid, otherwise FALSE
    private boolean validate_phoneNum(String phoneNum){
        for (int i=0; i<phoneNum.length(); i++){
            if (phoneNum.charAt(i) < '0' || phoneNum.charAt(i) > '9'){
                phone_format_warning.setText("Invalid format for phone number.");
                return false;
            }
        }
        
        if (phoneNum.length() < 10 || phoneNum.length() > 11){
            phone_format_warning.setText("Invalid format for phone number.");
            return false;
        }
        
        if (phoneNum.charAt(0) != '0'){
            phone_format_warning.setText("Invalid format for phone number.");
            return false;
        }
        
        if (phoneNum.charAt(1) != '1'){
            phone_format_warning.setText("Invalid format for phone number.");
            return false;
        }
        return true;
    } 
    
    // To register new accounts for customers
    private void registerAccount(String uName, String pwd, String phoneNum) throws IOException {
        FileWriter output;
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("\\data\\Credentials\\Customer.txt");
        //append new credentials into Customer.txt
        output = new FileWriter(filePath, true);
        output.write(uName + "," + pwd + "," + phoneNum + "\n");
        output.close();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == return_but){
            frame.dispose();
            CusLoginPage cusLoginPage = new CusLoginPage();
        } else if(e.getSource() == create_but){
            // Get all the credentials for registering new accounts
            String uName = uname_tf.getText();
            String pwd = String.valueOf(pwd_tf.getPassword());
            String phoneNum = phone_tf.getText();
            
            //Clear the warning messages
            pwd_format_warning.setText("");
            uName_format_warning.setText("");
            phone_format_warning.setText("");
            //if all register info are valid
            if (validate_uName(uName) & validate_pwd(pwd) & validate_phoneNum(phoneNum)) {
                try {
                    registerAccount(uName, pwd, phoneNum);
                    //pop-up window to say account has been registered
                    JOptionPane.showMessageDialog(null, "Account has been registered.", "Notification", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    CusLoginPage cusLoginPage = new CusLoginPage();
                }catch(IOException ex){
                    System.out.println("Failed to append the credentials");
                }
            }     
        }
    }
}