package com.zhao.deep.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.zhao.deep.bean.Order;
import com.zhao.deep.bean.OrderItem;
import com.zhao.deep.common.ServerResponse;

public interface IOrderService {

	 List<OrderItem> listPayedOrder();
	 
	 ServerResponse PayOrder(Integer userId, int shippingId,int status);

	 List<Order> listOrder();

	/*void exportOrder();*/

	void exportOrder(HttpServletResponse response) throws Exception;

	//新增客户端请求
	List<Order> listMyOrderById(Integer id);

	List<OrderItem> custPayedOrder(Integer id);
}
