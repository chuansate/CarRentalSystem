/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
public class ApplyDiscountPage implements ActionListener{
    private JLabel title_label1;
    private JLabel title_label2;
    private JLabel regNum_label;
    private JTextField regNum_tf;
    private JLabel brand_label;
    private JTextField brand_tf;
    private JLabel discount_label;
    private JTextField discount_tf;
    private JLabel percentage_symbol;
    private JPanel panel;
    private JFrame frame;
    private JButton apply_but;
    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_HEIGHT = 600;
    ApplyDiscountPage(){
        title_label1 = new JLabel("Apply Discount Page");
        title_label1.setBounds(0, 30, 600, 30);
        title_label1.setHorizontalAlignment(SwingConstants.CENTER);
        title_label1.setVerticalAlignment(SwingConstants.CENTER);
        title_label1.setFont(new Font(null, Font.ITALIC, 20));
        
        title_label2 = new JLabel("Choose a car(by entering its Reg No.) or a car brand to apply discount on:");
        title_label2.setBounds(0, 70, 600, 30);
        title_label2.setHorizontalAlignment(SwingConstants.CENTER);
        title_label2.setVerticalAlignment(SwingConstants.CENTER);
        
        regNum_label = new JLabel("Reg No.:");
        regNum_label.setBounds(150, 130, 100, 30);
        regNum_tf = new JTextField();
        regNum_tf.setBounds(250, 130, 200, 30);
        
        brand_label = new JLabel("Brand:");
        brand_label.setBounds(150, 240, 100, 30);
        brand_tf = new JTextField();
        brand_tf.setBounds(250, 240, 200, 30);
        
        discount_label = new JLabel("Discount Rate (min is 10%, max is 50%):");
        discount_label.setBounds(150, 330, 250, 30);
        discount_tf = new JTextField();
        discount_tf.setBounds(400, 330, 50, 30);
        percentage_symbol = new JLabel("%");
        percentage_symbol.setBounds(450, 330, 50, 30);
        
        apply_but = new JButton("Apply Discount");
        apply_but.setBounds(400, 500, 150, 30);
        apply_but.setFocusable(false);
        apply_but.addActionListener(this);
        panel = new JPanel();
        panel.setBounds(0, 0, WINDOW_HEIGHT, WINDOW_WIDTH);
        panel.add(title_label1); panel.add(title_label2); panel.add(apply_but); panel.add(regNum_label); panel.add(regNum_tf);
        panel.add(brand_label); panel.add(brand_tf);
        panel.add(discount_label); panel.add(discount_tf); panel.add(percentage_symbol);
        panel.setVisible(true);
        panel.setLayout(null);
        
        frame = new JFrame();
        frame.setTitle("Apply Discount Page");
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLayout(null);
        frame.setVisible(true);
        
    }
    
    // Update the rental fee per day in Cars.txt
    private void applyDiscounts(String regNum, String brand, String discount_rate){
        String filePath = new File("").getAbsolutePath(); 
        String filePath_cars = filePath.concat("\\data\\Admin\\Cars.txt");
        String filePath_temp = filePath.concat("\\data\\Admin\\temp.txt"); 
        String record;
        String[] attributes;
        int discount_rate_num = Integer.parseInt(discount_rate);
        try {
            BufferedReader input_car = new BufferedReader(new FileReader(filePath_cars));
            BufferedWriter output_temp = new BufferedWriter(new FileWriter(filePath_temp));
            if (!regNum.equals("")){// apply discount for a particular car based on its Reg No.
                while((record = input_car.readLine()) != null){
                    attributes = record.split(",",5);
                    if (!attributes[0].equals(regNum)){
                        output_temp.write(record + "\n");
                    }else {
                        //int dis_price = (price * (100-dis_rate))/100; (FORMULA FOR CALCULATING DISCOUNTED_PRICE)
                        attributes[4] = String.valueOf((Integer.parseInt(attributes[4]) * (100 - discount_rate_num))/100);
                        output_temp.write(attributes[0] + "," + attributes[1] + "," + attributes[2] + "," + attributes[3] + "," + attributes[4] + "\n");
                    }            
                }  
                output_temp.close();
                input_car.close();
            } else if(!brand.equals("")){//apply discount for a category of car based on the brand
                while((record = input_car.readLine()) != null){
                    attributes = record.split(",",5);
                    if (!attributes[1].equals(brand)){
                        output_temp.write(record + "\n");
                    }else {
                        //int dis_price = (price * (100-dis_rate))/100; (FORMULA FOR CALCULATING DISCOUNTED_PRICE)
                        attributes[4] = String.valueOf((Integer.parseInt(attributes[4]) * (100 - discount_rate_num))/100);
                        output_temp.write(attributes[0] + "," + attributes[1] + "," + attributes[2] + "," + attributes[3] + "," + attributes[4] + "\n");
                    }            
                }  
                output_temp.close();
                input_car.close();
            }
            JOptionPane.showMessageDialog(null, "Discount has been applied successfully!","Notification", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        } catch(IOException ex){
            JOptionPane.showMessageDialog(null, "Failed to apply discounts, try again.","Warnings", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Delete the original Cars.txt, which its rental fee is not updated
        File file_to_be_deleted = new File(filePath_cars);
        file_to_be_deleted.delete();
        // rename the created temp.txt to  Cars.txt
        File file_to_be_renamed = new File(filePath_temp);
        File renamed_file = new File(filePath_cars);
        file_to_be_renamed.renameTo(renamed_file);
    }
    
    // check if given info is complete
    private boolean infoIsComplete(){
        if (discount_tf.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Discount rate cannot be null!","Invalid inputs", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        // allows either regNum or Brand is given
        String strs[] = {regNum_tf.getText(), brand_tf.getText()};
        int count_not_null = 0;
        for (String str: strs){
            if (!str.equals("")){
                count_not_null++;
            }
        }
        // allows either regNum or Brand is given
        if (count_not_null != 1){
            JOptionPane.showMessageDialog(null, "Either Reg No. or brand is allowed to be given.","Invalid inputs", JOptionPane.INFORMATION_MESSAGE);
            return false;
        } 
        return true;
    }
    
    private boolean regNumExists(){
        boolean flag = false;
        BufferedReader input;//if FileReader fails to read a file, it returns null as well
        String filePath;
        try {
            filePath = new File("").getAbsolutePath();
            filePath = filePath.concat("\\data\\Admin\\Cars.txt");
            input = new BufferedReader(new FileReader(filePath));
            //validate if regNum exist in Cars.txt
            String record;
            String[] attributes;
            while((record = input.readLine()) != null){
                attributes = record.split(",",5);
                if (regNum_tf.getText().equals(attributes[0])){
                    flag = true;
                    break;
                }
            }
            input.close();
        } catch(IOException ex){
            System.out.println("Failed to check if Reg No. exists....");
        }
        if (!flag){
            JOptionPane.showMessageDialog(null, "Reg No. does not exist, try again!","Invalid inputs", JOptionPane.INFORMATION_MESSAGE);
        }
        
        return flag;
    }
    
    private boolean carBrandExists(){
        boolean flag = false;
        BufferedReader input;//if FileReader fails to read a file, it returns null as well
        String filePath;
        try {
            filePath = new File("").getAbsolutePath();
            filePath = filePath.concat("\\data\\Admin\\Cars.txt");
            input = new BufferedReader(new FileReader(filePath));
            //validate if car brand exist in Cars.txt
            String record;
            String[] attributes;
            while((record = input.readLine()) != null){
                attributes = record.split(",",5);
                if (brand_tf.getText().equals(attributes[1])){
                    flag = true;
                    break;
                }
            }
            input.close();
        } catch(IOException ex){
            System.out.println("Failed to check if car brand exists....");
        }
        if (!flag){
            JOptionPane.showMessageDialog(null, "Car brand does not exist, try again!","Invalid inputs", JOptionPane.INFORMATION_MESSAGE);
        }
        
        return flag;
    }
    
    
    // ensure that given discount rate is between 10% to 50%.
    private boolean discountRateIsValid(){
        int discount_rate;
        try {
            discount_rate = Integer.parseInt(discount_tf.getText());
        } catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Invalid discount rate given!","Invalid inputs", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        if (discount_rate < 10 || discount_rate > 50){
            JOptionPane.showMessageDialog(null, "Discount rate should be between 10% and 50%","Invalid inputs", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }
    
    @Override 
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == apply_but){
            if (infoIsComplete()){
                if (!regNum_tf.getText().equals("")){
                    if (regNumExists() && discountRateIsValid()){
                        //Discount applied for a particular car
                        applyDiscounts(regNum_tf.getText(), "", discount_tf.getText());
                    }
                } else if(!brand_tf.getText().equals("")){
                    if (carBrandExists() && discountRateIsValid()){
                       //Discount applied for a category
                       applyDiscounts("", brand_tf.getText(), discount_tf.getText());
                    }
                }
            }  
        }
    }
}
