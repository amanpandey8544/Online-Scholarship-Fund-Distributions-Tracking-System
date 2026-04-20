package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.mongodb.client.*;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

public class StudentDashboard extends JFrame implements ActionListener {

    int id;
    JButton applyLoan, checkStatus, back, logout;

    public StudentDashboard(int id) {
        this.id = id;

        setTitle("Student Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // 🌈 Background
        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(new Color(230, 240, 255));
        add(bg);

        // 🔝 TOP BAR
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(30,30,30));
        top.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

        JLabel title = new JLabel("STUDENT DASHBOARD");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));

        JPanel rightPanel = new JPanel();
        rightPanel.setOpaque(false);

        back = new JButton("Back");
        logout = new JButton("Logout");

        styleButton(back, Color.GRAY);
        styleButton(logout, new Color(200,0,0));

        rightPanel.add(back);
        rightPanel.add(logout);

        top.add(title, BorderLayout.WEST);
        top.add(rightPanel, BorderLayout.EAST);

        // 🧾 CENTER CARD
        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(400,300));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,20,20,20);

        applyLoan = new JButton("Apply Loan");
        checkStatus = new JButton("Check Status");

        styleButton(applyLoan, new Color(0,102,204));
        styleButton(checkStatus, new Color(0,153,76));

        gbc.gridy=0; card.add(applyLoan, gbc);
        gbc.gridy=1; card.add(checkStatus, gbc);

        // 📌 ADD COMPONENTS
        GridBagConstraints main = new GridBagConstraints();
        main.gridx=0;
        main.insets = new Insets(20,20,20,20);

        main.gridy=0;
        main.fill = GridBagConstraints.HORIZONTAL;
        bg.add(top, main);

        main.gridy=1;
        main.anchor = GridBagConstraints.CENTER;
        bg.add(card, main);

        // 🔥 ACTIONS
        applyLoan.addActionListener(this);
        checkStatus.addActionListener(this);
        back.addActionListener(this);
        logout.addActionListener(this);

        setVisible(true);
    }

    // 🎨 BUTTON STYLE
    void styleButton(JButton btn, Color color){
        btn.setPreferredSize(new Dimension(200,60));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
    }

    public void actionPerformed(ActionEvent e) {

        MongoDatabase db = MongoDBConnection.getDB();
        MongoCollection<Document> col = db.getCollection("loans");

        if(e.getSource()==applyLoan){
            new ApplyLoanGUI(id);
            this.dispose();
        }

        if(e.getSource()==checkStatus){
            Document d = col.find(eq("student_id", id)).first();
            if(d!=null){
                JOptionPane.showMessageDialog(this,
                        "Status: "+d.getString("status")+
                                "\nAmount: "+d.getInteger("amount"));
            }else{
                JOptionPane.showMessageDialog(this,"No Loan Applied Yet!");
            }
        }

        if(e.getSource()==back){
            new StudentGUI();
            this.dispose();
        }

        if(e.getSource()==logout){
            new LoginGUI();
            this.dispose();
        }
    }
}