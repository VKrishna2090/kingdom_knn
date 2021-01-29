import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.io.*;
public class Account
{
    private int current_user;
    Account(int cu)
    {
        current_user = cu;
    }

    void displayAccountDetails()
    {
        Connection con = (new ConnectToDB()).getConnectionToDB();
        JFrame frame = new JFrame("Account Details");        
        frame.setSize(1000,520);
        ImageIcon img = new ImageIcon("C:\\Users\\vkris\\OneDrive\\Desktop\\icon knn.jpg");
        frame.setIconImage(img.getImage());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLayout(null);
        
        Color myColor2=new Color(224,224,224);//background
        Color myColor3=new Color(117,117,117);//alt
        
        JPanel toppanel = new JPanel();        
        toppanel.setBounds(10,10,880,35);
        toppanel.setLayout(null);
        frame.add(toppanel);
        JPanel leftpanel = new JPanel();        
        leftpanel.setBounds(10,50,480,350);
        leftpanel.setLayout(null);
        leftpanel.setBorder(BorderFactory.createLineBorder(Color.black));
        frame.add(leftpanel);
        JPanel rightpanel = new JPanel();        
        rightpanel.setBounds(500,50,480,350);
        rightpanel.setLayout(null);
        rightpanel.setBorder(BorderFactory.createLineBorder(Color.black));
        frame.add(rightpanel);
        JPanel bottompanel = new JPanel();        
        bottompanel.setBounds(10,410,970,55);
        bottompanel.setLayout(null);
        frame.add(bottompanel);
        try
        {
            PreparedStatement st = (PreparedStatement) con.prepareStatement("select * from customer where c_id = ?");  
            st.setString(1,""+current_user);
            ResultSet rs = st.executeQuery();  
            if(rs.next())
            {
                JLabel logoLabel = new JLabel("Account Details"); 
                logoLabel.setBounds(390,0,250,35);
                logoLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
                toppanel.add(logoLabel); 
                JLabel loginDetailsLabel = new JLabel("Login Information:");
                loginDetailsLabel.setBounds(10,10,250,25);
                loginDetailsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
                leftpanel.add(loginDetailsLabel);
                JLabel emailLabel = new JLabel("Email ID:");
                emailLabel.setBounds(10,40,200,25);
                leftpanel.add(emailLabel);
                JTextField emailText = new JTextField(rs.getString("email"));
                emailText.setBounds(250,40,200,30);
                emailText.setEditable(false);
                leftpanel.add(emailText);                
                JLabel passLabel = new JLabel("Password:");
                passLabel.setBounds(10,70,200,25);
                leftpanel.add(passLabel);
                JPasswordField passText = new JPasswordField(rs.getString("pass"));
                leftpanel.add(passText);
                passText.setBounds(250,70,200,30);
                passText.setEditable(false);

                JLabel perDetailsLabel = new JLabel("Personal Information:");
                perDetailsLabel.setBounds(10,110,250,25);
                perDetailsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
                leftpanel.add(perDetailsLabel);
                JLabel fnameLabel = new JLabel("First Name:");
                fnameLabel.setBounds(10,140,200,25);
                leftpanel.add(fnameLabel);
                JTextField fnameText = new JTextField(rs.getString("fname"));
                fnameText.setBounds(250,140,200,30);
                leftpanel.add(fnameText);
                fnameText.setEditable(false);
                JLabel mnameLabel = new JLabel("Middle Name:");
                mnameLabel.setBounds(10,170,200,25);
                leftpanel.add(mnameLabel);
                JTextField mnameText = new JTextField(rs.getString("mname"));
                mnameText.setBounds(250,170,200,30);
                leftpanel.add(mnameText);
                mnameText.setEditable(false);
                JLabel lnameLabel = new JLabel("Last Name:");
                lnameLabel.setBounds(10,200,200,25);
                leftpanel.add(lnameLabel);
                JTextField lnameText = new JTextField(rs.getString("lname"));
                lnameText.setBounds(250,200,200,30);
                leftpanel.add(lnameText);
                lnameText.setEditable(false);
                JLabel mobileLabel = new JLabel("Mobile Num:");
                mobileLabel.setBounds(10,230,200,25);
                leftpanel.add(mobileLabel);
                JTextField mobileText = new JTextField(rs.getString("mobile_num"));
                mobileText.setBounds(250,230,200,30);
                leftpanel.add(mobileText);
                mobileText.setEditable(false);

                JLabel delLabel = new JLabel("Delivery Address:");
                delLabel.setBounds(10,10,250,25);
                delLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
                rightpanel.add(delLabel);
                JLabel add1Label = new JLabel("Address 1:");
                add1Label.setBounds(10,40,200,25);
                rightpanel.add(add1Label);
                JTextField add1Text = new JTextField(rs.getString("address1"));
                add1Text.setBounds(250,40,200,30);
                rightpanel.add(add1Text);
                add1Text.setEditable(false);
                JLabel add2Label = new JLabel("Address 2:");
                add2Label.setBounds(10,70,200,25);
                rightpanel.add(add2Label);
                JTextField add2Text = new JTextField(rs.getString("address2"));
                add2Text.setBounds(250,70,200,30);
                rightpanel.add(add2Text);
                add2Text.setEditable(false);
                JLabel cityLabel = new JLabel("City:");
                cityLabel.setBounds(10,100,200,25);
                rightpanel.add(cityLabel);
                JTextField cityText = new JTextField(rs.getString("city"));
                cityText.setBounds(250,100,200,30);
                rightpanel.add(cityText);
                cityText.setEditable(false);
                JLabel stateLabel = new JLabel("State:");
                stateLabel.setBounds(10,130,200,25);
                rightpanel.add(stateLabel);
                JTextField stateText = new JTextField(rs.getString("state_address"));
                stateText.setBounds(250,130,200,30);
                rightpanel.add(stateText);
                stateText.setEditable(false);
                JLabel countryLabel = new JLabel("Country:");
                countryLabel.setBounds(10,160,200,25);
                rightpanel.add(countryLabel);                
                JTextField countryText = new JTextField(rs.getString("country"));
                countryText.setBounds(250,160,200,30);
                rightpanel.add(countryText);
                countryText.setEditable(false);

                JLabel payLabel = new JLabel("Payment Information:");
                payLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 17));
                payLabel.setBounds(10,200,250,25);
                rightpanel.add(payLabel);
                JLabel cardLabel = new JLabel("Credit Card Num:");
                cardLabel.setBounds(10,230,200,25);
                rightpanel.add(cardLabel);
                JTextField cardText = new JTextField(rs.getString("credit_card_num"));
                cardText.setBounds(250,230,200,30);
                rightpanel.add(cardText);
                cardText.setEditable(false);

                JLabel regsuccess = new JLabel("");
                regsuccess.setBounds(350,10,300,35);
                regsuccess.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
                bottompanel.add(regsuccess);
                JButton saveButton = new JButton(new AbstractAction("Save Changes") {
                            @Override
                            public void actionPerformed( ActionEvent e ) {                                     
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
                                try
                                {
                                    PreparedStatement st = con.prepareStatement("UPDATE customer SET email=?,pass=?,fname=?,mname=?,lname=?,mobile_num=?,address1=?,address2=?,city=?,state_address=?,country=?,credit_card_num=? where c_id=?;");
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
                                    st.setString(13,""+current_user);
                                    int x = st.executeUpdate();
                                    
                                    emailText.setEditable(false);
                                    passText.setEditable(false);
                                    fnameText.setEditable(false);
                                    mnameText.setEditable(false);
                                    lnameText.setEditable(false);
                                    mobileText.setEditable(false);
                                    add1Text.setEditable(false);
                                    add2Text.setEditable(false);
                                    cityText.setEditable(false);
                                    stateText.setEditable(false);
                                    countryText.setEditable(false);
                                    cardText.setEditable(false);
                                    if(x==1)
                                    {                                        
                                        regsuccess.setText("Changes saved successfully!");
                                    }
                                    else
                                        regsuccess.setText("Updation failed!");
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
                saveButton.setBounds(840,10,120,35);
                saveButton.setVisible(true);
                bottompanel.add(saveButton);
                JButton editButton = new JButton(new AbstractAction("Edit") {
                            @Override
                            public void actionPerformed( ActionEvent e ) {      
                                saveButton.setVisible(true);
                                emailText.setEditable(true);
                                passText.setEditable(true);
                                fnameText.setEditable(true);
                                mnameText.setEditable(true);
                                lnameText.setEditable(true);
                                mobileText.setEditable(true);
                                add1Text.setEditable(true);
                                add2Text.setEditable(true);
                                cityText.setEditable(true);
                                stateText.setEditable(true);
                                countryText.setEditable(true);
                                cardText.setEditable(true);
                                regsuccess.setText("");
                            }
                        });  
                editButton.setBounds(750,10,80,35);
                editButton.setVisible(true);
                bottompanel.add(editButton);
                JButton logoutButton = new JButton(new AbstractAction("LOGOUT") {
                            @Override
                            public void actionPerformed( ActionEvent e ) {  
                                System.exit(0);   
                            }
                        });  
                logoutButton.setBounds(10,10,100,35);
                logoutButton.setVisible(true);
                bottompanel.add(logoutButton);
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
        toppanel.setBackground(myColor2);
        leftpanel.setBackground(Color.white);
        rightpanel.setBackground(Color.white);
        bottompanel.setBackground(myColor2);
    }
}