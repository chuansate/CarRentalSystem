/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author User
 */
public class CarTable extends JTable{
    private static String[][] car_records;
    private int num_records;
    private int num_fields = 5;
    private boolean found;
    CarTable() {
        num_records = 0;
        //open file
        //System.out.println("this = " + car_records.length);
        String filePath;
        BufferedReader input;
        try {
            filePath = new File("").getAbsolutePath();
            filePath = filePath.concat("\\data\\Admin\\Cars.txt");
            input = new BufferedReader(new FileReader(filePath));

            String record;
            String[] attributes;
            attributes = new String[num_fields];
            while((record = input.readLine()) != null){
                num_records++;
            }
            input.close();
        
            car_records = new String[num_records][num_fields];
            int i=0;
            input = new BufferedReader(new FileReader(filePath));

            while((record = input.readLine()) != null){
                if (record.isEmpty()){//becoz readLine() will read nothing after last record, and then causing ERR
                    break;
                }
                attributes = record.split(",", num_fields);
                for (int j=0; j<num_fields; j++){
                    car_records[i][j] = attributes[j];
                }
                i++;
            }
            input.close();
        } catch(IOException e){
            JOptionPane.showMessageDialog(null, "Failed to display car records!", "Display errors", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    CarTable(String regNum){
        num_records = 1;
        //open file
        //System.out.println("this = " + car_records.length);
        String filePath;
        BufferedReader input = null;
        found = false;
        try {
            filePath = new File("").getAbsolutePath();
            filePath = filePath.concat("\\data\\Admin\\Cars.txt");
            String record;
            String[] attributes;
            attributes = new String[num_fields];
            
            car_records = new String[num_records][num_fields];
            input = new BufferedReader(new FileReader(filePath));

            while((record = input.readLine()) != null){
                if (record.isEmpty()){//becoz readLine() will read nothing after last record, and then causing ERR
                    break;
                }
                attributes = record.split(",", num_fields);
                if (attributes[0].equals(regNum)){
                    for (int j=0; j<num_fields; j++){
                        car_records[0][j] = attributes[j];
                    }
                    found = true;
                    break;
                }
            }
            input.close();
        } catch(IOException e){
            JOptionPane.showMessageDialog(null, "Failed to display car records!", "Display errors", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    CarTable(String brand, String lower_lim, String upper_lim){
        int lower_lim_int = 0;
        int upper_lim_int = 0;
        String filePath;
        num_records = 0;
        BufferedReader input = null;
        found = false;
        int cur_rec_num = 0;
        //Filter brand only
        if (!brand.equals("") && (lower_lim.equals("") && upper_lim.equals(""))){
            try {
                filePath = new File("").getAbsolutePath();
                filePath = filePath.concat("\\data\\Admin\\Cars.txt");
                input = new BufferedReader(new FileReader(filePath));
                String record;
                String[] attributes;
                attributes = new String[num_fields];
                while((record = input.readLine()) != null){
                    attributes = record.split(",", num_fields);
                    if (attributes[1].equals(brand)){
                        num_records++;
                    }
                }
                System.out.println("num_records which match the brand = " + num_records);
                input.close();
                
                car_records = new String[num_records][num_fields];
                input = new BufferedReader(new FileReader(filePath));
                while((record = input.readLine()) != null){
                    if (cur_rec_num == num_records){
                        break;
                    }
                    if (record.isEmpty()){//becoz readLine() will read nothing after last record, and then causing ERR
                        break;
                    }
                    attributes = record.split(",", num_fields);
                    if (attributes[1].equals(brand)){
                        for (int j=0; j<num_fields; j++){
                            car_records[cur_rec_num][j] = attributes[j];
                        }
                        found = true;
                        cur_rec_num++;
                    }
                    
                }
                input.close();
                
            } catch(IOException e){
                JOptionPane.showMessageDialog(null, "Failed to display car records!", "Display errors", JOptionPane.INFORMATION_MESSAGE);
            }
        }else if(brand.equals("") && (!lower_lim.equals("") && !upper_lim.equals(""))){//Filter rentalFeePerDay only
            try {
                lower_lim_int = Integer.parseInt(lower_lim);
                upper_lim_int = Integer.parseInt(upper_lim);
            }catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter only digits!", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (upper_lim_int <= 0 || lower_lim_int <= 0){
                JOptionPane.showMessageDialog(null, "Rental fee cannot be 0 or negative!", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            //Ensure that the upper_lim > lower_lim
            if (upper_lim_int <= lower_lim_int){
                JOptionPane.showMessageDialog(null, "Upper limit of the rental fee should be higher than lower limit!", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                filePath = new File("").getAbsolutePath();
                filePath = filePath.concat("\\data\\Admin\\Cars.txt");
                input = new BufferedReader(new FileReader(filePath));

                String record;
                String[] attributes;
                attributes = new String[num_fields];
                while((record = input.readLine()) != null){
                    attributes = record.split(",", num_fields);
                    if (Integer.parseInt(attributes[4]) >= lower_lim_int && Integer.parseInt(attributes[4]) <= upper_lim_int){
                        num_records++;
                    }
                }
                System.out.println("num_records which match the rentalFee range = " + num_records);
                input.close();
                
                car_records = new String[num_records][num_fields];
                input = new BufferedReader(new FileReader(filePath));
                while((record = input.readLine()) != null){
                    if (cur_rec_num == num_records){
                        break;
                    }
                    if (record.isEmpty()){//becoz readLine() will read nothing after last record, and then causing ERR
                        break;
                    }
                    attributes = record.split(",", num_fields);
                    if (Integer.parseInt(attributes[4]) >= lower_lim_int && Integer.parseInt(attributes[4]) <= upper_lim_int){
                        for (int j=0; j<num_fields; j++){
                            car_records[cur_rec_num][j] = attributes[j];
                        }
                        found = true;
                        cur_rec_num++;
                    }
                    
                }
                input.close();
                
            } catch(IOException e){
                JOptionPane.showMessageDialog(null, "Failed to display car records!", "Display errors", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
        }else if(!brand.equals("") && !lower_lim.equals("") && !upper_lim.equals("")){
            try {
                lower_lim_int = Integer.parseInt(lower_lim);
                upper_lim_int = Integer.parseInt(upper_lim);
            }catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter only digits!", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (upper_lim_int <= 0 || lower_lim_int <= 0){
                JOptionPane.showMessageDialog(null, "Rental fee cannot be 0 or negative!", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            //Ensure that the upper_lim > lower_lim
            if (upper_lim_int <= lower_lim_int){
                JOptionPane.showMessageDialog(null, "Upper limit of the rental fee should be higher than lower limit!", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            try {
                filePath = new File("").getAbsolutePath();
                filePath = filePath.concat("\\data\\Admin\\Cars.txt");
                input = new BufferedReader(new FileReader(filePath));
                String record;
                String[] attributes;
                attributes = new String[num_fields];
                while((record = input.readLine()) != null){
                    attributes = record.split(",", num_fields);
                    if (attributes[1].equals(brand) && (Integer.parseInt(attributes[4]) >= lower_lim_int && Integer.parseInt(attributes[4]) <= upper_lim_int)){
                        num_records++;
                    }
                }
                System.out.println("num_records which match both = " + num_records);
                input.close();
                car_records = new String[num_records][num_fields];
                input = new BufferedReader(new FileReader(filePath));
                while((record = input.readLine()) != null){
                    if (cur_rec_num == num_records){
                        break;
                    }
                    if (record.isEmpty()){//becoz readLine() will read nothing after last record, and then causing ERR
                        break;
                    }
                    attributes = record.split(",", num_fields);
                    if (attributes[1].equals(brand) && (Integer.parseInt(attributes[4]) >= lower_lim_int && Integer.parseInt(attributes[4]) <= upper_lim_int)){
                        for (int j=0; j<num_fields; j++){
                            car_records[cur_rec_num][j] = attributes[j];
                        }
                        found = true;
                        cur_rec_num++;
                    }
                    
                }
                input.close();
                
            } catch(IOException e){
                JOptionPane.showMessageDialog(null, "Failed to display car records!", "Display errors", JOptionPane.INFORMATION_MESSAGE);
            }
        }else {
            JOptionPane.showMessageDialog(null, "Invalid filters applied! Try either filter based on brand, or rental fee, or both.", "Invalid filters", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    
    public String[][] getCarRecords(){
        return this.car_records;
    }
    
    
    public int getNumRec(){
        return this.num_records;
    }
    
    public int getNumField(){
        return this.num_fields;
    }
    
    public boolean getFound(){
        return this.found;
    }
}
