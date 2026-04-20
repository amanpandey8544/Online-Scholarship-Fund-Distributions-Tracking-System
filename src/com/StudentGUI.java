package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.mongodb.client.*;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

public class StudentGUI extends JFrame implements ActionListener {

    JTextField id;
    JPasswordField pass;
    JButton login, back;

    public StudentGUI(){

        setTitle("Student Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // 🌈 Background Panel
        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(new Color(230, 240, 255));
        add(bg);

        // 🔙 BACK BUTTON (Top Left)
        back = new JButton("← Back");
        back.setFont(new Font("Segoe UI", Font.BOLD, 16));
        back.setBackground(Color.DARK_GRAY);
        back.setForeground(Color.WHITE);

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.anchor = GridBagConstraints.NORTHWEST;
        gbcMain.insets = new Insets(20,20,0,0);
        bg.add(back, gbcMain);

        back.addActionListener(e -> {
            new LoginGUI(); // 🔥 previous page (change if needed)
            this.dispose();
        });

        // 🧾 LOGIN CARD PANEL
        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(500,400));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,2));

        GridBagConstraints gbcCard = new GridBagConstraints();
        gbcCard.insets = new Insets(15,15,15,15);
        gbcCard.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 18);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 18);

        // 🔥 TITLE
        JLabel title = new JLabel("STUDENT LOGIN", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(0,102,204));

        gbcCard.gridx=0; gbcCard.gridy=0; gbcCard.gridwidth=2;
        card.add(title, gbcCard);

        gbcCard.gridwidth=1;

        // ID
        gbcCard.gridx=0; gbcCard.gridy=1;
        JLabel l1 = new JLabel("ID");
        l1.setFont(labelFont);
        card.add(l1, gbcCard);

        gbcCard.gridx=1;
        id = new JTextField(20);
        id.setFont(fieldFont);
        id.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        card.add(id, gbcCard);

        // Password
        gbcCard.gridx=0; gbcCard.gridy=2;
        JLabel l2 = new JLabel("Password");
        l2.setFont(labelFont);
        card.add(l2, gbcCard);

        gbcCard.gridx=1;
        pass = new JPasswordField(20);
        pass.setFont(fieldFont);
        pass.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        card.add(pass, gbcCard);

        // LOGIN BUTTON
        gbcCard.gridx=0; gbcCard.gridy=3; gbcCard.gridwidth=2;

        login = new JButton("LOGIN");
        login.setFont(new Font("Segoe UI", Font.BOLD, 20));
        login.setBackground(new Color(0,153,76));
        login.setForeground(Color.WHITE);
        login.setFocusPainted(false);
        login.setPreferredSize(new Dimension(200,50));

        card.add(login, gbcCard);

        login.addActionListener(this);

        // 🎯 CENTER CARD
        gbcMain.gridx = 0;
        gbcMain.gridy = 1;
        gbcMain.anchor = GridBagConstraints.CENTER;
        gbcMain.insets = new Insets(50,0,0,0);
        bg.add(card, gbcMain);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){

        MongoCollection<Document> col =
                MongoDBConnection.getDB().getCollection("students");

        int sid = Integer.parseInt(id.getText());
        String p = new String(pass.getPassword());

        Document d = col.find(and(eq("id",sid), eq("password",p))).first();

        if(d!=null){
            new StudentDashboard(sid);
            this.dispose();
        }else{
            JOptionPane.showMessageDialog(this,"Invalid Login");
        }
    }
}