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
public class HomePage
{    
    private int current_user;
    HomePage(int cu)
    {
        current_user = cu;
    }

    void displayHomePage()
    {
        Connection con = (new ConnectToDB()).getConnectionToDB();
        JFrame frame = new JFrame("Home");
        frame.setSize(1273,645);
        ImageIcon img = new ImageIcon("C:\\Users\\vkris\\OneDrive\\Desktop\\icon knn.jpg");
        frame.setIconImage(img.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(null);

        Color myColor2=new Color(224,224,224);//background
        Color myColor3=new Color(117,117,117);//alt

        JPanel menupanel = new JPanel();        
        menupanel.setBounds(0,0,1260,55);
        menupanel.setLayout(null);
        frame.add(menupanel);

        JLabel logoLabel = new JLabel("Kingdom KNN"); 
        logoLabel.setBounds(40,10,200,35);
        logoLabel.setForeground(Color.white);
        logoLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
        menupanel.add(logoLabel);

        BufferedImage image1=null;
        try 
        {
            image1=ImageIO.read(new File("C:\\Users\\vkris\\OneDrive\\Desktop\\Java project\\long_logo_knn.jpg"));
            Image modifiedimage=image1.getScaledInstance(25,35,java.awt.Image.SCALE_SMOOTH);
            JLabel imagePanel=new JLabel(new ImageIcon(modifiedimage));
            imagePanel.setBounds(10,10,25,35);
            imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
            menupanel.add(imagePanel);
        } 
        catch(IOException e) 
        {
            e.printStackTrace();
        }

        JTextField searchText = new JTextField(""); 
        searchText.setBounds(400,10,300,35);
        menupanel.add(searchText);           

        JPanel titlebarpanel = new JPanel(); 
        titlebarpanel.setBounds(10,70,1250,30);
        titlebarpanel.setLayout(null);
        JLabel catLabel = new JLabel("Categories");
        catLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        catLabel.setBounds(20,0,500,25);
        titlebarpanel.add(catLabel);
        JLabel prodLabel = new JLabel("Trending Products");
        prodLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        prodLabel.setBounds(630,0,500,25);
        titlebarpanel.add(prodLabel);        
        JLabel searchResLabel = new JLabel("Search Results");
        searchResLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
        searchResLabel.setBounds(550,0,500,25);
        titlebarpanel.add(searchResLabel);
        searchResLabel.setVisible(false);
        frame.add(titlebarpanel);

        JPanel categorypanel = new JPanel(); 
        GridBagConstraints cc = new GridBagConstraints();
        cc.insets = new Insets(2, 5, 0, 5);
        JScrollPane sc = new JScrollPane(categorypanel);
        sc.setBounds(10,100,300,500);
        categorypanel.setLayout(new GridBagLayout());
        frame.add(sc);   
        try
        {
            PreparedStatement st = (PreparedStatement) con.prepareStatement("select cat_id,cat_name from category");                                        
            ResultSet rs = st.executeQuery(); 
            int a=0;            
            while(rs.next())
            {                
                cc.gridx=0;
                cc.gridy=a;
                cc.gridwidth=5;                
                int cat_id=rs.getInt("cat_id");

                JButton cat = new JButton(new AbstractAction(rs.getString("cat_name")) {
                            @Override
                            public void actionPerformed( ActionEvent e ) {      
                                Category obj = new Category(cat_id);
                                obj.displayCategory();
                            }
                        });   
                cat.setPreferredSize(new Dimension(270,30));
                categorypanel.add(cat,cc);
                a+=1;
            }    
        }
        catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }

        JPanel productpanel = new JPanel();  
        GridBagConstraints ccc = new GridBagConstraints();
        ccc.insets = new Insets(10, 5, 0, 5);
        JScrollPane scp = new JScrollPane(productpanel);
        scp.setBounds(350,100,900,500);
        productpanel.setLayout(new GridBagLayout());
        frame.add(scp);    
        try
        {
            int a=0;
            PreparedStatement st = (PreparedStatement) con.prepareStatement("select * from product;");                                        
            ResultSet rs = st.executeQuery();  
            while(rs.next())
            {
                ccc.gridx=a%2;
                ccc.gridy=a/2;

                JPanel prodPanel = new JPanel();
                prodPanel.setBorder(BorderFactory.createLineBorder(Color.black));
                prodPanel.setLayout(null);
                prodPanel.setPreferredSize(new Dimension(430,120));
                productpanel.add(prodPanel,ccc); 

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
                prodName.setBounds(120,5,100,40);
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

        JPanel searchResultPanel = new JPanel();          
        searchResultPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 5, 0, 5);        

        JScrollPane scr = new JScrollPane(searchResultPanel);
        scr.setBounds(10,100,1240,500);
        scr.setVisible(false);
        frame.add(scr);
        scr.setBackground(myColor2);

        JButton searchButton = new JButton( new AbstractAction("Search") {
                    @Override
                    public void actionPerformed( ActionEvent e ) {  
                        String toSearch = searchText.getText().trim();
                        if(!toSearch.equals(""))
                        { 
                            searchResultPanel.removeAll();
                            catLabel.setVisible(false);
                            prodLabel.setVisible(false);
                            sc.setVisible(false);
                            scp.setVisible(false);
                            searchResLabel.setVisible(true);
                            scr.setVisible(true);

                            try
                            {
                                int a=0;
                                PreparedStatement st = (PreparedStatement) con.prepareStatement("select * from product where pname like '"+toSearch+"%'");                                        
                                ResultSet rs = st.executeQuery(); 
                                if(!rs.next())
                                {
                                    JLabel noneFound = new JLabel("No Products Found!");
                                    noneFound.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 23));
                                    searchResultPanel.add(noneFound);
                                }
                                else
                                {
                                    rs = st.executeQuery(); 
                                    while(rs.next())
                                    {
                                        c.gridx=a%2;
                                        c.gridy=a/2;
                                        JPanel prodPanel = new JPanel();

                                        prodPanel.setBorder(BorderFactory.createLineBorder(Color.black));
                                        prodPanel.setLayout(null);
                                        prodPanel.setPreferredSize(new Dimension(600,120));
                                        searchResultPanel.add(prodPanel,c); 

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
                                        catch(IOException e1) 
                                        {
                                            e1.printStackTrace();
                                        }

                                        JLabel prodName = new JLabel(rs.getString("pname"));
                                        prodName.setBounds(120,5,100,40);
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
        searchButton.setBounds(700,10,90,35);
        menupanel.add(searchButton);
        JButton homeButton = new JButton(new AbstractAction("Home") {
                    @Override
                    public void actionPerformed( ActionEvent e ) {      
                        searchText.setText("");
                        catLabel.setVisible(true);
                        prodLabel.setVisible(true);
                        sc.setVisible(true);
                        scp.setVisible(true);
                        searchResLabel.setVisible(false);
                        scr.setVisible(false);
                    }
                });
        homeButton.setBounds(850,10,100,35);
        menupanel.add(homeButton);
        JButton accButton = new JButton( new AbstractAction("Account") {
                    @Override
                    public void actionPerformed( ActionEvent e ) {      
                        Account obj = new Account(current_user);
                        obj.displayAccountDetails();
                    }
                });
        accButton.setBounds(950,10,100,35);
        menupanel.add(accButton);
        JButton cartButton = new JButton( new AbstractAction("Cart") 
                {
                    @Override
                    public void actionPerformed( ActionEvent e ) {      
                        Cart obj = new Cart(current_user);
                        obj.displayCart();
                    }
                });
        cartButton.setBounds(1050,10,100,35);
        menupanel.add(cartButton);
        JButton myorderButton = new JButton( new AbstractAction("My Orders") {
                    @Override
                    public void actionPerformed( ActionEvent e ) {      
                        MyOrders obj = new MyOrders(current_user);
                        obj.displayOrders();
                    }
                });
        myorderButton.setBounds(1150,10,100,35);
        menupanel.add(myorderButton);

        frame.getContentPane().setBackground(myColor2);
        menupanel.setBackground(myColor3);
        titlebarpanel.setBackground(myColor2);
        categorypanel.setBackground(myColor2);
        productpanel.setBackground(myColor2);
        searchResultPanel.setBackground(myColor2);
    }
}

