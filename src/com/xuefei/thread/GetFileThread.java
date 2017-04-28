package com.xuefei.thread;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.xuefei.gui.PrivateGui;

public class GetFileThread implements Runnable
{	
	String name;
	String type;
	GetFileThread(String name,String type){
		this.name=name;
		this.type=type;
	}
	@Override
	public void run() {
		ServerSocket ss=null;
		OutputStream ops=null;
		try {
			ss=new ServerSocket(20000);
			Socket s=ss.accept();
			PrivateGui.setMessage("ϵͳ��Ϣ:"+"\n"+"    "+name+"���㷢����"+type+"�ļ�"+"\n");
			int len=0;
			byte[] b=new byte[1024];
			String path = PrivateGui.saveFile();
			if(path.contains("null")){
				PrivateGui.setMessage("ϵͳ��Ϣ:"+"\n"+"    ȡ�������ļ�"+"\n");
				ss.close();
				return;
			}				
			ops=new FileOutputStream(path);
			InputStream ips = s.getInputStream();
			while((len=ips.read(b))!=-1){
				ops.write(b, 0, len);
			}
			PrivateGui.setMessage("ϵͳ��Ϣ:"+"\n"+"    �յ���"+name+"���͵��ļ�"+"\n");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		} finally {
			try {
				if(ops!=null)
					ops.close();
				if(ss!=null){
					ss.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}

}
