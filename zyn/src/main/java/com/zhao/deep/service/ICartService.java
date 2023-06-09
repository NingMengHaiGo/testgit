package com.zhao.deep.service;

import com.zhao.deep.common.ServerResponse;

import vo.CartVo;

public interface ICartService {
	
	 ServerResponse<CartVo> add(Integer userId,Integer productId,Integer count);
	 
	 ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);

	 ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);

	 ServerResponse<CartVo> list (Integer userId);
	 
	 ServerResponse<CartVo> selectOrUnSelect (Integer userId,Integer productId,Integer checked);
	 
	 ServerResponse<Integer> getCartProductCount(Integer userId);
}
