import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class KingdomKNN 
{      
    private static int current_user;
    public static void main(String[] args) 
    {
        Connection con = (new ConnectToDB()).getConnectionToDB();
        //FIRST WINDOW FRAME
        JFrame frame = new JFrame("Welcome to Kingdom KNN");
        ImageIcon img = new ImageIcon("C:\\Users\\vkris\\OneDrive\\Desktop\\icon knn.jpg");
        frame.setIconImage(img.getImage());
        frame.setSize(570,337);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //LEFT PANEL FOR MENU
        JPanel leftpanel = new JPanel();
        leftpanel.setBounds(0,0,150,900);
        leftpanel.setBackground(Color.LIGHT_GRAY); 
        frame.add(leftpanel);

        BufferedImage image=null;
        try 
        {
            image=ImageIO.read(new File("C:\\Users\\vkris\\OneDrive\\Desktop\\Java project\\logo_knn.jpg"));
            Image modifiedimage=image.getScaledInstance(140,130,java.awt.Image.SCALE_SMOOTH);//returns a scaled version of image
            JLabel imagePanel=new JLabel(new ImageIcon(modifiedimage));
            imagePanel.setPreferredSize(new Dimension(140,130));
            imagePanel.setBorder(BorderFactory.createLineBorder(Color.black));
            leftpanel.add(imagePanel);
        } 
        catch(IOException e) 
        {
            e.printStackTrace();
        }

        JPanel regispanel = new JPanel();
        JScrollPane sc = new JScrollPane(regispanel);

        //RIGHT LOGIN PANEL
        JPanel loginpanel = new JPanel();
        loginpanel.setBounds(150,0,410,300);    
        loginpanel.setLayout(null);//to remove flow layout
        frame.add(loginpanel);
        frame.setLayout(null);//to remove border layout
        //CONTAINERS OF LOGIN PANEL
        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(55,20,100,25);
        loginpanel.add(userLabel);
        JTextField userText = new JTextField();
        userText.setBounds(190,20,180,25);
        loginpanel.add(userText);
        JLabel pLabel = new JLabel("Password");
        pLabel.setBounds(55,60,100,25);
        loginpanel.add(pLabel);
        JPasswordField pText = new JPasswordField();
        pText.setBounds(190,60,180,25);
        loginpanel.add(pText);
        JLabel success = new JLabel("");
        success.setBounds(70,110,300,25);
        loginpanel.add(success);
        JButton button = new JButton(new AbstractAction("Login") {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        String user = userText.getText().trim();
                        String password = pText.getText().trim();

                        try
                        {
                            PreparedStatement st = (PreparedStatement) con.prepareStatement("select pass from customer where email=?");
                            st.setString(1, user);            
                            ResultSet rs = st.executeQuery();  
                            if(rs.next())
                            {
                                String actual_password = rs.getString("pass").trim(); 
                                if(actual_password.equals(password))
                                {
                                    success.setText("Login Successful!");
                                    userText.setText("");
                                    pText.setText("");
                                    frame.setVisible(false);
                                    PreparedStatement st2 = (PreparedStatement) con.prepareStatement("select c_id from customer where email=?");
                                    st2.setString(1, user);            
                                    ResultSet rs2 = st2.executeQuery();
                                    if(rs2.next())
                                        current_user=rs2.getInt("c_id");

                                    HomePage ob1 = new HomePage(current_user);
                                    ob1.displayHomePage();  
                                }
                                else
                                {
                                    userText.setText("");
                                    pText.setText("");
                                    success.setText("Invalid Password!");
                                }

                            }
                            else
                            {
                                userText.setText("");
                                pText.setText("");
                                success.setText("User Not Found! Please Register");
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
        button.setBounds(274,100,80,25);        
        loginpanel.add(button);                
        JButton loginButton = new JButton( new AbstractAction("Login") {
                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        userText.setText("");
                        pText.setText("");
                        sc.setVisible(false);
                        loginpanel.setVisible(true);
                    }
                });
        loginButton.setPreferredSize(new Dimension(130, 30));

        leftpanel.add(loginButton);

        //RIGHT REGISTRATION PANEL
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 0, 5, 0);

        sc.setBounds(150,0,400,300);
        regispanel.setLayout(new GridBagLayout());
        sc.setVisible(false);
        frame.add(sc);
        //COMPONENTS OF REGISTRATION PANEL
        c.gridx=0;
        c.gridy=0;
        JLabel emailLabel = new JLabel("Email ID:");
        emailLabel.setPreferredSize(new Dimension(100,25));
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        regispanel.add(emailLabel,c);
        c.gridx=1;
        c.gridy=0;
        JTextField emailText = new JTextField();
        emailText.setPreferredSize(new Dimension(180,25));
        regispanel.add(emailText,c);
        c.gridx=0;
        c.gridy=1;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setPreferredSize(new Dimension(100,25));
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        regispanel.add(passLabel,c);
        c.gridx=1;
        c.gridy=1;
        JPasswordField passText = new JPasswordField();
        passText.setPreferredSize(new Dimension(180,25));
        regispanel.add(passText,c);
        c.gridx=0;
        c.gridy=2;
        JLabel fnameLabel = new JLabel("First Name:");
        fnameLabel.setPreferredSize(new Dimension(100,25));
        fnameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        regispanel.add(fnameLabel,c);
        c.gridx=1;
        c.gridy=2;
        JTextField fnameText = new JTextField();
        fnameText.setPreferredSize(new Dimension(180,25));
        regispanel.add(fnameText,c);
        c.gridx=0;
        c.gridy=3;
        JLabel mnameLabel = new JLabel("Middle Name:");
        mnameLabel.setPreferredSize(new Dimension(100,25));
        mnameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        regispanel.add(mnameLabel,c);
        c.gridx=1;
        c.gridy=3;
        JTextField mnameText = new JTextField();
        mnameText.setPreferredSize(new Dimension(180,25));
        regispanel.add(mnameText,c);
        c.gridx=0;
        c.gridy=4;
        JLabel lnameLabel = new JLabel("Last Name:");
        lnameLabel.setPreferredSize(new Dimension(100,25));
        lnameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        regispanel.add(lnameLabel,c);
        c.gridx=1;
        c.gridy=4;
        JTextField lnameText = new JTextField();
        lnameText.setPreferredSize(new Dimension(180,25));
        regispanel.add(lnameText,c);
        c.gridx=0;
        c.gridy=5;
        JLabel mobileLabel = new JLabel("Mobile Num:");
        mobileLabel.setPreferredSize(new Dimension(100,25));
        mobileLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        regispanel.add(mobileLabel,c);
        c.gridx=1;
        c.gridy=5;
        JTextField mobileText = new JTextField();
        mobileText.setPreferredSize(new Dimension(180,25));
        regispanel.add(mobileText,c);
        c.gridx=0;
        c.gridy=6;
        JLabel add1Label = new JLabel("Address 1:");
        add1Label.setPreferredSize(new Dimension(100,25));
        add1Label.setAlignmentX(Component.LEFT_ALIGNMENT);
        regispanel.add(add1Label,c);
        c.gridx=1;
        c.gridy=6;
        JTextField add1Text = new JTextField();
        add1Text.setPreferredSize(new Dimension(180,25));
        regispanel.add(add1Text,c);
        c.gridx=0;
        c.gridy=7;
        JLabel add2Label = new JLabel("Address 2:");
        add2Label.setPreferredSize(new Dimension(100,25));
        add2Label.setAlignmentX(Component.LEFT_ALIGNMENT);
        regispanel.add(add2Label,c);
        c.gridx=1;
        c.gridy=7;
        JTextField add2Text = new JTextField();
        add2Text.setPreferredSize(new Dimension(180,25));
        regispanel.add(add2Text,c);
        c.gridx=0;
        c.gridy=8;
        JLabel cityLabel = new JLabel("City:");
        cityLabel.setPreferredSize(new Dimension(100,25));
        cityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        regispanel.add(cityLabel,c);
        c.gridx=1;
        c.gridy=8;
        JTextField cityText = new JTextField();
        cityText.setPreferredSize(new Dimension(180,25));
        regispanel.add(cityText,c);
        c.gridx=0;
        c.gridy=9;
        JLabel stateLabel = new JLabel("State:");
        stateLabel.setPreferredSize(new Dimension(100,25));
        stateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        regispanel.add(stateLabel,c);
        c.gridx=1;
        c.gridy=9;
        JTextField stateText = new JTextField();
        stateText.setPreferredSize(new Dimension(180,25));
        regispanel.add(stateText,c);
        c.gridx=0;
        c.gridy=10;
        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setPreferredSize(new Dimension(100,25));
        countryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        regispanel.add(countryLabel,c);
        c.gridx=1;
        c.gridy=10;
        JTextField countryText = new JTextField();
        countryText.setPreferredSize(new Dimension(180,25));
        regispanel.add(countryText,c);
        c.gridx=0;
        c.gridy=11;
        JLabel cardLabel = new JLabel("Credit Card Num:");
        cardLabel.setPreferredSize(new Dimension(100,25));
        cardLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        regispanel.add(cardLabel,c);
        c.gridx=1;
        c.gridy=11;
        JTextField cardText = new JTextField();
        cardText.setPreferredSize(new Dimension(180,25));
        regispanel.add(cardText,c);
        c.gridx=0;
        c.gridy=12;
        JLabel regsuccess = new JLabel("");
        regsuccess.setPreferredSize(new Dimension(170,25));
        regispanel.add(regsuccess,c);
        JButton regisbutton = new JButton( new AbstractAction("Register") {
                    @Override
                    public void actionPerformed(ActionEvent e) 
                    {
                        String email = emailText.getText().trim();
                        String pass = passText.getText().trim();
                        String fname = fnameText.getText().trim();
                        String mname = mnameText.getText().trim();
                        String lname = lnameText.getText().trim();
                        String mobile = mobileText.getText().trim();
                        String add1 = add1Text.getText().trim();
                        String add2 = add2Text.getText().trim();
                        String city = cityText.getText().trim();
                        String state = stateText.getText().trim();
                        String country = countryText.getText().trim();
                        String card = cardText.getText().trim();

                        int digitCount=0;
                        for(int i=0; i<mobile.length(); i++)
                        {
                            if(Character.isDigit(mobile.charAt(i)))
                                digitCount++;
                        }
                        int cardCount=0;
                        for(int i=0; i<card.length(); i++)
                        {
                            if(Character.isDigit(card.charAt(i)))
                                cardCount++;
                        }
                        if(!isValid(pass))
                        {
                            regsuccess.setText("Enter a better password!");                            
                        }
                        else if(digitCount!=10)
                        {
                            regsuccess.setText("Enter valid mobile number!");
                        }
                        else if(cardCount!=16)
                        {
                            regsuccess.setText("Enter valid card number!");
                        }
                        else
                        {
                            try
                            {
                                PreparedStatement st = con.prepareStatement("INSERT INTO customer VALUES ("+((int)(Math.random()*10000))+",?,?,?,?,?,?,?,?,?,?,?,?);");
                                st.setString(1,email);
                                st.setString(2,pass);
                                st.setString(3,fname);
                                st.setString(4,mname);
                                st.setString(5,lname);
                                st.setString(6,mobile);
                                st.setString(7,add1);
                                st.setString(8,add2);
                                st.setString(9,city);
                                st.setString(10,state);
                                st.setString(11,country);
                                st.setString(12,card);
                                int x = st.executeUpdate();
                                if(x==1)
                                {
                                    emailText.setText("");
                                    passText.setText("");
                                    fnameText.setText("");
                                    mnameText.setText("");
                                    lnameText.setText("");
                                    mobileText.setText("");
                                    add1Text.setText("");
                                    add2Text.setText("");
                                    cityText.setText("");
                                    stateText.setText("");
                                    countryText.setText("");
                                    cardText.setText("");
                                    regsuccess.setText("Registration successful!");
                                }
                                else
                                    regsuccess.setText("Registration failed!");
                            }
                            catch(SQLException sqlException) {
                                sqlException.printStackTrace();
                            }
                            catch(Exception ex)
                            {
                                System.out.println(ex);
                            }
                        }

                    }});
        c.gridx=1;
        c.gridy=12;            
        regisbutton.setPreferredSize(new Dimension(90,25));        
        regispanel.add(regisbutton,c);

        JButton registerButton = new JButton( new AbstractAction("Register") {
                    @Override
                    public void actionPerformed( ActionEvent e ) {
                        sc.setVisible(true);
                        loginpanel.setVisible(false);
                    }
                });
        registerButton.setPreferredSize(new Dimension(130, 30));
        leftpanel.add(registerButton);         

        frame.setVisible(true);

        Color myColor1=new Color(117,117,117);
        leftpanel.setBackground(myColor1);
        Color myColor2=new Color(224,224,224);//background
        Color myColor3=new Color(224,224,224);
        loginpanel.setBackground(myColor2);
        
        regispanel.setBackground(myColor3);

    }

    static boolean isValid(String password)
    {
        boolean flag=true;
        //checking if it contains atleast one digit
        int count_of_digits=0;
        for(int i=0;i<password.length();i++)
        {
            char ch=password.charAt(i);
            if(ch>='0' && ch<='9')
                count_of_digits++;
        }
        if(count_of_digits==0)
        {
            flag=false;
        }
        //checking if length is between 8 and 15
        else if(password.length()<8 || password.length()>15)
        {
            flag=false;
        }
        else
        {//checking for special character
            int count_of_special_char=0;
            for(int i=0;i<password.length();i++)
            {
                char ch=password.charAt(i);
                if(!Character.isDigit(ch) && !Character.isLetter(ch) && !Character.isSpaceChar(ch))
                {
                    count_of_special_char++;
                }
            }
            if(count_of_special_char==0)
                flag=false;
        }
        return flag;
    }
}
