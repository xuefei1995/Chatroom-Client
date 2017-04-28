package com.xuefei.gui;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.xuefei.entity.userSocket;
import com.xuefei.util.SendMessage;

public class LoginGui extends JFrame {

	private static final long serialVersionUID = 1L;
	// �û���
    private JTextField username;
    // ����
    private JPasswordField password;
    // С����
    private JLabel jl1;
    private JLabel label_image;
    private JLabel label_register;
    private JLabel label_findpw; 
    // С��ť
    private JButton but_login;
    // ��ѡ��
    private JCheckBox jc1;
    private JCheckBox jc2; 
    // �б��
    private JComboBox<String> jcb;

    //�û���������󵯴�
    private JDialog dia_error;
    private JLabel lab_error;
    private JButton btn_error_yes;
    
    //�û��Ѿ����ڵ���
    private JDialog dia_exist;
    private JLabel lab_exist;
    private JButton btn_exist_yes;
    
    private Properties pro;
    private InputStream in;
    private OutputStream out;
    private String path;
    public void setWindow(){
    	this.setVisible(false);
    }
    
    public LoginGui() {
        // ���ô��ڱ���
        this.setTitle("�����ҵ�½");
        // ���������ʼ��     
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // ���ò��ַ�ʽΪ���Զ�λ
        this.setLayout(null);         
        this.setBounds(0, 0, 450, 300);
        // ���ô���ı���ͼ��
        String pathimage1 =this.getClass().getResource("/images/001.jpg").getPath();
        Image image = new ImageIcon(pathimage1).getImage();
        this.setIconImage(image);        
        // �����С���ܸı�
        this.setResizable(false);        
        // ������ʾ
        this.setLocationRelativeTo(null); 
        
        //��ȡ����������Ϣ�ļ�
        path =this.getClass().getResource("/userMessage.properties").getPath(); 
        try {
        	in=new FileInputStream(path);			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}        
		pro = new Properties();
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean flag=false;
		String checkRemember=pro.getProperty("checkRemember");
		if(checkRemember.equals("true")){
			flag=true;
		}
		String name=pro.getProperty("name");
		String password=pro.getProperty("password");
		init(name,password,flag);
        
        // ����ɼ�
        this.setVisible(true);
    }
 
    public void init(String myname,String mypassword,boolean flag) {
        // ����һ������
        Container con = this.getContentPane();
        jl1 = new JLabel();
        jl1.setBounds(0, 0, 450, 300);
         
        // QQ��¼ͷ���趨
        label_image = new JLabel();
        String pathimage2 =this.getClass().getResource("/images/qq.jpg").getPath();
        Image image2 = new ImageIcon(pathimage2).getImage();
        label_image.setIcon(new ImageIcon(image2));
        label_image.setBounds(40, 60, 85, 85);
 
        // �û������¼�����
        username = new JTextField();
        username.setFont(new Font("TimesRoman", Font.PLAIN,14));
        username.setText(myname);
        username.setBounds(145, 60, 180, 30);
        // �û������¼������Աߵ�����
        label_register = new JLabel("ע���˺�");
        label_register.setFont(new Font("TimesRoman", Font.BOLD,14));
        label_register.setBounds(335, 60, 100, 30);
 
        // ���������
        password = new JPasswordField(mypassword);
        password.setFont(new Font("TimesRoman", Font.PLAIN,14));
        password.setBounds(145, 100, 180, 30);
        // ����������Աߵ�����
        label_findpw = new JLabel("�һ�����");
        label_findpw.setFont(new Font("TimesRoman", Font.BOLD,14));
        label_findpw.setBounds(335, 100, 100, 30);
 
        // ������·�����
        jc1 = new JCheckBox("��ס����",flag);
        jc1.setBounds(141, 140, 92, 20);
        jc2 = new JCheckBox("�Զ���¼");
        jc2.setBounds(250, 140, 92, 20);
 
        // �û���¼״̬ѡ��
        jcb = new JComboBox<String>();
        jcb.addItem("����");
        jcb.addItem("����");
        jcb.setSize(50, 40);
        jcb.setBounds(50, 165, 55, 20);
 
        // ��ť�趨
        but_login = new JButton("��¼");
        but_login.setBounds(145, 180, 65, 20);
        but_login.setSize(180, 28);
        
        //�û���������󵯴�
        dia_error=new JDialog(this,"��ʾ",true);
        dia_error.setBounds(800, 420, 300, 180);
        dia_error.setLayout(new FlowLayout());
        lab_error=new JLabel("                   �û������������");
        lab_error.setPreferredSize(new Dimension(350,55));
        lab_error.setFont(new Font("TimesRoman", Font.PLAIN,19));
        btn_error_yes=new JButton("ȷ��");
        dia_error.add(lab_error);
        dia_error.add(btn_error_yes);
        
        //�û��Ѿ����ߵ���
        dia_exist=new JDialog(this,"��ʾ",true);
        dia_exist.setBounds(800, 420, 300, 180);
        dia_exist.setLayout(new FlowLayout());
        lab_exist=new JLabel("                     ���û��Ѿ�����");
        lab_exist.setPreferredSize(new Dimension(350,55));
        lab_exist.setFont(new Font("TimesRoman", Font.PLAIN,19));
        btn_exist_yes=new JButton("ȷ��");
        dia_exist.add(lab_exist);
        dia_exist.add(btn_exist_yes);
        
 
        // �������������װ��
        jl1.add(label_image);
        jl1.add(label_register);
        jl1.add(label_findpw);
        jl1.add(jc1);
        jl1.add(jc2);
        jl1.add(jcb);
        jl1.add(but_login);
        con.add(jl1);
        con.add(username);
        con.add(password);
        
        myEvent();
 
    }
    
    //������ʾ�û���������󵯴�
    public void setDialog(){
    	dia_error.setVisible(true);
    }
    
    //������ʾ�û��Ѿ����ߵ���
    public void setExist(){
    	dia_exist.setVisible(true);
    }
    
	private void myEvent() {

		but_login.addActionListener(new ActionListener() {       
            @Override
            public void actionPerformed(ActionEvent e) {
            	String myname=username.getText();
            	char[] pw = password.getPassword();            	
            	String mypassword=String.valueOf(pw);
            	//���������
            	try {
					out=new FileOutputStream(path);
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
            	//�ж��û��Ƿ�ѡ���˼�ס����
            	if(jc1.isSelected()){
            		pro.setProperty("checkRemember", "true");
            		pro.setProperty("name",myname);
            		pro.setProperty("password", mypassword);
            		try {
						pro.store(out, "");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
            	} else{
            		pro.setProperty("checkRemember", "false");
            		pro.setProperty("name","");
            		pro.setProperty("password", "");
            		try {
						pro.store(out, "");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
            	}           	           	
            	if("".equals(myname.trim()) && "".equals(mypassword.trim())){
            		System.out.println("�˺�����Ϊ��");
            	} else {           		           		          	
                	Socket sk=userSocket.getuser().getSk();
                	SendMessage.sendLogin(myname, mypassword, sk);
            	}           	
            }
        });
		
        //ע��JLabel���¼�
        label_register.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e){
        		Desktop desktop = Desktop.getDesktop();
				try {
					desktop.browse(new URI("http://10.170.36.23/RegisterServer/Adduser.jsp"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}	
        	}
        	
        	public void mouseEntered(MouseEvent e){
        		label_register.setForeground(Color.red);
        	}
        	public void mouseExited(MouseEvent e){
        		label_register.setForeground(Color.black);
        	}  
        	
        });
        
        //��ȷ�ϴ���ť
        btn_error_yes.addActionListener(new ActionListener() {       
            @Override
            public void actionPerformed(ActionEvent e) {
            	dia_error.setVisible(false);
            }
        });  
        
        //��ȷ�����߰�ť
        btn_exist_yes.addActionListener(new ActionListener() {       
            @Override
            public void actionPerformed(ActionEvent e) {
            	dia_exist.setVisible(false);
            }
        });  
	}
}
 