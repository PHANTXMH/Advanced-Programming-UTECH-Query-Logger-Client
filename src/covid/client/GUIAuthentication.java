package covid.client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class GUIAuthentication extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JRadioButton rbtnStudent;
	private JRadioButton rbtnStudentRep;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	private JButton button;
	private JPanel radioBtnPanel;
	private JPanel usernamePanel;
	private JPanel passwordPanel;
	private JPanel buttonPanel;
	String uname;
	String pwrd;
		
	Dashboard dashboard = new Dashboard();
			
	public GUIAuthentication() 
	{		
		radioBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		passwordPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));					
	}	
	
	public void logIn()
	{			
		frame = new JFrame("Student Services/Query Logger");
		
		rbtnStudent = new JRadioButton("Student");
		rbtnStudentRep = new JRadioButton("Staff");
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbtnStudent);
		bg.add(rbtnStudentRep);
		
		usernameLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("Password: ");
		usernameTextField = new JTextField(20);
		passwordField = new JPasswordField(20);		
		
		button = new JButton("Log In");
		
		frame.setLayout(new GridLayout(4,1,0,0));
		frame.setSize(new Dimension(350,200));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		radioBtnPanel.setSize(new Dimension(450,30));
		radioBtnPanel.add(rbtnStudent);
		radioBtnPanel.add(rbtnStudentRep);
		frame.add(radioBtnPanel);
		
		usernamePanel.setSize(new Dimension(450,30));
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameTextField);
		frame.add(usernamePanel);
		
		passwordPanel.setSize(new Dimension(450,30));
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		frame.add(passwordPanel);
		
		buttonPanel.setSize(new Dimension(450,30));
		buttonPanel.add(button);
		frame.add(buttonPanel);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				uname = usernameTextField.getText();
				pwrd = passwordField.getText();	
				
				if(rbtnStudent.isSelected() && checkCredentials())
				{							
					System.out.println("Username: " +uname +"\nPassword: " +pwrd);
					dashboard.studentDashboard();
					frame.dispose();
					
				}else
				if(rbtnStudentRep.isSelected() && checkCredentials())
				{								
					System.out.println("Username: " +uname +"\nPassword: " +pwrd);
					dashboard.staffDashboard();
					frame.dispose();
					
				}else				
				{						
				JOptionPane.showMessageDialog(frame, "Please specify your role.", "Log In", JOptionPane.WARNING_MESSAGE);
				}					
			}			
		});		
	}
	
	
	
	public boolean checkCredentials()
	{
		return true;
	}
}
