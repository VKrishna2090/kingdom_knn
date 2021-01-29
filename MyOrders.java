import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.io.*;
public class MyOrders
{
    private int current_user;    

    MyOrders(int c_id)
    {
        current_user = c_id;
    }

    public void displayOrders()
    {
        ConnectToDB obj1 = new ConnectToDB();
        Connection con = obj1.getConnectionToDB();
        JFrame frame = new JFrame("My Orders"); 
        ImageIcon img = new ImageIcon("C:\\Users\\vkris\\OneDrive\\Desktop\\icon knn.jpg");
        frame.setIconImage(img.getImage());
        frame.setSize(813,535);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(null);
        
        Color myColor2=new Color(224,224,224);//background
        Color myColor3=new Color(117,117,117);//alt
        
        JLabel logoLabel = new JLabel("My Orders"); 
        logoLabel.setBounds(10,10,300,50);
        logoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        frame.add(logoLabel);

        JPanel catProdsPanel = new JPanel();          
        catProdsPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 5, 0, 5); 
        JScrollPane scr = new JScrollPane(catProdsPanel);
        scr.setBounds(10,70,780,420);
        scr.setVisible(true);
        frame.add(scr);

        try
        {
            int a=0;
            PreparedStatement st = (PreparedStatement) con.prepareStatement("select order_id from ordered_prod where c_id = ? group by order_id"); 
            st.setString(1,""+current_user);
            ResultSet rs = st.executeQuery(); 
            while(rs.next())
            {
                c.gridx=0;
                c.gridy=a;
                JPanel prodPanel = new JPanel();

                prodPanel.setBorder(BorderFactory.createLineBorder(Color.black));
                prodPanel.setLayout(null);
                prodPanel.setPreferredSize(new Dimension(740,130));
                catProdsPanel.add(prodPanel,c); 
                
                String orderedProds = "";

                PreparedStatement st2 = (PreparedStatement) con.prepareStatement("select pname from product where prod_id in (select prod_id from ordered_prod where order_id = ? and c_id=?);"); 
                st2.setString(1,""+rs.getInt("order_id"));
                st2.setString(2,""+current_user);
                ResultSet rs2 = st2.executeQuery();
                while(rs2.next())
                {
                    orderedProds += rs2.getString("pname")+", ";
                }

                PreparedStatement st1 = (PreparedStatement) con.prepareStatement("select * from order_details where order_id="+rs.getInt("order_id")); 
                ResultSet rs1 = st1.executeQuery();
                if(rs1.next())
                {
                    JLabel orderedProdLabel = new JLabel(orderedProds);
                    orderedProdLabel.setForeground(Color.white);
                    orderedProdLabel.setBounds(10,10,200,25);
                    orderedProdLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
                    prodPanel.add(orderedProdLabel);
                    JLabel orderDateLabel = new JLabel("Order Date: " + rs1.getString("order_date"));
                    orderDateLabel.setForeground(Color.white);
                    orderDateLabel.setBounds(10,40,200,25);
                    prodPanel.add(orderDateLabel);
                    JLabel payTypeLabel = new JLabel("Payment type: "+ rs1.getString("payment_type"));
                    payTypeLabel.setForeground(Color.white);
                    payTypeLabel.setBounds(10,70,200,25);
                    prodPanel.add(payTypeLabel);
                    JLabel totalAmountLabel = new JLabel("Amount paid: ₹ "+String.format("%.2f",rs1.getDouble("total_amount")));
                    totalAmountLabel.setForeground(Color.white);
                    totalAmountLabel.setBounds(220,40,200,25);
                    prodPanel.add(totalAmountLabel);
                    JLabel shipDateLabel = new JLabel("Ship Date: " + rs1.getString("ship_date"));
                    shipDateLabel.setForeground(Color.white);
                    shipDateLabel.setBounds(220,70,200,25);
                    prodPanel.add(shipDateLabel);
                    JLabel orderStatusLabel = new JLabel("Order status: "+rs1.getString("order_status"));
                    orderStatusLabel.setForeground(Color.white);
                    orderStatusLabel.setBounds(440,40,200,30);
                    prodPanel.add(orderStatusLabel);
                }
                a+=1;
                prodPanel.setBackground(myColor3);
            }
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        
        frame.getContentPane().setBackground(myColor2);
        catProdsPanel.setBackground(myColor2);
    }
}