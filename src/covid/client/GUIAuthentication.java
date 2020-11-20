package covid.client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import covid.client.controllers.AuthenticationController;
import covid.client.httpclient.service.ServerClient;
import covid.client.httpclient.service.SessionManager;
import covid.client.logging.LoggingManager;
import covid.client.models.AuthResponse;
import covid.client.models.request.LoginRequest;

public class GUIAuthentication extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	private JButton button;
	private JPanel usernamePanel;
	private JPanel passwordPanel;
	private JPanel buttonPanel;
	int uname;
	String pwrd;
		
	Dashboard dashboard = new Dashboard();
			
	public GUIAuthentication() 
	{		
//		radioBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
						
		usernameLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("Password: ");
		usernameTextField = new JTextField(20);
		passwordField = new JPasswordField(20);		
		
		button = new JButton("Log In");
		
		frame = new JFrame("Student Services/Query Logger");
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(3,1,0,0));
		frame.setSize(new Dimension(350,200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
				
		usernamePanel.setSize(new Dimension(450,20));
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameTextField);
		frame.add(usernamePanel);
		
		passwordPanel.setSize(new Dimension(450,20));
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		frame.add(passwordPanel);
		
		buttonPanel.setSize(new Dimension(450,30));
		buttonPanel.add(button);
		frame.add(buttonPanel);
	}	
	
	public void logIn()
	{		
		button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{				
				try 
		        {
		        	uname = Integer.parseInt(usernameTextField.getText());
		            } catch (Exception z) { 
		            	JOptionPane.showMessageDialog(frame, "Username should only be numbers","Log In", JOptionPane.WARNING_MESSAGE);
		            	usernameTextField.setText("");
		            	passwordField.setText("");
		            	return;
		       }
				pwrd = passwordField.getText();
					
				connectUserToServer();									
			}			
		});		
	}
	
	private void connectUserToServer() 
	{
		try
		{
			AuthenticationController authenticationController = new AuthenticationController();
			AuthResponse authResponse = authenticationController.authenticateUser(new LoginRequest(uname,pwrd));
					
			if(authResponse != null && authResponse.getToken() != null)
			{			
				try {
					if(Objects.equals(authResponse.getUser().getRole(), new String("STUDENT")))
					{
						SessionManager.createUserSession(SessionManager.USER_KEY, authResponse.getUser());
						ServerClient.createClient(authResponse);
						dashboard.studentDashboard(authResponse.getUser());					
						frame.dispose();
					}else
					if(Objects.equals(authResponse.getUser().getRole(), new String("STUDENT_REPRESENTATIVE")))	
					{
						SessionManager.createUserSession(SessionManager.USER_KEY, authResponse.getUser());
						ServerClient.createClient(authResponse);
						dashboard.staffDashboard(authResponse.getUser());		
						frame.dispose();
					}else
					{
						JOptionPane.showMessageDialog(null, "Invalid login", "Log In", JOptionPane.WARNING_MESSAGE);
						usernameTextField.setText("");
		            	passwordField.setText("");
					}									
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "An error occured! Please try again.", "Log In", JOptionPane.INFORMATION_MESSAGE);
					usernameTextField.setText("");
		        	passwordField.setText("");
				}
			}else{
				LoggingManager.getLogger(DRIVER.class).error("Authentication failed");
				JOptionPane.showMessageDialog(null, "An error occured! Please try again.", "Log In", JOptionPane.INFORMATION_MESSAGE);
				usernameTextField.setText("");
	        	passwordField.setText("");
			}
		}catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Invalid login", "Log In", JOptionPane.WARNING_MESSAGE);
			usernameTextField.setText("");
        	passwordField.setText("");
		}
				
	}

	
}


