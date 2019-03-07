package com.frame.service;

import com.frame.service.bean.IProductService;
import com.frame.service.bean.Product;

public class ProductService implements IProductService {

	public Product queryBtId(Long id) {
		Product product = new Product();
		product.setId(id);
		product.setName("youben");
		product.setPrice(1.0);
		return product;
	}

}
