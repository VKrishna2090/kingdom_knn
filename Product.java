import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Product
{
    private int current_user;
    private int product_id;
    Product(int p_id)
    {
        product_id = p_id;
    }

    public void displayProduct()
    {
        ConnectToDB obj1 = new ConnectToDB();
        Connection con = obj1.getConnectionToDB();
        JFrame frame = new JFrame("Product Details");        
        frame.setSize(745,460);
        ImageIcon img = new ImageIcon("C:\\Users\\vkris\\OneDrive\\Desktop\\icon knn.jpg");
        frame.setIconImage(img.getImage());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(null);

        Color myColor2=new Color(224,224,224);//background
        Color myColor3=new Color(117,117,117);//alt
        
        JPanel leftpanel = new JPanel();        
        leftpanel.setBounds(10,10,300,400);
        leftpanel.setLayout(null);
        leftpanel.setBorder(BorderFactory.createLineBorder(Color.black));
        frame.add(leftpanel);
        JPanel rightpanel = new JPanel();        
        rightpanel.setBounds(320,10,400,400);
        rightpanel.setLayout(null);
        rightpanel.setBorder(BorderFactory.createLineBorder(Color.black));
        frame.add(rightpanel);

        try
        {
            PreparedStatement st = (PreparedStatement) con.prepareStatement("select * from product where prod_id = ?");  
            st.setString(1,""+product_id);
            ResultSet rs = st.executeQuery();  
            if(rs.next())
            {
                JButton addToCartButton = new JButton(new AbstractAction("Add To Cart") {
                            @Override
                            public void actionPerformed( ActionEvent e ) { 
                                boolean already_exists=false;
                                try
                                {
                                    PreparedStatement st3 = (PreparedStatement) con.prepareStatement("select * from cart where c_id=? and prod_id=?;");                                   
                                    st3.setString(1,""+current_user);
                                    st3.setString(2,""+product_id);
                                    ResultSet rs3 = st3.executeQuery();
                                    if(rs3.next())
                                    {
                                        already_exists=true;
                                        /* UPDATING THE EXISTING QUANTITY */
                                        PreparedStatement st4 = (PreparedStatement) con.prepareStatement("update cart set prod_qty = ? where cart_id= ?;");  
                                        st4.setString(1,""+(rs3.getInt("prod_qty")+1));
                                        st4.setString(2,""+(rs3.getInt("cart_id")));                                        
                                        int x = st4.executeUpdate();
                                        if(x==1)
                                        {
                                            JDialog jd = new JDialog(frame, "Success");     
                                            jd.getContentPane().setBackground(myColor2);
                                            jd.setSize(300,100);
                                            jd.setLocationRelativeTo(null);
                                            jd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                            jd.setVisible(true);
                                            JLabel lab = new JLabel("Product quantity in cart updated!");  
                                            lab.setHorizontalAlignment(JLabel.CENTER);
                                            jd.add(lab);
                                        }
                                    }
                                }
                                catch(SQLException sqlException) {
                                    sqlException.printStackTrace();
                                }
                                catch(Exception ex)
                                {
                                    System.out.println(ex);
                                }
                                if(!already_exists)
                                {
                                    try
                                    {
                                        PreparedStatement st3 = (PreparedStatement) con.prepareStatement("insert into cart values(?,?,?,?);");  
                                        st3.setString(1,""+((int)(Math.random()*100000)));
                                        st3.setString(2,""+current_user);
                                        st3.setString(3,""+product_id);
                                        st3.setString(4,"1");
                                        int x = st3.executeUpdate();
                                        if(x==1)
                                        {
                                            JDialog jd = new JDialog(frame, "Success");
                                            jd.setSize(200,100);
                                            jd.setLocationRelativeTo(null);
                                            jd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                            jd.setVisible(true);
                                            JLabel lab = new JLabel("Added to Your Cart!");  
                                            lab.setHorizontalAlignment(JLabel.CENTER);
                                            jd.add(lab);
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
                            }
                        });  

                addToCartButton.setBounds(10,350,280,40);
                leftpanel.add(addToCartButton);
                
                BufferedImage image=null;
                try 
                {
                    image=ImageIO.read(new File(rs.getString("picture")));
                    Image modifiedimage=image.getScaledInstance(280,330,java.awt.Image.SCALE_SMOOTH);
                    JLabel imagePanel=new JLabel(new ImageIcon(modifiedimage));
                    imagePanel.setBounds(10,10,280,330);
                    imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
                    leftpanel.add(imagePanel);
                } 
                catch(IOException e) 
                {
                    e.printStackTrace();
                }
                
                JLabel nameLabel = new JLabel(rs.getString("pname")); 
                nameLabel.setBounds(10,10,300,40);
                nameLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
                rightpanel.add(nameLabel); 
                JLabel priceLabel = new JLabel("₹ "+rs.getFloat("unit_price")); 
                priceLabel.setBounds(10,50,300,30);
                priceLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
                rightpanel.add(priceLabel); 
                JLabel weightLabel = new JLabel("Weight: "+rs.getFloat("unit_weight")); 
                weightLabel.setBounds(10,90,90,25);
                rightpanel.add(weightLabel); 
                JLabel sizeLabel = new JLabel("Size: "+rs.getFloat("size")); 
                sizeLabel.setBounds(140,90,70,25);
                rightpanel.add(sizeLabel);
                JLabel colourLabel = new JLabel("Color: "+rs.getString("colour")); 
                colourLabel.setBounds(250,90,140,25);
                rightpanel.add(colourLabel); 
                JLabel descLabel = new JLabel("Description:"); 
                descLabel.setBounds(10,120,140,15);
                rightpanel.add(descLabel);
                JTextArea descriptionLabel = new JTextArea(rs.getString("prod_desc")); 
                descriptionLabel.setFont(new Font("Sans_Serif",Font.PLAIN,12));
                descriptionLabel.setLineWrap(true);
                descriptionLabel.setWrapStyleWord(true);
                descriptionLabel.setBounds(10,140,380,140);
                descriptionLabel.setEditable(false);
                rightpanel.add(descriptionLabel); 

                PreparedStatement st2 = (PreparedStatement) con.prepareStatement("select * from supplier where sup_id = ?");  
                st2.setString(1,""+rs.getInt("sup_id"));
                ResultSet rs2 = st2.executeQuery();
                if(rs2.next())
                {
                    JLabel supplierLabel = new JLabel("Supplier: " + rs2.getString("company_name") + " (+91 "+rs2.getLong("contact_num")+")"); 
                    supplierLabel.setBounds(10,290,300,25);
                    rightpanel.add(supplierLabel);
                }
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
        leftpanel.setBackground(myColor3);
        rightpanel.setBackground(Color.white);

    }
}
