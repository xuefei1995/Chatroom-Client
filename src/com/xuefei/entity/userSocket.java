package com.xuefei.entity;

import java.net.Socket;

public class userSocket {
	
	//�������ģʽ,����ʽ
	private userSocket(){}
	private static userSocket us=new userSocket();
	public static userSocket getuser(){
		return us;
	}
	
	private  Socket sk;
	public  Socket getSk() {
		return sk;
	}
	public  void setSk(Socket sk) {
		this.sk = sk;
	}
}
