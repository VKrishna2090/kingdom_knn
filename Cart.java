import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Cart
{
    private int current_user;

    Cart(int c_id)
    {
        current_user = c_id;
    }

    public void displayCart()
    {
        ConnectToDB obj1 = new ConnectToDB();
        Connection con = obj1.getConnectionToDB();

        JFrame frame = new JFrame("My Cart");        
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
        frame.add(topPanel);
        JLabel titleLabel = new JLabel("My Cart"); 
        titleLabel.setBounds(330,10,250,30);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        topPanel.add(titleLabel);

        JPanel leftPanel = new JPanel();   
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);
        JScrollPane sc = new JScrollPane(leftPanel);        
        sc.setBounds(10,70,450,400);
        leftPanel.setSize(new Dimension(450,400));
        leftPanel.setLayout(new GridBagLayout());
        frame.add(sc);

        float totalAmount=0;
        try
        {
            int a=0;
            PreparedStatement st = (PreparedStatement) con.prepareStatement("select * from cart c join product p on c.prod_id=p.prod_id where c.c_id = ?;");
            st.setString(1,""+current_user);
            ResultSet rs = st.executeQuery();  

            while(rs.next())
            {
                c.gridx=0;
                c.gridy=a;

                JPanel prod = new JPanel();
                prod.setLayout(null);
                prod.setPreferredSize(new Dimension(410,120));
                prod.setBorder(BorderFactory.createLineBorder(Color.black));
                
                BufferedImage image=null;
                try 
                {
                    image=ImageIO.read(new File(rs.getString("picture")));
                    Image modifiedimage=image.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH);
                    JLabel imagePanel=new JLabel(new ImageIcon(modifiedimage));
                    imagePanel.setBounds(10,10,100,100);
                    imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
                    prod.add(imagePanel);
                } 
                catch(IOException e) 
                {
                    e.printStackTrace();
                }
                
                JLabel prodName = new JLabel(rs.getString("pname"));
                prodName.setBounds(120,5,100,40);
                prodName.setForeground(Color.white);
                prodName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
                prod.add(prodName);

                JLabel prodUnitPrice = new JLabel("₹ "+rs.getString("unit_price")+" /unit");
                float pricePerProd = Float.parseFloat(rs.getString("unit_price"));
                prodUnitPrice.setForeground(Color.white);
                prodUnitPrice.setBounds(120,50,100,25);
                prod.add(prodUnitPrice);

                JLabel prodQty = new JLabel(rs.getString("prod_qty"));
                int prod_qty1 = Integer.parseInt(prodQty.getText());
                prodQty.setForeground(Color.white);
                prodQty.setBounds(173,80,40,25);                
                prod.add(prodQty);

                float totalProdPrice = prod_qty1*pricePerProd;
                JLabel totalPricePerProductQty = new JLabel("₹ "+totalProdPrice);  
                totalPricePerProductQty.setForeground(Color.white);
                totalPricePerProductQty.setBounds(300,15,80,25);    
                totalPricePerProductQty.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
                prod.add(totalPricePerProductQty);
                totalAmount+=totalProdPrice;

                int product_id = rs.getInt("prod_id");
                if(prod_qty1>1)
                {
                    JButton decreaseQty = new JButton(new AbstractAction("-") {
                                @Override
                                public void actionPerformed( ActionEvent e ) {      
                                    try
                                    {   
                                        int prod_qty = Integer.parseInt(prodQty.getText());
                                        PreparedStatement st = (PreparedStatement) con.prepareStatement("update cart set prod_qty=? where prod_id=? and c_id=?;");
                                        st.setString(1,""+(prod_qty-1));
                                        st.setString(2,""+product_id);
                                        st.setString(3,""+current_user);
                                        int x = st.executeUpdate(); 
                                        if(x==1)
                                        {
                                            frame.dispose();
                                            Cart obj = new Cart(current_user);
                                            obj.displayCart();
                                        }
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
                    decreaseQty.setBounds(120,80,45,25);
                    prod.add(decreaseQty);
                }

                JButton updateQty = new JButton(new AbstractAction("+") {
                            @Override
                            public void actionPerformed( ActionEvent e ) {      
                                try
                                {   
                                    int prod_qty = Integer.parseInt(prodQty.getText());
                                    PreparedStatement st = (PreparedStatement) con.prepareStatement("update cart set prod_qty=? where prod_id=? and c_id=?;");
                                    st.setString(1,""+(prod_qty+1));
                                    st.setString(2,""+product_id);
                                    st.setString(3,""+current_user);
                                    int x = st.executeUpdate(); 
                                    if(x==1)
                                    {
                                        frame.dispose();
                                        Cart obj = new Cart(current_user);
                                        obj.displayCart();
                                    }
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
                updateQty.setBounds(190,80,45,25);
                prod.add(updateQty);

                JButton deleteProd = new JButton(new AbstractAction("Remove") {
                            @Override
                            public void actionPerformed( ActionEvent e ) {      
                                try
                                {   
                                    PreparedStatement st = (PreparedStatement) con.prepareStatement("delete from cart where prod_id=? and c_id=?;");
                                    st.setString(1,""+product_id);
                                    st.setString(2,""+current_user);
                                    int x = st.executeUpdate(); 
                                    if(x==1)
                                    {
                                        frame.setVisible(false);
                                        Cart obj = new Cart(current_user);
                                        obj.displayCart();
                                    }
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
                deleteProd.setBounds(300,80,100,25);
                prod.add(deleteProd);

                leftPanel.add(prod,c);
                
                prod.setBackground(myColor3);
                a++;
            }

        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }

        JPanel rightPanel = new JPanel();        
        rightPanel.setBounds(460,70,250,400);
        rightPanel.setLayout(null);
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        if(totalAmount!=0)
        {
            JLabel totalAmt = new JLabel("Total Amount:");
            totalAmt.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            totalAmt.setBounds(30,10,200,40);
            rightPanel.add(totalAmt);

            JLabel totalAmt2 = new JLabel("₹ "+totalAmount);
            totalAmt2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            totalAmt2.setBounds(32,50,200,40);
            rightPanel.add(totalAmt2);
        }
        else
        {
            JLabel totalAmt = new JLabel("Cart is Empty!");
            totalAmt.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            totalAmt.setBounds(33,50,200,40);
            rightPanel.add(totalAmt);
        }

        JButton createOrder = new JButton(new AbstractAction("Checkout") {
                    @Override
                    public void actionPerformed( ActionEvent e ) {      
                        frame.dispose();
                        //create frame for the checkout page
                        Checkout obj = new Checkout(current_user);
                        obj.displayCheckout(); 
                    }
                }); 
        createOrder.setBounds(30,330,190,40);
        rightPanel.add(createOrder);

        frame.add(rightPanel);
        
        frame.getContentPane().setBackground(myColor2);
        topPanel.setBackground(myColor2);
        leftPanel.setBackground(myColor2);
        sc.setBackground(myColor2);
        rightPanel.setBackground(myColor2);
    }
}