import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.JScrollPane;
import java.sql.*;
import javax.swing.ImageIcon;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
public class Category
{
    private int current_user;
    private int category_id;
    
    Category(int cat_id)
    {
        category_id = cat_id;            
    }

    public void displayCategory()
    {
        ConnectToDB obj1 = new ConnectToDB();
        Connection con = obj1.getConnectionToDB();
        JFrame frame = new JFrame("Category"); 
        ImageIcon img = new ImageIcon("C:\\Users\\vkris\\OneDrive\\Desktop\\icon knn.jpg");
        frame.setIconImage(img.getImage());
        frame.setSize(923,535);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setLayout(null);
        
        Color myColor2=new Color(224,224,224);//background
        Color myColor3=new Color(117,117,117);//alt
        
        String cat_name="", cat_desc="";
        try
        {
            PreparedStatement st = (PreparedStatement) con.prepareStatement("select * from category where cat_id=?"); 
            st.setString(1,""+category_id);
            ResultSet rs = st.executeQuery(); 
            if(rs.next())
            {
                cat_name=rs.getString("cat_name");
                cat_desc=rs.getString("cat_desc");
            }
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
        
        JLabel logoLabel = new JLabel(cat_name); 
        logoLabel.setBounds(10,10,300,50);
        logoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        frame.add(logoLabel); 
        JLabel descLabel = new JLabel(cat_desc); 
        descLabel.setBounds(320,10,300,50);
        
        frame.add(descLabel); 
        
        JPanel catProdsPanel = new JPanel();          
        catProdsPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5); 
        JScrollPane scr = new JScrollPane(catProdsPanel);
        scr.setBounds(10,70,890,420);
        scr.setVisible(true);
        frame.add(scr);

        try
        {
            int a=0;
            PreparedStatement st = (PreparedStatement) con.prepareStatement("select * from product where cat_id=?");
            st.setString(1,""+category_id);
            ResultSet rs = st.executeQuery();  
            while(rs.next())
            {
                c.gridx=a%2;
                c.gridy=a/2;
                JPanel prodPanel = new JPanel();
                prodPanel.setBorder(BorderFactory.createLineBorder(Color.black));
                prodPanel.setLayout(null);
                prodPanel.setPreferredSize(new Dimension(430,120));
                catProdsPanel.add(prodPanel,c); 

                BufferedImage image=null;
                try 
                {
                    image=ImageIO.read(new File(rs.getString("picture")));
                    Image modifiedimage=image.getScaledInstance(100,100,java.awt.Image.SCALE_SMOOTH);
                    JLabel imagePanel=new JLabel(new ImageIcon(modifiedimage));
                    imagePanel.setBounds(10,10,100,100);
                    imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
                    prodPanel.add(imagePanel);
                } 
                catch(IOException e) 
                {
                    e.printStackTrace();
                }

                JLabel prodName = new JLabel(rs.getString("pname"));
                prodName.setBounds(120,5,200,40);
                prodName.setForeground(Color.white);
                prodName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
                prodPanel.add(prodName);

                JLabel prodUnitPrice = new JLabel("₹ "+rs.getString("unit_price")+" /unit");
                float pricePerProd = Float.parseFloat(rs.getString("unit_price"));
                prodUnitPrice.setForeground(Color.white);
                prodUnitPrice.setBounds(120,50,100,25);
                prodPanel.add(prodUnitPrice);

                int prod_id=rs.getInt("prod_id");
                JButton prod = new JButton(new AbstractAction("More Details") {
                            @Override
                            public void actionPerformed( ActionEvent e ) {      
                                Product obj = new Product(prod_id);
                                obj.displayProduct();
                            }
                        });   
                prod.setBounds(260,40,120,30);                                    
                prodPanel.add(prod);                                        
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
        scr.setBackground(myColor2);
    }
}