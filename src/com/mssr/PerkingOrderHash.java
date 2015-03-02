

package com.mssr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PerkingOrderHash extends JFrame {

private JPanel contentPane;
private JTextField textField;
private JTextField textField_1;

/**
* Launch the application.
*/
public static void main(String[] args) {
EventQueue.invokeLater(new Runnable() {
public void run() {
try {
PerkingOrderHash frame = new PerkingOrderHash();
frame.setVisible(true);
} catch (Exception e) {
e.printStackTrace();
}
}
});
}

/**
* Create the frame.
*/
public PerkingOrderHash() {
setTitle("Pecking Order Hash (POH)     ---  A New Hashing Algorithm     By Siva Srinivasa Rao Mothukuri");
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setBounds(100, 100, 653, 233);
contentPane = new JPanel();
contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
setContentPane(contentPane);
contentPane.setLayout(null);

JLabel lblInputString = new JLabel("Input String ::");
lblInputString.setFont(new Font("Tahoma", Font.PLAIN, 13));
lblInputString.setBounds(12, 26, 122, 31);
contentPane.add(lblInputString);

textField = new JTextField();
textField.setForeground(Color.RED);
textField.setFont(new Font("Tahoma", Font.PLAIN, 13));
textField.setBounds(107, 29, 522, 25);
contentPane.add(textField);
textField.setColumns(10);

JLabel lblOutputHash = new JLabel("Output Hash ::");
lblOutputHash.setFont(new Font("Tahoma", Font.PLAIN, 13));
lblOutputHash.setBounds(10, 139, 122, 31);
contentPane.add(lblOutputHash);

JButton btnGeneratePohHash = new JButton("Generate POH hash");
btnGeneratePohHash.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent actionevent) {
String input = textField.getText();
String output="";
POH poh = new POH();
try {
output=poh.performOperation(input);
} catch (UnsupportedEncodingException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}

textField_1.setText(output);
}
});
btnGeneratePohHash.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
btnGeneratePohHash.setBounds(334, 66, 262, 31);
contentPane.add(btnGeneratePohHash);

JButton btnClear = new JButton("Clear");
btnClear.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent actionevent) {

textField.setText("");
textField_1.setText("");

}
});
btnClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
btnClear.setBounds(142, 69, 180, 25);
contentPane.add(btnClear);

textField_1 = new JTextField();
textField_1.setBounds(105, 141, 522, 26);
contentPane.add(textField_1);
textField_1.setForeground(Color.BLUE);
textField_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
textField_1.setColumns(10);
}
}
