package com.frame;

import java.io.IOException;
import java.io.OutputStream;

public class MyResponse {

	private OutputStream outputStream;
	
	public  MyResponse(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	/**
	 * 基于HTTP协议的格式进行输出写入。
	 * @param content
	 * @throws IOException
	 */
	public void write(String content) throws IOException {
		
		
		StringBuffer httpResponse  = new StringBuffer();
		httpResponse.append("HTTP/1.1 200 OK\n")
					.append("Content-Type:text/html\n ")
					.append("\r\n")
					.append("<html><body>")
					.append(content)
					.append("</html></body>");
		
		outputStream.write(httpResponse.toString().getBytes());
		outputStream.close();
		
	}
}
