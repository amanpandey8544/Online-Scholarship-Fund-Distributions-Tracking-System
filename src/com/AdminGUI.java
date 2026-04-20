package com;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import com.mongodb.client.*;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

public class AdminGUI extends JFrame implements ActionListener {

    JButton viewAll, approve, reject, approvedList, rejectedList, logout;
    JTable table;
    DefaultTableModel model;

    public AdminGUI(){

        setTitle("Admin Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // 🌑 HEADER PANEL
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30,30,30));
        header.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

        JLabel title = new JLabel("ADMIN DASHBOARD");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));

        logout = new JButton("Logout");
        styleButton(logout, new Color(200,0,0));

        header.add(title, BorderLayout.WEST);
        header.add(logout, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // 🎛️ BUTTON PANEL
        JPanel top = new JPanel();
        top.setBackground(new Color(240,240,240));

        viewAll = new JButton("View All");
        approve = new JButton("Approve");
        reject = new JButton("Reject");
        approvedList = new JButton("Approved List");
        rejectedList = new JButton("Rejected List");

        styleButton(viewAll, new Color(0,102,204));
        styleButton(approve, new Color(0,153,76));
        styleButton(reject, new Color(204,0,0));
        styleButton(approvedList, new Color(102,0,153));
        styleButton(rejectedList, new Color(255,140,0));

        top.add(viewAll);
        top.add(approve);
        top.add(reject);
        top.add(approvedList);
        top.add(rejectedList);

        add(top, BorderLayout.SOUTH);

        // 📊 TABLE
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Student ID","Amount","Purpose","Status","Approved Amount"
        });

        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        table.getTableHeader().setBackground(new Color(0,102,204));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // 🔥 ACTIONS
        viewAll.addActionListener(this);
        approve.addActionListener(this);
        reject.addActionListener(this);
        approvedList.addActionListener(this);
        rejectedList.addActionListener(this);
        logout.addActionListener(this);

        setVisible(true);
    }

    // 🎨 BUTTON STYLE METHOD
    void styleButton(JButton btn, Color color){
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(180,45));
    }

    public void actionPerformed(ActionEvent e){

        MongoCollection<Document> col =
                MongoDBConnection.getDB().getCollection("loans");

        // 🔥 VIEW ALL
        if(e.getSource()==viewAll){
            model.setRowCount(0);

            for(Document d : col.find()){
                model.addRow(new Object[]{
                        d.getInteger("student_id"),
                        d.getInteger("amount"),
                        d.getString("purpose"),
                        d.getString("status"),
                        d.getInteger("approvedAmount",0)
                });
            }
        }

        // 🔥 APPROVE
        if(e.getSource()==approve){

            int row = table.getSelectedRow();

            if(row==-1){
                JOptionPane.showMessageDialog(this,"Select Row First!");
                return;
            }

            int sid = (int) model.getValueAt(row,0);

            String amt = JOptionPane.showInputDialog("Enter Approved Amount");

            col.updateOne(eq("student_id", sid),
                    new Document("$set",
                            new Document("status","Approved")
                                    .append("approvedAmount", Integer.parseInt(amt))));

            JOptionPane.showMessageDialog(this,"Approved!");
        }

        // 🔥 REJECT
        if(e.getSource()==reject){

            int row = table.getSelectedRow();

            if(row==-1){
                JOptionPane.showMessageDialog(this,"Select Row First!");
                return;
            }

            int sid = (int) model.getValueAt(row,0);

            col.updateOne(eq("student_id", sid),
                    new Document("$set",
                            new Document("status","Rejected")
                                    .append("approvedAmount", 0)));

            JOptionPane.showMessageDialog(this,"Rejected!");
        }

        // 🔥 APPROVED LIST
        if(e.getSource()==approvedList){
            model.setRowCount(0);

            for(Document d : col.find(eq("status","Approved"))){
                model.addRow(new Object[]{
                        d.getInteger("student_id"),
                        d.getInteger("amount"),
                        d.getString("purpose"),
                        d.getString("status"),
                        d.getInteger("approvedAmount",0)
                });
            }
        }

        // 🔥 REJECTED LIST
        if(e.getSource()==rejectedList){
            model.setRowCount(0);

            for(Document d : col.find(eq("status","Rejected"))){
                model.addRow(new Object[]{
                        d.getInteger("student_id"),
                        d.getInteger("amount"),
                        d.getString("purpose"),
                        d.getString("status"),
                        d.getInteger("approvedAmount",0)
                });
            }
        }

        // 🔥 LOGOUT
        if(e.getSource()==logout){
            new LoginGUI();
            this.dispose();
        }
    }
}