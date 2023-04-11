/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalsystem;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 *
 * @author User
 */
public class SearchList {
    private JFrame frame;
    private JScrollPane spane;
    private JPanel panel;
    private JLabel title; // state the filters: record(s) which match regNum, price range, brand
    private JTable table;
    SearchList(String[][] car_records) {
        panel = new JPanel();
        frame = new JFrame();
        String[] columnNames = { "Reg No.", "Brand", "Model", "Quantity", "RentalFee/Day"};
        table = new JTable(car_records, columnNames);
        table.setBounds(0, 100, 600, 450);
        table.setDefaultEditor(Object.class, null);
        spane = new JScrollPane(table);
        spane.setBounds(0, 100, 600, 450);
        panel.add(spane);
        panel.setBounds(0, 0, 600,600);
        
        title = new JLabel("Matched record(s):");
        title.setBounds(0, 0, 600, 100);
        title.setFont(new Font(null, Font.ROMAN_BASELINE, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        panel.add(title);
        panel.setLayout(null);
        frame.add(panel);
        frame.setTitle("Search List");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(615, 600);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
