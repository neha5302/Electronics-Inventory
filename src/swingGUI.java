import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class swingGUI {
    private JPanel Main;
    private JTextField txtName;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField txtPrice;
    private JTextField txtQty;
    private JButton searchButton;
    private JTextField txtpid;
    Connection con;
    PreparedStatement pst;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Electronics Inventory");
        frame.setContentPane(new swingGUI().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);
        frame.setLocation(650,250);
        frame.pack();
        frame.setVisible(true);
    }

    public swingGUI() {
        connect();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,price,qty;
                name = txtName.getText();
                price = txtPrice.getText();
                qty = txtQty.getText();
                try {
                    pst = con.prepareStatement("insert into products(pname,price,qty)values(?,?,?)");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Added Successfully!");
                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                } catch (SQLException ex) {
//                    throw new RuntimeException(ex);
                    ex.printStackTrace();
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             String pid = txtpid.getText();
                try {
                    pst = con.prepareStatement("select pname,price,qty from products where pid = ?");
                    pst.setString(1,pid);
                    ResultSet rs = pst.executeQuery();
                    if (rs.next() == true) {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);
                        txtName.setText(name);
                        txtPrice.setText(price);
                        txtQty.setText(qty);
                    }else {
                        JOptionPane.showMessageDialog(null,"Invalid Product Id!!");
                        txtName.setText("");
                        txtPrice.setText("");
                        txtQty.setText("");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid,name,price,qty;
                name = txtName.getText();
                price = txtPrice.getText();
                qty = txtQty.getText();
                pid = txtpid.getText();
                try {
                    pst = con.prepareStatement("update products set pname = ?,price = ?,qty = ? where pid = ?");
                    pst.setString(1,name);
                    pst.setString(2,price);
                    pst.setString(3,qty);
                    pst.setString(4,pid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Updated Successfully!");
                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                    txtpid.setText("");
                } catch (SQLException ex) {
//                    throw new RuntimeException(ex);
                    ex.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pid = txtpid.getText();
                try {
                    pst = con.prepareStatement("delete from products where pid = ?");
                    pst.setString(1,pid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Deleted Successfully!");
                    txtName.setText("");
                    txtPrice.setText("");
                    txtQty.setText("");
                    txtName.requestFocus();
                    txtpid.setText("");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_products", "root","Sakshi@db1");
            System.out.println("Connected successfully!");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
