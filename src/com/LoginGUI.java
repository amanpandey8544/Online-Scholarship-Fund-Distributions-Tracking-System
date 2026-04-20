package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame implements ActionListener {

    JButton studentBtn, adminBtn;

    public LoginGUI() {

        setTitle("Scholarship Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JLabel title = new JLabel("SCHOLARSHIP MANAGEMENT SYSTEM", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 42));
        title.setForeground(new Color(0, 102, 204));
        title.setBorder(BorderFactory.createEmptyBorder(30,10,30,10));
        add(title, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 240, 255));

        studentBtn = createButton("STUDENT");
        adminBtn = createButton("ADMIN");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 30, 20, 30);

        panel.add(studentBtn, gbc);
        gbc.gridx = 1;
        panel.add(adminBtn, gbc);

        add(panel, BorderLayout.CENTER);

        studentBtn.addActionListener(this);
        adminBtn.addActionListener(this);

        setVisible(true);
    }

    JButton createButton(String text){
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(250,80));
        btn.setFont(new Font("Arial", Font.BOLD, 22));
        btn.setBackground(new Color(0,102,204));
        btn.setForeground(Color.WHITE);
        return btn;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==studentBtn){
            new StudentOptionGUI();
        }
        if(e.getSource()==adminBtn){
            new AdminGUI();
        }
        this.dispose();
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
