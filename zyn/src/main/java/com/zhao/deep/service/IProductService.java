package com.zhao.deep.service;

import java.util.List;

import com.zhao.deep.bean.Product;
import com.zhao.deep.common.ServerResponse;

import vo.productUserVo;


public interface IProductService {

	ServerResponse<String> saveOrUpdateProduct(Product product);
	
	//public List<Product> getAll();

	Object deleteProductById(int id);

	Object selectProductById(int id);

	List<Product> selectByMists(String subtitle,String username);

	List<Product> selectProByCid(Integer cid);

	List<productUserVo> getProductWithUser();

	List<Product> getAll();

	
	List<Product> getProductAll();

	List<Product> getProductByUId(Integer id);

	

}
