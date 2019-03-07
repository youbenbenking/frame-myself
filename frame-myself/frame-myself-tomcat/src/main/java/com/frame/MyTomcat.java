package com.frame;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
/**
 * Tomcat 的处理流程，即把 URL 对应处理的 Servlet 关系形成，解析 HTTP 协议，封装请求/响应对象，利用反射实例化具体的 Servlet 进行处理
 * @author youben
 *
 */
public class MyTomcat {
	
	private int port = 8080;
	
	private Map<String, String> urlServletMap = new HashMap<String, String>();
	
	public MyTomcat(int port) {
		this.port = port;
	}
	
	
	public void start() {
		
		//初始化URL与对应servlet的关系
		initServletMappling();
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("tomcat is start ...");
			
			while (true) {
				Socket socket = serverSocket.accept();
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				
				MyRequest myRequest = new MyRequest(inputStream);
				MyResponse myResponse = new MyResponse(outputStream);
				
				//请求分发
				dispatch(myRequest,myResponse);
			}
		} catch (Exception e) {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e2) {
					// TODO: handle exception
				}
			}
		}
	}
	
	public void initServletMappling() {
		for (ServletMapping servletMapping : ServletMappingConfig.servletMappingList) {
			urlServletMap.put(servletMapping.getUrl(), servletMapping.getClazz());
		}
	}


	public void dispatch(MyRequest myRequest,MyResponse myResponse) {
		String clazz = urlServletMap.get(myRequest.getUrl());
		
		try {
			Class<MyServlet> mySevletClass = (Class<MyServlet>)Class.forName(clazz);
			MyServlet myServlet = mySevletClass.newInstance();
			
			myServlet.service(myRequest, myResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new MyTomcat(8080).start();
	}
}
