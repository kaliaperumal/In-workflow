package com.gui;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Canvas;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JCheckBoxMenuItem;

import com.Transformation.XSLTConversion;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;


public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
    public static String XSLT_File="";
    public static String INXML_File="";
    public static String OutXML_File="";
    private JTextField textField_3;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		setTitle("XSLT Transformation");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/com/logo/icon.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(-45, -279, 599, 582);
		setBounds(145, 100, 599, 572);
		//setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(1, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 161, 565, 63);
		panel_1.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Input XML", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null));
		contentPane.add(panel_1);
		
		JLabel lblInputXml = new JLabel("Input XML File Path");
		lblInputXml.setBounds(11, 25, 116, 22);
		lblInputXml.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		textField_1 = new JTextField();
		textField_1.setBounds(164, 21, 296, 28);
		textField_1.setColumns(10);
		panel_1.setLayout(null);
		panel_1.add(lblInputXml);
		panel_1.add(textField_1);
		
		final JLabel select_label = new JLabel("");
		select_label.setBounds(14, 340, 239, 22);
		select_label.setVerticalAlignment(SwingConstants.TOP);
		select_label.setForeground(Color.RED);
		select_label.setBackground(Color.RED);
		contentPane.add(select_label);
		
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 249, 565, 63);
		panel_2.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Output XML", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null));
		contentPane.add(panel_2);
		
		JLabel lblOutput = new JLabel("Output XML Directory path ");
		lblOutput.setBounds(13, 26, 141, 22);
		lblOutput.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		textField_2 = new JTextField();
		textField_2.setBounds(168, 22, 289, 28);
		textField_2.setColumns(10);
		panel_2.setLayout(null);
		panel_2.add(lblOutput);
		panel_2.add(textField_2);
		
		JButton XML_btnBrowse = new JButton("Browse");
		XML_btnBrowse.setForeground(Color.WHITE);
		XML_btnBrowse.setBackground(Color.GRAY);
		XML_btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				textField_3.setText("");
				select_label.setText("");
				  JFileChooser chooser = new JFileChooser();
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "xml", "xml", "XML");
				    chooser.setFileFilter(filter);
				    int returnVal = chooser.showOpenDialog(null);
				    if(returnVal == JFileChooser.APPROVE_OPTION) 
				    {
				       System.out.println("You chose to open this file: " +chooser.getSelectedFile().getName());
				      
				         try {
							INXML_File=chooser.getSelectedFile().getCanonicalPath();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				          if(INXML_File.toLowerCase().endsWith(".xml"))
				          {
				        	  textField_1.setText(INXML_File);
				        	  OutXML_File=INXML_File.replace(".xml",".html");
				        	  textField_2.setText(OutXML_File);
				        	  System.out.println("You Selected INXML_File File is : "+INXML_File);
				          }
				          else
				          {
				        	  select_label.setText("Select XML File only....");
				          }
				    }
			}
		});
		XML_btnBrowse.setBounds(470, 20, 83, 29);
		panel_1.add(XML_btnBrowse);
		
	
		

		
	
		
		
		/*
		 JButton OutXML_btnBrowse = new JButton("Browse");
		OutXML_btnBrowse.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				textField_3.setText("");				
				select_label.setText("");
				  JFileChooser chooser = new JFileChooser();
				    FileNameExtensionFilter filter = new FileNameExtensionFilter(
				        "xml", "xml");
				    chooser.setFileFilter(filter);
				    int returnVal = chooser.showSaveDialog(null);
				    if(returnVal == JFileChooser.APPROVE_OPTION) 
				    {
				       System.out.println("You chose to open this file: " +chooser.getSelectedFile().getName());
				       try {
						OutXML_File=chooser.getSelectedFile().getCanonicalPath();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}				
				       textField_2.setText(OutXML_File);
				       textField_3.setText("");
				       System.out.println("You Selected OutXML_File File is : "+OutXML_File);
				          
				    }
			}
		});
		  */
		
		
		JPanel panel = new JPanel();
		panel.setBounds(9, 71, 565, 63);
		panel.setForeground(Color.GRAY);
		panel.setBorder(new CompoundBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "XSLT Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), null));
		contentPane.add(panel);
		
		JLabel lblXslt = new JLabel("XSLT File Path");
		lblXslt.setBounds(15, 23, 90, 22);
		lblXslt.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		textField = new JTextField();
		textField.setBounds(165, 18, 296, 28);
		textField.setColumns(10);
		panel.setLayout(null);
		panel.add(lblXslt);
		panel.add(textField);
		
		
		JButton XSLT_btnBrowse = new JButton("Browse");
		XSLT_btnBrowse.setForeground(Color.WHITE);
		XSLT_btnBrowse.setBackground(Color.GRAY);
		XSLT_btnBrowse.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				textField_3.setText("");
				select_label.setText("");
			    JFileChooser chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "xls & xslt", "xsl", "xslt");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) 
			    {
			       System.out.println("You chose to open this file: " +chooser.getSelectedFile().getName());
			    try {
					XSLT_File=chooser.getSelectedFile().getCanonicalPath();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			          if(XSLT_File.toLowerCase().endsWith(".xsl")||XSLT_File.toLowerCase().endsWith("xslt"))
			          {
			        	 textField.setText(XSLT_File);
			        	 System.out.println("You Selected XSLT_File File is : "+XSLT_File);					        	 
			          }
			          else
			          {
			        	  select_label.setText("Select XSLT File only....");
			          }
			    }
			}
		});
		XSLT_btnBrowse.setBounds(471, 21, 82, 29);
		panel.add(XSLT_btnBrowse);
		
		JButton Start_btnStart = new JButton("Start");
		Start_btnStart.setBounds(273, 333, 78, 29);
		Start_btnStart.setForeground(Color.WHITE);
		Start_btnStart.setBackground(Color.GRAY);
		Start_btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
			  textField_3.setText("XML Transformation Processing.......");
			  if((XSLT_File.toLowerCase().endsWith(".xsl")||XSLT_File.toLowerCase().endsWith(".xslt"))&& INXML_File.toLowerCase().endsWith(".xml"))
			  {
				  XSLTConversion conv=new XSLTConversion();
				  try {
					conv.xslTransformation(XSLT_File,INXML_File,OutXML_File,textField_3);
				} catch (IOException e1) 
				{					
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
			  }
			  else
			  {
				  System.out.println("Please Select File Path ");
				  select_label.setText("Select File for transformation......");
				  textField_3.setText("Select File for transformation......");
			  }
			}
		});
		contentPane.add(Start_btnStart);
		
		JButton close_btnClose = new JButton("Clear");
		close_btnClose.setBounds(371, 333, 78, 29);
		close_btnClose.setBackground(Color.GRAY);
		close_btnClose.setForeground(Color.WHITE);
		close_btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{			
			    textField.setText("");
			    textField_1.setText("");
			    textField_2.setText("");
			    textField_3.setText("");
			    XSLT_File="";
			    INXML_File="";
			    OutXML_File="";
			    select_label.setText("");
			}
		});
		contentPane.add(close_btnClose);
		
		JLabel lblXmlTransformation = new JLabel("XSLT Transformation");
		lblXmlTransformation.setBounds(206, 20, 214, 24);
		lblXmlTransformation.setFont(new Font("Arial", Font.BOLD, 20));
		contentPane.add(lblXmlTransformation);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(12, 8, 170, 37);
		lblNewLabel_1.setIcon(new ImageIcon(GUI.class.getResource("/com/logo/logo.png")));
		contentPane.add(lblNewLabel_1);
		
		JButton button = new JButton("Close");
		button.setBounds(466, 333, 78, 29);
		button.setForeground(Color.WHITE);
		button.setBackground(Color.GRAY);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(03);
			}
		});
		contentPane.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(10, 382, 561, 143);
		contentPane.add(scrollPane);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setFont(new Font("Arial", Font.BOLD, 12));
		textField_3.setHorizontalAlignment(SwingConstants.LEFT);
		scrollPane.setViewportView(textField_3);
		//textField_3.setColumns(10);
		scrollPane.setViewportView(textField_3);
		
		
		
	
	}
}

