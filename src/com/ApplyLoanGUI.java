package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.mongodb.client.*;
import org.bson.Document;

public class ApplyLoanGUI extends JFrame implements ActionListener {

    int id;
    JTextField amountField, purposeField;
    JButton submit, back;

    public ApplyLoanGUI(int id){

        this.id = id;

        setTitle("Apply Loan");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel(new GridLayout(5,2,20,20));
        panel.setBorder(BorderFactory.createEmptyBorder(100,200,100,200));

        amountField = new JTextField();
        purposeField = new JTextField();

        submit = new JButton("Submit");
        back = new JButton("Back");

        panel.add(new JLabel("Amount:"));
        panel.add(amountField);

        panel.add(new JLabel("Purpose:"));
        panel.add(purposeField);

        panel.add(submit);
        panel.add(back);

        add(panel);

        submit.addActionListener(this);
        back.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource()==submit){

            MongoDatabase db = MongoDBConnection.getDB();
            MongoCollection<Document> col = db.getCollection("loans");

            col.insertOne(new Document("student_id", id)
                    .append("amount", Integer.parseInt(amountField.getText()))
                    .append("purpose", purposeField.getText())
                    .append("status", "Pending"));

            JOptionPane.showMessageDialog(this,"Loan Applied!");

            new StudentDashboard(id); // 🔥 redirect
            this.dispose();
        }

        if(e.getSource()==back){
            new StudentDashboard(id);
            this.dispose();
        }
    }
}