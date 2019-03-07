package com.frame;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

import com.frame.service.bean.IProductService;
import com.frame.service.bean.Product;

public class Main {

		public static void main(String[] args) {
			IProductService productService  = (IProductService )rpc(IProductService.class);
			
			Product product = productService.queryBtId(100L);
			
			System.out.println(product);
		}
		
		
		
		
		
		
		/**
		 * 第一，我们看到了Proxy.newProxyInstance
		 * 在订单服务调用商品服务的代码中，我们先是通过动态代理返回一个代理的IProductService类型对象，这意味着当代理对象调用queryById方法的时候，会自动调用invoke方法！
		 * 第二，我们看看invoke到底做了些什么？
		 * 本质上就是进行Socket通信,只要我们能确定这个类的全限定类名、确定方法名、确定方法的参数类型，给定方法需要的具体参数，通过反射就能实现。
		 *	商品服务调用后得到的结果，我们序列化写入Socket流中，在订单系统中反序列化得到对象即可。
		 * 
		 * 
		 * 
		 * 
		 * @param clazz
		 * @return
		 */
		public static Object rpc(final Class clazz) {
			return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, new InvocationHandler() {

				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					
					Socket socket = new Socket("127.0.0.1", 8888);
					
					//我们想调用哪个类的哪个方法,并传递给这个方法什么参数
					//注意:我们只知道Product API,并不知道Product API在Product的实现
					String apiClassName = clazz.getName();
					String methodName = method.getName();
					Class[] parameterTypes = method.getParameterTypes();
					
					ObjectOutputStream  objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
					objectOutputStream.writeUTF(apiClassName);
					objectOutputStream.writeUTF(methodName);
					objectOutputStream.writeObject(parameterTypes);
					objectOutputStream.writeObject(args);
					objectOutputStream.flush();
					
					ObjectInputStream  objectInputStream = new ObjectInputStream(socket.getInputStream());
					Object obj = objectInputStream.readObject();
					
					objectInputStream.close();
					objectOutputStream.close();
					
					return obj;
				}
			});
			
		}
	
}
