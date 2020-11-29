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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import covid.client.enumeration.ComplainStatus;
import covid.client.enumeration.Role;
import covid.client.httpclient.service.Covid19Client;
import covid.client.httpclient.service.LiveChatHelper;
import covid.client.httpclient.service.ServerClient;
import covid.client.httpclient.service.SessionManager;
import covid.client.logging.LoggingManager;
import covid.client.models.*;
import covid.client.models.request.LiveChatMessage;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.concurrent.ListenableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	private JButton viewAComplaint;
	private JInternalFrame internalFrame;
	private JButton liveChat;
	private JButton viewAllEnquiries;
	private JButton viewAStudentEnquiry;
	private User user;
	int x;
	long complaintIdSearch = 0;
	boolean found = false;
	TitledBorder borderTitle;
	Border blackline = BorderFactory.createLineBorder(Color.black);
	
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		
		addQuery = new JButton("Add Query");
		viewPastComplaints = new JButton("View Previous Queries");	
		viewAComplaint = new JButton("View Query Details");
		liveChat = new JButton("Live Chat");
		
		activities.add(home);
		activities.add(addQuery);
		activities.add(viewPastComplaints);
		activities.add(viewAComplaint);
		activities.add(liveChat);
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
				
				borderTitle = BorderFactory.createTitledBorder(
	                       blackline, "QUERY");
				borderTitle.setTitleJustification(TitledBorder.CENTER);
				
				query.setBorder(borderTitle);
				query.setPreferredSize(new Dimension(1250,650));
//				queryTitlePanel.add(queryTitle);
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
//				internalFrame.add(queryTitlePanel,BorderLayout.WEST);
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
						
						if(Objects.equals("", query.getText()))
						{
							JOptionPane.showMessageDialog(frame,"Unable to post this query!", "Submission Failed", JOptionPane.WARNING_MESSAGE);
						}else
						{
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
					}			
				});
								
			}			
		});
		
		viewPastComplaints.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				internalFrame.dispose();
				internalFrame = new JInternalFrame("",false,false,false,false);
				internalFrame.setVisible(true);
				
				String[] columns = {"DATE","STATUS","RESPONDER","QUERY","ID"};
				String[][] data_rows = new String[50][50];
				
				Covid19Client serverClient = ServerClient.getClient();
				try {
					List<Complaints> complaintsList = serverClient.getComplaintsByStudentID(user.getId());
					
					x=0;
					
					complaintsList.forEach(c -> {	
						
						data_rows[x][0] = c.getCreatedAt() != null ? c.getCreatedAt().toString():"<DATE>";
						data_rows[x][1] = c.getComplainStatus() != null ? c.getComplainStatus().toString():"<STATUS>";
						
						c.getResponses().forEach(cr -> {
							data_rows[x][2] = cr.getCreatedUser() != null ? cr.getCreatedUser().getLastName():"N/A";
						});
						
						data_rows[x][3] = c.getQuery() != null ? c.getQuery():"<QUERY>";
						data_rows[x][4] = c.getId() != null ? c.getId().toString():"<ID>";
						x++;
					});
				}catch(NullPointerException ex)
				{
					JOptionPane.showMessageDialog(frame,"An error occured! Please try again.", "Complaints List", JOptionPane.WARNING_MESSAGE);
					ex.printStackTrace();
				}
				
				
							
				
				JTable table = new JTable(data_rows, columns);	
				JScrollPane scrollPane = new JScrollPane(table);
				
				table.getColumnModel().getColumn(0).setPreferredWidth(250);
				table.getColumnModel().getColumn(1).setPreferredWidth(120);
				table.getColumnModel().getColumn(2).setPreferredWidth(120);
				table.getColumnModel().getColumn(3).setPreferredWidth(1200);
				
				internalFrame.add(scrollPane);
				frame.add(internalFrame);					
			}			
		});
		
		viewAComplaint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				internalFrame.dispose();
				internalFrame = new JInternalFrame("",false,false,false,false);
				internalFrame.setVisible(true);
				
				JPanel searchPanel = new JPanel();
				JLabel searchLabel = new JLabel("Enter query ID here: ");
				JTextField searchTextField = new JTextField(3);
				JButton searchButton = new JButton("View Details");
				
				searchTextField.setFocusable(true);
				searchPanel.add(searchLabel);
				searchPanel.add(searchTextField);
				searchPanel.add(searchButton);
				
				internalFrame.add(searchPanel);				
				frame.add(internalFrame);			
				
				searchButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						Covid19Client serverClient = ServerClient.getClient();							
						
						try
						{
							List<Complaints> complaintsList = serverClient.getComplaintsByStudentID(user.getId());
							
							complaintIdSearch = Long.parseLong(searchTextField.getText());
							
							found=false;
							
							complaintsList.forEach(c -> {	
								
								if(c.getId() == complaintIdSearch)
								{
									found = true;
									
									internalFrame.dispose();
									internalFrame = new JInternalFrame("",false,false,false,false);
									internalFrame.setVisible(true);
									
									JPanel WestPanel = new JPanel();
									JPanel CenterPanel = new JPanel();
									JPanel NorthPanel = new JPanel();
									JPanel SouthPanel = new JPanel();
									JPanel responsePanel = new JPanel();
									JLabel responseLabel = new JLabel("RESPONSE: ");
									JLabel blank = new JLabel();
									JLabel queryLabel = new JLabel("QUERY ["+c.getId()+"] :");
									JLabel queryService = new JLabel("SERVICE: ["+c.getServices().getName()+"]");
									JLabel queryDate = new JLabel("SUBMISSION DATE: ["+c.getCreatedAt()+"]");
									JLabel queryStatus = new JLabel("STATUS: ["+c.getComplainStatus().toString()+"]");
									JTextArea query = new JTextArea(c.getQuery());	
									JTextArea response = new JTextArea();
									
									
									borderTitle = BorderFactory.createTitledBorder(
						                       blackline, "QUERY["+c.getId()+"]");
									borderTitle.setTitleJustification(TitledBorder.CENTER);
									query.setBorder(borderTitle);
									borderTitle = BorderFactory.createTitledBorder(
						                       blackline, "RESPONSES");
									borderTitle.setTitleJustification(TitledBorder.CENTER);
									response.setBorder(borderTitle);
									
									WestPanel.setLayout(new GridLayout(3,1,0,0));
									SouthPanel.setLayout(new GridLayout(1,2,0,0));
									queryService.setAlignmentX(CENTER_ALIGNMENT);
									queryDate.setAlignmentX(CENTER_ALIGNMENT);
									
									query.setPreferredSize(new Dimension(1250,150));
									query.setBackground(new Color(211,211,211));
									response.setBackground(new Color(211,211,211));
									response.setPreferredSize(new Dimension(1250,520));
									
									NorthPanel.add(queryStatus);	
									
//									WestPanel.add(queryLabel);
//									WestPanel.add(responseLabel);
									
									CenterPanel.add(query);
									CenterPanel.add(blank);
									CenterPanel.add(blank);
									
									c.getResponses().forEach(r -> { 
										response.append(">"+r.getResponse()+"\n	SENT BY: "+r.getCreatedUser().getLastName()+"	DATE: "+r.getCreatedAt().toString()+"\n\n");
										//response.setText("<RESPONSE>"+"\n	SENT BY: "+"<RESPONDER NAME>"+" DATE: "+"<RESPONSE DATE>"+"\n\n");
									});
									CenterPanel.add(response);
									
									SouthPanel.add(queryService);
									SouthPanel.add(queryDate);
									
									query.setLineWrap(true);
									query.setEditable(false);
									
									response.setLineWrap(true);
									response.setEditable(false);									
									
									internalFrame.add(NorthPanel,BorderLayout.NORTH);
//									internalFrame.add(WestPanel,BorderLayout.WEST);
									internalFrame.add(CenterPanel,BorderLayout.CENTER);
									internalFrame.add(SouthPanel,BorderLayout.SOUTH);
									
									frame.add(internalFrame);
								}
							});	
							
							if(!found)
							{
								JOptionPane.showMessageDialog(frame,"Your query Id was not found.", "Search Failed", JOptionPane.WARNING_MESSAGE);
								searchTextField.setText("");
								searchTextField.setFocusable(true);
							}
						}catch(Exception ex)
						{
							JOptionPane.showMessageDialog(frame,"Your query Id was not found.", "Search Failed", JOptionPane.WARNING_MESSAGE);
							searchTextField.setText("");
							searchTextField.setFocusable(true);
						}														
					}					
				});
			}							
		});
		
		liveChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				internalFrame.dispose();
				internalFrame = new JInternalFrame("",false,false,false,false);	
				internalFrame.setVisible(true);
				
				JPanel headerPanel = new JPanel();
				JPanel availableRep = new JPanel();
				JLabel headerLabel = new JLabel("Available Student Representatives:");
				
				headerLabel.setFont(new Font("Algerian", Font.BOLD, 20));
				headerPanel.add(headerLabel);
				internalFrame.add(headerPanel,BorderLayout.NORTH);
				
				Covid19Client serverClient = ServerClient.getClient();
				List<User> userList = serverClient.getAllUsersByRole(Role.STUDENT_REPRESENTATIVE);
				
				userList.forEach(u -> {
					JLabel studentRep = new JLabel(u.getFirstName()+" "+u.getLastName());
					JButton chatButton = new JButton("CHAT");
					
					availableRep.add(studentRep);
					availableRep.add(chatButton);
					
					internalFrame.add(availableRep,BorderLayout.CENTER);					
					
					chatButton.addActionListener(new ActionListener() {			//Send message to server
						public void actionPerformed(ActionEvent e) {
							
							JFrame chatWindow = new JFrame(u.getFirstName()+" "+u.getLastName());
							JTextArea viewer = new JTextArea();
							JPanel editorPanel = new JPanel();
							JTextField editor = new JTextField(50);
							JButton sendButton = new JButton("Send");							
							
							editorPanel.add(editor);
							editorPanel.add(sendButton);
							chatWindow.setAlwaysOnTop(true);
							viewer.setEditable(false);
							viewer.setBackground(new Color(211,211,211));							
							chatWindow.setVisible(true);
							chatWindow.setMinimumSize(new Dimension(800,500));
							JScrollPane scrollPane = new JScrollPane(viewer);
							scrollPane.setVisible(true);
							scrollPane.setAutoscrolls(true);
							chatWindow.add(scrollPane,BorderLayout.CENTER);
							chatWindow.add(editorPanel,BorderLayout.SOUTH);
							chatWindow.getRootPane().setDefaultButton(sendButton);

							// fetch chat user chat based on the clicked user
							Chat chat =  ServerClient.getClient().getAllMessagesByToAndFrom(user.getId(), u.getId()); //

							if(chat != null) {
								if (!CollectionUtils.isEmpty(chat.getChatMessagesList())) {
									chat.getChatMessagesList().forEach(message -> {
										//System.out.println("Message :" + message.getMessage() + " Send by: " + String.valueOf(message.getSendBy()));
										String userName = message.getSendBy().getId().equals(user.getId()) ? "ME" : message.getSendBy().getFullname();
										viewer.append("(" + userName + ")\n" + message.getMessage() + "\n\n");
									});
								} else {
									//chat is empty
									System.out.println("Message list is empty");
								}
							}else{
								System.out.println("Chat was null");
							}

							LiveChatHelper helloClient = new LiveChatHelper();
							StompSession stompSession = null;

							try {
								 // initialize the live chat socket so the user can send and receive messages in real time
								ListenableFuture<StompSession> connection = helloClient.connect();
								stompSession = connection.get();
								// subscribe the user to the chat channel
								stompSession.subscribe("/user/target/1", new StompFrameHandler() {

									public java.lang.reflect.Type getPayloadType(StompHeaders stompHeaders) {
										return byte[].class;
									}

									public void handleFrame(StompHeaders stompHeaders, Object o) { //Accepts incoming message from student Rep
										LoggingManager.getLogger(this).info("Received message " + new String((byte[]) o));

											 String message = new String((byte[]) o);
											 ObjectMapper mapper = new ObjectMapper();
											 try
											 {
												 LiveChatMessage liveChatMessage = mapper.readValue(message,LiveChatMessage.class);
												 if(liveChatMessage.getFrom().equals(u.getId())) {

													 viewer.append("(" + liveChatMessage.getName() + ")\n" + liveChatMessage.getMessage() + "\n\n");
													 System.out.println(liveChatMessage.getMessage());
												 }else{
												 	// message send from differnt user
												 }
											 }catch(Throwable e)
											 {
												 
											 }																				
									}
								});
							} catch (ExecutionException e1) {
								e1.printStackTrace();
							} catch (InterruptedException e1) {
								e1.printStackTrace();
							}

							StompSession finalStompSession = stompSession;
							
							sendButton.addActionListener(new ActionListener() {						//Send message to student Rep
								public void actionPerformed(ActionEvent e) {
									final String message = editor.getText();
									if(finalStompSession != null)
									{										
										try 
										{
											helloClient.sendMessage(finalStompSession, new LiveChatMessage(u.getFullname(), message, user.getId(), u.getId()));
											viewer.append("(Me)\n"+editor.getText()+"\n\n");
											
											System.out.println("Message sent to user with ID: " + u.getId() + " Message: " + message);
										} catch (InterruptedException e1) {
											e1.printStackTrace();
											JOptionPane.showMessageDialog(null,"An error occurred!", "Chat", JOptionPane.WARNING_MESSAGE);
										}
									}else{
										System.out.println("StompSession is null");
										JOptionPane.showMessageDialog(null,"An error occurred!", "Chat", JOptionPane.WARNING_MESSAGE);
									}
									editor.setText("");
								}
							});
							
						}
					});
				});
				frame.add(internalFrame);
			}			
		});

		
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
				user = null;
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
		
		viewAllEnquiries = new JButton("View all Student's Queries");
		viewAStudentEnquiry = new JButton("View a Student's Query");
		liveChat = new JButton("Live Chat");
		logout = new JButton("Log Out");			
		
		activities.add(home);
		activities.add(viewAllEnquiries);
		activities.add(viewAStudentEnquiry);
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
				
				String[] columns = {"STUDENT ID","SERVICE","QUERY","STATUS"};
				String[][] data_rows = new String[50][50];	
				
				Covid19Client serverClient = ServerClient.getClient();
				List<User> userList = serverClient.getAllUsersByRole(Role.STUDENT);								
				
				x=0;
					
					JTable table = new JTable(data_rows, columns);	
					JScrollPane scrollPane = new JScrollPane(table);
					
					table.getColumnModel().getColumn(0).setPreferredWidth(7);
					table.getColumnModel().getColumn(1).setPreferredWidth(4);
					table.getColumnModel().getColumn(2).setPreferredWidth(1000);
					table.getColumnModel().getColumn(3).setPreferredWidth(2);
					
					internalFrame.add(scrollPane);
					frame.add(internalFrame);						
										
					userList.forEach(u -> {	
						
						List<Complaints> complaintsList = serverClient.getComplaintsByStudentID(u.getId());					
							
							complaintsList.forEach(c -> {
								data_rows[x][0] = u.getUserName() != null ? u.getUserName():"<USERNAME>";
								data_rows[x][1] = c.getServices() != null ? c.getServices().getName():"<SERVICE>";
								data_rows[x][2] = c.getQuery() != null ? c.getQuery():"<QUERY>";
								data_rows[x][3] = c.getComplainStatus() != null ? c.getComplainStatus().toString():"<STATUS>";
								x++;
							});					
						});					
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
				
				frame.add(internalFrame);
				
				requestBtn.addActionListener(new ActionListener()								//Searching students for complaint list
				{
					public void actionPerformed(ActionEvent e) 
					{							
						String requestId = requestTextField.getText();
						
						Covid19Client serverClient = ServerClient.getClient();
						List<User> userList = serverClient.getAllUsersByRole(Role.STUDENT);
						
						found = false;
						
						userList.forEach(u -> {
							if(Objects.equals(u.getUserName(),requestId))
							{
								found = true;
								
								internalFrame.dispose();
								internalFrame = new JInternalFrame("",false,false,false,false);
								internalFrame.setVisible(true);
								
								JPanel searchPanel = new JPanel();
								JLabel searchLabel = new JLabel("Enter a query ID: ");
								JTextField searchTextField = new JTextField(3);
								JButton searchButton = new JButton("Respond");							
								
								String[] columns = {"DATE","STATUS","RESPONDER","QUERY","ID"};
								String[][] data_rows = new String[50][50];
								
								try {
									List<Complaints> complaintsList = serverClient.getComplaintsByStudentID(u.getId());
									
									x=0;
									
									complaintsList.forEach(c -> {	
										
										data_rows[x][0] = c.getCreatedAt() != null ? c.getCreatedAt().toString():"<DATE>";
										data_rows[x][1] = c.getComplainStatus() != null ? c.getComplainStatus().toString():"<STATUS>";
										
										c.getResponses().forEach(cr -> {
											data_rows[x][2] = cr.getCreatedUser() != null ? cr.getCreatedUser().getLastName():"N/A";
										});
										
										data_rows[x][3] = c.getQuery() != null ? c.getQuery():"<QUERY>";
										data_rows[x][4] = c.getId() != null ? c.getId().toString():"<ID>";
										x++;
									});
								}catch(NullPointerException ex)
								{
									JOptionPane.showMessageDialog(frame,"An error occured! Please try again.", "Complaints List", JOptionPane.WARNING_MESSAGE);
									ex.printStackTrace();
								}								
								
								JTable table = new JTable(data_rows, columns);	
								JScrollPane scrollPane = new JScrollPane(table);
								
								table.getColumnModel().getColumn(0).setPreferredWidth(250);
								table.getColumnModel().getColumn(1).setPreferredWidth(120);
								table.getColumnModel().getColumn(2).setPreferredWidth(120);
								table.getColumnModel().getColumn(3).setPreferredWidth(1200);								

								searchPanel.add(searchLabel);
								searchPanel.add(searchTextField);
								searchPanel.add(searchButton);	
								
								internalFrame.add(searchPanel,BorderLayout.NORTH);
								internalFrame.add(scrollPane,BorderLayout.CENTER);
								
								frame.add(internalFrame);
								
								searchButton.addActionListener(new ActionListener()				//Searching students complaint details by query ID
								{
									public void actionPerformed(ActionEvent e) 
									{										
										try
										{
											List<Complaints> complaintsList = serverClient.getComplaintsByStudentID(u.getId());
											
											complaintIdSearch = Long.parseLong(searchTextField.getText());
											
											complaintsList.forEach(c -> {	
												
												if(c.getId() == complaintIdSearch)
												{
													found = true;
													
													internalFrame.dispose();
													internalFrame = new JInternalFrame("",false,false,false,false);
													internalFrame.setVisible(true);
													
													JPanel WestPanel = new JPanel();
													JPanel CenterPanel = new JPanel();
													JPanel NorthPanel = new JPanel();
													JPanel SouthPanel = new JPanel();
													JPanel responsePanel = new JPanel();
													JPanel statusPanel = new JPanel();
													JLabel responseLabel = new JLabel("RESPONSE: ");
													JLabel profile = new JLabel("STUDENTS'S PROFILE: ");
													JLabel studentName = new JLabel(u.getFirstName()+" "+u.getLastName());
													JLabel studentID = new JLabel(u.getUserName());
													JLabel studentEmail = new JLabel(u.getEmail());
													JLabel studentContact = new JLabel("Cell# :"+u.getContact().toString());
													JButton sendResponse = new JButton("Send");
													JPanel blank = new JPanel();
													JRadioButton resolvedRButton = new JRadioButton("RESOLVED");
													JRadioButton processingRButton = new JRadioButton("PROCESSING");
													JRadioButton unresolvedRButton = new JRadioButton("UNRESOLVED");
													JRadioButton canceledRButton = new JRadioButton("CANCELED");
													ButtonGroup bg = new ButtonGroup();
													JLabel queryLabel = new JLabel("QUERY ["+c.getId()+"] :");
													JLabel queryDate = new JLabel("SUBMISSION DATE: ["+c.getCreatedAt()+"]");
													JLabel queryStatus = new JLabel("STATUS: ["+c.getComplainStatus().toString()+"]");
													JLabel queryService = new JLabel("SERVICE: ["+c.getServices().getName()+"]");
													JTextArea query = new JTextArea(c.getQuery());	
													JTextArea response = new JTextArea();
													
													
													bg.add(resolvedRButton);
													bg.add(processingRButton);
													bg.add(unresolvedRButton);
													bg.add(canceledRButton);
													
													statusPanel.add(processingRButton);
													statusPanel.add(resolvedRButton);
													statusPanel.add(unresolvedRButton);
													statusPanel.add(canceledRButton);
													
													NorthPanel.setLayout(new GridLayout(1,5,1,0));
													WestPanel.setLayout(new GridLayout(4,1,0,0));
													SouthPanel.setLayout(new GridLayout(1,3,1,0));
													
													query.setPreferredSize(new Dimension(1250,150));
													query.setBackground(new Color(211,211,211));
													response.setPreferredSize(new Dimension(1250,500));
													
													borderTitle = BorderFactory.createTitledBorder(
										                       blackline, "QUERY["+c.getId()+"]");
													borderTitle.setTitleJustification(TitledBorder.CENTER);
													query.setBorder(borderTitle);
													borderTitle = BorderFactory.createTitledBorder(
										                       blackline, "RESPONSE");
													borderTitle.setTitleJustification(TitledBorder.CENTER);
													response.setBorder(borderTitle);
													
													NorthPanel.add(profile);
													NorthPanel.add(studentName);
													NorthPanel.add(studentID);
													NorthPanel.add(studentEmail);
													NorthPanel.add(studentContact);
													
													if(Objects.equals(c.getComplainStatus(), ComplainStatus.RESOLVED))
													{
														resolvedRButton.setSelected(true);
													}else
													if(Objects.equals(c.getComplainStatus(), ComplainStatus.CANCELED))	
													{
														canceledRButton.setSelected(true);
													}else
													if(Objects.equals(c.getComplainStatus(),ComplainStatus.PROCESSING))
													{
														processingRButton.setSelected(true);
													}else
													if(Objects.equals(c.getComplainStatus(), ComplainStatus.UNRESOLVED))
													{
														unresolvedRButton.setSelected(true);
													}
															
													CenterPanel.add(query);
													CenterPanel.add(response);
													CenterPanel.add(statusPanel);
													CenterPanel.add(sendResponse);
													
													SouthPanel.add(queryStatus);
													SouthPanel.add(queryService);
													SouthPanel.add(queryDate);
													
													query.setLineWrap(true);
													query.setEditable(false);
													
													response.setLineWrap(true);
													response.setEditable(true);									
													
													internalFrame.add(NorthPanel,BorderLayout.NORTH);
													internalFrame.add(WestPanel,BorderLayout.WEST);
													internalFrame.add(CenterPanel,BorderLayout.CENTER);
													internalFrame.add(SouthPanel,BorderLayout.SOUTH);
													
													frame.add(internalFrame);
													
													sendResponse.addActionListener(new ActionListener()				//Staff sends their response here
													{
														public void actionPerformed(ActionEvent e) 
														{
															if(Objects.equals("", response.getText()))
															{
																JOptionPane.showMessageDialog(frame,"Unable to post this response!", "Response Failed", JOptionPane.WARNING_MESSAGE);
															}else
															{
																try
																{
																	if(resolvedRButton.isSelected())
																	{
																		ApiResponse<Complaints> statusUpdate =  serverClient.updateComplaintStatus(c.getId(), ComplainStatus.RESOLVED);
																	}else
																	if(processingRButton.isSelected())	
																	{
																		ApiResponse<Complaints> statusUpdate =  serverClient.updateComplaintStatus(c.getId(), ComplainStatus.PROCESSING);
																	}else
																	if(unresolvedRButton.isSelected())
																	{
																		ApiResponse<Complaints> statusUpdate =  serverClient.updateComplaintStatus(c.getId(), ComplainStatus.UNRESOLVED);
																	}else
																	if(canceledRButton.isSelected())
																	{
																		ApiResponse<Complaints> statusUpdate =  serverClient.updateComplaintStatus(c.getId(), ComplainStatus.CANCELED);
																	}
																	
																	ApiResponse<ComplaintResponses> complaintResponseApiResponse = 
																			serverClient.reply(new ComplaintResponses(response.getText(),c,staff));
																	JOptionPane.showMessageDialog(frame,complaintResponseApiResponse.getMessage(), "Response sent!", JOptionPane.INFORMATION_MESSAGE);
																	
																}catch(NullPointerException ex)
																{
																	JOptionPane.showMessageDialog(frame,"An error occured! Please try again.", "Response Failed", JOptionPane.WARNING_MESSAGE);																
																}
															}															
														}
													});	
												}
											});	
											
											if(!found)
											{
												JOptionPane.showMessageDialog(frame,"Your query Id was not found.", "Search Failed", JOptionPane.WARNING_MESSAGE);
												searchTextField.setText("");
												searchTextField.setFocusable(true);
											}
										}catch(Exception ex)
										{
											JOptionPane.showMessageDialog(frame,"An error occured! Please try again.", "Search Failed", JOptionPane.WARNING_MESSAGE);
											searchTextField.setText("");
											searchTextField.setFocusable(true);
										}
									}
								});
							}
						});							
						if(!found)
						{
							JOptionPane.showMessageDialog(frame,"Student ID was not found!", "Student Search", JOptionPane.WARNING_MESSAGE);
							requestTextField.setText("");
						}
					}			
				});
			}			
		});
	
		liveChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				internalFrame.dispose();
				internalFrame = new JInternalFrame("",false,false,false,false);	
				internalFrame.setVisible(true);
				internalFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
				
				JPanel availablePanel = new JPanel();
				JPanel fromPanel = new JPanel();
				JPanel toPanel = new JPanel();
				JPanel messagePanel = new JPanel();
				JPanel north = new JPanel();
				JPanel updatePanel = new JPanel();
				JLabel messageLabel = new JLabel("MESSAGES");
				JLabel availableLabel = new JLabel("You are available to chat on:");
				JLabel toColon = new JLabel(" : ");
				JLabel fromColon = new JLabel(" : ");
				JLabel fromLabel = new JLabel("Available from: ");
				JLabel toLabel = new JLabel("Available until: ");
				JTextField fromHourField = new JTextField(2);
				JTextField fromMinField = new JTextField(2);
				JRadioButton amFrom = new JRadioButton("AM",true);
				JRadioButton pmFrom = new JRadioButton("PM");
				ButtonGroup fromBG = new ButtonGroup();
				JTextField toHourField = new JTextField(2);
				JTextField toMinField = new JTextField(2);
				JRadioButton amTo = new JRadioButton("AM");
				JRadioButton pmTo = new JRadioButton("PM",true);
				ButtonGroup toBG = new ButtonGroup();
				JRadioButton mon = new JRadioButton("Monday");
				JRadioButton tue = new JRadioButton("Tuesday");
				JRadioButton wed = new JRadioButton("Wednesday");
				JRadioButton thu = new JRadioButton("Thursday");
				JRadioButton fri = new JRadioButton("Friday");
				JButton updateButton = new JButton("UPDATE");
				JButton chatButton = new JButton("CHAT");
//				
				fromBG.add(amFrom);
				fromBG.add(pmFrom);
				
				toBG.add(amTo);
				toBG.add(pmTo);
				
				availablePanel.add(availableLabel);
				availablePanel.add(mon);
				availablePanel.add(tue);
				availablePanel.add(wed);
				availablePanel.add(thu);
				availablePanel.add(fri);
				
				fromPanel.add(fromLabel);
				fromPanel.add(fromHourField);
				fromPanel.add(fromColon);
				fromPanel.add(fromMinField);
				fromPanel.add(amFrom);
				fromPanel.add(pmFrom);
				
				toPanel.add(toLabel);
				toPanel.add(toHourField);
				toPanel.add(toColon);
				toPanel.add(toMinField);
				toPanel.add(amTo);
				toPanel.add(pmTo);
				
				updatePanel.add(updateButton);
															//initialize Chat GUI with Rep's info before displaying
				wed.setSelected(true);
				fromHourField.setText("9");
				fromMinField.setText("00");
				toHourField.setText("5");
				toMinField.setText("00");
															//displays initialized GUI
				north.setLayout(new GridLayout(4,1,0,0));
				north.add(availablePanel);
				north.add(fromPanel);
				north.add(toPanel);
				north.add(updatePanel);
				
				messageLabel.setFont(new Font("Algerian", Font.BOLD, 20));
				messagePanel.add(messageLabel); 
				
				updateButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int startHour,startMin,endHour,endMin;
						
						try
						{
							startHour = Integer.parseInt(fromHourField.getText());
							startMin = Integer.parseInt(fromMinField.getText());
							endHour = Integer.parseInt(toHourField.getText());
							endMin = Integer.parseInt(toMinField.getText());
						}catch(Exception w)
						{
							JOptionPane.showMessageDialog(frame, "Check to ensure correct time format!"
									, "Update Availability", JOptionPane.WARNING_MESSAGE);
							return;
						}						
						
						if(startHour < 1 || startMin < 0 || startHour > 12 || startMin > 59 || Objects.equals(fromHourField.getText(), "") || Objects.equals(fromMinField.getText(), ""))
						{
							JOptionPane.showMessageDialog(frame, "Please ensure your start times are correct!"
									, "Update Availability", JOptionPane.WARNING_MESSAGE);
							fromHourField.setText("");
							fromMinField.setText("");							
							return;
						}
						
						if(endHour < 1 || endMin < 0 || endHour > 12 || endMin > 59 || Objects.equals(toHourField.getText(), "") || Objects.equals(toMinField.getText(), ""))
						{
							JOptionPane.showMessageDialog(frame, "Please ensure your end times are correct!"
									, "Update Availability", JOptionPane.WARNING_MESSAGE);
							toHourField.setText("");
							toMinField.setText("");
							return;
						}						
						
						if(mon.isSelected())
						{
							
							//Add Monday to Day list
						}else
						if(tue.isSelected())
						{
							//Add Tuesday to Day list
							
						}else
						if(wed.isSelected())
						{
							
							JOptionPane.showMessageDialog(frame, "You are now available to chat on Wednesday. "+startHour+":"+startMin+" to "+endHour+":"+endMin
									, "Chat Availability Changed!", JOptionPane.INFORMATION_MESSAGE);
						}else
						if(thu.isSelected())
						{
							
							//Add Thursday to Day list
						}else
						if(fri.isSelected())
						{
							
							//Add Friday to Day list
						}
					}			
				});	
				
				chatButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
					}			
				});
				
				JSplitPane layer = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
				layer.setTopComponent(north);
				layer.setBottomComponent(messagePanel);
				layer.setDividerLocation(0.8);
				internalFrame.add(layer);
//				internalFrame.add(north,BorderLayout.NORTH);
//				internalFrame.add(messagePanel,BorderLayout.AFTER_LAST_LINE);
				frame.add(internalFrame);
			}	
			
		});
		
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				user = null;
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
