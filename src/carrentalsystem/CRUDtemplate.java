/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
//Admin has ability to CRUD on cars
//Abstract class for the four windows: AddCar, EditCar, DeleteCar, SearchCar(based on each field). These windows are redirected from AdminMainPage(do not dispose the AdminMainPage after clicking on CRUD button)
public abstract class CRUDtemplate {
    protected final int WINDOW_WIDTH = 900;
    protected final int WINDOW_HEIGHT = 900;
    protected JFrame frame;
    protected JScrollPane car_records;//for displaying list of car records with scroll bar 
    protected JPanel panel;
    protected CarTable carTable;
    protected JLabel title;
    protected JLabel regNum_lb;
    protected JLabel brand_lb;
    protected JLabel model_lb;
    protected JLabel quantity_lb;   //TRY TO MODIFY TO QUANTITY_LB
    protected JLabel rentalFeePerDay_lb;
    protected JTextField regNum_tf;
    protected JTextField brand_tf;
    protected JTextField model_tf;
    protected JTextField quantity_tf;
    protected JTextField rentalFeePerDay_tf;
    protected JButton CRUD_but;
    protected JTable table;
    CRUDtemplate() {
        panel = new JPanel();
        frame = new JFrame();
        // Data to be displayed in the JTable
        carTable = new CarTable();
        // Column Names
        String[] columnNames = { "Reg No.", "Brand", "Model", "Quantity", "RentalFee/Day"};
        table = new JTable(carTable.getCarRecords(), columnNames);
        table.setBounds(0, 0, 600, 900);
        table.setDefaultEditor(Object.class, null);
        car_records = new JScrollPane(table);
        car_records.setBounds(0, 0, 600, 850);
        panel.add(car_records);
        panel.setBounds(0, 0, WINDOW_WIDTH,WINDOW_HEIGHT);
        
        title = new JLabel("Create New Car");
        title.setBounds(600, 50, 300, 30);
        title.setFont(new Font(null, Font.ROMAN_BASELINE, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        
        regNum_lb = new JLabel("Reg No.:");
        regNum_lb.setBounds(625, 150, 50, 25);
        
        regNum_tf = new JTextField();
        regNum_tf.setBounds(675, 150, 150, 25);
        
        brand_lb = new JLabel("Brand:");
        brand_lb.setBounds(625, 200, 50, 25);
        
        model_lb = new JLabel("Model:");
        model_lb.setBounds(625, 250, 50, 25);

        quantity_lb = new JLabel("Quantity:");
        quantity_lb.setBounds(625, 300, 75, 25);
        
        rentalFeePerDay_lb = new JLabel("Rental Fee/day:");
        rentalFeePerDay_lb.setBounds(625, 350, 100, 25);
        
        brand_tf = new JTextField();
        brand_tf.setBounds(675, 200, 150, 25);
        
        model_tf = new JTextField();
        model_tf.setBounds(675, 250, 150, 25);
       
        quantity_tf = new JTextField();
        quantity_tf.setBounds(675, 300, 150, 25);
        
        rentalFeePerDay_tf = new JTextField();
        rentalFeePerDay_tf.setBounds(725, 350, 50, 25);
        
        CRUD_but = new JButton("Create");
        CRUD_but.setBounds(750, 400, 100, 25);
        panel.add(regNum_lb);
        panel.add(regNum_tf);panel.add(brand_lb);
        panel.add(model_lb);panel.add(quantity_tf);panel.add(rentalFeePerDay_lb);
        panel.add(brand_tf);panel.add(model_tf);panel.add(quantity_lb);
        panel.add(rentalFeePerDay_tf);panel.add(CRUD_but);
        panel.add(title);
        panel.setLayout(null);
        frame.add(panel);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    //must be overriden by child class: 4 windows will implement C, R, U, D respectively
    public abstract void modifyCarRecords(String regNum, String brand, String model, String quantity, String rentalFeePerDay);
    
}

class CreateNewCarPage extends CRUDtemplate implements ActionListener{
    private boolean write_success;//indicate whether writing into Cars.txt is successful
    CreateNewCarPage() {
        CRUD_but.addActionListener(this);
        frame.setTitle("Create New Car");
    }
    
    //Creating cars
    @Override
    public void modifyCarRecords(String regNum, String brand, String model, String quantity, String rentalFeePerDay){
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("\\data\\Admin\\Cars.txt");
        FileWriter output;
        try {
            output = new FileWriter(filePath, true);
            output.write(regNum + "," + brand + "," + model + "," + quantity + "," + rentalFeePerDay + "\n");
            output.close();
            write_success = true;
        }catch(IOException ex){
            write_success = false;
        }      
    };
    
    protected int validateQuantity() throws NumberFormatException{
        int quantity;
        quantity = Integer.parseInt(quantity_tf.getText());
        if (quantity < 0){
            quantity = quantity * -1;
            JOptionPane.showMessageDialog(null, "Quantity is negative, converted to positive automatically!", "Auto Conversion", JOptionPane.INFORMATION_MESSAGE);
        } else if (quantity == 0){
            quantity = 1;
            JOptionPane.showMessageDialog(null, "Quantity is 0, converted to 1 automatically", "Auto Conversion", JOptionPane.INFORMATION_MESSAGE);
        }
        return quantity;
    }
    
    protected int validateRentalFeePDay() throws NumberFormatException{
        int rentalFeePerDay = Integer.parseInt(rentalFeePerDay_tf.getText());    
        if (rentalFeePerDay < 0){
            rentalFeePerDay = rentalFeePerDay * -1;
            JOptionPane.showMessageDialog(null, "Rental fee is negative, converted to positive automatically!", "Auto Conversion", JOptionPane.INFORMATION_MESSAGE);
        } else if (rentalFeePerDay == 0){
            rentalFeePerDay = 1;
            JOptionPane.showMessageDialog(null, "Rental fee is 0, converted to positive automatically!", "Auto Conversion", JOptionPane.INFORMATION_MESSAGE);
        }
        
        return rentalFeePerDay;
    }
    
    // return TRUE if the input info are complete, otherwise return FALSE
    private boolean infoIsComplete(){
        if (regNum_tf.getText().equals("")){
            return false;
        }
        if (brand_tf.getText().equals("")){
            return false;
        }
        
        if (model_tf.getText().equals("")){
            return false;
        }
        
        if (quantity_tf.getText().equals("")){
            return false;
        }
        
        if (rentalFeePerDay_tf.getText().equals("")){
            return false;
        }
        return true;
    }
    
    @Override 
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == CRUD_but){
            String regNum = regNum_tf.getText();
            String brand = brand_tf.getText();
            String model = model_tf.getText();
            int quantity; 
            int rentalFeePerDay;
            
            if (!infoIsComplete()){
                JOptionPane.showMessageDialog(null, "Please fill in all the details.", "Insufficient input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // validate if given quantity is valid
            try {
                quantity = validateQuantity();
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Invalid quantity is given.", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            //VALIDATE IF INPUT regNum is duplicated in txt files.
            for (int i=0; i<carTable.getNumRec(); i++){
                if (carTable.getCarRecords()[i][0].equals(regNum)){
                    JOptionPane.showMessageDialog(null, "Reg No. existed. Please try another Reg No.", "Duplication Warning", JOptionPane.WARNING_MESSAGE);
                    return ;
                }     
            }
            // validate if given rentalFee is valid
            try {
                rentalFeePerDay = validateRentalFeePDay();     
            }catch (NumberFormatException ex){       
                JOptionPane.showMessageDialog(null, "Invalid rental fee per day is given.", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            //Write inputs into Cars.txt
            modifyCarRecords(regNum, brand, model, Integer.toString(quantity), Integer.toString(rentalFeePerDay));
            if (write_success){
                JOptionPane.showMessageDialog(null, "Car is added successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }else {
                JOptionPane.showMessageDialog(null, "Failed to add the car, try again!", "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    } 
}

class ReadCarPage extends CRUDtemplate implements ActionListener {
    //Reading cars
    private JTextField rentalFeePerDay_tf2;
    private JLabel dash;
    private JButton filter_but;
    ReadCarPage() {
        title.setText("Search Car(s)");
        rentalFeePerDay_lb.setBounds(625, 250, 100, 25);
        Component[] componentList = panel.getComponents();
        for (Component c: componentList){
            if (c.equals(model_lb) ){
                panel.remove(c);
            }else if(c.equals(quantity_lb)){
                panel.remove(c);
            }else if(c.equals(model_tf)){
                panel.remove(c);
            }else if(c.equals(quantity_tf)){
                panel.remove(c);
            }
        }
        regNum_tf.setBounds(675, 150, 100, 25);
        rentalFeePerDay_tf.setBounds(730, 250, 50, 25);
        brand_tf.setBounds(675, 200, 100, 25);
        dash = new JLabel("-");
        dash.setBounds(785, 250, 10, 25);
        rentalFeePerDay_tf2 = new JTextField();
        rentalFeePerDay_tf2.setBounds(800, 250, 50, 25);
        CRUD_but.setText("Search");
        CRUD_but.setBounds(780, 150, 100, 25);
        CRUD_but.addActionListener(this);
        filter_but = new JButton("Filter");
        filter_but.setBounds(750, 300, 100, 25);
        filter_but.addActionListener(this);
        panel.add(dash); panel.add(rentalFeePerDay_tf2);panel.add(filter_but);
        frame.setTitle("Search Car(s)");
    }
    
    @Override
    public void modifyCarRecords(String regNum, String brand, String model, String quantity, String rentalFeePerDay){
        if (!regNum.equals("")){
            carTable = new CarTable(regNum);
            if (!carTable.getFound()){
                JOptionPane.showMessageDialog(null, "No car with Reg No. of " + regNum + "!", "Record does not exist", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            //Just open another window, then display the table of matched records in the new window
            SearchList searchLi = new SearchList(carTable.getCarRecords());
        }else {
            JOptionPane.showMessageDialog(null, "No car matches that Reg Num!", "Record(s) does not exist", JOptionPane.INFORMATION_MESSAGE);
        }
        
    };
    
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == CRUD_but){
            String regNum = regNum_tf.getText();
            modifyCarRecords(regNum, "", "", "", "");
            
        } else if(e.getSource() == filter_but){
            String brand = brand_tf.getText();
            String low_lim = rentalFeePerDay_tf.getText();
            String high_lim = rentalFeePerDay_tf2.getText();
            carTable = new CarTable(brand, low_lim, high_lim);
            if (!carTable.getFound()){
                JOptionPane.showMessageDialog(null, "No car(s) matches the filter!", "Record(s) does not exist", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            SearchList searchLi = new SearchList(carTable.getCarRecords());
            
        }
    }
}

class UpdateCarPage extends CreateNewCarPage {
    private JLabel explanation_lb;
    private JLabel explanation_lb2;
    private boolean found; // indicate whether the input regNum is found
    UpdateCarPage() {
        explanation_lb = new JLabel("Update a car based on its Reg No.");
        explanation_lb.setBounds(625, 80, 200, 25);
        found = false; 
        explanation_lb2 = new JLabel("Leaving a field blank means unchanged.");
        explanation_lb2.setBounds(625, 110, 250, 25);
        CRUD_but.setText("Update");
        
        title.setText("Update a Car");
        frame.setTitle("Update Car");
        panel.add(explanation_lb);panel.add(explanation_lb2);
    }
    //Updating cars
    @Override
    public void modifyCarRecords(String regNum, String brand, String model, String quantity, String rentalFeePerDay){
        //update a car based on its Reg No.
        this.found = false;
        for (int i=0; i<carTable.getNumRec(); i++){
            if (carTable.getCarRecords()[i][0].equals(regNum)){
                //if found, update the record
                if (!brand.equals("")){
                    carTable.getCarRecords()[i][1] = brand;
                }
                if (!model.equals("")){
                    carTable.getCarRecords()[i][2] = model;
                }
                if (!quantity.equals("-999")){// if it does not equal default value, then update its quantity
                    carTable.getCarRecords()[i][3] = quantity;
                }
                if (!rentalFeePerDay.equals("-999")){// if it does not equal default value, then update its quantity
                    carTable.getCarRecords()[i][4] = rentalFeePerDay;
                }
                this.found = true;
                break;      
            } 
        }
        if (!found){
            JOptionPane.showMessageDialog(null, "Reg No. does not exist! Try again!", "Message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // Search for the Reg No. in car_records, then update the info. Rewrite the file Cars.txt
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("\\data\\Admin\\Cars.txt"); 
        FileWriter output;
        try {
            output = new FileWriter(filePath);
            for (int j=0; j<carTable.getNumRec(); j++){
                output.write(carTable.getCarRecords()[j][0] + "," + carTable.getCarRecords()[j][1] + "," + carTable.getCarRecords()[j][2] + "," + carTable.getCarRecords()[j][3] + "," + carTable.getCarRecords()[j][4] + "\n");
            }
            output.close();
            JOptionPane.showMessageDialog(null, "Car is updated successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        }catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Updating failed, please try again!", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    };
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == CRUD_but){
            String regNum = regNum_tf.getText();
            String brand = brand_tf.getText();
            String model = model_tf.getText();
            int rentalFeePerDay; // initialize it with -1, so that it can be left unchanged
            int quantity;
            
            if (regNum.equals("")){
                JOptionPane.showMessageDialog(null, "Please insert Reg No.", "Insufficient input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // validate if given quantity is valid
            try {
                if (quantity_tf.getText().equals("")){
                    quantity = -999; // give a default value which means it remains unchanged
                } else{
                    quantity = validateQuantity();
                }       
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Invalid quantity is given.", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
           
            // validate if given rentalFee is valid
            try {
                if (rentalFeePerDay_tf.getText().equals("")){
                    rentalFeePerDay = -999; // give a default value which means it remains unchanged
                } else {
                    rentalFeePerDay = validateRentalFeePDay(); 
                }     
            }catch (NumberFormatException ex){       
                JOptionPane.showMessageDialog(null, "Invalid rental fee per day is given.", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            //Write inputs into Cars.txt
            modifyCarRecords(regNum, brand, model, Integer.toString(quantity), Integer.toString(rentalFeePerDay));       
        }
    }
}

class DeleteCarPage extends CreateNewCarPage {
    private JLabel explanation_lb;
    private JLabel explanation_lb2;
    DeleteCarPage() {
        title.setText("Delete Car(s)"); 
        frame.setTitle("Delete Car(s)");
        explanation_lb = new JLabel("Delete a car based on its Reg No.");
        explanation_lb.setBounds(625, 80, 200, 25);
        explanation_lb2 = new JLabel("Or delete car(s) based on a field.");
        explanation_lb2.setBounds(625, 110, 250, 25);
        CRUD_but.setText("Delete");
        Component[] componentList = panel.getComponents();
        for (Component c: componentList){
            if (c.equals(quantity_lb) ){
                panel.remove(c);
            }else if(c.equals(quantity_tf)){
                panel.remove(c);
            }
        }
        
        rentalFeePerDay_lb.setBounds(625, 300, 100, 25);
        rentalFeePerDay_tf.setBounds(750, 300, 50, 25);
        panel.add(explanation_lb); panel.add(explanation_lb2);
    }
    //Deleting cars
    @Override
    public void modifyCarRecords(String regNum, String brand, String model, String quantity, String rentalFeePerDay){
        if (rentalFeePerDay.equals("0")){
            rentalFeePerDay = "";
        }
        
        int count = 0;
        String[] fields = {regNum, brand, model, quantity, rentalFeePerDay};
        int index_field = -1;
        //Since only allowing deletion based on a field, hence need to check if the received field is more than 1
        for (int i=0; i<fields.length; i++){
            if (!fields[i].equals("")){
                count = count + 1;
                index_field = i;
            }
            if (count > 1) {
                JOptionPane.showMessageDialog(null, "Deletion of records is based on one field only! Please leave those irrelevant fields blank.", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        if (count == 0){
            JOptionPane.showMessageDialog(null, "No field is given.", "Insufficient input", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
       
        String field = fields[index_field];
        FileWriter output = null;
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("\\data\\Admin\\Cars.txt");
        int count_deleted = 0;//indicates how many records are deleted
        try {
            output = new FileWriter(filePath);
            for (int i=0; i<carTable.getNumRec(); i++){
                if (!carTable.getCarRecords()[i][index_field].equals(field)){
                    output.write(carTable.getCarRecords()[i][0] + "," + carTable.getCarRecords()[i][1] + "," + carTable.getCarRecords()[i][2] + "," + carTable.getCarRecords()[i][3] + "," + carTable.getCarRecords()[i][4] + "\n");
                }else {
                    count_deleted++;
                }
            }
            output.close();
        }catch(IOException ex){
            System.out.println("Failed to delete the record(s)....");
            JOptionPane.showMessageDialog(null, "Deletion failed, please try again!", "Message", JOptionPane.INFORMATION_MESSAGE);
            return ;
        }
        
        if (count_deleted == 0){
            JOptionPane.showMessageDialog(null, "No record(s) matches the input field! No record(s) is deleted.", "Message", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Deleted the record(s) successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
        
    };
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == CRUD_but){
            String regNum = regNum_tf.getText();
            String brand = brand_tf.getText();
            String model = model_tf.getText();
        
            int rentalFeePerDay = 0;
            boolean found = false;
            
            try {
                if (!rentalFeePerDay_tf.getText().equals("")){
                    rentalFeePerDay = Integer.parseInt(rentalFeePerDay_tf.getText());
                
                    if (rentalFeePerDay <= 0){
                        JOptionPane.showMessageDialog(null, "No records having negative or zero rental fee!", "Invalid input", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Rental fee is invalid!", "Wrong Inputs", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //check if the input Reg No. exists
            modifyCarRecords(regNum, brand, model, "", Integer.toString(rentalFeePerDay));
            
        } 
    }
}
