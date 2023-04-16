package com.zhao.deep.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.zhao.deep.bean.Shipping;
import com.zhao.deep.common.ServerResponse;
import com.zhao.deep.dao.ShippingMapper;
import com.zhao.deep.service.IShippingService;

@Service("iShippingService")
public class ShippingServiceImpl implements IShippingService{
	
	@Autowired
	private ShippingMapper shippingMapper;

	public ServerResponse add(Integer userId,Shipping shipping){
		shipping.setUserId(userId);
		int rowCount = shippingMapper.insert(shipping);
		if(rowCount != 0 ){
			Map result = Maps.newHashMap();
			
			result.put("shippingId", shipping.getId());
			return ServerResponse.createBySuccess("创建地址成功", result);
		}
		return ServerResponse.createByErrorMessage("创建地址失败");
		
	}
	
	public ServerResponse<String> delete(Integer userId,Integer shippingId){
		int resultCount = shippingMapper.deleteByShipingIdUserId(userId, shippingId);
		if(resultCount!=0){
			return ServerResponse.createBySuccessMessage("删除地址成功");
		}
		return ServerResponse.createByErrorMessage("删除地址失败");
		
	} 
	
	public ServerResponse<String> update(Integer userId,Shipping shipping){
		shipping.setUserId(userId);
		int rowCount = shippingMapper.updateByShipping(shipping);
		if(rowCount > 0 ){
			
			return ServerResponse.createBySuccess("创建地址成功");
		}
		return ServerResponse.createByErrorMessage("创建地址失败");
		
	}
	
	public ServerResponse<Shipping> select(Integer userId,Integer shippingId){
		Shipping shipping = shippingMapper.selectByuserIdShippingId(userId, shippingId);
		if(shipping==null){
			return ServerResponse.createByErrorMessage("查询地址错误");
		}
		return ServerResponse.createBySuccess("查询地址成功",shipping);
		
	} 
	
	public ServerResponse<PageInfo> list(Integer userId,int pageNum,int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
		PageInfo pageInfo = new PageInfo(shippingList);
		return ServerResponse.createBySuccess(pageInfo);
		
	}

	//新增客户端查询收货地址
	@Override
	public List<Shipping> getMyshipping(Integer id) {
		// TODO Auto-generated method stub
		return shippingMapper.selectByUserId(id);
	}
}
