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
	//定义组件变量
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
    //用户socket对象
	public ClientGui(){
		init();
	}
	
	
	public static void setMessage(String message){
		contentArea.append(message);
		contentArea.setCaretPosition( contentArea.getDocument().getLength());//自动滚动到当前行
	}
	public static void setID(String message){
		listModel.add(count++, message);		
		userList.setModel(listModel);
	}
	
	//判断用户是否已经在列表
	public static boolean chheckID(String message){
		boolean flag=false;
		ListModel<Object> model = userList.getModel();
		for(int i = 0; i < model.getSize(); i++) {
			 if(model.getElementAt(i).equals(message)) {
				 flag=true;
				 break;//存在
			 } else {
				 flag=false;
			 }
		}
		return flag;
	}
	
	//移除指定名称的元素
	public static void removeID(String name){
		ListModel<Object> model = userList.getModel();
		for(int i = 0; i < model.getSize(); i++) {
			 if(model.getElementAt(i).equals(name)) {
				 listModel.remove(i);
				 count--;//移除一位count需要减一，否则会报数组越界异常
			 }
		}
	}
	
	//设置上线消息
	public static void setBullet(String message){
		Date d=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		String date=sdf.format(d);
		txt_bullet.setText(date+" "+message);
	}

	//初始化组件函数
	public void init(){
		
		frame=new JFrame("聊天室");
		frame.setBounds(500, 200, 900, 600);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//取消默认关闭功能
		
		contentArea = new JTextArea();  
        contentArea.setEditable(false); 
        contentArea.setForeground(Color.black);
        contentArea.setFont(new Font("TimesRoman", Font.PLAIN,19));
		
		northPan=new JPanel(new BorderLayout());
		northPan.setBorder(new TitledBorder("基本功能"));
		txt_bullet= new JTextField(); 
		txt_bullet.setBackground(Color.white);
		txt_bullet.setEditable(false);		
		txt_bullet.setFont(new Font("TimesRoman", Font.PLAIN,20));
		Bullent=new JScrollPane(txt_bullet); 

		btn_private=new JButton("打开私聊");
		btn_private.setPreferredSize(new Dimension(88,32));
		northPan.add(Bullent,"Center");
		northPan.add(btn_private,"East");
		
		southPan = new JPanel(new BorderLayout());  
        southPan.setBorder(new TitledBorder("写消息"));
        txt_message = new JTextArea(2,47); 
        txt_message.setLineWrap(true);//设置自动换行
        
        //取消回车换行功能
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        txt_message.getInputMap().put(enter, "none");
        
        txt_message.setFont(new Font("TimesRoman", Font.PLAIN,19));
        southPan.add(txt_message, "West");  
        btn_send = new JButton("发送");
        btn_clean = new JButton("清空"); 
        southPan.add(btn_send, "Center");  
        southPan.add(btn_clean,"East");
		
        listModel = new DefaultListModel<Object>();  
        userList = new JList<Object>(listModel); 
        userList.setFont(new Font("TimesRoman", Font.PLAIN,19));
		leftPanel=new JScrollPane(userList); 		 
		leftPanel.setBorder(new TitledBorder("在线用户"));  
		
		rightPanel = new JScrollPane(contentArea);  
        rightPanel.setBorder(new TitledBorder("消息显示区")); 
        
        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,rightPanel);  
        centerSplit.setDividerLocation(160);
		
        //关闭窗口
        dia_close=new JDialog(frame,"离开",true);
        dia_close.setBounds(700, 400, 400, 210);
        dia_close.setLayout(new FlowLayout());
        lab_close=new JLabel("              确定要离开本聊天室吗?");
        lab_close.setPreferredSize(new Dimension(350,100));
        lab_close.setFont(new Font("TimesRoman", Font.PLAIN,19));
        btn_close_yes=new JButton("确定");
        btn_close_no=new JButton("取消");
        dia_close.add(lab_close);
        dia_close.add(btn_close_yes);
        dia_close.add(btn_close_no);
        

                
		
		frame.add(northPan,"North");
		 frame.add(centerSplit, "Center");  
		 frame.add(southPan, "South");  

		
		myEvent();
		frame.setVisible(true);
		
	}
	
	//添加事件监听
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
		//发送按钮
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
		//清空按钮
		btn_clean.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				txt_message.setText("");	
			}			
		});
		//为写信息区域添加键盘监听
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
		
		//私聊按钮
		btn_private.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				PrivateGui.setShow();
			}			
		});
		
	}	
}
