package com.xuefei.gui;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import com.xuefei.entity.userSocket;
import com.xuefei.util.SendMessage;

public class PrivateGui {
	private static JFrame frame;
	private JPanel northPan;
	private JPanel southPan;
	private static JTextArea contentArea;
    private JScrollPane centerPanel;
    private JTextArea txt_send; 
    private static JComboBox<String> jcb;
    
    private JButton btn_filesend;
    private FileDialog file_send;
    private static FileDialog file_get;
    public PrivateGui(){
    	init();
    }
    
    public static void setShow(){
	   frame.setVisible(true);
    } 
    
    public static void setMessage(String message){
       contentArea.append(message);
       contentArea.setCaretPosition( contentArea.getDocument().getLength());//自动滚动到当前行
    }
    
    public static void addList(String name){
    	jcb.addItem(name);
    }
    
    public static void removelist(String name){
    	jcb.removeItem(name);
    }
    
    public static String saveFile(){ 
    	setShow();
    	file_get.setVisible(true);
    	String directory = file_get.getDirectory();
    	String filename=file_get.getFile();
    	String s=directory+filename;
    	return s;
    }
    
    public void init(){
    	
		frame=new JFrame("私聊界面");
		frame.setBounds(670, 200, 450, 560);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
	
		
		northPan=new JPanel(new BorderLayout());
		northPan.setBorder(new TitledBorder("发送给"));
		jcb=new JComboBox<String>();
		jcb.setFont(new Font("TimesRoman", Font.PLAIN,19));
		northPan.add(jcb);
		frame.add(northPan,"North");
		
		contentArea = new JTextArea();  
        contentArea.setEditable(false);  
        contentArea.setFont(new Font("TimesRoman", Font.PLAIN,19));	
		centerPanel = new JScrollPane(contentArea);  
		centerPanel.setBorder(new TitledBorder("消息显示区")); 
		frame.add(centerPanel,"Center");
		
		southPan=new JPanel(new BorderLayout());
		southPan.setBorder(new TitledBorder("写消息"));
		txt_send = new JTextArea(2,23); 
		txt_send.setLineWrap(true);//设置自动换行
        
        //取消回车换行功能
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        txt_send.getInputMap().put(enter, "none");
        
        txt_send.setFont(new Font("TimesRoman", Font.PLAIN,19));
        southPan.add(txt_send, "West");  
        btn_filesend = new JButton("文件");
        southPan.add(btn_filesend, "East"); 
        frame.add(southPan,"South");
        
        //发送文件窗口
        file_send=new FileDialog(frame,"选择发送文件",FileDialog.LOAD);
        //接收文件窗口
        file_get=new FileDialog(frame,"选择保存文件",FileDialog.SAVE);
		
		myEvent();
		frame.setVisible(false);
    }

	private void myEvent() {
		//发送文件
		btn_filesend.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String Toname=(String)jcb.getSelectedItem();
				file_send.setVisible(true);
				String directory = file_send.getDirectory();
				String filename = file_send.getFile();			
				if(directory!=null && filename!=null){
					String path=directory+filename;
					SendMessage.path=path;
					String type=filename.substring(filename.lastIndexOf(".")+1);
					SendMessage.sendFileHeader(Toname+","+type, userSocket.getuser().getSk());		
				} else{
					PrivateGui.setMessage("系统消息:"+"\n"+"    取消了发送文件"+"\n");
				}
			}
		});	
		
		txt_send.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					String Toname=(String)jcb.getSelectedItem();			
					String message=txt_send.getText();
					if("".equals(message.trim()) || "".equals(Toname.trim())){
						System.out.println("为空");
					}else{
						SendMessage.sendPriMessage(Toname, message, userSocket.getuser().getSk());
						String mymessage="对"+Toname+"说"+":"+"\n"+"    "+message+"\n";
						contentArea.append(mymessage);
						txt_send.setText("");
					}
				}				
			}			
		});		
	}    
}
