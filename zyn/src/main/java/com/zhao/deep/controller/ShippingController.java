package com.zhao.deep.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zhao.deep.bean.Shipping;
import com.zhao.deep.bean.User;
import com.zhao.deep.common.Const;
import com.zhao.deep.common.ResponseCode;
import com.zhao.deep.common.ServerResponse;
import com.zhao.deep.service.IShippingService;
import com.zhao.deep.service.IUserService;

@Controller
@RequestMapping("/user/shipping/")
public class ShippingController {
	
	@Autowired
	private IShippingService iShippingService;
	
	@Autowired
	private IUserService iUserService;

	private ServerResponse<PageInfo> list;

	@RequestMapping(value = "add",method = RequestMethod.POST)
	@ResponseBody
	public ServerResponse add(HttpSession session,Shipping shipping){
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
		}
		return iShippingService.add(user.getId(), shipping);
		
	}
	
	
	@RequestMapping("delete")
	@ResponseBody
	public ServerResponse delete(HttpSession session,Integer shippingId){
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
		}
		return iShippingService.delete(user.getId(), shippingId);
		
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ServerResponse update(HttpSession session,Shipping shipping){
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
		}
		return iShippingService.update(user.getId(), shipping);
		
	}
	
	@RequestMapping("select")
	@ResponseBody
	public ServerResponse<Shipping> select(HttpSession session,Integer shippingId){
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
		}
		return iShippingService.select(user.getId(), shippingId);
		
	}
	
	
	@RequestMapping("list")
	public String list(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
										 @RequestParam(value="pageSize",defaultValue="5")int pageSize,
										 HttpSession session,Model model ,@RequestParam(value="id",defaultValue="0") int id){
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		if(user==null){
			model.addAttribute("message", "用户未登陆，无法获取当前用户信息");
			 return "error";
		}
		 ServerResponse<PageInfo> response = iShippingService.list(user.getId(), pageNum, pageSize);
		 PageInfo ShippingList =response.getData();
		 
		
		 model.addAttribute("shippingList", ShippingList);
		 if(id==1){
			 return "shipping/select_shipping";
		 }
		 return "shipping/list_shipping";
	}
	
	//添加收货地址
	@RequestMapping("to_edit_shipping")
	public String to_edit_shipping(HttpSession session,Model model){
		User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
        	model.addAttribute("message", "用户未登陆，无法获取当前用户信息");
			 return "error";
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
        	return "shipping/edit_shipping";
        }else{
        	model.addAttribute("message", "无权限操作,需要管理员权限");
            return "error";
    	}
		
	}
	
	//新增客户端查询收货地址
	@RequestMapping("listMyshipping")
	@ResponseBody
	public Map<Object, Object> getMyshipping(HttpSession session){
		User user =  (User) session.getAttribute(Const.CURRENT_USER);
		Map<Object, Object> data = new HashMap<>();
		if(user==null){
			data.put("errorMSG","用户未登陆，无法获取当前用户信息");
			return data;
		}
		List<Shipping> shippingList = iShippingService.getMyshipping(user.getId());
		data.put("data", shippingList);
		return data;
	}
	
	
	
}
