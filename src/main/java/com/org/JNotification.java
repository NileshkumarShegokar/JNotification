package com.org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class JNotification extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JProgressBar progress;
	private int value = 0;
	private Timer timer ;
	private Frame parent;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JNotification.showNotification(null,"Success","Hi its a notification",JNotification.SUCCESS_MESSAGE,10,JNotification.BOTTOM_LEFT,true);
	}

	/**
	 * Create the dialog.
	 */
	private JNotification(Frame parent,String header,String message,Integer messageType,final Integer timeout,Integer position,Boolean closable) {
		super(parent);
		this.parent=parent;
		getContentPane().setBackground(Color.GRAY);
		setBackground(Color.GRAY);
		
		setUndecorated(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
		Map<String,Object> map=getMessageDetails(messageType);
		JLabel label = new JLabel("  ",(ImageIcon)map.get("image"),SwingConstants.CENTER);
		label.setHorizontalTextPosition(JLabel.LEFT);
		label.setVerticalTextPosition(JLabel.CENTER);
		getContentPane().add(label, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		if(closable) {
		JButton close = new JButton("",new ImageIcon(JNotification.class.getClass().getResource("/images/24/close_1.png")));
		close.setBorderPainted(false); 
		close.setContentAreaFilled(false); 
		close.setFocusPainted(false); 
		close.setOpaque(false);
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JNotification.this.setVisible(false);
				JNotification.this.dispose();
			}
		});
		panel_1.add(close);
		}
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.GRAY, 5));
		panel_2.setBackground(Color.GRAY);
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		if(header!=null) {
		JLabel heading = new JLabel(header);
		heading.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		heading.setForeground(Color.WHITE);		
		panel_2.add(heading, BorderLayout.NORTH);
		}else {
			JLabel heading = new JLabel((String)map.get("title"));
			heading.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			heading.setForeground(Color.WHITE);		
			panel_2.add(heading, BorderLayout.NORTH);
		}
		
		JLabel msg = new JLabel(message);
		msg.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
		msg.setForeground(Color.WHITE);
		panel_2.add(msg);
		if(timeout!=null) {
		progress = new JProgressBar(0,timeout);
		progress.setForeground((Color)map.get("color"));
		progress.setBorderPainted(false);
		progress.setBorder(new LineBorder(Color.GRAY, 5));
		progress.setOpaque(false);
		panel.add(progress, BorderLayout.SOUTH);		
		}
		
	    timer = new Timer(1000, new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(progress.getValue() >= timeout) {
					timer.stop();
					JNotification.this.setVisible(false);
					JNotification.this.dispose();
				}
				progress.setValue(value++);
				
				
			}
		});

	    timer.start();
	 /*   try {
	      Thread.sleep(10000);
	    } catch (InterruptedException e) {
	    }
	    timer.stop();*/
	  this.validate();
	    setBounds(getBounds(position,parent));
		setVisible(true);
		Toolkit.getDefaultToolkit().beep();
	}
	
	
	public static Integer SUCCESS_MESSAGE=1;
	public static Integer INFO_MESSAGE=2;
	public static Integer WARNING_MESSAGE=3;
	public static Integer ERROR_MESSAGE=4;
	public static Integer IDEA_MESSAGE=5;
	
	public static Integer TOP_LEFT=1;
	public static Integer TOP_RIGHT=2;
	public static Integer BOTTOM_LEFT=3;
	public static Integer BOTTOM_RIGHT=4;
	
	public static Integer PARENT_TOP_LEFT=5;
	public static Integer PARENT_TOP_RIGHT=6;
	public static Integer PARENT_BOTTOM_LEFT=7;
	public static Integer PARENT_BOTTOM_RIGHT=8;
	
	
	
	public static Rectangle getBounds(Integer position,Frame parent) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle bounds;
		switch(position) {
		case 1:{
			bounds=new Rectangle(0, 0, 450, 125);
			break;
		}
		case 2:{
			bounds=new Rectangle(Integer.parseInt(""+(screen.width-450)), 0, 450, 125);
			break;
		}
		case 3:{
			bounds=new Rectangle(0, Integer.parseInt(""+(screen.height-175)), 450, 125);
			break;
		}
		case 4:{
			bounds=new Rectangle(Integer.parseInt(""+(screen.width-450)), Integer.parseInt(""+(screen.height-175)), 450, 125);
			break;
		}
		case 5:{
			
			bounds=new Rectangle(parent.getBounds().x, parent.getBounds().y, 450, 125);
			break;
		}
		case 6:{
			bounds=new Rectangle(Integer.parseInt(""+(parent.getBounds().width-450)), parent.getBounds().y, 450, 125);
			break;
		}
		case 7:{
			bounds=new Rectangle(parent.getBounds().x, Integer.parseInt(""+(parent.getBounds().height-175)), 450, 125);
			break;
		}
		case 8:{
			bounds=new Rectangle(parent.getBounds().width-450, parent.getBounds().height-175, 450, 125);
			break;
		}
		default:{
			bounds=new Rectangle(100, 100, 450, 124);
		}
		}
		return bounds;
	}
	
	public static void showNotification(Frame parent,String header,String message,Integer messageType, Integer timeout, Integer position, Boolean closable) {
		new JNotification(parent,header,message,messageType,timeout,position,closable);
	}
	
	public static void showNotification(String header,String message,Integer messageType, Integer timeout, Integer position, Boolean closable) {
		new JNotification(null,header,message,messageType,timeout,position,closable);
	}
	
	public static void showNotification(String message,Integer messageType, Integer timeout, Integer position, Boolean closable) {
		new JNotification(null,null,message,messageType,timeout,position,closable);
	}
	
	public static void showNotification(String message,Integer messageType, Integer position) {
		new JNotification(null,null,message,messageType,null,position,true);
	}
	
	public static void showNotification(String message,Integer messageType, Boolean closable) {
		new JNotification(null,null,message,messageType,null,null,true);
	}
	
	public static Map<String,Object> getMessageDetails(Integer messageType) {
		
		HashMap<String,Object> map=new HashMap<String,Object>();
		switch(messageType) {
		case 1:{
			map.put("title","SUCCESS");
			map.put("color",new Color(37, 174, 136));
			map.put("image",new ImageIcon(JNotification.class.getClass().getResource("/images/64/success.png")));
			break;
		}
		case 2:{
			map.put("title","INFORMATION");
			map.put("color",new Color(72, 160, 220));
			map.put("image",new ImageIcon(JNotification.class.getClass().getResource("/images/64/info.png")));
			break;
		}
		case 3:{
			map.put("title","WARNING");
			map.put("color",new Color(239, 206, 74));
			map.put("image",new ImageIcon(JNotification.class.getClass().getResource("/images/64/warning.png")));
			break;
		}
		case 4:{
			map.put("title","ERROR");
			map.put("color",new Color(215, 90, 74));
			map.put("image",new ImageIcon(JNotification.class.getClass().getResource("/images/64/error.png")));
			break;
		}
		case 5:{
			map.put("title","IDEA");
			map.put("color",new Color(239, 206, 74));
			map.put("image",new ImageIcon(JNotification.class.getClass().getResource("/images/64/idea.png")));
			break;
		}
		default:{
			map.put("title","");
			map.put("color",new Color(37, 174, 136));
			map.put("image",new ImageIcon(JNotification.class.getClass().getResource("/images/64/user.png")));
		}
		}
		return map;
	}

}
