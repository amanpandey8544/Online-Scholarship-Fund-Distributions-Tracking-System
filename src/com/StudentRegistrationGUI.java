package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import com.mongodb.client.*;
import org.bson.Document;

public class StudentRegistrationGUI extends JFrame implements ActionListener {

    JTextField id,name,dob,email,phone,fatherName,fatherOcc,fatherIncome,address,college,course,start,end;
    JPasswordField password; // 🔥 NEW
    JComboBox<String> gender,category;
    JButton submit, back;

    String aadhar="",bank="",photo="",sign="",bonafide="";

    JPanel panel;

    public StudentRegistrationGUI(){

        setTitle("Student Registration");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240,248,255));

        JScrollPane scroll = new JScrollPane(panel);
        add(scroll);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 18);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 18);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,20,10,20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        // 🔙 BACK BUTTON
        back = new JButton("← Back");
        back.setFont(new Font("Segoe UI", Font.BOLD, 16));
        back.setBackground(Color.DARK_GRAY);
        back.setForeground(Color.WHITE);

        gbc.gridx=0; gbc.gridy=y;
        panel.add(back, gbc);

        back.addActionListener(e -> {
            new StudentGUI();
            this.dispose();
        });

        // 🔥 TITLE
        JLabel title = new JLabel("STUDENT REGISTRATION FORM");
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setForeground(new Color(0,102,204));

        gbc.gridx=1; gbc.gridy=y++;
        panel.add(title, gbc);

        id = addField("ID", y++, labelFont, fieldFont);
        name = addField("Name", y++, labelFont, fieldFont);
        dob = addField("DOB", y++, labelFont, fieldFont);

        // 🔐 PASSWORD FIELD
        password = new JPasswordField(30);
        password.setFont(fieldFont);

        gbc.gridx=0; gbc.gridy=y;
        JLabel pl = new JLabel("Password");
        pl.setFont(labelFont);
        panel.add(pl, gbc);

        gbc.gridx=1;
        panel.add(password, gbc);
        y++;

        gender = new JComboBox<>(new String[]{"Male","Female"});
        styleCombo(gender);
        addCombo("Gender", gender, y++, labelFont);

        category = new JComboBox<>(new String[]{"General","OBC","SC/ST"});
        styleCombo(category);
        addCombo("Category", category, y++, labelFont);

        email = addField("Email", y++, labelFont, fieldFont);
        phone = addField("Phone", y++, labelFont, fieldFont);

        fatherName = addField("Father Name", y++, labelFont, fieldFont);
        fatherOcc = addField("Father Occupation", y++, labelFont, fieldFont);
        fatherIncome = addField("Father Income", y++, labelFont, fieldFont);

        address = addField("Permanent Address", y++, labelFont, fieldFont);
        college = addField("College Name", y++, labelFont, fieldFont);

        course = addField("Course Name", y++, labelFont, fieldFont);
        start = addField("Course Start Date", y++, labelFont, fieldFont);
        end = addField("Course End Date", y++, labelFont, fieldFont);

        upload("Aadhar Card", "aadhar", y++, labelFont);
        upload("Bank Passbook", "bank", y++, labelFont);
        upload("Student Photo", "photo", y++, labelFont);
        upload("Signature", "sign", y++, labelFont);
        upload("Bonafide Letter", "bonafide", y++, labelFont);

        // 🔥 SUBMIT BUTTON
        gbc.gridx=0; gbc.gridy=y; gbc.gridwidth=2;

        submit = new JButton("SUBMIT");
        submit.setFont(new Font("Segoe UI", Font.BOLD, 22));
        submit.setBackground(new Color(0,153,76));
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.setPreferredSize(new Dimension(250,60));

        panel.add(submit, gbc);

        submit.addActionListener(this);

        setVisible(true);
    }

    JTextField addField(String label, int y, Font labelFont, Font fieldFont){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,20,10,20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx=0; gbc.gridy=y;
        JLabel l = new JLabel(label);
        l.setFont(labelFont);
        panel.add(l, gbc);

        gbc.gridx=1;
        JTextField t = new JTextField(30);
        t.setFont(fieldFont);
        t.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        panel.add(t, gbc);

        return t;
    }

    void addCombo(String label, JComboBox box, int y, Font labelFont){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,20,10,20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx=0; gbc.gridy=y;
        JLabel l = new JLabel(label);
        l.setFont(labelFont);
        panel.add(l, gbc);

        gbc.gridx=1;
        panel.add(box, gbc);
    }

    void styleCombo(JComboBox box){
        box.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    }

    void upload(String label,String type,int y, Font labelFont){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,20,10,20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx=0; gbc.gridy=y;
        JLabel l = new JLabel(label);
        l.setFont(labelFont);
        panel.add(l, gbc);

        gbc.gridx=1;
        JButton btn = new JButton("Upload");
        btn.setBackground(new Color(0,102,204));
        btn.setForeground(Color.WHITE);

        panel.add(btn, gbc);

        btn.addActionListener(e -> {
            JFileChooser ch = new JFileChooser();
            if(ch.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){
                File f = ch.getSelectedFile();

                if(type.equals("aadhar")) aadhar = f.getAbsolutePath();
                if(type.equals("bank")) bank = f.getAbsolutePath();
                if(type.equals("photo")) photo = f.getAbsolutePath();
                if(type.equals("sign")) sign = f.getAbsolutePath();
                if(type.equals("bonafide")) bonafide = f.getAbsolutePath();

                btn.setText("Uploaded ✅");
            }
        });
    }

    public void actionPerformed(ActionEvent e){

        MongoCollection<Document> col =
                MongoDBConnection.getDB().getCollection("students");

        Document d = new Document("id", Integer.parseInt(id.getText()))
                .append("name", name.getText())
                .append("dob", dob.getText())
                .append("password", new String(password.getPassword())) // 🔥 ADDED
                .append("gender", gender.getSelectedItem())
                .append("category", category.getSelectedItem())
                .append("email", email.getText())
                .append("phone", phone.getText())
                .append("fatherName", fatherName.getText())
                .append("fatherOcc", fatherOcc.getText())
                .append("fatherIncome", fatherIncome.getText())
                .append("address", address.getText())
                .append("college", college.getText())
                .append("course", course.getText())
                .append("start", start.getText())
                .append("end", end.getText())
                .append("aadhar", aadhar)
                .append("bank", bank)
                .append("photo", photo)
                .append("sign", sign)
                .append("bonafide", bonafide);

        col.insertOne(d);

        JOptionPane.showMessageDialog(this,"Registration Successful!");

        new StudentGUI();
        this.dispose();
    }
}