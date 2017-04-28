package com.xuefei.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.xuefei.gui.PrivateGui;


public class SendMessage {
	public static String path=null;
	
	//发送登录验证消息
	public static void sendLogin(String name,String password,Socket sk){
		String message=name+","+password;
		try {
			OutputStream ops = sk.getOutputStream();
			ops.write(message.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//发送群聊信息
	public static void sendAllMessage(String message,Socket sk){
		try {
			OutputStream ops = sk.getOutputStream();
			ops.write((6+message).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//发送私聊信息
	public static void sendPriMessage(String Toname,String message,Socket sk){
		try {
			OutputStream ops = sk.getOutputStream();
			ops.write((7+Toname+","+message).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//发送文件前方法
	public static void sendFileHeader(String Toname,Socket sk){
		try {
			OutputStream ops = sk.getOutputStream();
			ops.write((8+Toname).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//连接接收方ServiceSocket
	public static void connectSocket(String ip,int port){
		Socket sk=null;
			try {						
					sk=new Socket(ip,port);
					sendFile(sk);
					PrivateGui.setMessage("系统消息:"+"\n"+"    发送文件成功"+"\n");
					path="";
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(sk!=null)
						sk.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
	}	
	//发送文件
	public static void sendFile(Socket sk){
		OutputStream ops=null;
		InputStream ips=null;
		try {
			ops = sk.getOutputStream();
			ips=new FileInputStream(path);
			int len=0;
			byte[] b=new byte[1024];
			while((len=ips.read(b))!=-1){
				ops.write(b, 0, len);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ips.close();
				ops.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
	
}
