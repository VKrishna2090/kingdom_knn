import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

public class Checkout
{
    private int current_user;

    Checkout(int c_id)
    {
        current_user = c_id;
    }

    public void displayCheckout()
    {
        ConnectToDB obj1 = new ConnectToDB();
        Connection con = obj1.getConnectionToDB();
        JFrame frame = new JFrame("Checkout");        
        frame.setSize(740,520);
        ImageIcon img = new ImageIcon("C:\\Users\\vkris\\OneDrive\\Desktop\\icon knn.jpg");
        frame.setIconImage(img.getImage());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(null);
        
        Color myColor2=new Color(224,224,224);//background
        Color myColor3=new Color(117,117,117);//alt

        JPanel topPanel = new JPanel();        
        topPanel.setBounds(10,10,710,50);
        topPanel.setLayout(null);
        //topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        frame.add(topPanel);
        JLabel titleLabel = new JLabel("Checkout"); 
        titleLabel.setBounds(320,10,250,30);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        topPanel.add(titleLabel);

        JPanel leftPanel = new JPanel();                           
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        leftPanel.setBounds(10,70,350,400);
        
        leftPanel.setLayout(null);                        
        frame.add(leftPanel);

        JLabel addressLabel = new JLabel("Delivery Information:");
        addressLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        addressLabel.setBounds(10,10,330,40);
        leftPanel.add(addressLabel);

        String address="";
        String credit_card_num="";
        try
        {
            PreparedStatement st = (PreparedStatement) con.prepareStatement("select * from customer where c_id = ?;");
            st.setString(1,""+current_user);
            ResultSet rs = st.executeQuery();  
            if(rs.next())
            {
                address="Name : "+rs.getString("fname")+" "+rs.getString("mname")+" "+rs.getString("lname")+"\n\nAddress 1: "+rs.getString("address1")+"\n\nAddress 2: " +rs.getString("address2")+"\n\nCity: "+rs.getString("city")+"\n\nState: "+rs.getString("state_address")+"\n\nCountry: "+rs.getString("country");
                credit_card_num=rs.getString("credit_card_num");
            }
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        JTextArea addressArea = new JTextArea(address);
        addressArea.setBounds(10,50,330,340);         
        addressArea.setFont(new Font("Sans_Serif",Font.PLAIN,16));
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);        
        addressArea.setEditable(false);
        leftPanel.add(addressArea);

        JPanel rightPanel = new JPanel();        
        rightPanel.setBounds(360,70,350,400);
        rightPanel.setLayout(null);
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        frame.add(rightPanel);

        float totalAmount=0;
        try
        {
            PreparedStatement st = (PreparedStatement) con.prepareStatement("select * from cart c join product p on c.prod_id=p.prod_id where c.c_id = ?;");
            st.setString(1,""+current_user);
            ResultSet rs = st.executeQuery();  
            while(rs.next())
            {
                float pricePerProd = Float.parseFloat(rs.getString("unit_price"));                
                int prod_qty1 = rs.getInt("prod_qty");
                float totalProdPrice = prod_qty1*pricePerProd;
                totalAmount+=totalProdPrice;
            }
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        double discountAmount = 2/100.0*totalAmount;
        JLabel totalAmt = new JLabel("Order Amount:  ₹ "+String.format("%.2f",totalAmount));
        totalAmt.setForeground(Color.white);
        totalAmt.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        totalAmt.setBounds(30,10,300,40);
        rightPanel.add(totalAmt);
        JLabel totalAmt2 = new JLabel("Discount: ₹ "+String.format("%.2f",discountAmount));
        totalAmt2.setForeground(Color.white);
        totalAmt2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        totalAmt2.setBounds(30,50,300,40);
        rightPanel.add(totalAmt2);
        JLabel discountLabel = new JLabel("Payable Amount: ₹ "+String.format("%.2f",(totalAmount-discountAmount)));
        discountLabel.setForeground(Color.white);
        discountLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        discountLabel.setBounds(30,100,300,40);
        rightPanel.add(discountLabel);

        JLabel paymentTypeLabel = new JLabel("Select Payment Type:");
        paymentTypeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        paymentTypeLabel.setForeground(Color.white);
        paymentTypeLabel.setBounds(30,150,200,40);
        rightPanel.add(paymentTypeLabel);
        JRadioButton cashButton = new JRadioButton("Cash on Delivery");
        cashButton.setForeground(Color.white);
        cashButton.setBounds(30,200,200,25);
        cashButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        cashButton.setBackground(myColor3);
        rightPanel.add(cashButton);
        JRadioButton cardButton = new JRadioButton("Credit/Debit Card");
        cardButton.setForeground(Color.white);
        cardButton.setBounds(30,230,200,25);
        cardButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
        cardButton.setBackground(myColor3);
        rightPanel.add(cardButton);
        ButtonGroup bg=new ButtonGroup();
        bg.add(cashButton);
        bg.add(cardButton);

        JButton goBack = new JButton(new AbstractAction("Go Back") {
                    @Override
                    public void actionPerformed( ActionEvent e ) {   
                        frame.dispose();
                        Cart obj = new Cart(current_user);
                        obj.displayCart();       
                    }
                }); 
        goBack.setBounds(30,330,130,40);
        rightPanel.add(goBack);

        final double total_Amount = totalAmount;
        final double discount_Amount = discountAmount;
        final String full_address= address;
        JButton placeOrder = new JButton(new AbstractAction("Place Order") {
                    @Override
                    public void actionPerformed( ActionEvent e ) {   
                        frame.dispose();

                        int orderId = (int)(Math.random()*10000);
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
                        LocalDateTime now = LocalDateTime.now();//collects current date and time for Systems clock
                        String orderDate = dtf.format(now);                        
                        LocalDateTime tomorrow = now.plusDays(1); 
                        String shipDate = dtf.format(tomorrow);
                        String paymenttype="";
                        if(cashButton.isSelected()){    
                            paymenttype="CASH";
                        }    
                        if(cardButton.isSelected()){    
                            paymenttype="CARD";
                        }    
                        String orderStatus = "PROCESSING";

                        try
                        {
                            PreparedStatement st = (PreparedStatement) con.prepareStatement("insert into order_details values (?,?,?,?,?,?,?,?);");
                            st.setString(1,""+orderId);
                            st.setString(2,""+paymenttype);
                            st.setString(3,""+orderDate);
                            st.setString(4,""+String.format("%.2f",(total_Amount-discount_Amount)));
                            st.setString(5,""+orderStatus);
                            st.setString(6,""+String.format("%.2f",discount_Amount));
                            st.setString(7,""+shipDate);
                            st.setString(8,full_address);
                            int x = st.executeUpdate();  

                            PreparedStatement st1 = (PreparedStatement) con.prepareStatement("select * from cart where c_id = ?;");
                            st1.setString(1,""+current_user);
                            ResultSet rs1 = st1.executeQuery();  
                            while(rs1.next())
                            {
                                PreparedStatement st2 = (PreparedStatement) con.prepareStatement("insert into ordered_prod values (?,?,?,?);");
                                st2.setString(1,""+orderId);
                                st2.setString(2,""+rs1.getInt("c_id"));
                                st2.setString(3,""+rs1.getInt("prod_id"));
                                st2.setString(4,""+rs1.getInt("prod_qty"));                                
                                int x2 = st2.executeUpdate();                                
                            }

                            PreparedStatement st3 = (PreparedStatement) con.prepareStatement("delete from cart where c_id = ?;");
                            st3.setString(1,""+current_user);
                            int x3 = st3.executeUpdate();

                            JDialog jd = new JDialog(frame, "Order Placed");                                            
                            jd.setSize(400,200);
                            jd.setLocationRelativeTo(null);
                            jd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            jd.setLayout(null);
                            jd.setVisible(true);
                            JLabel lab1 = new JLabel("You earned discount of ₹ "+String.format("%.2f",discount_Amount));                             
                            lab1.setBounds(30,20,200,40);                            
                            jd.add(lab1);
                            JLabel lab2 = new JLabel("Your order will be delivered on "+shipDate);                             
                            lab2.setBounds(20,60,300,40);                            
                            jd.add(lab2);
                        }
                        catch(SQLException sqlException) {
                            sqlException.printStackTrace();
                        }
                        catch(Exception ex)
                        {
                            System.out.println(ex);
                        }
                    }
                }); 
        placeOrder.setBounds(190,330,130,40);
        rightPanel.add(placeOrder);
        
        frame.getContentPane().setBackground(myColor2);
        topPanel.setBackground(myColor2);
        leftPanel.setBackground(Color.white);
        rightPanel.setBackground(myColor3);
    }
}