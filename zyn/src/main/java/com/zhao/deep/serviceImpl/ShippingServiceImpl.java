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
			return ServerResponse.createBySuccess("������ַ�ɹ�", result);
		}
		return ServerResponse.createByErrorMessage("������ַʧ��");
		
	}
	
	public ServerResponse<String> delete(Integer userId,Integer shippingId){
		int resultCount = shippingMapper.deleteByShipingIdUserId(userId, shippingId);
		if(resultCount!=0){
			return ServerResponse.createBySuccessMessage("ɾ����ַ�ɹ�");
		}
		return ServerResponse.createByErrorMessage("ɾ����ַʧ��");
		
	} 
	
	public ServerResponse<String> update(Integer userId,Shipping shipping){
		shipping.setUserId(userId);
		int rowCount = shippingMapper.updateByShipping(shipping);
		if(rowCount > 0 ){
			
			return ServerResponse.createBySuccess("������ַ�ɹ�");
		}
		return ServerResponse.createByErrorMessage("������ַʧ��");
		
	}
	
	public ServerResponse<Shipping> select(Integer userId,Integer shippingId){
		Shipping shipping = shippingMapper.selectByuserIdShippingId(userId, shippingId);
		if(shipping==null){
			return ServerResponse.createByErrorMessage("��ѯ��ַ����");
		}
		return ServerResponse.createBySuccess("��ѯ��ַ�ɹ�",shipping);
		
	} 
	
	public ServerResponse<PageInfo> list(Integer userId,int pageNum,int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
		PageInfo pageInfo = new PageInfo(shippingList);
		return ServerResponse.createBySuccess(pageInfo);
		
	}

	//�����ͻ��˲�ѯ�ջ���ַ
	@Override
	public List<Shipping> getMyshipping(Integer id) {
		// TODO Auto-generated method stub
		return shippingMapper.selectByUserId(id);
	}
}
