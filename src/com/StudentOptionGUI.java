package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentOptionGUI extends JFrame implements ActionListener {

    JButton register, login, back;

    public StudentOptionGUI(){

        setTitle("Student Portal");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // 🌈 Background Panel
        JPanel bg = new JPanel(new GridBagLayout());
        bg.setBackground(new Color(230, 240, 255));
        add(bg);

        // 🧾 CARD PANEL
        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(400,400));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,20,20,20);

        // 🔥 TITLE
        JLabel title = new JLabel("STUDENT PORTAL");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(0,102,204));

        gbc.gridy = 0;
        card.add(title, gbc);

        // 🎛️ BUTTONS
        register = createButton("REGISTER", new Color(0,153,76));
        login = createButton("LOGIN", new Color(0,102,204));
        back = createButton("BACK", Color.DARK_GRAY);

        gbc.gridy = 1;
        card.add(register, gbc);

        gbc.gridy = 2;
        card.add(login, gbc);

        gbc.gridy = 3;
        card.add(back, gbc);

        // 🎯 CENTER CARD
        bg.add(card);

        // 🔥 ACTIONS
        register.addActionListener(this);
        login.addActionListener(this);
        back.addActionListener(this);

        setVisible(true);
    }

    // 🎨 BUTTON STYLE
    JButton createButton(String text, Color color){
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(220,60));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==register) new StudentRegistrationGUI();
        if(e.getSource()==login) new StudentGUI();
        if(e.getSource()==back) new LoginGUI();

        this.dispose();
    }
}