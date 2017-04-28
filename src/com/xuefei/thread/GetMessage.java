package com.xuefei.thread;

import java.io.IOException;
import java.io.InputStream;

import com.xuefei.gui.ClientGui;
import com.xuefei.gui.PrivateGui;
import com.xuefei.util.SendMessage;

public class GetMessage implements Runnable {
	InputStream ips=null;
	boolean mytag=false;
	public GetMessage(InputStream ips){
		this.ips=ips;
	}
	@Override
	public void run() {	
		try {
			byte[] buf = new byte[1024];
			int len = 0;		
			while((len=ips.read(buf))!=-1){
				if(buf[0]==54){
					String message=new String(buf,1,len-1);
					String[] msp = message.split(",");
					message=msp[0]+":"+"\n"+"   "+msp[1]+"\n";
					ClientGui.setMessage(message);	
				}
				if(buf[0]==55){
					String message=new String(buf,1,len-1);
					String[] split = message.split(",");
					String name=split[0];
					String othermessage=split[1];
					String mymessage=name+"对我说:"+"\n"+"    "+othermessage+"\n";
					PrivateGui.setShow();
					PrivateGui.setMessage(mymessage);	
				}
				if(buf[0]==52){
					String message=new String(buf,1,len-1);
					String[] msp = message.split(",");
					for (String name : msp) {
						boolean flag=ClientGui.chheckID(name);
						if(flag==false){
							if(mytag==true)
								ClientGui.setBullet(name+"上线了"+"\n");
							ClientGui.setID(name);
							PrivateGui.addList(name);
						}
					}
					mytag=true;
				}
				if(buf[0]==53){
					String message=new String(buf,1,len-1);
					String[] msp = message.split(",");
					ClientGui.setBullet(msp[0]+"下线了"+"\n");
					ClientGui.removeID(msp[0]);
					PrivateGui.removelist(msp[0]);
				}	
				if(buf[0]==56){
					String message=new String(buf,1,len-1);
					String[] split = message.split(",");
					String name=split[0];
					String type=split[1];
					new Thread(new GetFileThread(name,type)).start();
				}
				if(buf[0]==57){
					String message=new String(buf,1,len-1);
					String[] split = message.split(",");
					String ip=split[0];
					int port=Integer.parseInt(split[1]);
					SendMessage.connectSocket(ip, port);
				}
			}
		} catch (IOException e) {	
			e.printStackTrace();
		} finally {			
			try {
				ips.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
