import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
	private JPanel welcome;
	private JPanel content;
	
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
	
	public void studentDashboard()
	{
		
		JOptionPane.showMessageDialog(frame, "Logged in as Student.", "Log In", JOptionPane.INFORMATION_MESSAGE);
		
		frame = new JFrame("Dashboard (Student)");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setBackground(Color.BLUE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		addQuery = new JButton("Add Query");
		viewPastComplaints = new JButton("View Previous Complaints");
		viewSpecificComplaint = new JButton("View Specific Complaint");		
		
		studentHome();
		
		activities.add(home);
		activities.add(addQuery);
		activities.add(viewPastComplaints);
		activities.add(viewSpecificComplaint);
		activities.add(logout);
		
		window.add(internalFrame);
		window.setSize(new Dimension(1000,500));		
		
		frame.add(activities,BorderLayout.NORTH);
		frame.add(window,BorderLayout.CENTER);
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					
			}			
		});
		
		addQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
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
	
	public void staffDashboard()
	{
		JOptionPane.showMessageDialog(frame, "Logged in as Staff.", "Log In", JOptionPane.INFORMATION_MESSAGE);
		
		frame = new JFrame("Dashboard (Staff)");
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		viewAllEnquiries = new JButton("View all Student's Enquiries");
		viewAStudentEnquiry = new JButton("View a Student's Enquiry");
		respondToEnquiry = new JButton("Respond to Enquiry");
		liveChat = new JButton("Live Chat");
		logout = new JButton("Log Out");		
		
		staffHome();
		
		activities.add(home);
		activities.add(viewAllEnquiries);
		activities.add(viewAStudentEnquiry);
		activities.add(respondToEnquiry);
		activities.add(liveChat);
		activities.add(logout);
		
		window.add(internalFrame);
		window.setSize(new Dimension(1000,500));		
		
		frame.add(activities,BorderLayout.NORTH);
		frame.add(window,BorderLayout.CENTER);
		
		home.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
			}			
		});
		
		viewAllEnquiries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
			}			
		});
		
		viewAStudentEnquiry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
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
	
	public void staffHome()
	{
		internalFrame.setSize(new Dimension(1000,1000));
		head = new JLabel("***WELCOME TO THE STAFF DASHBOARD***");		
		internalFrame.add(head);
	}
	
	public void studentHome()
	{
		internalFrame.setMinimumSize(new Dimension(1920,1080));
		head = new JLabel("***WELCOME TO TO STUDENT DASHBOARD***");
		internalFrame.add(head);
	}
	
}
