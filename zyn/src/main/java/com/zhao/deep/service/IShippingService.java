package com.zhao.deep.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.zhao.deep.bean.Shipping;
import com.zhao.deep.common.ServerResponse;

public interface IShippingService {

	ServerResponse add(Integer userId,Shipping shipping);
	
	ServerResponse<String> delete(Integer userId,Integer shippingId);
	
	ServerResponse update(Integer userId,Shipping shipping);
	
	ServerResponse<Shipping> select(Integer userId,Integer shippingId);
	
	ServerResponse<PageInfo> list(Integer userId,int pageNum,int pageSize);

	//新增客户端查询收货地址
	List<Shipping> getMyshipping(Integer id);
}
