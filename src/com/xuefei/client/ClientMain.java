package com.xuefei.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.xuefei.entity.userSocket;
import com.xuefei.gui.ClientGui;
import com.xuefei.gui.LoginGui;
import com.xuefei.gui.PrivateGui;
import com.xuefei.thread.GetMessage;

public class ClientMain {

	public static void main(String[] args) {
		try {			
			LoginGui lg = new LoginGui();			
			Socket sk=new Socket("127.0.0.1",10000);
			//��Socket������ʵ�������
			userSocket.getuser().setSk(sk);
			InputStream ips = sk.getInputStream();
			int len=0;
			//�ȴ���������Ӧ
			while((len=ips.read())!=-1){
				if(len==1){					
					lg.setWindow();
					new ClientGui();
					new PrivateGui();
					new Thread(new GetMessage(ips)).start();
					break;
				}
				if(len==2){
					lg.setDialog();//�û������������
				}
				if(len==3){
					lg.setExist();//�û��Ѿ�����
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
