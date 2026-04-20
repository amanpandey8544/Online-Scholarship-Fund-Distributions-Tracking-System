package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import com.mongodb.client.*;
import org.bson.Document;

public class LoanApplicationGUI extends JFrame implements ActionListener {

    int id;

    JTextField amount, purpose, income;
    JButton submit, aadharBtn, bankBtn;

    String aadharPath="", bankPath="";

    JPanel panel;

    public LoanApplicationGUI(int id){
        this.id = id;

        setTitle("Loan Application");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JScrollPane scroll = new JScrollPane(panel);
        add(scroll);

        Font labelFont = new Font("Arial", Font.BOLD, 20);
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15,15,15,15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        // TITLE
        JLabel title = new JLabel("LOAN APPLICATION FORM");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridx=0; gbc.gridy=y++; gbc.gridwidth=2;
        panel.add(title, gbc);

        gbc.gridwidth=1;

        // Amount
        gbc.gridx=0; gbc.gridy=y;
        panel.add(new JLabel("Loan Amount"), gbc);

        gbc.gridx=1;
        amount = new JTextField(30);
        amount.setFont(fieldFont);
        panel.add(amount, gbc);
        y++;

        // Purpose
        gbc.gridx=0; gbc.gridy=y;
        panel.add(new JLabel("Purpose"), gbc);

        gbc.gridx=1;
        purpose = new JTextField(30);
        purpose.setFont(fieldFont);
        panel.add(purpose, gbc);
        y++;

        // Income
        gbc.gridx=0; gbc.gridy=y;
        panel.add(new JLabel("Family Income"), gbc);

        gbc.gridx=1;
        income = new JTextField(30);
        income.setFont(fieldFont);
        panel.add(income, gbc);
        y++;

        // Aadhar Upload
        gbc.gridx=0; gbc.gridy=y;
        panel.add(new JLabel("Aadhar Card"), gbc);

        gbc.gridx=1;
        aadharBtn = new JButton("Upload");
        panel.add(aadharBtn, gbc);
        y++;

        // Bank Upload
        gbc.gridx=0; gbc.gridy=y;
        panel.add(new JLabel("Bank Proof"), gbc);

        gbc.gridx=1;
        bankBtn = new JButton("Upload");
        panel.add(bankBtn, gbc);
        y++;

        // Submit Button
        gbc.gridx=0; gbc.gridy=y; gbc.gridwidth=2;

        submit = new JButton("SUBMIT APPLICATION");
        submit.setFont(new Font("Arial", Font.BOLD, 22));
        submit.setPreferredSize(new Dimension(300,60));

        panel.add(submit, gbc);

        // Upload Actions
        aadharBtn.addActionListener(e -> {
            JFileChooser ch = new JFileChooser();
            if(ch.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
                File f = ch.getSelectedFile();
                aadharPath = f.getAbsolutePath();
                aadharBtn.setText("Uploaded ✅");
            }
        });

        bankBtn.addActionListener(e -> {
            JFileChooser ch = new JFileChooser();
            if(ch.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
                File f = ch.getSelectedFile();
                bankPath = f.getAbsolutePath();
                bankBtn.setText("Uploaded ✅");
            }
        });

        submit.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){

        MongoCollection<Document> col =
                MongoDBConnection.getDB().getCollection("loans");

        col.insertOne(new Document("student_id", id)
                .append("amount", Integer.parseInt(amount.getText()))
                .append("purpose", purpose.getText())
                .append("income", income.getText())
                .append("aadhar", aadharPath)
                .append("bank", bankPath)
                .append("status", "Pending")
                .append("approvedAmount", 0));

        JOptionPane.showMessageDialog(this,"Loan Applied Successfully!");

// 🔥 Redirect back to Dashboard
        new StudentDashboard(id);

        this.dispose();

        this.dispose();
    }
}