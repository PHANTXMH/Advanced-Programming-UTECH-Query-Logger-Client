package covid.client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
/*
		// move these code to wherever you want to use them
		Covid19Client serverClient = ServerClient.getClient(); // crate the API client service instance. this will already have the JWT token from the previous screen when we initialize the abstract class

		Session<User> sessionManager = (Session<User>) SessionManager.getByKey(SessionManager.USER_KEY); // get user from session

		User user = sessionManager.getData() != null ? sessionManager.getData() : null; // get user date from the session

		Assert.notNull(user, "User cannot be null"); // assert that the user is not empty

		ApiResponse<Complaints> complaintsApiResponse = serverClient
				.createUserComplaint(new Complaints(new Services(1L), "When is the dealThe is a test complaint and the remaining ones "));

		System.out.println(complaintsApiResponse.getMessage());

		System.out.println(complaintsApiResponse.getStatus());

		List<Complaints> complaintsList = serverClient.getComplaintsByStudentID(user.getId());

		complaintsList.forEach(c -> {
			System.out.println(c.getQuery());
			System.out.println(c.getComplainStatus());
		});

		List<Complaints> complaintsList2 = serverClient.getComplaintsByStatusAndStudentID(user.getId(), ComplainStatus.NEW);

		complaintsList2.forEach(c -> {
			System.out.println(c.getQuery());
			System.out.println(c.getComplainStatus());
		});

		List<Complaints> complaintsList3 = serverClient.getComplaintsByStatus(ComplainStatus.NEW);

		if(complaintsList3 != null && !CollectionUtils.isEmpty(complaintsList3)) {
			complaintsList3.forEach(c -> {
				System.out.println(c.getQuery());
				System.out.println(c.getComplainStatus());
			});
		}else{
			// list is empty
		}

		ApiResponse<Complaints> deleteResponse = serverClient.deleteUserComplaint(user.getId());

		if(deleteResponse != null && deleteResponse.isSuccess()){
			// delete was successful
			System.out.println(deleteResponse.getMessage());
		}else if(deleteResponse != null && !deleteResponse.isSuccess()){
			// unsuccessful
		}else{
			// unsuccessful
		}

		// end of test code -- i will put these code in a unit test class when i have the time but i just added it here to show you how to use the client
*/

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
				internalFrame.setFocusable(true);
				
				JRadioButton addDropBtn = new JRadioButton("Add Drop");
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
						
						Covid19Client serverClient = ServerClient.getClient(); // crate the API client service instance. this will already have the JWT token from the previous screen when we initialize the abstract class

//						Session<User> sessionManager = (Session<User>) SessionManager.getByKey(SessionManager.USER_KEY); // get user from session

//						User user = sessionManager.getData() != null ? sessionManager.getData() : null; // get user date from the session

//						Assert.notNull(user, "User cannot be null"); // assert that the user is not empty

						ApiResponse<Complaints> complaintsApiResponse = serverClient
								.createUserComplaint(new Complaints(new Services(queryService), query.getText()));

//						System.out.println(complaintsApiResponse.getMessage());
						JOptionPane.showMessageDialog(frame,complaintsApiResponse.getMessage(), "Add Query", JOptionPane.INFORMATION_MESSAGE);
//						System.out.println(complaintsApiResponse.getStatus());
					}			
				});
								
			}			
		});
		
		viewPastComplaints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
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
				Covid19Client serverClient = ServerClient.getClient(); // crate the API client service instance. this will already have the JWT token from the previous screen when we initialize the abstract class

//				Session<User> sessionManager = (Session<User>) SessionManager.getByKey(SessionManager.USER_KEY); // get user from session

//				User user = sessionManager.getData() != null ? sessionManager.getData() : null; // get user date from the session

//				Assert.notNull(user, "User cannot be null"); // assert that the user is not empty
				
				List<Complaints> complaintsList3 = serverClient.getComplaintsByStatus(ComplainStatus.NEW);

				if(complaintsList3 != null && !CollectionUtils.isEmpty(complaintsList3)) {
					complaintsList3.forEach(c -> {
						System.out.println(c.getQuery());
						System.out.println(c.getComplainStatus());
					});
				}else{
					JOptionPane.showMessageDialog(frame,"There are no complaints at this time.", "Complaints List", JOptionPane.INFORMATION_MESSAGE);// list is empty
				}
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
						setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);							
												
						Covid19Client serverClient = ServerClient.getClient(); // crate the API client service instance. this will already have the JWT token from the previous screen when we initialize the abstract class
						
//						Session<User> sessionManager = (Session<User>) SessionManager.getByKey(SessionManager.USER_KEY); // get user from session

//						User user = sessionManager.getData() != null ? sessionManager.getData() : null; // get user date from the session

//						Assert.notNull(user, "User cannot be null"); // assert that the user is not empty	
						List<Complaints> complaintsList = serverClient.getComplaintsByStudentID(1L);
					
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
