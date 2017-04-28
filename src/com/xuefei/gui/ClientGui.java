package com.xuefei.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import com.xuefei.entity.userSocket;
import com.xuefei.util.SendMessage;

public class ClientGui {
	//�����������
	private static JFrame frame;
	
	private JPanel northPan;
	private JPanel southPan;
	private JScrollPane rightPanel;  
    private JScrollPane leftPanel; 
    private JScrollPane Bullent; 
    private JSplitPane centerSplit;
    
    private static JList<Object> userList; 
    private static JTextArea contentArea;
    private JTextArea txt_message; 
    private static DefaultListModel<Object> listModel;
    private static JTextField txt_bullet;
    
    private JButton btn_send;
    private JButton btn_clean;
    private JButton btn_close_yes;
    private JButton btn_close_no;
    private JButton btn_private;
    
    private JDialog dia_close;
    private JLabel lab_close;
    
 
    
    private static int count=0;
    //�û�socket����
	public ClientGui(){
		init();
	}
	
	
	public static void setMessage(String message){
		contentArea.append(message);
		contentArea.setCaretPosition( contentArea.getDocument().getLength());//�Զ���������ǰ��
	}
	public static void setID(String message){
		listModel.add(count++, message);		
		userList.setModel(listModel);
	}
	
	//�ж��û��Ƿ��Ѿ����б�
	public static boolean chheckID(String message){
		boolean flag=false;
		ListModel<Object> model = userList.getModel();
		for(int i = 0; i < model.getSize(); i++) {
			 if(model.getElementAt(i).equals(message)) {
				 flag=true;
				 break;//����
			 } else {
				 flag=false;
			 }
		}
		return flag;
	}
	
	//�Ƴ�ָ�����Ƶ�Ԫ��
	public static void removeID(String name){
		ListModel<Object> model = userList.getModel();
		for(int i = 0; i < model.getSize(); i++) {
			 if(model.getElementAt(i).equals(name)) {
				 listModel.remove(i);
				 count--;//�Ƴ�һλcount��Ҫ��һ������ᱨ����Խ���쳣
			 }
		}
	}
	
	//����������Ϣ
	public static void setBullet(String message){
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		String date=sdf.format(d);
		txt_bullet.setText(date+" "+message);
	}

	//��ʼ���������
	public void init(){
		
		frame=new JFrame("������");
		frame.setBounds(500, 200, 900, 600);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//ȡ��Ĭ�Ϲرչ���
		
		contentArea = new JTextArea();  
        contentArea.setEditable(false); 
        contentArea.setForeground(Color.black);
        contentArea.setFont(new Font("TimesRoman", Font.PLAIN,19));
		
		northPan=new JPanel(new BorderLayout());
		northPan.setBorder(new TitledBorder("��������"));
		txt_bullet= new JTextField(); 
		txt_bullet.setBackground(Color.white);
		txt_bullet.setEditable(false);		
		txt_bullet.setFont(new Font("TimesRoman", Font.PLAIN,20));
		Bullent=new JScrollPane(txt_bullet); 

		btn_private=new JButton("��˽��");
		btn_private.setPreferredSize(new Dimension(88,32));
		northPan.add(Bullent,"Center");
		northPan.add(btn_private,"East");
		
		southPan = new JPanel(new BorderLayout());  
        southPan.setBorder(new TitledBorder("д��Ϣ"));
        txt_message = new JTextArea(2,47); 
        txt_message.setLineWrap(true);//�����Զ�����
        
        //ȡ���س����й���
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        txt_message.getInputMap().put(enter, "none");
        
        txt_message.setFont(new Font("TimesRoman", Font.PLAIN,19));
        southPan.add(txt_message, "West");  
        btn_send = new JButton("����");
        btn_clean = new JButton("���"); 
        southPan.add(btn_send, "Center");  
        southPan.add(btn_clean,"East");
		
        listModel = new DefaultListModel<Object>();  
        userList = new JList<Object>(listModel); 
        userList.setFont(new Font("TimesRoman", Font.PLAIN,19));
		leftPanel=new JScrollPane(userList); 		 
		leftPanel.setBorder(new TitledBorder("�����û�"));  
		
		rightPanel = new JScrollPane(contentArea);  
        rightPanel.setBorder(new TitledBorder("��Ϣ��ʾ��")); 
        
        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,rightPanel);  
        centerSplit.setDividerLocation(160);
		
        //�رմ���
        dia_close=new JDialog(frame,"�뿪",true);
        dia_close.setBounds(700, 400, 400, 210);
        dia_close.setLayout(new FlowLayout());
        lab_close=new JLabel("              ȷ��Ҫ�뿪����������?");
        lab_close.setPreferredSize(new Dimension(350,100));
        lab_close.setFont(new Font("TimesRoman", Font.PLAIN,19));
        btn_close_yes=new JButton("ȷ��");
        btn_close_no=new JButton("ȡ��");
        dia_close.add(lab_close);
        dia_close.add(btn_close_yes);
        dia_close.add(btn_close_no);
        

                
		
		frame.add(northPan,"North");
		 frame.add(centerSplit, "Center");  
		 frame.add(southPan, "South");  

		
		myEvent();
		frame.setVisible(true);
		
	}
	
	//����¼�����
	public void myEvent(){
		
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dia_close.setVisible(true);
			}
		});
		
		dia_close.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dia_close.setVisible(false);
			}
		});
		
		btn_close_yes.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				count=0;
				System.exit(0);				
			}
		});
		
		btn_close_no.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dia_close.setVisible(false);			
			}
		});
		//���Ͱ�ť
		btn_send.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String message=txt_message.getText();
				if(!"".equals(message.trim()) ){					
					SendMessage.sendAllMessage(message, userSocket.getuser().getSk());
					txt_message.setText("");	
				}	
			}
		});
		//��հ�ť
		btn_clean.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				txt_message.setText("");	
			}			
		});
		//Ϊд��Ϣ������Ӽ��̼���
		txt_message.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					String message=txt_message.getText();
					if(!"".equals(message.trim()) ){
						SendMessage.sendAllMessage(message, userSocket.getuser().getSk());
						txt_message.setText("");
					}
				}				
			}			
		});
		
		//˽�İ�ť
		btn_private.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				PrivateGui.setShow();
			}			
		});
		
	}	
}
