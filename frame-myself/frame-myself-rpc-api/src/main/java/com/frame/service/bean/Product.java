package com.frame.service.bean;

import java.io.Serializable;

/**
 * 订单系统调用商品系统的时候，需要商品系统返回一个商品，必然涉及到发生网络传输，这就涉及对象的序列化和反序列化了
 * @author youben
 *
 */
public class Product implements Serializable{

	private Long id;
	
	private String name;
	
	private double price;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
	
	
}
