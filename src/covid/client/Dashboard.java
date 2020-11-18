package covid.client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import covid.client.enumeration.ComplainStatus;
import covid.client.httpclient.service.Covid19Client;
import covid.client.httpclient.service.ServerClient;
import covid.client.httpclient.service.SessionManager;
import covid.client.models.*;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class Dashboard extends JFrame
{
	private JFrame frame;
	
	private JPanel activities;
	private JPanel window;
	private JLabel title,head;
	private JButton home;
	private JButton logout;
	private JButton addQuery;
	private JButton viewPastComplaints;
	private JButton viewSpecificComplaint;
	private JInternalFrame internalFrame;
	private JButton liveChat;
	private JButton viewAllEnquiries;
	private JButton viewAStudentEnquiry;
	private JButton respondToEnquiry;
	private User user;
	int x;
	
	public Dashboard()
	{
		activities = new JPanel();
		window = new JPanel();
		title = new JLabel("Select an option below:");
		home = new JButton("Home");
		logout = new JButton("Log Out");
		internalFrame = new JInternalFrame("",false,false,false,false);	
		internalFrame.setVisible(true);
	}

	public void studentDashboard(User student)
	{
		this.user = student;
		JOptionPane.showMessageDialog(frame, "Welcome back, "+student.getFirstName()+"!", "Log In", JOptionPane.INFORMATION_MESSAGE);
		
		frame = new JFrame("covid.client.Dashboard (Student)");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setBackground(Color.BLUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		addQuery = new JButton("Add Query");
		viewPastComplaints = new JButton("View Previous Complaints");
		viewSpecificComplaint = new JButton("View Specific Complaint");		
		
		activities.add(home);
		activities.add(addQuery);
		activities.add(viewPastComplaints);
		activities.add(viewSpecificComplaint);
		activities.add(logout);
		
		window.add(internalFrame);
		window.setSize(new Dimension(1000,500));		
		
		frame.add(activities,BorderLayout.NORTH);
		
		studentHome();
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				studentHome();	
			}			
		});
		
		addQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				internalFrame.dispose();
				internalFrame = new JInternalFrame("",false,false,false,false);
				internalFrame.setVisible(true);
				
				JRadioButton addDropBtn = new JRadioButton("Add Drop",true);
				JRadioButton graduationBtn = new JRadioButton("Graduation");
				JRadioButton accountingBtn = new JRadioButton("Accounting");
				JRadioButton registrationBtn = new JRadioButton("Registration");
				JPanel options = new JPanel();
				JPanel queryTitlePanel = new JPanel();
				JPanel queryTextAreaPanel = new JPanel();
				JPanel buttonPanel = new JPanel();
				JLabel title = new JLabel("Select a query option:");
				JLabel queryTitle = new JLabel("State your query here:");
				JTextArea query = new JTextArea();
				JButton sendQuery = new JButton("Send");
				
				query.setPreferredSize(new Dimension(1250,650));
				queryTitlePanel.add(queryTitle);
				queryTextAreaPanel.add(query);
				query.setFocusable(true);
				query.setLineWrap(true); //Creates new line of text at end of Text Area
				buttonPanel.add(sendQuery);
				
				ButtonGroup bg = new ButtonGroup();
				
				bg.add(addDropBtn);
				bg.add(graduationBtn);
				bg.add(accountingBtn);
				bg.add(registrationBtn);
				
				options.add(title);
				options.add(addDropBtn);
				options.add(graduationBtn);
				options.add(accountingBtn);
				options.add(registrationBtn);				
				
				internalFrame.add(options,BorderLayout.NORTH);
				internalFrame.add(queryTitlePanel,BorderLayout.WEST);
				internalFrame.add(queryTextAreaPanel,BorderLayout.CENTER);
				internalFrame.add(buttonPanel,BorderLayout.SOUTH);
				
				frame.add(internalFrame);
				
				sendQuery.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) {
						
						long queryService = 1L;
						
						if(addDropBtn.isSelected())
						{
							queryService = 2L;
						}else
						if(graduationBtn.isSelected())
						{
							queryService = 4L;
						}else
						if(accountingBtn.isSelected())
						{
							queryService = 3L;
						}else
						if(registrationBtn.isSelected())
						{
							queryService = 1L;
						}
						
						try
						{
							Covid19Client serverClient = ServerClient.getClient();
							
							ApiResponse<Complaints> complaintsApiResponse = serverClient
									.createUserComplaint(new Complaints(new Services(queryService), query.getText()));
							
							JOptionPane.showMessageDialog(frame,complaintsApiResponse.getMessage(), "Add Query", JOptionPane.INFORMATION_MESSAGE);
						}catch(NullPointerException np)
						{
							JOptionPane.showMessageDialog(frame,"Unable to post this query.", "Submission Failed", JOptionPane.WARNING_MESSAGE);
							query.setText("");
						}
						
					}			
				});
								
			}			
		});
		
		viewPastComplaints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				internalFrame.dispose();
				internalFrame = new JInternalFrame("",false,false,false,false);
				internalFrame.setVisible(true);
				
				String[] columns = {"DATE","RESPONDER","QUERY",""};
				String[][] data_rows = new String[50][50];
				
				Covid19Client serverClient = ServerClient.getClient();
				
				List<Complaints> complaintsList = serverClient.getComplaintsByStudentID(user.getId());
				
				x=0;
				
				complaintsList.forEach(c -> {	
					
					data_rows[x][0] = c.getId().toString();
					data_rows[x][1] = c.getComplainStatus().toString();
					data_rows[x][2] = c.getQuery();
					data_rows[x][3] = "VIEW";
					x++;
				});			
				
				JTable table = new JTable(data_rows, columns);	
				JScrollPane scrollPane = new JScrollPane(table);
				
				table.getColumnModel().getColumn(0).setPreferredWidth(200);
				table.getColumnModel().getColumn(1).setPreferredWidth(120);
				table.getColumnModel().getColumn(2).setPreferredWidth(1400);
//				table.getColumnModel().getColumn(2).setPreferredWidth(3);
				
				internalFrame.add(scrollPane);
				frame.add(internalFrame);					
			}			
		});
		
		viewSpecificComplaint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}			
		});
		
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				
				new GUIAuthentication().logIn();				
			}			
		});
	}
	
	public void staffDashboard(User staff)
	{
		this.user = staff;
		JOptionPane.showMessageDialog(frame, "Welcome back, "+staff.getFirstName()+"!", "Log In", JOptionPane.INFORMATION_MESSAGE);
		
		frame = new JFrame("covid.client.Dashboard (Staff)");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		viewAllEnquiries = new JButton("View all Student's Enquiries");
		viewAStudentEnquiry = new JButton("View a Student's Enquiry");
		respondToEnquiry = new JButton("Respond to Enquiry");
		liveChat = new JButton("Live Chat");
		logout = new JButton("Log Out");			
		
		activities.add(home);
		activities.add(viewAllEnquiries);
		activities.add(viewAStudentEnquiry);
		activities.add(respondToEnquiry);
		activities.add(liveChat);
		activities.add(logout);
		
		window.add(internalFrame);
		window.setSize(new Dimension(1000,500));		
		
		frame.add(activities,BorderLayout.NORTH);
		
		staffHome();
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				staffHome();		
			}			
		});
		
		viewAllEnquiries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				internalFrame.dispose();
				internalFrame = new JInternalFrame("",false,false,false,false);
				internalFrame.setVisible(true);
				
				String[] columns = {"STUDENT ID","SERVICE","QUERY",""};
				String[][] data_rows = new String[50][50];
				
				Covid19Client serverClient = ServerClient.getClient(); 
				
				List<Complaints> complaintsList = serverClient.getComplaintsByStudentID(1L); //Code not complete to list all students queries...1L is for testing
				
				x=0;
				
				complaintsList.forEach(c -> {
					data_rows[x][0] = c.getId().toString();
					data_rows[x][1] = c.getComplainStatus().toString();
					data_rows[x][2] = c.getQuery();
					data_rows[x][3] = "View";
					x++;
						
				});					
				
				JTable table = new JTable(data_rows, columns);	
				JScrollPane scrollPane = new JScrollPane(table);
				
				table.getColumnModel().getColumn(0).setPreferredWidth(7);
				table.getColumnModel().getColumn(1).setPreferredWidth(4);
				table.getColumnModel().getColumn(2).setPreferredWidth(1000);
				table.getColumnModel().getColumn(3).setPreferredWidth(2);
				
				internalFrame.add(scrollPane);
				frame.add(internalFrame);
			}			
			
		});
		
		viewAStudentEnquiry.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				
				internalFrame.dispose();
				internalFrame = new JInternalFrame("",false,false,false,false);	
				internalFrame.setVisible(true);
				
				JPanel requestPanel = new JPanel();
				JLabel requestLabel = new JLabel("Enter a student's ID number:");
				JTextField requestTextField = new JTextField(7);
				JButton requestBtn = new JButton("View");
				
				requestPanel.add(requestLabel);
				requestPanel.add(requestTextField);
				requestPanel.add(requestBtn);
				internalFrame.add(requestPanel);			
				
				
				requestBtn.addActionListener(new ActionListener()
				{
					String requestId;
					public void actionPerformed(ActionEvent e) 
					{							
												
						Covid19Client serverClient = ServerClient.getClient(); // crate the API client service instance. this will already have the JWT token from the previous screen when we initialize the abstract class
												
						List<User> userList = serverClient.getAllUsers();
						
						if(userList != null)
						{
							userList.forEach(o ->{
								if(o.getUserName() == requestId)
								{
									System.out.println(o.getFirstName()+"'s complaints are as follows:");
									
									List<Complaints> complaintsList = serverClient.getComplaintsByStatusAndStudentID(user.getId(), ComplainStatus.NEW);
									
									if(complaintsList != null)
									{
										complaintsList.forEach(c -> {
											System.out.println(c.getQuery());
											System.out.println(c.getComplainStatus());
										});							
									}else
									{
										JOptionPane.showMessageDialog(frame,"There are no complaints at this time.", "Complaints List", JOptionPane.INFORMATION_MESSAGE);
									}
								}
								
							});
						}else
						{
							JOptionPane.showMessageDialog(frame,"Unable to grant request", "Staff", JOptionPane.WARNING_MESSAGE);
						}
						


//						Assert.notNull(user, "User cannot be null"); // assert that the user is not empty	
																					
					}			
				});
				
				frame.add(internalFrame);
			}			
		});
		
		respondToEnquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
			}			
		});
		
		liveChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
			}			
		});
		
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new GUIAuthentication().logIn();				
			}			
		});		
		
	}	
	
	public void studentHome()
	{
		internalFrame.dispose();
		internalFrame = new JInternalFrame("",false,false,false,false);	
		internalFrame.setVisible(true);
		
		JLabel title = new JLabel("WELCOME TO THE STUDENT DASHBOARD");		
		JLabel profile = new JLabel("Your Profile:");		
		JLabel name = new JLabel("NAME: "+user.getFirstName()+" "+user.getLastName());	
		JLabel id = new JLabel("ID: "+user.getUserName());
		JLabel email = new JLabel("EMAIL: "+user.getEmail());
		JPanel headerPanel = new JPanel();
		JPanel profilePanel = new JPanel();
		JPanel namePanel = new JPanel();
		JPanel idPanel = new JPanel();
		JPanel emailPanel = new JPanel();
		JPanel space1 = new JPanel();
		JPanel space2 = new JPanel();
		JPanel space3 = new JPanel();
		
		title.setFont(new Font("Algerian", Font.BOLD, 40));
		profile.setFont(new Font("Serif", Font.ITALIC, 18));		
				
		headerPanel.add(title);
		profilePanel.add(profile);
		namePanel.add(name);
		idPanel.add(id);
		emailPanel.add(email);

		internalFrame.add(headerPanel,BorderLayout.CENTER);
		internalFrame.add(space1);
		internalFrame.add(space2);
		internalFrame.add(profilePanel,BorderLayout.CENTER);
		internalFrame.add(space3);
		internalFrame.add(namePanel,BorderLayout.CENTER);
		internalFrame.add(idPanel,BorderLayout.CENTER);
		internalFrame.add(emailPanel,BorderLayout.CENTER);
		internalFrame.setLayout(new GridLayout(12,0));
		frame.add(internalFrame);
		
	}
	
	public void staffHome()
	{
		internalFrame.dispose();
		internalFrame = new JInternalFrame("",false,false,false,false);	
		internalFrame.setVisible(true);
		
		JLabel title = new JLabel("WELCOME TO THE STAFF DASHBOARD");		
		JLabel profile = new JLabel("Your Profile:");		
		JLabel name = new JLabel("NAME: "+user.getFirstName()+" "+user.getLastName());	
		JLabel id = new JLabel("ID: "+user.getUserName());
		JLabel email = new JLabel("EMAIL: "+user.getEmail());
		JPanel headerPanel = new JPanel();
		JPanel profilePanel = new JPanel();
		JPanel namePanel = new JPanel();
		JPanel idPanel = new JPanel();
		JPanel emailPanel = new JPanel();
		JPanel space1 = new JPanel();
		JPanel space2 = new JPanel();
		JPanel space3 = new JPanel();
		
		title.setFont(new Font("Algerian", Font.BOLD, 40));
		profile.setFont(new Font("Serif", Font.ITALIC, 18));		
				
		headerPanel.add(title);
		profilePanel.add(profile);
		namePanel.add(name);
		idPanel.add(id);
		emailPanel.add(email);

		internalFrame.add(headerPanel,BorderLayout.CENTER);
		internalFrame.add(space1);
		internalFrame.add(space2);
		internalFrame.add(profilePanel,BorderLayout.CENTER);
		internalFrame.add(space3);
		internalFrame.add(namePanel,BorderLayout.CENTER);
		internalFrame.add(idPanel,BorderLayout.CENTER);
		internalFrame.add(emailPanel,BorderLayout.CENTER);
		internalFrame.setLayout(new GridLayout(12,0));
		frame.add(internalFrame);

	}	
	
	
}
