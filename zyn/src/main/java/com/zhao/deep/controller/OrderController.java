package com.zhao.deep.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.fabric.xmlrpc.base.Array;
import com.mysql.fabric.xmlrpc.base.Value;
import com.zhao.deep.bean.Order;
import com.zhao.deep.bean.OrderItem;
import com.zhao.deep.bean.Shipping;
import com.zhao.deep.bean.User;
import com.zhao.deep.common.Const;
import com.zhao.deep.common.ResponseCode;
import com.zhao.deep.common.ServerResponse;
import com.zhao.deep.service.ICartService;
import com.zhao.deep.service.IOrderService;
import com.zhao.deep.service.IShippingService;

import vo.CartProductVo;
import vo.CartVo;

@Controller
public class OrderController {
	
	@Autowired
	private IShippingService iShippingService;
	
	@Autowired
	private ICartService iCartService;
	
	@Autowired
	private IOrderService iOrderService;
	
	@RequestMapping("user/shipping/order/addToOrder")
	public String toViewOrder(HttpSession session,Model model,int shippingId){
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			model.addAttribute("message", "用户未登陆，无法获取当前用户信息");
			 return "error";
		}
		
		//查购物车中被选中的商品
		 ServerResponse<CartVo> CartMessage = iCartService.list(user.getId());
	     List<CartProductVo> listCartProductVo=CartMessage.getData().getCartProductVoList();
	     model.addAttribute("cartMsg", listCartProductVo);
	    
	     model.addAttribute("cartMsg2", CartMessage);
		
		//查被选中的收货地址
		ServerResponse<Shipping> select = iShippingService.select(user.getId(), shippingId);
		Shipping selectedShipping = select.getData();
		model.addAttribute("selectedShipping", selectedShipping);
		return "order/orderToPay";
		
	}
	
	@RequestMapping("user/shipping/order/order")
	@ResponseBody
	public ServerResponse order(HttpSession session,Model model,int shippingId,Integer status){
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
		}
		System.out.println(".................."+status);
		 ServerResponse payOrder = iOrderService.PayOrder(user.getId(), shippingId,status);
		return payOrder;
		
	}
	
	/*管理员查询所有订单*/
	@RequestMapping("user/order/listPayedOrder")
	public String listPayedOrder(HttpSession session,Model model){
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			model.addAttribute("message", "用户未登陆，无法获取当前用户信息");
			 return "error";
		}
		List<OrderItem> listPayedOrder = iOrderService.listPayedOrder();
		model.addAttribute("listPayedOrder", listPayedOrder);
		return "order/payedOrder";
	}
	
	@RequestMapping("user/order/listOrder")
	public String listOrder(HttpSession session,Model model){
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			model.addAttribute("message", "用户未登陆，无法获取当前用户信息");
			 return "error";
		}
		List<Order> listOrder = iOrderService.listOrder();
		model.addAttribute("listOrder", listOrder);
		return "order/Order";
	}
	
	
	@RequestMapping("user/order/exportOrder")
	public String exportOrder(HttpSession session,HttpServletResponse response,Model model) throws Exception{
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			model.addAttribute("message", "用户未登陆，无法获取当前用户信息");
			 return "error";
		}
		iOrderService.exportOrder(response);
		return null;
	}
	
	//新增客户端请求
	@RequestMapping("user/order/getMyOrder")
	@ResponseBody
	public  Map<Object, Object> myOrder(HttpSession session,Model model){
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		Map<Object, Object> data = new HashMap<>();
		if(user==null){
			data.put("errorMSG","用户未登陆，无法获取当前用户信息");
			return data;
		}
		List<Order> orderMsg =  iOrderService.listMyOrderById(user.getId());
		data.put("data", orderMsg);
		return data;
	}
	
	@RequestMapping("user/order/custPayedOrder")
	@ResponseBody
	public Map<Object, Object> custPayedOrder(HttpSession session,Model model){
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		Map<Object, Object> data = new HashMap<>();
		if(user==null){
			data.put("errorMSG","用户未登陆，无法获取当前用户信息");
			return data;
		}
		data.put("data", iOrderService.custPayedOrder(user.getId()));
		return data;
	}

	

}
